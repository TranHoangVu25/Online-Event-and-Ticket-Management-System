// Admin Dashboard JavaScript
// Simple JavaScript for basic functionality
function logout() {
    if (confirm('Bạn có chắc muốn đăng xuất?')) {
        showToast('Đang đăng xuất...', 'info');
        setTimeout(() => {
            window.location.href = '/';
        }, 1000);
    }
}

function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const messageEl = toast.querySelector('.toast-message');
    
    messageEl.textContent = message;
    toast.className = `toast ${type} glass`;
    toast.classList.add('show');
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

function refreshDashboard() {
    showToast('Đang làm mới dữ liệu...', 'info');
    // Simulate refresh
    setTimeout(() => {
        showToast('Dashboard đã được làm mới!', 'success');
    }, 1000);
}

// Chart controls
document.addEventListener('DOMContentLoaded', function() {
    const chartBtns = document.querySelectorAll('.chart-btn');
    chartBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            chartBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            showToast(`Đã chuyển sang xem dữ liệu ${this.textContent.toLowerCase()}`, 'info');
        });
    });

});

// Revenue Chart functionality
class RevenueChart {
    constructor() {
        this.chart = null;
        this.init();
    }

    init() {
        this.setupEventListeners();
    }

    setupEventListeners() {
        // Xử lý sự kiện click cho các nút period
        document.querySelectorAll('.chart-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const period = e.target.dataset.period;
                this.setActivePeriod(period);
                this.loadChartData(period);
            });
        });
    }

    setActivePeriod(period) {
        document.querySelectorAll('.chart-btn').forEach(btn => {
            btn.classList.remove('active');
        });
        document.querySelector(`[data-period="${period}"]`).classList.add('active');
    }

    async loadChartData(period) {
        try {
            this.showLoading();

            const response = await fetch(`/admin/revenue-chart?period=${period}`);
            const html = await response.text();

            // Tạo một div tạm thời để parse HTML
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = html;

            // Cập nhật nội dung biểu đồ
            const chartContainer = document.querySelector('.content-card.large .chart-container');
            if (chartContainer) {
                chartContainer.innerHTML = tempDiv.querySelector('.chart-container').innerHTML;

                // Chạy lại script trong fragment
                const scripts = tempDiv.querySelectorAll('script');
                scripts.forEach(script => {
                    eval(script.textContent);
                });
            }

        } catch (error) {
            console.error('Lỗi tải dữ liệu biểu đồ:', error);
            this.showError('Không thể tải dữ liệu biểu đồ');
        }
    }

    showLoading() {
        const placeholder = document.getElementById('chartPlaceholder');
        if (placeholder) {
            placeholder.innerHTML = `
                <div class="loading-spinner">
                    <i class="fas fa-spinner fa-spin"></i>
                    <p>Đang tải dữ liệu...</p>
                </div>
            `;
            placeholder.style.display = 'flex';
        }
    }

    showError(message) {
        const placeholder = document.getElementById('chartPlaceholder');
        if (placeholder) {
            placeholder.innerHTML = `
                <div class="error-message">
                    <i class="fas fa-exclamation-triangle"></i>
                    <p>${message}</p>
                    <button onclick="revenueChart.loadChartData('year')" class="retry-btn">Thử lại</button>
                </div>
            `;
            placeholder.style.display = 'flex';
        }
    }
}

// Hàm toàn cục để render biểu đồ
function renderRevenueChart(revenueData, totalRevenue, period) {
    const ctx = document.getElementById('revenueChart');
    if (!ctx) {
        console.error('Không tìm thấy canvas biểu đồ');
        return;
    }

    const chartCtx = ctx.getContext('2d');

    // Hủy biểu đồ cũ nếu tồn tại
    if (window.revenueChartInstance) {
        window.revenueChartInstance.destroy();
    }

    const labels = revenueData.map(item => `Tháng ${item.month}/${item.year}`);
    const revenueValues = revenueData.map(item => item.revenue);

    window.revenueChartInstance = new Chart(chartCtx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu (VND)',
                data: revenueValues,
                borderColor: '#4f46e5',
                backgroundColor: 'rgba(79, 70, 229, 0.1)',
                borderWidth: 3,
                fill: true,
                tension: 0.4,
                pointBackgroundColor: '#4f46e5',
                pointBorderColor: '#ffffff',
                pointBorderWidth: 2,
                pointRadius: 5,
                pointHoverRadius: 7
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        color: '#374151',
                        font: {
                            size: 14,
                            family: "'Inter', sans-serif"
                        }
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(0, 0, 0, 0.8)',
                    titleColor: '#ffffff',
                    bodyColor: '#ffffff',
                    callbacks: {
                        label: function(context) {
                            return `Doanh thu: ${context.parsed.y.toLocaleString('vi-VN')} VND`;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(255, 255, 255, 0.1)'
                    },
                    ticks: {
                        color: '#e5e7eb',
                        callback: function(value) {
                            return value.toLocaleString('vi-VN') + ' VND';
                        }
                    }
                },
                x: {
                    grid: {
                        color: 'rgba(255, 255, 255, 0.1)'
                    },
                    ticks: {
                        color: '#e5e7eb'
                    }
                }
            },
            interaction: {
                intersect: false,
                mode: 'index'
            },
            animations: {
                tension: {
                    duration: 1000,
                    easing: 'linear'
                }
            }
        }
    });

    // Ẩn placeholder
    hideChartPlaceholder();
}

function showChartError(message) {
    const placeholder = document.getElementById('chartPlaceholder');
    if (placeholder) {
        placeholder.innerHTML = `
            <div class="error-message">
                <i class="fas fa-exclamation-triangle"></i>
                <p>${message}</p>
                <button onclick="revenueChart.loadChartData('year')" class="retry-btn">Thử lại</button>
            </div>
        `;
        placeholder.style.display = 'flex';
    }
}

function hideChartPlaceholder() {
    const placeholder = document.getElementById('chartPlaceholder');
    if (placeholder) {
        placeholder.style.display = 'none';
    }
}

// Khởi tạo biểu đồ khi trang tải xong
document.addEventListener('DOMContentLoaded', function() {
    window.revenueChart = new RevenueChart();
});
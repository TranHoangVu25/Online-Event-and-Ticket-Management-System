// Admin Reports JavaScript
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

function toggleFilters() {
    const filtersSection = document.getElementById('filtersSection');
    filtersSection.classList.toggle('show');
}

function clearFilters() {
    document.getElementById('dateRangeFilter').value = '7days';
    document.getElementById('eventTypeFilter').value = '';
    document.getElementById('locationFilter').value = '';
    document.getElementById('reportTypeFilter').value = 'sales';
    showToast('Đã xóa bộ lọc', 'info');
}

function applyFilters() {
    const dateRange = document.getElementById('dateRangeFilter').value;
    const eventType = document.getElementById('eventTypeFilter').value;
    const location = document.getElementById('locationFilter').value;
    const reportType = document.getElementById('reportTypeFilter').value;
    
    showToast('Đang tạo báo cáo...', 'info');
    setTimeout(() => {
        showToast('Đã tạo báo cáo thành công!', 'success');
    }, 2000);
}

function exportReport() {
    showToast('Đang xuất báo cáo...', 'info');
    setTimeout(() => {
        showToast('Đã xuất báo cáo thành công!', 'success');
    }, 2000);
}

function switchChart(chartType) {
    // Remove active class from all buttons
    const buttons = document.querySelectorAll('.chart-btn');
    buttons.forEach(btn => btn.classList.remove('active'));
    
    // Add active class to clicked button
    event.target.classList.add('active');
    
    showToast(`Đang chuyển sang biểu đồ ${chartType}...`, 'info');
}

function generateReport(reportType) {
    if (reportType === 'custom') {
        document.getElementById('customReportModal').style.display = 'flex';
    } else {
        showToast(`Đang tạo báo cáo ${reportType}...`, 'info');
        setTimeout(() => {
            showToast(`Đã tạo báo cáo ${reportType} thành công!`, 'success');
        }, 2000);
    }
}

function closeCustomReportModal() {
    document.getElementById('customReportModal').style.display = 'none';
}

function createCustomReport() {
    const reportName = document.getElementById('reportName').value;
    if (!reportName) {
        showToast('Vui lòng nhập tên báo cáo', 'error');
        return;
    }
    
    showToast('Đang tạo báo cáo tùy chỉnh...', 'info');
    closeCustomReportModal();
    
    setTimeout(() => {
        showToast('Đã tạo báo cáo tùy chỉnh thành công!', 'success');
    }, 2000);
}

function viewReport(reportType) {
    showToast(`Đang mở báo cáo ${reportType}...`, 'info');
}

function downloadReport(reportType) {
    showToast(`Đang tải xuống báo cáo ${reportType}...`, 'info');
    setTimeout(() => {
        showToast(`Đã tải xuống báo cáo ${reportType} thành công!`, 'success');
    }, 2000);
}

// Active navigation highlighting
document.addEventListener('DOMContentLoaded', function() {
    const currentPage = window.location.pathname.split('/').pop();
    const navItems = document.querySelectorAll('.nav-item');
    
    navItems.forEach(item => {
        const link = item.querySelector('.nav-link');
        if (link.getAttribute('href') === currentPage) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });
});

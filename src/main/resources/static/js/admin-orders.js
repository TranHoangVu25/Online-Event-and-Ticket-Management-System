// Admin Orders JavaScript
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
    document.getElementById('statusFilter').value = '';
    document.getElementById('eventFilter').value = '';
    document.getElementById('dateFilter').value = '';
    document.getElementById('searchFilter').value = '';
    showToast('Đã xóa bộ lọc', 'info');
}

function applyFilters() {
    const status = document.getElementById('statusFilter').value;
    const event = document.getElementById('eventFilter').value;
    const date = document.getElementById('dateFilter').value;
    const search = document.getElementById('searchFilter').value;
    
    showToast('Đang áp dụng bộ lọc...', 'info');
    setTimeout(() => {
        showToast('Đã áp dụng bộ lọc thành công!', 'success');
    }, 1000);
}

function exportOrders() {
    showToast('Đang xuất dữ liệu đơn hàng...', 'info');
    setTimeout(() => {
        showToast('Đã xuất dữ liệu thành công!', 'success');
    }, 2000);
}

function toggleSelectAll() {
    const selectAll = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('.order-checkbox');
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAll.checked;
    });
}

function bulkConfirm() {
    const selectedOrders = document.querySelectorAll('.order-checkbox:checked');
    if (selectedOrders.length > 0) {
        showToast(`Đang xác nhận ${selectedOrders.length} đơn hàng...`, 'info');
        setTimeout(() => {
            showToast(`Đã xác nhận ${selectedOrders.length} đơn hàng thành công!`, 'success');
        }, 1500);
    } else {
        showToast('Vui lòng chọn đơn hàng để xác nhận', 'error');
    }
}

function bulkCancel() {
    const selectedOrders = document.querySelectorAll('.order-checkbox:checked');
    if (selectedOrders.length > 0) {
        if (confirm(`Bạn có chắc muốn hủy ${selectedOrders.length} đơn hàng đã chọn?`)) {
            showToast(`Đang hủy ${selectedOrders.length} đơn hàng...`, 'info');
            setTimeout(() => {
                showToast(`Đã hủy ${selectedOrders.length} đơn hàng thành công!`, 'success');
            }, 1500);
        }
    } else {
        showToast('Vui lòng chọn đơn hàng để hủy', 'error');
    }
}

function viewOrder(orderId) {
    // Populate modal with order details
    document.getElementById('modalOrderId').textContent = `#${orderId}`;
    document.getElementById('orderModal').style.display = 'flex';
}

function closeOrderModal() {
    document.getElementById('orderModal').style.display = 'none';
}

function confirmOrder(orderId) {
    showToast(`Đang xác nhận đơn hàng ${orderId}...`, 'info');
    setTimeout(() => {
        showToast(`Đã xác nhận đơn hàng ${orderId} thành công!`, 'success');
    }, 1000);
}

function cancelOrder(orderId) {
    if (confirm(`Bạn có chắc muốn hủy đơn hàng ${orderId}?`)) {
        showToast(`Đang hủy đơn hàng ${orderId}...`, 'info');
        setTimeout(() => {
            showToast(`Đã hủy đơn hàng ${orderId} thành công!`, 'success');
        }, 1000);
    }
}

function refundOrder(orderId) {
    if (confirm(`Bạn có chắc muốn hoàn tiền cho đơn hàng ${orderId}?`)) {
        showToast(`Đang xử lý hoàn tiền cho đơn hàng ${orderId}...`, 'info');
        setTimeout(() => {
            showToast(`Đã hoàn tiền thành công cho đơn hàng ${orderId}!`, 'success');
        }, 1500);
    }
}

function restoreOrder(orderId) {
    if (confirm(`Bạn có chắc muốn khôi phục đơn hàng ${orderId}?`)) {
        showToast(`Đang khôi phục đơn hàng ${orderId}...`, 'info');
        setTimeout(() => {
            showToast(`Đã khôi phục đơn hàng ${orderId} thành công!`, 'success');
        }, 1000);
    }
}

function processOrder() {
    showToast('Đang xử lý đơn hàng...', 'info');
    setTimeout(() => {
        showToast('Đã xử lý đơn hàng thành công!', 'success');
        closeOrderModal();
    }, 1000);
}

function goToPage(page) {
    showToast(`Đang chuyển đến trang ${page}...`, 'info');
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

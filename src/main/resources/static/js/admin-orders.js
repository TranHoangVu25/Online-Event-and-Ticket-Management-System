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

function formatDate(date) {
    if (!(date instanceof Date) || isNaN(date)) return '-';
    return date.toLocaleString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}
function formatCurrency(amount) {
    if (amount == null || isNaN(amount)) return '-';
    return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

window.viewOrder = function (orderId, ticketClassId) {
    fetch(`/admin/admin-order-detail/${orderId}/${ticketClassId}`)
        .then(res => res.json())
        .then(order => {
            // ===== Thông tin đơn hàng =====
            document.getElementById('modalOrderId').textContent = order.orderId || '-';
            document.getElementById('modalOrderDate').textContent = formatDate(new Date(order.orderDate));
            document.getElementById('modalOrderStatus').textContent =
                order.status === 0 ? 'Chờ xử lý' :
                order.status === 1 ? 'Thành công' :
                order.status === 2 ? 'Thất bại' : 'Không xác định';
            document.getElementById('modalOrderTotal').textContent = formatCurrency(order.totalAmount);

            // ===== Thông tin khách hàng =====
            document.getElementById('modalCustomerName').textContent = order.fullName || '-';
            document.getElementById('modalCustomerEmail').textContent = order.email || '-';
            document.getElementById('modalCustomerPhone').textContent = order.phoneNumber || '-';

            // ===== Chi tiết sự kiện =====
            document.getElementById('modalEventName').textContent = order.eventName || '-';
            document.getElementById('modalEventTime').textContent = formatDate(new Date(order.eventStartTime));
            document.getElementById('modalEventLocation').textContent = order.eventLocation || '-';
            document.getElementById('modalTicketCount').textContent = `${order.quantity} vé`;

            // Hiển thị modal
            document.getElementById('orderModal').style.display = 'flex';
        })
        .catch(err => {
            console.error('Lỗi khi lấy order:', err);
            alert('Không thể lấy thông tin đơn hàng');
        });
};

function closeOrderModal() {
    document.getElementById('orderModal').style.display = 'none';
}

function confirmOrder(orderId) {
if (confirm("Bạn có chắc muốn xác nhận đơn hàng #" + orderId + " không?")) {
        fetch(`/admin/admin-order-confirm/${orderId}`, {
            method: "POST"
        })
        .then(response => {
            if (response.redirected) {
                // Spring redirect -> tự động chuyển hướng
                window.location.href = response.url;
            } else if (response.ok) {
                alert("Xác nhận đơn hàng thành công!");
                location.reload();
            } else {
                alert("Không thể xác đơn hàng (mã lỗi: " + response.status + ")");
            }
        })
        .catch(error => {
            console.error("Lỗi khi gửi yêu cầu xác nhận:", error);
            alert("Đã xảy ra lỗi, vui lòng thử lại!");
        });
    }
}

function cancelOrder(orderId) {
    if (confirm("Bạn có chắc muốn hủy đơn hàng #" + orderId + " không?")) {
        fetch(`/admin/admin-order-cancel/${orderId}`, {
            method: "POST"
        })
        .then(response => {
            if (response.redirected) {
                // Spring redirect -> tự động chuyển hướng
                window.location.href = response.url;
            } else if (response.ok) {
                alert("Hủy đơn hàng thành công!");
                location.reload();
            } else {
                alert("Không thể hủy đơn hàng (mã lỗi: " + response.status + ")");
            }
        })
        .catch(error => {
            console.error("Lỗi khi gửi yêu cầu hủy:", error);
            alert("Đã xảy ra lỗi, vui lòng thử lại!");
        });
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

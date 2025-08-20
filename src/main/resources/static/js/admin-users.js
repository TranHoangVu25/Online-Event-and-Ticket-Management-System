// Admin Users JavaScript
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
    toast.className = `toast glass ${type}`;
    toast.classList.add('show');
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

function openAddUserModal() {
    document.getElementById('userModal').style.display = 'flex';
    document.getElementById('userForm').reset();
    document.querySelector('.modal-header h3').textContent = 'Thêm người dùng mới';
}

function closeUserModal() {
    document.getElementById('userModal').style.display = 'none';
}

function refreshUsers() {
    showToast('Đang làm mới danh sách người dùng...', 'info');
    setTimeout(() => {
        showToast('Đã làm mới danh sách người dùng!', 'success');
    }, 1000);
}

function applyFilters() {
    const status = document.getElementById('statusFilter').value;
    const role = document.getElementById('roleFilter').value;
    const search = document.getElementById('searchFilter').value;
    
    showToast('Đang áp dụng bộ lọc...', 'info');
    setTimeout(() => {
        showToast('Đã áp dụng bộ lọc thành công!', 'success');
    }, 1000);
}

function clearFilters() {
    document.getElementById('statusFilter').value = '';
    document.getElementById('roleFilter').value = '';
    document.getElementById('searchFilter').value = '';
    showToast('Đã xóa bộ lọc', 'info');
}

function exportUsers() {
    showToast('Đang xuất dữ liệu người dùng...', 'info');
    setTimeout(() => {
        showToast('Đã xuất dữ liệu thành công!', 'success');
    }, 2000);
}

function editUser(userId) {
    showToast(`Đang mở thông tin người dùng ${userId} để chỉnh sửa...`, 'info');
    document.getElementById('userModal').style.display = 'flex';
    document.querySelector('.modal-header h3').textContent = 'Chỉnh sửa người dùng';
    
    // Populate form with user data (simulated)
    setTimeout(() => {
        document.getElementById('userName').value = 'Nguyễn Văn An';
        document.getElementById('userEmail').value = 'nguyenvanan@email.com';
        document.getElementById('userRole').value = 'user';
        document.getElementById('userStatus').value = 'active';
    }, 500);
}

function viewUser(userId) {
    showToast(`Đang mở chi tiết người dùng ${userId}...`, 'info');
    document.getElementById('userDetailsModal').style.display = 'flex';
    
    // Populate user details (simulated)
    setTimeout(() => {
        document.getElementById('detailUserName').textContent = 'Nguyễn Văn An';
        document.getElementById('detailUserEmail').textContent = 'nguyenvanan@email.com';
    }, 500);
}

function toggleUserStatus(userId) {
    const action = confirm('Bạn có chắc muốn thay đổi trạng thái người dùng này?') ? 'khóa' : 'mở khóa';
    showToast(`Đang ${action} người dùng ${userId}...`, 'info');
    setTimeout(() => {
        showToast(`Đã ${action} người dùng ${userId} thành công!`, 'success');
    }, 1000);
}

function deleteUser(userId) {
    if (confirm('Bạn có chắc muốn xóa người dùng này? Hành động này không thể hoàn tác.')) {
        showToast(`Đang xóa người dùng ${userId}...`, 'info');
        setTimeout(() => {
            showToast(`Đã xóa người dùng ${userId} thành công!`, 'success');
        }, 1000);
    }
}

function previousPage() {
    showToast('Đang chuyển đến trang trước...', 'info');
}

function nextPage() {
    showToast('Đang chuyển đến trang tiếp theo...', 'info');
}

function togglePassword(inputId) {
    const input = document.getElementById(inputId);
    const button = input.parentElement.querySelector('.toggle-password i');
    
    if (input.type === 'password') {
        input.type = 'text';
        button.classList.remove('fa-eye');
        button.classList.add('fa-eye-slash');
    } else {
        input.type = 'password';
        button.classList.remove('fa-eye-slash');
        button.classList.add('fa-eye');
    }
}

function saveUser() {
    const userName = document.getElementById('userName').value.trim();
    const userEmail = document.getElementById('userEmail').value.trim();
    const userRole = document.getElementById('userRole').value;
    const userStatus = document.getElementById('userStatus').value;
    const usename = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    if (!userName || !userEmail) {
        showToast('Vui lòng điền đầy đủ thông tin bắt buộc', 'error');
        return;
    }
    
    if (password && password !== confirmPassword) {
        showToast('Mật khẩu xác nhận không khớp', 'error');
        return;
    }
    
    showToast('Đang lưu thông tin người dùng...', 'info');
    closeUserModal();
    
    setTimeout(() => {
        showToast('Đã lưu thông tin người dùng thành công!', 'success');
    }, 1000);
}

function closeUserDetailsModal() {
    document.getElementById('userDetailsModal').style.display = 'none';
}

function editUserFromDetails() {
    closeUserDetailsModal();
    setTimeout(() => {
        editUser('current');
    }, 300);
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

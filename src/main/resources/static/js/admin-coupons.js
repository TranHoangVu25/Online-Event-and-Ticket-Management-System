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
    document.getElementById('createUser').reset();
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

function toggleUserStatus(userId) {
    const action = confirm('Bạn có chắc muốn thay đổi trạng thái người dùng này?') ? 'khóa' : 'mở khóa';
    showToast(`Đang ${action} người dùng ${userId}...`, 'info');
    setTimeout(() => {
        showToast(`Đã ${action} người dùng ${userId} thành công!`, 'success');
    }, 1000);
}

function deleteCoupon(couponId) {
    if (confirm('Bạn có chắc muốn xóa mã này? Hành động này không thể hoàn tác.')) {
        showToast(`Đang xóa mã ${couponId}...`, 'info');

        fetch(`/admin-coupon/remove-coupon/${couponId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi khi xoá user");
            }
            return response.text(); // vì controller trả về String "OK"
        })
        .then(result => {
            if (result === "OK") {
                showToast(`Đã xóa mã ${couponId} thành công!`, 'success');
                // Có thể xoá user khỏi bảng mà không cần reload
                document.getElementById(`user-row-${couponId}`).remove();
            }
        })
        .catch(error => {
            console.error("Error:", error);
            showToast("Xóa thất bại!", 'error');
        });
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
    const fullName = document.getElementById('firstName').value.trim();
    const userEmail = document.getElementById('email').value.trim();
    const userRole = document.getElementById('role').value;
    const userStatus = document.getElementById('status').value;
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (!fullName || !userEmail) {
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


function closeUpdateUserModal() {
    document.getElementById("updateUserModal").style.display = "none";
}

function openAddCouponModal() {
    const modal = document.getElementById('couponModal');
    if (modal) {
        // Reset form (đảm bảo form trống khi mở)
        document.getElementById('createCouponForm').reset();

        // Đặt lại tiêu đề modal
        document.getElementById('modalTitle').textContent = 'Thêm mã giảm giá';

        // Hiển thị modal
        modal.style.display = 'flex';
    }
}


/**
 * Mở modal cập nhật coupon và tải dữ liệu từ server.
 * @param {number} couponId ID của coupon cần cập nhật
 */
function openUpdateCouponModal(couponId) {
    // 1. Fetch dữ liệu coupon từ server
    fetch(`/admin-coupon/${couponId}`) // Giả định endpoint này tồn tại (xem phần Controller)
        .then(res => {
            if (!res.ok) {
                throw new Error('Không thể tải thông tin coupon');
            }
            return res.json();
        })
        .then(coupon => {
            // 2. Điền dữ liệu vào form
            document.getElementById('modalUpdateTitle').textContent = `Cập nhật mã: ${coupon.code}`;

            document.getElementById('updateCouponId').value = coupon.coupon_id;
            document.getElementById('updateCouponCode').value = coupon.code;
            document.getElementById('updateCouponType').value = coupon.type;
            document.getElementById('updateCouponDiscount').value = coupon.discount;

            // Xử lý giá trị 'condition' có thể là null
            document.getElementById('updateCouponCondition').value = coupon.condition || '';

            // 3. Định dạng ngày giờ cho input 'datetime-local'
            // Input này yêu cầu định dạng YYYY-MM-DDTHH:MM
            // Giả sử server trả về một chuỗi ISO (vd: "2025-10-29T17:00:00")
            if (coupon.expire) {
                // Cắt chuỗi để lấy 16 ký tự đầu (YYYY-MM-DDTHH:MM)
                document.getElementById('updateCouponExpire').value = coupon.expire.slice(0, 16);
            } else {
                document.getElementById('updateCouponExpire').value = '';
            }

            // 4. Đặt 'action' cho form một cách động
            // Điều này ghi đè 'th:action' và đảm bảo form submit đúng ID
            const form = document.querySelector('#updateCouponForm');
            form.action = `/admin-coupon/admin-update-coupon/${couponId}`;

            // 5. Hiển thị modal
            document.getElementById('updateCouponModal').style.display = 'flex';
        })
        .catch(err => {
            console.error('Lỗi khi lấy thông tin coupon:', err);
            // Bạn có thể dùng hàm showToast ở đây
            // showToast('Không thể lấy thông tin coupon', 'error');
        });
}

/**
 * Đóng modal cập nhật coupon.
 * (Hàm này được gọi bởi nút "Hủy" và nút "X" trong HTML của bạn)
 */
function closeUpdateCouponModal() {
    document.getElementById('updateCouponModal').style.display = 'none';
}
// Thêm hàm này vào file admin-coupons.js
function closeCouponModal() {
    document.getElementById('couponModal').style.display = 'none';
}
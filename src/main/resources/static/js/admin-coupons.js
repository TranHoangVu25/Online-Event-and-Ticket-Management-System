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
            // Thêm CSRF token header nếu bạn dùng Spring Security
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi khi xoá coupon");
            }
            return response.text(); // vì controller trả về String "OK"
        })
        .then(result => {
            if (result === "OK") {
                showToast(`Đã xóa mã ${couponId} thành công!`, 'success');
                // Xoá dòng khỏi bảng
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

        // --- SỬA LỖI: DÙNG RESET THỦ CÔNG ---
        // 1. Reset các trường input về rỗng
        document.getElementById('couponCode').value = '';
        document.getElementById('couponDiscount').value = '';
        document.getElementById('couponCondition').value = '';
        document.getElementById('couponExpire').value = '';
        // 2. Reset trường select
        document.getElementById('couponType').value = '1';
        // --- KẾT THÚC SỬA ---

        // 3. Đặt lại tiêu đề modal
        document.getElementById('modalTitle').textContent = 'Thêm mã giảm giá';

    const form = document.getElementById('createCouponForm');
    if (form) {
        const errorMessages = form.querySelectorAll('.error');
        errorMessages.forEach(msg => msg.textContent = '');

        const errorInputs = form.querySelectorAll('.error-field');
        errorInputs.forEach(input => input.classList.remove('error-field'));
    }
        // 4. Hiển thị modal
        modal.style.display = 'flex';
    }
}


/**
 * Mở modal cập nhật coupon và tải dữ liệu từ server.
 * @param {number} couponId ID của coupon cần cập nhật
 */
function openUpdateCouponModal(couponId) {
    // 1. Fetch dữ liệu coupon từ server
    fetch(`/admin-coupon/${couponId}`) // Endpoint này đã có trong Controller
        .then(res => {
            if (!res.ok) {
                throw new Error('Không thể tải thông tin coupon');
            }
            return res.json();
        })
        .then(coupon => {
            // 2. Điền dữ liệu vào form
            document.getElementById('modalUpdateTitle').textContent = `Cập nhật mã: ${coupon.code}`;

            document.getElementById('updateCouponId').value = coupon.coupon_id; // Dùng cho input hidden
            document.getElementById('updateCouponCode').value = coupon.code;
            document.getElementById('updateCouponType').value = coupon.type;
            document.getElementById('updateCouponDiscount').value = coupon.discount;
            document.getElementById('updateCouponCondition').value = coupon.condition || '';

            // 3. Định dạng ngày giờ cho input 'datetime-local'
            if (coupon.expire) {
                document.getElementById('updateCouponExpire').value = coupon.expire.slice(0, 16);
            } else {
                document.getElementById('updateCouponExpire').value = '';
            }

            // 4. Đặt 'action' cho form một cách động
            // *** QUAN TRỌNG: Dòng này vẫn cần thiết ***
            // Nó đặt action cho lần MỞ ĐẦU TIÊN.
            // Bản sửa lỗi HTML/Controller sẽ xử lý sau khi TẢI LẠI TRANG.
            const form = document.querySelector('#updateCouponForm');
            form.action = `/admin-coupon/admin-update-coupon/${couponId}`;

            // 5. Xóa lỗi cũ (nếu có)
            const errorMessages = form.querySelectorAll('.error');
            errorMessages.forEach(msg => msg.textContent = '');
            const errorInputs = form.querySelectorAll('.error-field');
            errorInputs.forEach(input => input.classList.remove('error-field'));

            // 6. Hiển thị modal
            document.getElementById('updateCouponModal').style.display = 'flex';
        })
        .catch(err => {
            console.error('Lỗi khi lấy thông tin coupon:', err);
            showToast('Không thể lấy thông tin coupon', 'error');
        });
}

/**
 * Đóng modal cập nhật coupon.
 * (Hàm này được gọi bởi nút "Hủy" và nút "X" trong HTML của bạn)
 */
function closeUpdateCouponModal() {
    document.getElementById('updateCouponModal').style.display = 'none';
}
// Thay thế hàm closeCouponModal() cũ của bạn bằng hàm này
// Dùng hàm này thay thế cho hàm closeCouponModal cũ của bạn
function closeCouponModal() {
    const modal = document.getElementById('couponModal');
    if (modal) {
        // 1. Ẩn modal
        modal.style.display = 'none';
    }

    // --- BẮT ĐẦU RESET THỦ CÔNG ---
    // 2. Reset các trường input về rỗng
    document.getElementById('couponCode').value = '';
    document.getElementById('couponDiscount').value = '';
    document.getElementById('couponCondition').value = ''; // Đặt về rỗng
    document.getElementById('couponExpire').value = '';

    // 3. Reset trường select về giá trị đầu tiên
    document.getElementById('couponType').value = '1'; // '1' là "Tiền mặt (fixed)"
    // --- KẾT THÚC RESET THỦ CÔNG ---


    // 4. Xóa các thông báo lỗi (giữ nguyên logic cũ)
    const form = document.getElementById('createCouponForm');
    if (form) {
        const errorMessages = form.querySelectorAll('.error');
        errorMessages.forEach(msg => msg.textContent = '');

        const errorInputs = form.querySelectorAll('.error-field');
        errorInputs.forEach(input => input.classList.remove('error-field'));
    }
}
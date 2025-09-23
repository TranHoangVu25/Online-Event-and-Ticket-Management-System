// Login JavaScript
// Toggle password visibility

window.addEventListener('DOMContentLoaded', function () {
    // Khi load trang -> check nếu có lỗi từ backend thì show toast
    const pageError = document.body.dataset.error;
    if (pageError && pageError.trim() !== "") {
        showToast(pageError, 'error');
    }
});

window.togglePassword = function ()  {
    const passwordInput = document.getElementById('password');
    const passwordIcon = document.getElementById('passwordIcon');
    
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        passwordIcon.classList.remove('fa-eye');
        passwordIcon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        passwordIcon.classList.remove('fa-eye-slash');
        passwordIcon.classList.add('fa-eye');
    }
}

//function showToast(message, type = 'success') {
//    const toast = document.getElementById('toast');
//    const messageEl = toast.querySelector('.toast-message');
//
//    messageEl.textContent = message;
//    toast.className = `toast glass ${type}`;
//    toast.classList.add('show');
//
//    setTimeout(() => {
//        toast.classList.remove('show');
//    }, 3000);
//}

//// Form validation
//document.querySelector('.login-form').addEventListener('submit', function(e) {
////    e.preventDefault();
//
//    const username = document.getElementById('username').value.trim();
//    const password = document.getElementById('password').value;
//
//    let isValid = true;
//
//    // Clear previous errors
//    document.querySelectorAll('.error-message').forEach(el => el.textContent = '');
//    document.querySelectorAll('.form-group').forEach(el => el.classList.remove('error'));
//
//    // Validate username
//    if (!username) {
//        document.getElementById('username-error').textContent = 'Vui lòng nhập tên đăng nhập';
//        document.getElementById('username').parentElement.classList.add('error');
//        isValid = false;
//    }
//
//    // Validate password
//    if (!password) {
//        document.getElementById('password-error').textContent = 'Vui lòng nhập mật khẩu';
//        document.getElementById('password').parentElement.classList.add('error');
//        isValid = false;
//    }
//
////    if (isValid) {
////        showToast('Đăng nhập thành công!', 'success');
//        // Here you would typically send the data to your backend
////        setTimeout(() => {
//////            window.location.href = '/admin-home';
////            window.location.href = '/test';
////
////        }, 2000);
//    } else {
//        showToast('Vui lòng kiểm tra lại thông tin', 'error');
//    }
//});


 window.showToast = function(message, type) {
    const toast = document.getElementById('toast');
    const toastMessage = toast.querySelector('.toast-message');
    const toastIcon = toast.querySelector('.toast-icon');

    toastMessage.textContent = message;

    // tuỳ chỉnh icon & màu theo type
    if (type === 'error') {
        toastIcon.className = 'toast-icon fas fa-exclamation-circle';
        toast.style.backgroundColor = '#e74c3c';
    } else if (type === 'success') {
        toastIcon.className = 'toast-icon fas fa-check-circle';
        toast.style.backgroundColor = '#2ecc71';
    }

    toast.classList.add('show');
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

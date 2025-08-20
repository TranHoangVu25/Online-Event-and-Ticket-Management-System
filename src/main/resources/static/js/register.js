// Register JavaScript
// Simple JavaScript for basic functionality
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

// Form validation
document.querySelector('.register-form').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const agree = document.getElementById('agree').checked;
    
    let isValid = true;
    
    // Clear previous errors
    document.querySelectorAll('.error-message').forEach(el => el.textContent = '');
    document.querySelectorAll('.form-group').forEach(el => el.classList.remove('error'));
    
    // Validate name
    if (!name) {
        document.getElementById('name-error').textContent = 'Vui lòng nhập họ và tên';
        document.getElementById('name').parentElement.classList.add('error');
        isValid = false;
    } else if (name.length < 2) {
        document.getElementById('name-error').textContent = 'Họ và tên phải có ít nhất 2 ký tự';
        document.getElementById('name').parentElement.classList.add('error');
        isValid = false;
    }
    
    // Validate email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email) {
        document.getElementById('email-error').textContent = 'Vui lòng nhập email';
        document.getElementById('email').parentElement.classList.add('error');
        isValid = false;
    } else if (!emailRegex.test(email)) {
        document.getElementById('email-error').textContent = 'Email không hợp lệ';
        document.getElementById('email').parentElement.classList.add('error');
        isValid = false;
    }
    
    // Validate password
    if (!password) {
        document.getElementById('password-error').textContent = 'Vui lòng nhập mật khẩu';
        document.getElementById('password').parentElement.classList.add('error');
        isValid = false;
    } else if (password.length < 6) {
        document.getElementById('password-error').textContent = 'Mật khẩu phải có ít nhất 6 ký tự';
        document.getElementById('password').parentElement.classList.add('error');
        isValid = false;
    }
    
    // Validate confirm password
    if (!confirmPassword) {
        document.getElementById('confirm-password-error').textContent = 'Vui lòng xác nhận mật khẩu';
        document.getElementById('confirm-password').parentElement.classList.add('error');
        isValid = false;
    } else if (password !== confirmPassword) {
        document.getElementById('confirm-password-error').textContent = 'Mật khẩu xác nhận không khớp';
        document.getElementById('confirm-password').parentElement.classList.add('error');
        isValid = false;
    }
    
    // Validate terms agreement
    if (!agree) {
        document.getElementById('agree-error').textContent = 'Vui lòng đồng ý với điều khoản sử dụng';
        document.getElementById('agree').parentElement.parentElement.classList.add('error');
        isValid = false;
    }
    
    if (isValid) {
        showToast('Đăng ký thành công!', 'success');
        // Here you would typically send the data to your backend
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 2000);
    } else {
        showToast('Vui lòng kiểm tra lại thông tin', 'error');
    }
});

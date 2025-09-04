// Form validation
document.querySelector('.register-form').addEventListener('submit', function(e) {
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value; // ✅ sửa id
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
        document.getElementById('confirmPassword-error').textContent = 'Vui lòng xác nhận mật khẩu';
        document.getElementById('confirmPassword').parentElement.classList.add('error');
        isValid = false;
    } else if (password !== confirmPassword) {
        document.getElementById('confirmPassword-error').textContent = 'Mật khẩu xác nhận không khớp';
        document.getElementById('confirmPassword').parentElement.classList.add('error');
        isValid = false;
    }

    // Validate terms agreement
    if (!agree) {
        document.getElementById('agree-error').textContent = 'Vui lòng đồng ý với điều khoản sử dụng';
        document.getElementById('agree').parentElement.parentElement.classList.add('error');
        isValid = false;
    }

    // ❌ Nếu form không hợp lệ → chặn submit
    if (!isValid) {
        e.preventDefault();
        showToast('Vui lòng kiểm tra lại thông tin', 'error');
    }
    // ✅ Nếu hợp lệ → KHÔNG preventDefault → form tự submit tới /register
});

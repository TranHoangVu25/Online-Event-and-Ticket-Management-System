// Forgot Password JavaScript
// Simple JavaScript for basic functionality

function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const messageEl = toast.querySelector('.toast-message');
    const iconEl = toast.querySelector('.toast-icon');
    
    // Set icon based on type
    const icons = {
        success: 'fas fa-check-circle',
        error: 'fas fa-exclamation-circle',
        warning: 'fas fa-exclamation-triangle',
        info: 'fas fa-info-circle'
    };
    
    iconEl.className = icons[type] || icons.info;
    messageEl.textContent = message;
    
    // Set toast type
    toast.className = `toast glass ${type}`;
    toast.classList.add('show');
    
    // Auto hide after 3 seconds
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Simulated user database for demo
const userDatabase = [
    { email: 'admin@eventhub.com', role: 'admin' },
    { email: 'user@eventhub.com', role: 'user' },
    { email: 'nguyenvanan@email.com', role: 'user' },
    { email: 'tranthibinh@email.com', role: 'user' },
    { email: 'levancuong@email.com', role: 'moderator' }
];

// Check if email exists in database
function checkEmailExists(email) {
    return userDatabase.some(user => user.email.toLowerCase() === email.toLowerCase());
}

// Simulate sending email
function sendResetEmail(email) {
    return new Promise((resolve, reject) => {
        // Simulate API call delay
        setTimeout(() => {
            // Simulate 90% success rate
            const isSuccess = Math.random() > 0.1;
            
            if (isSuccess) {
                resolve({
                    success: true,
                    message: 'Email đã được gửi thành công'
                });
            } else {
                reject({
                    success: false,
                    message: 'Gửi email thất bại. Vui lòng thử lại sau.'
                });
            }
        }, 2000);
    });
}

// Form validation
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Clear form errors
function clearFormErrors() {
    document.querySelectorAll('.error-message').forEach(el => el.textContent = '');
    document.querySelectorAll('.form-group').forEach(el => el.classList.remove('error'));
}

// Show field error
function showFieldError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const errorEl = document.getElementById(`${fieldId}-error`);
    const formGroup = field.closest('.form-group');
    
    if (errorEl) {
        errorEl.textContent = message;
    }
    
    if (formGroup) {
        formGroup.classList.add('error');
    }
}

// Handle form submission
document.getElementById('forgotPasswordForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value.trim();
    const submitBtn = document.querySelector('.submit-btn');
    
    // Clear previous errors
    clearFormErrors();
    
    let isValid = true;
    
    // Validate email
    if (!email) {
        showFieldError('email', 'Vui lòng nhập email');
        isValid = false;
    } else if (!validateEmail(email)) {
        showFieldError('email', 'Email không hợp lệ');
        isValid = false;
    }
    
    if (!isValid) {
        showToast('Vui lòng kiểm tra lại thông tin', 'error');
        return;
    }
    
    // Check if email exists
    if (!checkEmailExists(email)) {
        showFieldError('email', 'Email không tồn tại trong hệ thống');
        showToast('Email không tồn tại', 'error');
        return;
    }
    
    // Disable submit button and show loading
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang gửi...';
    
    try {
        // Send reset email
        const result = await sendResetEmail(email);
        
        if (result.success) {
            // Show success message
            document.getElementById('forgotPasswordForm').style.display = 'none';
            document.getElementById('successMessage').style.display = 'block';
            showToast('Email đã được gửi thành công!', 'success');
        }
    } catch (error) {
        showToast(error.message, 'error');
        
        // Re-enable submit button
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<i class="fas fa-paper-plane"></i> Gửi liên kết đặt lại mật khẩu';
    }
});

// Resend email function
function resendEmail() {
    const email = document.getElementById('email').value.trim();
    const resendBtn = document.querySelector('.btn-secondary');
    
    if (!email) {
        showToast('Không tìm thấy email để gửi lại', 'error');
        return;
    }
    
    // Disable resend button and show loading
    resendBtn.disabled = true;
    resendBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang gửi...';
    
    sendResetEmail(email)
        .then(result => {
            if (result.success) {
                showToast('Email đã được gửi lại thành công!', 'success');
            }
        })
        .catch(error => {
            showToast(error.message, 'error');
        })
        .finally(() => {
            // Re-enable resend button
            resendBtn.disabled = false;
            resendBtn.innerHTML = '<i class="fas fa-redo"></i> Gửi lại email';
        });
}

// Real-time email validation
document.getElementById('email').addEventListener('input', function() {
    const email = this.value.trim();
    const errorEl = document.getElementById('email-error');
    const formGroup = this.closest('.form-group');
    
    // Clear error when user starts typing
    if (errorEl) {
        errorEl.textContent = '';
    }
    
    if (formGroup) {
        formGroup.classList.remove('error');
    }
    
    // Real-time validation
    if (email && !validateEmail(email)) {
        showFieldError('email', 'Email không hợp lệ');
    }
});

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // Enter key to submit form
    if (e.key === 'Enter' && document.getElementById('forgotPasswordForm').style.display !== 'none') {
        document.getElementById('forgotPasswordForm').dispatchEvent(new Event('submit'));
    }
    
    // Escape key to go back to login
    if (e.key === 'Escape') {
        window.location.href = '/';
    }
});

// Focus management
document.addEventListener('DOMContentLoaded', function() {
    // Focus on email input when page loads
    document.getElementById('email').focus();
    
    // Add loading animation to shapes
    const shapes = document.querySelectorAll('.shape');
    shapes.forEach((shape, index) => {
        shape.style.animationDelay = `${index * 0.5}s`;
    });
});

// Export functions for global access
window.resendEmail = resendEmail;

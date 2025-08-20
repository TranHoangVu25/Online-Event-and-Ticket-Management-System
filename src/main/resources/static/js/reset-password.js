// Reset Password JavaScript
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

// Toggle password visibility
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

// Password strength checker
function checkPasswordStrength(password) {
    let score = 0;
    let feedback = [];
    
    // Length check
    if (password.length >= 8) {
        score += 1;
    } else {
        feedback.push('Mật khẩu phải có ít nhất 8 ký tự');
    }
    
    // Uppercase check
    if (/[A-Z]/.test(password)) {
        score += 1;
    } else {
        feedback.push('Cần có ít nhất 1 chữ hoa');
    }
    
    // Lowercase check
    if (/[a-z]/.test(password)) {
        score += 1;
    } else {
        feedback.push('Cần có ít nhất 1 chữ thường');
    }
    
    // Number check
    if (/\d/.test(password)) {
        score += 1;
    } else {
        feedback.push('Cần có ít nhất 1 số');
    }
    
    // Special character check
    if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
        score += 1;
    } else {
        feedback.push('Cần có ít nhất 1 ký tự đặc biệt');
    }
    
    // Determine strength level
    let strength = 'weak';
    let strengthText = 'Yếu';
    
    if (score >= 4) {
        strength = 'strong';
        strengthText = 'Mạnh';
    } else if (score >= 3) {
        strength = 'good';
        strengthText = 'Tốt';
    } else if (score >= 2) {
        strength = 'fair';
        strengthText = 'Trung bình';
    }
    
    return {
        score: score,
        strength: strength,
        strengthText: strengthText,
        feedback: feedback
    };
}

// Update password strength indicator
function updatePasswordStrength(password) {
    const strengthFill = document.getElementById('strengthFill');
    const strengthText = document.getElementById('strengthText');
    
    if (!password) {
        strengthFill.className = 'strength-fill';
        strengthText.className = 'strength-text';
        strengthText.textContent = 'Độ mạnh mật khẩu';
        return;
    }
    
    const result = checkPasswordStrength(password);
    
    // Update strength bar
    strengthFill.className = `strength-fill ${result.strength}`;
    
    // Update strength text
    strengthText.className = `strength-text ${result.strength}`;
    strengthText.textContent = result.strengthText;
}

// Form validation
function validatePassword(password) {
    const result = checkPasswordStrength(password);
    return result.score >= 3; // Minimum 3 criteria met
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

// Simulate password reset API call
function resetPassword(password) {
    return new Promise((resolve, reject) => {
        // Simulate API call delay
        setTimeout(() => {
            // Simulate 95% success rate
            const isSuccess = Math.random() > 0.05;
            
            if (isSuccess) {
                resolve({
                    success: true,
                    message: 'Mật khẩu đã được đặt lại thành công'
                });
            } else {
                reject({
                    success: false,
                    message: 'Đặt lại mật khẩu thất bại. Vui lòng thử lại sau.'
                });
            }
        }, 2000);
    });
}

// Handle form submission
document.getElementById('resetPasswordForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const submitBtn = document.querySelector('.submit-btn');
    
    // Clear previous errors
    clearFormErrors();
    
    let isValid = true;
    
    // Validate password
    if (!password) {
        showFieldError('password', 'Vui lòng nhập mật khẩu mới');
        isValid = false;
    } else if (!validatePassword(password)) {
        showFieldError('password', 'Mật khẩu không đủ mạnh');
        isValid = false;
    }
    
    // Validate confirm password
    if (!confirmPassword) {
        showFieldError('confirmPassword', 'Vui lòng xác nhận mật khẩu');
        isValid = false;
    } else if (password !== confirmPassword) {
        showFieldError('confirmPassword', 'Mật khẩu xác nhận không khớp');
        isValid = false;
    }
    
    if (!isValid) {
        showToast('Vui lòng kiểm tra lại thông tin', 'error');
        return;
    }
    
    // Disable submit button and show loading
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang đặt lại...';
    
    try {
        // Reset password
        const result = await resetPassword(password);
        
        if (result.success) {
            // Show success message
            document.getElementById('resetPasswordForm').style.display = 'none';
            document.getElementById('successMessage').style.display = 'block';
            showToast('Mật khẩu đã được đặt lại thành công!', 'success');
        }
    } catch (error) {
        showToast(error.message, 'error');
        
        // Re-enable submit button
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<i class="fas fa-key"></i> Đặt lại mật khẩu';
    }
});

// Real-time password strength checking
document.getElementById('password').addEventListener('input', function() {
    const password = this.value;
    const errorEl = document.getElementById('password-error');
    const formGroup = this.closest('.form-group');
    
    // Clear error when user starts typing
    if (errorEl) {
        errorEl.textContent = '';
    }
    
    if (formGroup) {
        formGroup.classList.remove('error');
    }
    
    // Update password strength indicator
    updatePasswordStrength(password);
    
    // Real-time validation
    if (password && !validatePassword(password)) {
        const result = checkPasswordStrength(password);
        if (result.feedback.length > 0) {
            showFieldError('password', result.feedback[0]);
        }
    }
});

// Real-time confirm password validation
document.getElementById('confirmPassword').addEventListener('input', function() {
    const password = document.getElementById('password').value;
    const confirmPassword = this.value;
    const errorEl = document.getElementById('confirmPassword-error');
    const formGroup = this.closest('.form-group');
    
    // Clear error when user starts typing
    if (errorEl) {
        errorEl.textContent = '';
    }
    
    if (formGroup) {
        formGroup.classList.remove('error');
    }
    
    // Real-time validation
    if (confirmPassword && password !== confirmPassword) {
        showFieldError('confirmPassword', 'Mật khẩu xác nhận không khớp');
    }
});

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // Enter key to submit form
    if (e.key === 'Enter' && document.getElementById('resetPasswordForm').style.display !== 'none') {
        document.getElementById('resetPasswordForm').dispatchEvent(new Event('submit'));
    }
    
    // Escape key to go back to login
    if (e.key === 'Escape') {
        window.location.href = '/';
    }
});

// Focus management
document.addEventListener('DOMContentLoaded', function() {
    // Focus on password input when page loads
    document.getElementById('password').focus();
    
    // Add loading animation to shapes
    const shapes = document.querySelectorAll('.shape');
    shapes.forEach((shape, index) => {
        shape.style.animationDelay = `${index * 0.5}s`;
    });
    
    // Check if there's a reset token in URL (for demo purposes)
    const urlParams = new URLSearchParams(window.location.search);
    const resetToken = urlParams.get('token');
    
    if (!resetToken) {
        // In a real application, you would validate the token
        // For demo purposes, we'll just show a warning
        showToast('Liên kết đặt lại mật khẩu không hợp lệ', 'warning');
    }
});

// Export functions for global access
window.togglePassword = togglePassword;

// ===========================
// Forgot Password JavaScript
// ===========================

// --- Hàm hiển thị Toast ---
function showToast(message, type = "info") {
    const toast = document.getElementById("toast");
    if (!toast) return console.error("Không tìm thấy phần tử #toast");

    const icon = toast.querySelector(".toast-icon");
    const msg = toast.querySelector(".toast-message");

    const icons = {
        success: "fas fa-check-circle",
        error: "fas fa-exclamation-circle",
        warning: "fas fa-exclamation-triangle",
        info: "fas fa-info-circle"
    };

    icon.className = `toast-icon ${icons[type] || icons.info}`;
    msg.textContent = message;

    toast.className = `toast glass ${type} show`;

    setTimeout(() => {
        toast.classList.remove("show");
    }, 3000);
}

// --- Validate Email ---
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// --- Xóa lỗi form ---
function clearFormErrors() {
    document.querySelectorAll('.error-message').forEach(el => el.textContent = '');
    document.querySelectorAll('.form-group').forEach(el => el.classList.remove('error'));
}

// --- Hiển thị lỗi cụ thể cho field ---
function showFieldError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const errorEl = document.getElementById(`${fieldId}-error`);
    const formGroup = field.closest('.form-group');

    if (errorEl) errorEl.textContent = message;
    if (formGroup) formGroup.classList.add('error');
}

// --- Nút cooldown ---
function disableButtonWithCooldown(button, seconds, originalHTML) {
    let remaining = seconds;
    button.disabled = true;
    button.innerHTML = `Vui lòng đợi (${remaining}s)`;

    const interval = setInterval(() => {
        remaining--;
        if (remaining > 0) {
            button.innerHTML = `Vui lòng đợi (${remaining}s)`;
        } else {
            clearInterval(interval);
            button.disabled = false;
            button.innerHTML = originalHTML;
        }
    }, 1000);
}

// --- Gửi form quên mật khẩu ---
document.getElementById("forgotPasswordForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const submitBtn = document.querySelector(".submit-btn");
    const originalHTML = submitBtn.innerHTML;

    clearFormErrors();

    if (!email) {
        showFieldError("email", "Vui lòng nhập email");
        showToast("Vui lòng nhập email", "error");
        return;
    }

    if (!validateEmail(email)) {
        showFieldError("email", "Email không hợp lệ");
        showToast("Email không hợp lệ", "error");
        return;
    }

    submitBtn.disabled = true;
    submitBtn.innerHTML = `<i class="fas fa-spinner fa-spin"></i> Đang gửi...`;

    try {
        const response = await fetch("/forgot-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email })
        });

        // Nếu server trả về HTML (vd: 404, redirect), tránh lỗi JSON
        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
            throw new Error("Server không trả về JSON!");
        }

        const result = await response.json();

        if (response.status === 429) {
            showToast(result.mess || "Bạn thao tác quá nhanh", "error");
            disableButtonWithCooldown(submitBtn, 30, originalHTML);
            return;
        }

        if (!response.ok) {
            showFieldError("email", result.mess || "Email không tồn tại");
            showToast(result.mess || "Có lỗi xảy ra", "error");
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalHTML;
            return;
        }

        document.getElementById("forgotPasswordForm").style.display = "none";
        document.getElementById("successMessage").style.display = "block";
        showToast(result.mess || "Email khôi phục đã được gửi!", "success");

    } catch (error) {
        console.error(error);
        showToast("Đã xảy ra lỗi, vui lòng thử lại sau!", "error");
        submitBtn.disabled = false;
        submitBtn.innerHTML = originalHTML;
    }
});

// --- Gửi lại email ---
async function resendEmail() {
    const email = document.getElementById("email").value.trim();
    const resendBtn = document.querySelector(".btn-secondary");
    const originalHTML = resendBtn.innerHTML;

    if (!email) {
        showToast("Không tìm thấy email để gửi lại", "error");
        return;
    }

    resendBtn.disabled = true;
    resendBtn.innerHTML = `<i class="fas fa-spinner fa-spin"></i> Đang gửi...`;

    try {
        const response = await fetch("/forgot-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email })
        });

        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
            throw new Error("Server không trả về JSON!");
        }

        const result = await response.json();

        if (response.status === 429) {
            showToast(result.mess || "Vui lòng chờ trước khi gửi lại", "error");
            disableButtonWithCooldown(resendBtn, 30, originalHTML);
            return;
        }

        if (!response.ok) {
            showToast(result.mess || "Có lỗi xảy ra", "error");
            resendBtn.disabled = false;
            resendBtn.innerHTML = originalHTML;
            return;
        }

        showToast(result.mess || "Email đã được gửi lại!", "success");
        disableButtonWithCooldown(resendBtn, 30, originalHTML);

    } catch (error) {
        console.error(error);
        showToast("Đã xảy ra lỗi, vui lòng thử lại sau!", "error");
        resendBtn.disabled = false;
        resendBtn.innerHTML = originalHTML;
    }
}
window.resendEmail = resendEmail;

// --- Xử lý input email realtime ---
document.getElementById("email").addEventListener("input", function () {
    const email = this.value.trim();
    const errorEl = document.getElementById("email-error");
    const formGroup = this.closest(".form-group");

    if (errorEl) errorEl.textContent = "";
    if (formGroup) formGroup.classList.remove("error");

    if (email && !validateEmail(email)) {
        showFieldError("email", "Email không hợp lệ");
    }
});

// --- Phím tắt ---
document.addEventListener("keydown", function (e) {
    if (e.key === "Enter" && document.getElementById("forgotPasswordForm").style.display !== "none") {
        document.getElementById("forgotPasswordForm").dispatchEvent(new Event("submit"));
    }
    if (e.key === "Escape") {
        window.location.href = "/login";
    }
});

// --- Animation & focus ---
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("email").focus();
    const shapes = document.querySelectorAll(".shape");
    shapes.forEach((shape, index) => {
        shape.style.animationDelay = `${index * 0.5}s`;
    });
});

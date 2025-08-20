// Admin Create Event JavaScript
// Simple JavaScript for basic functionality
function logout() {
    if (confirm('Bạn có chắc muốn đăng xuất?')) {
        showToast('Đang đăng xuất...', 'info');
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1000);
    }
}

function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const messageEl = toast.querySelector('.toast-message');
    
    messageEl.textContent = message;
    toast.className = `toast ${type}`;
    toast.classList.add('show');
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Form functionality
function previewEvent() {
    const formData = new FormData(document.getElementById('eventForm'));
    const eventData = Object.fromEntries(formData.entries());
    
    if (!eventData.eventName || !eventData.eventCategory || !eventData.eventDate || !eventData.ticketClass) {
        showToast('Vui lòng điền đầy đủ thông tin bắt buộc trước khi xem trước', 'error');
        return;
    }
    
    const previewContent = `
        <div class="preview-event">
            <div class="preview-header">
                <h2>${eventData.eventName}</h2>
                <span class="preview-category">${getCategoryName(eventData.eventCategory)}</span>
            </div>
            
            <div class="preview-info">
                <div class="preview-item">
                    <i class="fas fa-calendar"></i>
                    <span>${eventData.eventDate} - ${eventData.eventTime}</span>
                </div>
                <div class="preview-item">
                    <i class="fas fa-map-marker-alt"></i>
                    <span>${eventData.eventLocation}</span>
                </div>
                <div class="preview-item">
                    <i class="fas fa-ticket-alt"></i>
                    <span>${eventData.ticketClass ? getTicketClassName(eventData.ticketClass) : 'Chưa chọn'} - ${eventData.ticketPrice ? formatPrice(eventData.ticketPrice) : 'Chưa cập nhật'}</span>
                </div>
            </div>
            
            <div class="preview-description">
                <h4>Mô tả:</h4>
                <p>${eventData.eventDescription || 'Chưa có mô tả'}</p>
            </div>
        </div>
    `;
    
    document.querySelector('.event-preview').innerHTML = previewContent;
    document.getElementById('previewModal').style.display = 'flex';
}

function closePreviewModal() {
    document.getElementById('previewModal').style.display = 'none';
}

function saveDraft() {
    const formData = new FormData(document.getElementById('eventForm'));
    const eventData = Object.fromEntries(formData.entries());
    
    // Save to localStorage as draft
    localStorage.setItem('eventDraft', JSON.stringify(eventData));
    showToast('Đã lưu nháp thành công!', 'success');
}

function resetForm() {
    if (confirm('Bạn có chắc muốn làm mới form? Tất cả dữ liệu sẽ bị mất.')) {
        document.getElementById('eventForm').reset();
        showToast('Form đã được làm mới', 'info');
    }
}

function submitForm() {
    const form = document.getElementById('eventForm');
    if (form.checkValidity()) {
        showToast('Sự kiện đã được tạo thành công!', 'success');
        closePreviewModal();
        setTimeout(() => {
            window.location.href = 'admin-events.html';
        }, 1500);
    } else {
        showToast('Vui lòng kiểm tra lại thông tin bắt buộc', 'error');
    }
}

// Utility functions
function getCategoryName(category) {
    const categories = {
        'concert': 'Concert',
        'workshop': 'Workshop',
        'conference': 'Hội nghị',
        'exhibition': 'Triển lãm',
        'sports': 'Thể thao',
        'seminar': 'Hội thảo',
        'other': 'Khác'
    };
    return categories[category] || category;
}

function getTicketClassName(ticketClass) {
    const ticketClasses = {
        'vip': 'VIP',
        'premium': 'Premium',
        'standard': 'Standard',
        'economy': 'Economy',
        'student': 'Student',
        'senior': 'Senior',
        'children': 'Children'
    };
    return ticketClasses[ticketClass] || ticketClass;
}

function formatPrice(price) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price);
}

// Auto-fill ticket price based on ticket class
function autoFillTicketPrice() {
    const ticketClass = document.getElementById('ticketClass').value;
    const ticketPrice = document.getElementById('ticketPrice');
    
    const defaultPrices = {
        'vip': 500000,
        'premium': 300000,
        'standard': 200000,
        'economy': 100000,
        'student': 80000,
        'senior': 60000,
        'children': 50000
    };
    
    if (ticketClass && !ticketPrice.value) {
        ticketPrice.value = defaultPrices[ticketClass] || '';
        showToast(`Đã tự động điền giá vé cho hạng ${getTicketClassName(ticketClass)}`, 'info');
    }
}

// Auto-calculate end time
document.addEventListener('DOMContentLoaded', function() {
    const startTime = document.getElementById('eventTime');
    const duration = document.getElementById('eventDuration');
    const endTime = document.getElementById('eventEndTime');
    
    function calculateEndTime() {
        if (startTime.value && duration.value) {
            const start = new Date(`2000-01-01T${startTime.value}`);
            const end = new Date(start.getTime() + (duration.value * 60 * 60 * 1000));
            endTime.value = end.toTimeString().slice(0, 5);
        }
    }
    
    startTime.addEventListener('change', calculateEndTime);
    duration.addEventListener('input', calculateEndTime);
    
    // Auto-fill ticket price when ticket class changes
    const ticketClass = document.getElementById('ticketClass');
    if (ticketClass) {
        ticketClass.addEventListener('change', autoFillTicketPrice);
    }
    
    // Load draft if exists
//    const draft = localStorage.getItem('eventDraft');
//    if (draft) {
//        const eventData = JSON.parse(draft);
//        Object.keys(eventData).forEach(key => {
//            const element = document.getElementById(key);
//            if (element) {
//                if (element.type === 'checkbox') {
//                    element.checked = eventData[key] === 'on';
//                } else {
//                    element.value = eventData[key];
//                }
//            }
//        });
//        showToast('Đã tải bản nháp', 'info');
//    }
});

// Form submission
document.getElementById('eventForm').addEventListener('submit', function(e) {
    e.preventDefault();
    submitForm();
});

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

// ================= ADMIN EVENTS JS =================

// Biến phân trang
let currentPage = 1;
const itemsPerPage = 6;

let allEvents = [];       // tất cả event khi load trang
let filteredEvents = [];  // event sau khi filter/search

// ================= TOAST =================
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const messageEl = toast.querySelector('.toast-message');

    messageEl.textContent = message;
    toast.className = `toast ${type} glass`;
    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// ================= LOGOUT =================
function logout() {
    if (confirm('Bạn có chắc muốn đăng xuất?')) {
        showToast('Đang đăng xuất...', 'info');
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1000);
    }
}

// ================= FILTER TOGGLE =================
function toggleFilters() {
    const filtersSection = document.getElementById('filtersSection');
    filtersSection.classList.toggle('show');
}

// ================= CLEAR FILTER =================
function clearFilters() {
    document.getElementById('categoryFilter').value = '';
    document.getElementById('statusFilter').value = '';
    document.getElementById('dateFilter').value = '';
    document.getElementById('searchFilter').value = '';
    showToast('Đã xóa bộ lọc', 'info');

    filteredEvents = [...allEvents];
    currentPage = 1;
    renderEvents(currentPage);
}

// ================= APPLY FILTER =================
function applyFilters() {
    const search = document.getElementById('searchFilter').value;

    // Build query string
    const params = new URLSearchParams();
    if (search) params.append('keyword', search); // keyword tương ứng với controller

    showToast('Đang áp dụng bộ lọc...', 'info');

    fetch(`/admin/admin-events?${params.toString()}`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => response.text())
    .then(html => {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        const newGrid = doc.querySelector('.events-grid');

        // Cập nhật DOM
        document.querySelector('.events-grid').innerHTML = newGrid.innerHTML;

        // Cập nhật danh sách event cho phân trang
        allEvents = Array.from(document.querySelectorAll('.events-grid .event-card'));
        filteredEvents = [...allEvents];
        currentPage = 1;

        renderEvents(currentPage);
        document.getElementById('searchFilter').value = '';
        showToast('Đã áp dụng bộ lọc thành công!', 'success');
    })
    .catch(err => {
        console.error(err);
        showToast('Có lỗi xảy ra khi áp dụng bộ lọc!', 'error');
    });
}


// ================= RENDER EVENTS =================
function renderEvents(page = 1) {
    const container = document.querySelector('.events-grid');
    container.innerHTML = '';

    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const pageItems = filteredEvents.slice(start, end);

    if (pageItems.length === 0) {
        container.innerHTML = `<div class="no-products">Không có sản phẩm nào phù hợp</div>`;
    } else {
        pageItems.forEach(ev => {
            container.appendChild(ev);
            ev.style.display = 'block';
        });
    }

    renderPagination();
}

// ================= RENDER PAGINATION =================
function renderPagination() {
    const paginationContainer = document.querySelector('.pagination');
    paginationContainer.innerHTML = '';

    const totalPages = Math.ceil(filteredEvents.length / itemsPerPage);
    if (totalPages <= 1) return;

    // Prev
    const prevBtn = document.createElement('button');
    prevBtn.className = 'page-btn';
    prevBtn.innerHTML = `<i class="fas fa-chevron-left"></i>`;
    prevBtn.disabled = currentPage === 1;
    prevBtn.onclick = () => goToPage(currentPage - 1);
    paginationContainer.appendChild(prevBtn);

    // Page numbers
    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement('button');
        btn.className = 'page-btn';
        btn.textContent = i;
        if (i === currentPage) btn.classList.add('active');
        btn.onclick = () => goToPage(i);
        paginationContainer.appendChild(btn);
    }

    // Next
    const nextBtn = document.createElement('button');
    nextBtn.className = 'page-btn';
    nextBtn.innerHTML = `<i class="fas fa-chevron-right"></i>`;
    nextBtn.disabled = currentPage === totalPages;
    nextBtn.onclick = () => goToPage(currentPage + 1);
    paginationContainer.appendChild(nextBtn);
}

// ================= GO TO PAGE =================
function goToPage(page) {
    currentPage = page;
    renderEvents(currentPage);
}

// ================= EDIT / DELETE =================
function editEvent(eventId) {
    showToast(`Đang mở sự kiện ${eventId} để chỉnh sửa...`, 'info');
    setTimeout(() => {
        window.location.href = `admin-create-event.html?edit=${eventId}`;
    }, 1000);
}

function deleteEvent(eventId) {
    document.getElementById('deleteModal').style.display = 'flex';
    window.currentEventId = eventId;
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}

function confirmDelete() {
    const eventId = window.currentEventId;
    showToast(`Đang xóa sự kiện ${eventId}...`, 'info');
    closeDeleteModal();
    fetch(`/admin/delete/${eventId}`, { method: 'DELETE' })
        .then(res => {
            if (!res.ok) throw new Error(`Xóa thất bại - Status: ${res.status}`);
            window.location.href = '/admin/admin-events';
            showToast('Đã xóa sự kiện thành công!', 'success');
        })
        .catch(err => {
            console.error('Lỗi khi xóa:', err);
            showToast('Xóa sự kiện thất bại!', 'error');
        });
}

// ================== INIT ON DOM LOAD ==================
document.addEventListener('DOMContentLoaded', function() {
    // Lấy tất cả event từ DOM
    allEvents = Array.from(document.querySelectorAll('.events-grid .event-card'));
    filteredEvents = [...allEvents];

    // Ẩn tất cả để render bằng JS
    allEvents.forEach(ev => ev.style.display = 'none');

    // Render trang đầu tiên
    renderEvents(currentPage);

    // Event listener filter/search
//    document.getElementById('searchFilter').addEventListener('input', applyFilters);
    document.getElementById('categoryFilter').addEventListener('change', applyFilters);
    document.getElementById('statusFilter').addEventListener('change', applyFilters);
});

document.addEventListener('DOMContentLoaded', () => {
  const itemsPerPage = 3; // số items hiển thị 1 trang (col-md-4 -> 3 cột)
  let currentPage = 1;
  let searchTerm = '';

  const container = document.getElementById('event-container');
  const paginationEl = document.getElementById('pagination');
  const form = document.querySelector('.search-form-unique');
  const input = form ? form.querySelector('.form-control-unique') : null;
  const searchBtn = form ? form.querySelector('.btn-search-unique') : null;

  // tạo phần hiển thị "không có kết quả" nếu chưa có
  let noResultsEl = document.getElementById('no-results');
  if (!noResultsEl) {
    noResultsEl = document.createElement('div');
    noResultsEl.id = 'no-results';
    noResultsEl.style.display = 'none';
    noResultsEl.style.marginTop = '12px';
    noResultsEl.style.fontWeight = '600';
    noResultsEl.textContent = 'Không có sự kiện phù hợp';
    container.parentNode.insertBefore(noResultsEl, container.nextSibling);
  }

  // helper: normalize string (bỏ dấu tiếng Việt + lowercase)
  function normalize(str) {
    if (!str) return '';
    return str
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .toLowerCase();
  }

  function getAllEventElements() {
    // các event là các thẻ con trực tiếp của #event-container (thẻ col-md-4)
    return Array.from(container.querySelectorAll(':scope > div'));
  }

  function getMatchingEvents() {
    const all = getAllEventElements();
    if (!searchTerm) return all;
    const q = normalize(searchTerm);
    return all.filter(item => {
      const titleEl = item.querySelector('.card-title') || item.querySelector('h5') || item;
      const titleText = titleEl ? titleEl.textContent : item.textContent;
      return normalize(titleText).includes(q);
    });
  }

  function renderEvents(page) {
    const all = getAllEventElements();
    const matches = getMatchingEvents();

    // ẩn tất cả trước
    all.forEach(el => (el.style.display = 'none'));

    if (matches.length === 0) {
      noResultsEl.style.display = 'block';
      paginationEl.innerHTML = '';
      return;
    } else {
      noResultsEl.style.display = 'none';
    }

    const pageCount = Math.max(1, Math.ceil(matches.length / itemsPerPage));
    if (page > pageCount) {
      page = pageCount;
      currentPage = page;
    }

    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    matches.slice(start, end).forEach(el => (el.style.display = ''));

    renderPagination();
  }

  function renderPagination() {
    const matches = getMatchingEvents();
    const total = matches.length;
    paginationEl.innerHTML = '';

    if (total === 0) return;

    const pageCount = Math.ceil(total / itemsPerPage);

    // Prev
    const prevLi = document.createElement('li');
    prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
    prevLi.innerHTML = `<a class="page-link" href="#" aria-label="Previous">«</a>`;
    prevLi.style.cursor = currentPage === 1 ? 'default' : 'pointer';
    prevLi.addEventListener('click', (e) => {
      e.preventDefault();
      if (currentPage > 1) {
        currentPage--;
        renderEvents(currentPage);
      }
    });
    paginationEl.appendChild(prevLi);

    // Pages
    for (let i = 1; i <= pageCount; i++) {
      const li = document.createElement('li');
      li.className = `page-item ${currentPage === i ? 'active' : ''}`;
      li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
      li.style.cursor = 'pointer';
      li.addEventListener('click', (e) => {
        e.preventDefault();
        currentPage = i;
        renderEvents(currentPage);
      });
      paginationEl.appendChild(li);
    }

    // Next
    const nextLi = document.createElement('li');
    nextLi.className = `page-item ${currentPage === pageCount ? 'disabled' : ''}`;
    nextLi.innerHTML = `<a class="page-link" href="#" aria-label="Next">»</a>`;
    nextLi.style.cursor = currentPage === pageCount ? 'default' : 'pointer';
    nextLi.addEventListener('click', (e) => {
      e.preventDefault();
      if (currentPage < pageCount) {
        currentPage++;
        renderEvents(currentPage);
      }
    });
    paginationEl.appendChild(nextLi);
  }

  // update toàn bộ view
  function update() {
    renderEvents(currentPage);
  }

  // prevent form submit (HTML form có action -> ta xử lý trên client)
  if (form) {
    form.addEventListener('submit', (e) => {
      e.preventDefault();
    });
  }

  // khi bấm nút tìm
  if (searchBtn) {
    searchBtn.addEventListener('click', () => {
      searchTerm = input ? input.value.trim() : '';
      currentPage = 1;
      update();
    });
  }

  // enter trong ô input cũng kích hoạt tìm
  if (input) {
    input.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') {
        e.preventDefault();
        searchTerm = input.value.trim();
        currentPage = 1;
        update();
      }
      // nếu người dùng xoá hết input -> show tất cả
      if (e.key === 'Backspace' || e.key === 'Delete') {
        setTimeout(() => {
          if (input.value.trim() === '') {
            searchTerm = '';
            currentPage = 1;
            update();
          }
        }, 0);
      }
    });
  }

  // khởi tạo hiển thị ban đầu
  update();
});

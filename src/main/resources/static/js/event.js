const itemsPerPage = 3;
let currentPage = 1;
let events = []; // sẽ load từ file JSON

function renderEvents(page) {
    const container = document.getElementById("event-container");
    container.innerHTML = "";

    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const paginatedItems = events.slice(start, end);

    paginatedItems.forEach(event => {
        const card = document.createElement("div");
        card.className = "col-md-4";
        card.setAttribute("data-id", event.id);
        card.innerHTML = `
          <div class="card event-card shadow-sm h-100">
            <img src="${event.image}" class="card-img-top" alt="${event.title}">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title">${event.title}</h5>
              <p class="card-text flex-grow-1">${event.description}</p>
              <p class="mb-1"><strong>Date:</strong> ${event.date}</p>
              <p class="mb-1"><strong>Duration:</strong> ${event.startTime} - ${event.endTime}</p>
              <p class="mb-1"><strong>Location:</strong> ${event.location}</p>
              <p class="mb-0"><strong>Tickets left:</strong> ${event.remainingTickets}</p>
              <div class="d-flex gap-2 align-items-center justify-content-center">
                <a href="/customer/event-details" class="btn btn-primary mt-auto">Details</a>
                <a href="/customer/buy-ticket" class="btn btn-success mt-auto">Buy Ticket</a>
              </div>
            </div>
          </div>
        `;
        container.appendChild(card);
    });
}

function renderPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";

    const pageCount = Math.ceil(events.length / itemsPerPage);

    // Prev button
    const prevLi = document.createElement("li");
    prevLi.className = `page-item ${currentPage === 1 ? "disabled" : ""}`;
    prevLi.innerHTML = `<a class="page-link" href="#">«</a>`;
    prevLi.addEventListener("click", e => {
        e.preventDefault();
        if (currentPage > 1) {
            currentPage--;
            update();
        }
    });
    pagination.appendChild(prevLi);

    // Page numbers
    for(let i = 1; i <= pageCount; i++) {
        const li = document.createElement("li");
        li.className = `page-item ${currentPage === i ? "active" : ""}`;
        li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
        li.addEventListener("click", e => {
            e.preventDefault();
            currentPage = i;
            update();
        });
        pagination.appendChild(li);
    }

    // Next button
    const nextLi = document.createElement("li");
    nextLi.className = `page-item ${currentPage === pageCount ? "disabled" : ""}`;
    nextLi.innerHTML = `<a class="page-link" href="#">»</a>`;
    nextLi.addEventListener("click", e => {
        e.preventDefault();
        if (currentPage < pageCount) {
            currentPage++;
            update();
        }
    });
    pagination.appendChild(nextLi);
}

function update() {
    renderEvents(currentPage);
    renderPagination();
}

// Load data từ file JSON rồi gọi update
fetch('/json/events.json')
    .then(res => res.json())
    .then(data => {
        events = data;
        update();
    })
    .catch(err => {
        console.error("Lỗi khi load data.json:", err);
        // Nếu lỗi thì có thể để mảng rỗng hoặc dữ liệu tạm
        events = [];
        update();
    });

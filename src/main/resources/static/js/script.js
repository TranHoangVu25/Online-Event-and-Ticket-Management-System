document.addEventListener("DOMContentLoaded", () => {
    const navbarMenu = document.querySelector(".navbar .links");
    const hamburgerBtn = document.querySelector(".hamburger-btn");
    const hideMenuBtn = navbarMenu?.querySelector(".close-btn");
    const showPopupBtn = document.querySelector(".login-btn");
    const formPopup = document.querySelector(".form-popup");
    const hidePopupBtn = formPopup?.querySelector(".close-btn");
    const signupLoginLink = formPopup?.querySelectorAll(".bottom-link a");

    if (hamburgerBtn && navbarMenu && hideMenuBtn) {
        hamburgerBtn.addEventListener("click", () => {
            navbarMenu.classList.toggle("show-menu");
        });
        hideMenuBtn.addEventListener("click", () => hamburgerBtn.click());
    }

    if (showPopupBtn && formPopup && hidePopupBtn) {
        showPopupBtn.addEventListener("click", () => {
            document.body.classList.toggle("show-popup");
        });
        hidePopupBtn.addEventListener("click", () => showPopupBtn.click());
    }

    if (signupLoginLink) {
        signupLoginLink.forEach(link => {
            link.addEventListener("click", (e) => {
                e.preventDefault();
                formPopup.classList[link.id === 'signup-link' ? 'add' : 'remove']("show-signup");
            });
        });
    }
});


// hàm format thời gian
function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    let hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12 || 12; // đổi sang 12h format

    return `${day}/${month}/${year} ${String(hours).padStart(2, '0')}:${minutes} ${ampm}`;
}

//hàm format số tiền
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN').format(amount) + ' VN';
}

window.viewOrderDetail = function (orderId, ticketClassId) {
    fetch(`/user/order-detail/${orderId}/${ticketClassId}`)
        .then(res => res.json())
        .then(order => {
            document.getElementById('detailOrderId').textContent = order.orderId;
            document.getElementById('detailOrderDate').textContent = formatDate(new Date(order.orderDate));
            document.getElementById('detailEventTitle').textContent = order.eventName;
            document.getElementById('detailTicketImage').src = order.thumbnailUrl;
            document.getElementById('detailEventDate').textContent = formatDate(new Date(order.eventStartTime));
            document.getElementById('detailEventLocation').textContent = order.eventLocation;
            document.getElementById('detailTicketClass').textContent = order.ticketClassName;
            document.getElementById('detailQuantity').textContent = order.quantity;
            document.getElementById('detailTotal').textContent = formatCurrency(order.totalAmount);


            document.getElementById('orderDetailsModal').style.display = 'block';
        })
        .catch(err => {
            console.error('Lỗi khi lấy order:', err);
            alert('Không thể lấy thông tin đơn hàng');
        });
};



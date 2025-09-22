const navbarMenu = document.querySelector(".navbar .links");
const hamburgerBtn = document.querySelector(".hamburger-btn");
const hideMenuBtn = navbarMenu.querySelector(".close-btn");
const showPopupBtn = document.querySelector(".login-btn");
const formPopup = document.querySelector(".form-popup");
const hidePopupBtn = formPopup.querySelector(".close-btn");
const signupLoginLink = formPopup.querySelectorAll(".bottom-link a");

// Show mobile menu
hamburgerBtn.addEventListener("click", () => {
    navbarMenu.classList.toggle("show-menu");
});

// Hide mobile menu
hideMenuBtn.addEventListener("click", () =>  hamburgerBtn.click());

// Show login popup
showPopupBtn.addEventListener("click", () => {
    document.body.classList.toggle("show-popup");
});

// Hide login popup
hidePopupBtn.addEventListener("click", () => showPopupBtn.click());

// Show or hide signup form
signupLoginLink.forEach(link => {
    link.addEventListener("click", (e) => {
        e.preventDefault();
        formPopup.classList[link.id === 'signup-link' ? 'add' : 'remove']("show-signup");
    });
});



function viewOrderDetail(orderId, ticketClassId) {
    console.log(`Đang mở chi tiết orderId=${orderId}, ticketClassId=${ticketClassId}...`);

    fetch(`/user/order-detail/${orderId}/${ticketClassId}`)
        .then(res => res.json())
        .then(order => {
            document.getElementById('detailTicketImage').src = order.thumbnailUrl;
            document.getElementById('detailOrderId').textContent = order.orderId;
            document.getElementById('detailOrderDate').textContent = order.createdAt;
            document.getElementById('detailEventTitle').textContent = order.eventName;
            document.getElementById('detailEventDate').textContent = order.startTime;
            document.getElementById('detailEventLocation').textContent = order.addressDetail;
            document.getElementById('detailTicketClass').textContent = order.ticketClassName;
            document.getElementById('detailQuantity').textContent = order.quantity;
            document.getElementById('detailTotal').textContent = order.totalAmount;

            // mở modal
            document.getElementById('orderDetailsModal').style.display = 'block';
        })
        .catch(err => {
            console.error('Lỗi khi lấy order:', err);
            alert('Không thể lấy thông tin đơn hàng');
        });
}


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


document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".show-order-details");

    buttons.forEach(btn => {
        btn.addEventListener("click", function () {
            document.getElementById("modal-img").src = this.dataset.img;
            document.getElementById("modal-id").textContent = this.dataset.id;
            document.getElementById("modal-date").textContent = this.dataset.date;
            document.getElementById("modal-event").textContent = this.dataset.event;
            document.getElementById("modal-eventdate").textContent = this.dataset.eventdate;
            document.getElementById("modal-eventtime").textContent = this.dataset.eventtime;
            document.getElementById("modal-location").textContent = this.dataset.location;
            document.getElementById("modal-code").textContent = this.dataset.code;
            document.getElementById("modal-class").textContent = this.dataset.class;
            document.getElementById("modal-qty").textContent = this.dataset.qty;

            // Format số có dấu chấm (VD: 200000 -> 200.000)
            const formatNumber = (num) => {
                return new Intl.NumberFormat('vi-VN').format(num);
            };

            document.getElementById("modal-price").textContent = formatNumber(this.dataset.price);
            document.getElementById("modal-shipping").textContent = formatNumber(this.dataset.shipping);
            document.getElementById("modal-total").textContent = formatNumber(this.dataset.total);
        });
    });
});

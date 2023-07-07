const passwordModalButton = document.querySelector('.password-modal-button');
const passwordModalContainer = document.getElementById('password-modal-container');
const passwordModalContent = document.getElementById('password-modal-content');
const passwordcloseBtn = document.getElementById("password-modal-close");

passwordModalButton.addEventListener('click', () => {
    passwordModalContainer.classList.add('modal-show');
    console.log(closeBtn);
});

passwordModalContent.addEventListener('click', (event) => {
    event.stopPropagation();
});

passwordcloseBtn.addEventListener('click',(e)=>{
    closePasswordModal();
})

window.addEventListener('click', (event) => {
    if (event.target !== passwordModalContainer ) {
        return;
    }
    closePasswordModal();
});

function closePasswordModal() {
    passwordModalContainer.classList.remove('modal-show');
}
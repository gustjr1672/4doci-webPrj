const pwdModalButton = document.querySelector('.password-modal-button');
const pwdModalContainer = document.getElementById('password-modal-container');
const pwdModalContent = document.getElementById('password-modal-content');
const pwdModalCloseBtn = document.getElementById("password-modal-close");

const nowPwd = document.getElementById("pwd");
const newPwd = document.getElementById("new-pwd");
const checkNewPwd = document.getElementById("pwd-check");

let newPwdError  = document.getElementById("new-pwd-error");
let checkNewPwdError = document.getElementById("pwd-check-error");

const editFinishBtn = document.getElementById('password-finish-btn');
pwdModalButton.addEventListener('click', () => {
    pwdModalContainer.classList.add('modal-show');
});

pwdModalContent.addEventListener('click', (event) => {
    event.stopPropagation();
});

pwdModalCloseBtn.addEventListener('click', (e) => {
    closePasswordModal();
})

window.addEventListener('click', (event) => {
    if (event.target !== pwdModalContainer) {
        return;
    }
    closePasswordModal();
});

function closePasswordModal() {
    pwdModalContainer.classList.remove('modal-show');
    inputFormReset();
}

function inputFormReset(){
    newPwd.value = "";
    checkNewPwd.value = "";
    nowPwd.value = "";
    checkNewPwdError.innerHTML = "";
    newPwdError.innerHTML = "";
    newPasswordCheck = false;
    duplicateCheck = false;
    editFinishBtn.disabled = true;
    editFinishBtn.style.backgroundColor = '#ccc';
}

// ====================비밀번호 유효성========================
let nowPasswordCheck = false
let newPasswordCheck = false;
let duplicateCheck = false;


nowPwd.addEventListener('input',()=>{
    let nowPassword = nowPwd.value;
    if (nowPassword){
        nowPasswordCheck=true;
    }
    else {
        nowPasswordCheck = false;
    }
    turnOnFinishBtn();
})

newPwd.addEventListener('input', checkNewPassword);
checkNewPwd.addEventListener('input', checkPwdDuplicate);
function checkNewPassword() {

    let passwordValue = newPwd.value;
    let regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/;
    if (passwordValue && !regex.test(passwordValue)) {
        newPwdError.innerHTML = "비밀번호는 숫자,영문자,특수문자를<br>모두 포함하여 8자 이상입니다";
        newPasswordCheck = false;
    } else {
        newPwdError.innerHTML = "";
        newPasswordCheck = true;
    }
    turnOnFinishBtn();
}

function checkPwdDuplicate() {
    let passwordValue = newPwd.value;
    let pwdCheckValue = checkNewPwd.value;

    if (passwordValue && pwdCheckValue && passwordValue != pwdCheckValue) {
        checkNewPwdError.innerHTML = "새 비밀번호가 일치하지 않습니다.";
        duplicateCheck = false;
    } else {
        checkNewPwdError.innerHTML = "";
        duplicateCheck= true;
    }
    turnOnFinishBtn();
}

function turnOnFinishBtn(){
    if (duplicateCheck && newPasswordCheck && nowPasswordCheck ){
        editFinishBtn.disabled = false;
        editFinishBtn.style.backgroundColor = '#383d66';
    }
    else{
        editFinishBtn.disabled = true;
        editFinishBtn.style.backgroundColor = '#ccc';
    }
}

let submitCompleteModal = document.querySelector(".complete-modal");
let resultText = submitCompleteModal.querySelector(".result-text");
let okayButton = submitCompleteModal.querySelector(".modal-close");

editFinishBtn.addEventListener('click',matchNowPassword);

function matchNowPassword(){
    let inputNowPwd = nowPwd.value;
    let inputNewPwd = newPwd.value;

    let data = new URLSearchParams();
    data.append("nowPwd", inputNowPwd);
    data.append("newPwd", inputNewPwd);
    fetch(`api/user/password`,{
        method:'POST',
        body: data
    })
        .then(response => response.json())
        .then(result =>{

            closePasswordModal();
            if (result) resultText.innerHTML = "비밀번호가 변경되었습니다.";
            else {
                resultText.innerHTML = "현재 비밀번호가 일치하지 않습니다";
                // resultText.style.color = "#ff8f8f"
            }
            submitCompleteModal.classList.remove('hidden');
        })
}
okayButton.addEventListener('click',()=>{
    submitCompleteModal.classList.add('hidden')
})
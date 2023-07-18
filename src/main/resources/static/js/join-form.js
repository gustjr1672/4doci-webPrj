
const id = document.getElementById("user-id");
const nickname = document.getElementById("nickname");
const pwd = document.getElementById("pwd");
const pwdCheck = document.getElementById("pwd-check");
const finishBtn = document.querySelector('.finish-btn button');
const inputs = document.querySelectorAll('input:not(.user-id)');
let idValidity;
let nicknameValidity = false;

function checkId(idValue) {        // ID 유효성검사
    const regex = /^[a-zA-Z0-9]{4,12}$/;
    if (!regex.test(idValue)) {//유효성검사 통과하지 못할 때{
        document.getElementById("id-error").innerHTML = "아이디는 숫자,영문자를 포함하여 4자 이상 12자 이하입니다";
        return false;
    }
    document.getElementById("id-error").innerHTML = "";
    return true;
    
}

function checkNickname(nicknameValue){  // 닉네임 유효성검사
    const regex = /^[가-힣]{2,7}$/;
    if(!regex.test(nicknameValue)){
        document.getElementById("nickname-error").innerHTML = "닉네임은 한글로 2자 이상 7자 이하입니다.";
        return false;
    }
    document.getElementById("nickname-error").innerHTML = "";
    return true;
}

const idValidSection = document.querySelector(".id-input-form");
const idvalidBtn = idValidSection.querySelector("button");

idvalidBtn.onclick = function() {    // ID 중복검사
    idValidity = false;
    let idValue = id.value;
    const idError = document.getElementById("id-error");
    if (checkId(idValue)) {
        fetch(`/api/join/idCheck?id=${idValue}`)
            .then(response=> response.text())
            .then(result=>{
                if (result === "true"){
                    idError.innerHTML = "사용 가능한 아이디입니다";
                    idError.style.color="#6167FF";
                    idValidity = true;
                    if (checkInputValid()) {        
                        finishBtn.disabled = false;
                        finishBtn.style.backgroundColor = '#383d66';
                        finishBtn.classList.add("brighten-2 cursor")
                    }
                    return;
                }
                idError.style.color="red";
                idError.innerHTML = "이미 존재하는 아이디 입니다";
            })
    }
}

nickname.addEventListener("input", chkNicknameValidity);
function chkNicknameValidity(){    // 닉네임 중복검사
    let nicknameValue = nickname.value;
    if (checkNickname(nicknameValue)) {
        fetch(`/api/join/nicknameCheck?nic=${nicknameValue}`)
            .then(response=> response.text())
            .then(result=>{
                if (result === "true"){
                    nicknameValidity = true;
                    return;
                }
                document.getElementById("nickname-error").innerHTML = "이미 존재하는 닉네임 입니다";
            })
    }
}


function checkPassword() {
    let passwordValue = pwd.value;
    let regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/;
    if (passwordValue && !regex.test(passwordValue)) {
        document.getElementById("pwd-error").innerHTML = "비밀번호는 숫자,영문자,특수문자를<br>모두 포함하여 8자 이상입니다";
        return false;
    } else {
        document.getElementById("pwd-error").innerHTML = "";
        return true;

    }
}

function checkDuplicate() {
    let passwordValue = pwd.value;
    let pwdCheckValue = pwdCheck.value;
    if (passwordValue && pwdCheckValue && passwordValue != pwdCheckValue) {
        document.getElementById("pwd-check-error").innerHTML = "비밀번호가 일치하지 않습니다.";
        return false;
    } else {
        document.getElementById("pwd-check-error").innerHTML = "";
        return true;
    }
}

let inputValid;
function checkInputValid() {
    inputValid = true; 
    const email = document.getElementById("email").value;
    const nickname = document.getElementById("nickname").value;
    
    for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].value === '') {
            inputValid =  false; // 어느 하나라도 비어있다면 유효하지 않음
        }
    }
    if (!checkPassword()) {
        inputValid = false;
    }
    if (!checkDuplicate()) {
        inputValid = false;// 비밀번호와 비밀번호 확인이 일치하지 않음
    }

    if(!idValidity) 
        inputValid = false;

    if(!nicknameValidity)
        inputValid = false;

    return inputValid; // 모든 입력값이 유효하다면 true 반환
}

pwd.addEventListener("input", checkPassword);
pwdCheck.addEventListener("input", checkDuplicate);

for (let i = 0; i < inputs.length; i++) {
    inputs[i].addEventListener('input', function () {
        checkInputValid();
        if (inputValid == true) {
            finishBtn.disabled = false;
            finishBtn.style.backgroundColor = '#383d66';
            finishBtn.classList.add("brighten-2 cursor");
        }
        else{
            finishBtn.disabled = true;
            finishBtn.style.backgroundColor = '#ccc';
            finishBtn.classList.remove("brighten-2 cursor");
        }
    });
}




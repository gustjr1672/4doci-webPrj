
const id = document.getElementById("user-id");
const nickname = document.getElementById("nickname");
const pwd = document.getElementById("pwd");
const pwdCheck = document.getElementById("pwd-check");
const finishBtn = document.querySelector('.finish-btn button');
const inputs = document.querySelectorAll('input:not(.user-id)');
let idValidity;
let nicknameValidity = false;

function checkId(idValue) {
    const regex = /^[a-zA-Z0-9]{4,12}$/;
    if (!regex.test(idValue)) {//유효성검사 통과하지 못할 때{
        document.getElementById("id-error").innerHTML = "아이디는 숫자,영문자를 포함하여 4자 이상 12자 이하입니다";
        return false;
    }
    document.getElementById("id-error").innerHTML = "";
    return true;
    
}

function checkNickname(nicknameValue){
    const regex = /^[가-힣]{2,8}$/; 
    if(!regex.test(nicknameValue)){
        document.getElementById("nickname-error").innerHTML = "닉네임은 한글로 2자 이상 8자 이하입니다.";
        return false;
    }
    document.getElementById("nickname-error").innerHTML = "";
    return true;
}

const idValidSection = document.querySelector(".id-input-form");
const idvalidBtn = idValidSection.querySelector("button");

idvalidBtn.onclick = function() {
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
                    return;
                }
                idError.innerHTML = "이미 존재하는 아이디 입니다";
            })
    }
}

nickname.addEventListener("input", chkNicknameValidity);
function chkNicknameValidity(){
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





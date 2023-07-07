const nicknameModalButton = document.querySelector('.nickname-modal-button');
const nicknameModalContainer = document.getElementById('nickname-modal-container');
const nicknameModalContent = document.getElementById('nickname-modal-content');
const closeBtn = document.getElementById("nickname-modal-close");


let nickname = document.getElementById("nickname");
let nicknameValidity = false;
let nicknameError = document.getElementById("nickname-error");
const finishBtn = document.querySelector('.finish-btn button');
let loginUserNickname = document.querySelector('.nickname');


// ==============================모달============================
nicknameModalButton.addEventListener('click', () => {
    nicknameModalContainer.classList.add('modal-show');
    nickname.value = loginUserNickname.textContent;
});

nicknameModalContent.addEventListener('click', (event) => {
    event.stopPropagation();
});
closeBtn.addEventListener('click',(e)=>{
    closeNicknameModal();
})
window.addEventListener('click', (event) => {
    if (event.target === nicknameModalContainer ) {
        closeNicknameModal();
    }
});
function closeNicknameModal() {
    nicknameModalContainer.classList.remove('modal-show');
    nickname.value = loginUserNickname.textContent;
    nicknameError.innerHTML = "";
    finishBtn.style.backgroundColor = '#ccc';
    finishBtn.disabled = true;
}

// ===========================닉네임 유효성 검사==============================


nickname.addEventListener("input", chkNicknameValidity);
function chkNicknameValidity(){    // 닉네임 중복검사
    let nicknameValue = nickname.value;

    if (checkNickname(nicknameValue)) {
        fetch(`/api/join/nicknameCheck?nic=${nicknameValue}`)
            .then(response=> response.text())
            .then(result=>{
                if (result === "true"){
                    nicknameValidity = true;
                    nicknameError.innerHTML = "사용 가능한 아이디입니다";
                    nicknameError.style.color="#6167FF";
                    finishBtn.disabled = false;
                    finishBtn.style.backgroundColor = '#383d66';
                    return;
                }
                nicknameError.style.color= "red";
                nicknameError.innerHTML = "이미 존재하는 닉네임 입니다";
                finishBtn.style.backgroundColor = '#ccc';
                finishBtn.disabled = true;
            })
    }
}

function checkNickname(nicknameValue){  // 닉네임 유효성검사
    const regex = /^[가-힣]{2,8}$/;
    if(!regex.test(nicknameValue)){
        nicknameError.style.color= "red";
        finishBtn.style.backgroundColor = '#ccc';
        finishBtn.disabled = true;
        document.getElementById("nickname-error").innerHTML = "닉네임은 한글로 2자 이상 8자 이하입니다.";
        return false;
    }
    document.getElementById("nickname-error").innerHTML = "";
    return true;
}



finishBtn.addEventListener('click',()=>{
    let data = new URLSearchParams();
    data.append('nickname',nickname.value);

    fetch(`/member/nickname`,{
        method:'PUT',
        body: data
    }).then(response=> response.text())
        .then(result=>{
            loginUserNickname.textContent=result;
            nickname.value = result;
        })
})
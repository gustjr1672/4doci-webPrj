history.pushState(null, null, "www.4docci.today/welcome");

window.onpopstate = function(event) {
    history.go(1);
};

let nickname = document.getElementById("nickname");
const finishBtn = document.querySelector('.finish-btn button');
let nicknameError = document.getElementById("nickname-error");

nickname.addEventListener("input", checkNicknameValidity);

function checkNicknameValidity(){    // 닉네임 중복검사
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
        nicknameError.innerHTML = "닉네임은 한글로 2자 이상 8자 이하입니다.";
        return false;
    }
    nicknameError.innerHTML = "";
    return true;
}
finishBtn.addEventListener('click',()=>{
    let completeModal = document.querySelector(".complete-modal");
    completeModal.classList.remove("hidden")
})
//
// finishBtn.addEventListener('click',()=>{
//     let data = new URLSearchParams();
//     data.append('nickname',nickname.value);
//
//     fetch(`/member/nickname`,{
//         method:'PUT',
//         body: data
//     }).then(response=> response.text())
//         .then(result=>{
//             nickname.value = result;
//         })
// })
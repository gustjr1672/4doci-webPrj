let checkSuccess = false;

// 인증번호와 타이머는 기존의 실행되던걸 초기화해야해서 전역변수로 생성
let code; // 인증번호 변수
let intervalId; // 타이머 변수


function startTimer(duration, display) { //타이머
  checkSuccess = false;
  let timer = duration,
    minutes,
    seconds;
  clearInterval(intervalId); // 기존에 실행되고 있는 타이머는 멈춤

  document.getElementById("message").textContent = "";
  intervalId = setInterval(function () {
    minutes = parseInt(timer / 60, 10); //문자열로 된 timer 변수를 10진수 int 로 변환
    seconds = parseInt(timer % 60, 10);

    minutes = minutes < 10 ? "0" + minutes : minutes; // 10보다 작은 수는 앞에 0 을 붙히기 위한 코드
    seconds = seconds < 10 ? "0" + seconds : seconds;

    display.textContent = minutes + ":" + seconds; // 분과 초를 표시하기 위한 코드

    if (timer === 0) {
      clearInterval(startTimer);
      document.getElementById("message").textContent = "인증번호를 다시 받아주세요.";
      code = "";
    }
    else if (checkSuccess == true)
      return;
    else if (--timer < 0) {  // 타이머가 0보다 작아지면 초기값으로 설정합니다.
      timer = duration;
    }
  }, 1000);


}

function addcheck() { // 인증번호 입력칸 및 버튼 추가 코드

  const container = document.getElementById("checkContainer");
  container
  container.innerHTML = `
     
        <div class="check-input">
          <label for="check">인증번호</label>
          <div class="check-input-form">
          <input type="text" id="check" name="check" required />
          <button type="button" onclick="chkEmailConfirm(${code})">인증확인</button>
        </div>
        <div class="timer-wrap">
        <span id="message"></span>
        <span class="timer">03:00</span>
        </div>
      <div id="auth-btn-Container"></div>
      `;

}

function addBtn() { // 최종완료버튼 추가 코드
  const container = document.getElementById("auth-btn-Container")
  container.innerHTML = `
      <div class="auth-btn">
      <button type="submit">완료</button>
    </div>
    `
}


function chkEmailValidity() {  // 이메일의 유효성 검사 함수
  // email.addEventListener('keyup', function() {
  const error = document.getElementById("error");
  if (!(email.checkValidity())) {
    error.style.display = "block";
    error.innerHTML = "유효한 이메일 주소를 입력해주세요";
    return false;
  }
  error.innerHTML = "";
  error.style.display = "none";
  return true;
  // });
}

let authBtn = document.getElementById("auth-btn");
let email = document.getElementById("email");

//인증번호 받기 버튼 누르면 모달창 띄우고, 인증번호 입력버튼 생기고, 이메일 발송
authBtn.addEventListener("click", () => {

  chkEmailValidity(); // 이메일 유효성 확인

  if (chkEmailValidity()) {
    //  sendEmail();
    let value = email.value;
    let url = `/common/email-verification?email=${value}`;
    sendEmail(url);
  }
});

function sendEmail(url) {
  fetch(url, {
    method: 'POST'
  }).then(response => response.json())
    .then(authCode => {
      code = authCode;
      openModal();   // 전송 모달창 띄움
      addcheck();    // 인증번호 버튼 생김
    })
};


// controller 에서 받은 인증번호와 내가 입력한 인증번호를 비교한 뒤 완료버튼 생성
function chkEmailConfirm() {
  let checkinput = document.getElementById("check");
  const checkinputTxt = document.getElementById("message")
  // checkinput.addEventListener('keyup', function() {
  if (code.toString() !== checkinput.value || checkinput.value == "") {
    // checkSuccess = false;
    checkinputTxt.textContent = "인증번호가 잘못되었습니다."
  }
  else {
    checkSuccess = true;
    checkinputTxt.textContent = "인증번호 확인이 완료되었습니다.";
    checkinputTxt.style.color = "#6167FF";
    addBtn();
  }
}

const modal = document.getElementById("modal-wrap");
const closeBtn = modal.querySelector(".close-btn"); 

function openModal() { // 이메일 전송 모달창 띄움
  modal.style.display = "block";
}

closeBtn.onclick = function() { //이메일 전송 모달창 닫고 타이머 실행
  modal.style.display = "none";
  const timerDisplay = document.querySelector(".timer");
  startTimer(179, timerDisplay);
}





const uniqueId = document.getElementById("cid").dataset.uniqueId;

let giveUpButton = document.getElementById("challenge-give-up");
let nowStartButton = document.getElementById("challenge-now-start");

let checkModal = document.querySelector(".choice-modal");
let checkModalClose = document.getElementById("close-btn");
let giveUpBtn = document.getElementById("give-up-btn");
let startBtn = document.getElementById("start-btn");
let content = document.querySelector(".content");

let completeModal = document.querySelector(".complete-modal");
let completeModalClose = completeModal.querySelector(".modal-close");
let completeModalOk = completeModal.querySelector(".complete-ok");
let completeContent = completeModal.querySelector(".complete-content");
giveUpButton.addEventListener("click", function () {
  content.innerHTML = `도전을 포기하시겠습니까? <br> 포기할 시 해당 도전이 삭제됩니다.`;
  giveUpBtn.classList.remove("hidden");
  startBtn.classList.add("hidden");
  checkModal.classList.remove("hidden");
});

checkModalClose.addEventListener("click", function () {
  checkModal.classList.add("hidden");
});
giveUpBtn.addEventListener("click", function () {
  completeContent.innerHTML = "도전이 삭제되었습니다.";
  completeModal.classList.remove("hidden");
  completeModalClose.classList.remove("hidden");
});
completeModalClose.addEventListener("click", function () {
  location.href = `performance-records/delete?cid=${uniqueId}`;
});

// =========================바로시작하기==========================

nowStartButton.addEventListener("click", function () {
  content.innerHTML = "도전을 지금 바로 시작하시겠습니까?";
  giveUpBtn.classList.add("hidden");
  startBtn.classList.remove("hidden");
  checkModal.classList.remove("hidden");
});

startBtn.addEventListener("click", function () {
  let challengeId = uniqueId.split("_")[1];
  if (uniqueId.includes("CH")) {
    fetch(`/challenge/choice/${challengeId}`, {
      method: "PUT",
    }).then((response) => nowStartComplete());
  } else if (uniqueId.includes("FC")) {
    fetch(`/challenge/freeChallenge/${challengeId}`, {
      method: "PUT",
    }).then((response) => nowStartComplete());
  }
});

function nowStartComplete() {
  completeContent.innerHTML = "도전을 시작하였습니다.";
  completeModal.classList.remove("hidden");
  completeModalOk.classList.remove("hidden");
}

completeModalOk.addEventListener("click", function () {
  location.href = "/main";
});

function getDaysUntilStart(startDate) {
  const today = new Date();
  const start = new Date(startDate);
  const diffInMilliseconds = start - today;
  const oneDay = 24 * 60 * 60 * 1000; // 1일을 밀리초로 계산

  const remainingDays = Math.ceil(diffInMilliseconds / oneDay);

  if (remainingDays > 0) {
    return remainingDays;
  } else if (remainingDays === 0) {
    return 0;
  } else {
    return -1;
  }
}

const startDateSpan = document.getElementById("startDateText");
const startDateText = startDateSpan.innerText;
const daysLeft = getDaysUntilStart(startDateText);

const daysLeftSpan = document.getElementById("dayLeftSpan");
daysLeftSpan.innerText = `D - ${daysLeft}`;

//도전기간 수정 모달
let dateSection = document.querySelector(".challenge-period");
//let dateModifyBtn = document.querySelector(".modify-date");
const dateModal = document.getElementById("date-modal-container");
let modalCloseBtn = document.querySelector(".edit-modal-close");
let endAfterAlert = document.querySelector(".alert");
let endDateAlert = document.querySelector(".end-date-alert");

dateSection.addEventListener("click", function (e) {
  if (e.target.classList.contains("modify-date")) {
    dateModal.classList.add("modal-show");
  }
});
// dateModifyBtn.onclick = function (e) {
//     dateModal.classList.add("modal-show");
// }
modalCloseBtn.onclick = function (e) {
  dateModal.classList.remove("modal-show");
  endAfterAlert.classList.add("hidden");
  endDateAlert.classList.add("hidden");
};

//도전기간 수정완료시 db에 저장
let dateBtn = document.querySelector(".finish-btn");
let alertChangeModal = document.getElementById("alert-change-modal");
let startDate = document.querySelector("#start_inside");
let endDate = document.querySelector("#end_inside");

endDate.addEventListener("input", function () {
  if (this.value) {
    endDateAlert.classList.add("hidden");
  }
});
dateBtn.onclick = function (e) {
  let currentDate = new Date();
  let startDate = document.getElementById("start_inside").value;
  if (startDate === currentDate.toISOString().split("T")[0]) {
    return;
  }

  let endDate = document.getElementById("end_inside").value;
  if (endDate == "") {
    endDateAlert.classList.remove("hidden");
    endAfterAlert.classList.add("hidden");
    return;
  } else endDateAlert.classList.add("hidden");

  let challengeId = uniqueId.split("_")[1];
  const data = {
    startDate: startDate,
    endDate: endDate,
    challengeId: challengeId,
  };

  if (uniqueId.includes("FC")) {
    fetch("api/freechallenge/date", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((challenge) => {
        console.log(typeof challenge);
        console.log(challenge);
        reprintPeriod(challenge);
      });
  } else if (uniqueId.includes("CH")) {
    fetch("api/randomchoice/date", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((challenge) => {
        console.log(typeof challenge);
        console.log(challenge);
        reprintPeriod(challenge);
      });
  }
};

//function 으로 바꾸기
function reprintPeriod(challenge) {
  //변경완료 모달띄우고
  alertChangeModal.classList.remove("hidden");
  dateModal.classList.remove("modal-show");
  setTimeout(function () {
    alertChangeModal.classList.add("hidden");
  }, 3000);

  //도전기간 재출력

  dateSection.innerHTML = "";
  let newDateTemplate = `
      <div class="modify">
        <p>도전기간</p>
        <button class="modify-date cursor bold-2">수정</button>
      </div>
      <div class="date">
          <p>시작일</p>
          <span id="startDateText">${challenge.startDate}</span>
      </div>
      <div class="date">
          <p>종료일</p>
          <span>${challenge.endDate}</span>
      </div>
            `;
  dateSection.insertAdjacentHTML("beforeend", newDateTemplate);
}

let today = new Date().toISOString().split("T")[0];

startDate.setAttribute("min", today);
endDate.setAttribute("min", today);

startDate.addEventListener("change", () => {
  endDate.value = null;
});

endDate.addEventListener("change", () => {
  if (startDate.value >= endDate.value) {
    endAfterAlert.classList.remove("hidden");
    endDateAlert.classList.add("hidden");
    endDate.value = null;
  } else {
    endAfterAlert.classList.add("hidden");
  }
});

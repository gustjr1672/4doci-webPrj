const modal = document.querySelector("#modal");
const closeBtn = modal.querySelector(".btns button:nth-child(2)");
const friendsSection = document.querySelector(".profile-wrap");
const friendModalSection = document.querySelector(".friends-list");
const submitBtn = document.querySelector(".submit-btn");
const cancelModal = document.getElementById("cancel-modal");

let leaderId = document.querySelector(".leader-id").value;
let challengeId = document.querySelector(".challenge-id").value;
let userId;

friendsSection.addEventListener("click", function (e) {
  if (e.target.matches(".add-btn")) {
    modal.classList.remove("hidden");
  }
});
closeBtn.addEventListener("click", () => {
  modal.classList.add("hidden");
});

let selectedIds = []; // 모달에서 체크한 id 를 배열로 가져옴
friendModalSection.addEventListener("change", function (e) {
  if (e.target.matches(".checkbox")) {
    if (e.target.checked) selectedIds.push(e.target.dataset.id);
    else {
      let index = selectedIds.indexOf(e.target.dataset.id);
      if (index !== -1) selectedIds.splice(index, 1);
    }
  }
});

//초대 더보내기 보냈을 때 친구 데이터 추가
submitBtn.addEventListener("click", function (e) {
  (async () => {
    modal.classList.add("hidden");
    let formdata = new FormData();
    formdata.append("friendList", selectedIds);
    formdata.append("challengeId", challengeId);
    selectedIds = [];
    const putResponse = await fetch("/api/groupchallenge/invitation", {
      method: "PUT",
      body: formdata,
    });
    if (putResponse.ok) await reprintFreindSection();
  })();
});

friendsSection.addEventListener("click", function (e) {
  if (e.target.classList.contains("delete-img")) {
    cancelModal.classList.remove("hidden");
    userId = e.target.dataset.id;
  }
});

// 친구삭제 눌렀을때 모달뜨고 친구 데이터 삭제
cancelModal.addEventListener("click", function (e) {
  if (e.target.classList.contains("closeBtn")) {
    cancelModal.classList.add("hidden");
  } else if (e.target.classList.contains("okBtn")) {
    cancelModal.classList.add("hidden");
    (async () => {
      const deleteResponse = await fetch(`/api/groupchallenge/${challengeId}/members/${userId}`, {
        method: "DELETE",
      });
      if (deleteResponse.ok) await reprintFreindSection();
    })();
  }
});

// 친구 수정 눌렀을 때 수정취소로 바뀌고 - 생김
let friendModifyBtn = document.querySelector(".modify-friend");
friendModifyBtn.onclick = function (e) {
  let profiles = document.querySelectorAll(".profile");

  if (friendModifyBtn.textContent === "수정") {
    friendModifyBtn.textContent = "수정취소";
    profiles.forEach((profile) => {
      let deleteBtn = profile.querySelector(".delete-btn");
      deleteBtn.classList.remove("hidden");
      deleteBtn.classList.add("shake-animation");

      setTimeout(() => {
        deleteBtn.classList.remove("shake-animation");
      }, 2000);
    });
  } else {
    friendModifyBtn.textContent = "수정";

    profiles.forEach((profile) => {
      let deleteBtn = profile.querySelector(".delete-btn");
      deleteBtn.classList.add("hidden");
    });
  }
};

// 함께하는 친구, 초대더보내기 모달 재출력코드
async function reprintFreindSection() {
  let getResponse = await fetch(`/api/groupchallenge/${challengeId}`);
  let data = await getResponse.json();
  const friendList = data.newFriendList;
  const notInviList = data.notInviList;
  friendsSection.innerHTML = "";
  friendsSection.insertAdjacentHTML(
    "beforeend",
    `<div class="add-friend">
            <button class="add-btn">+</button>
        </div>`
  );

  for (const friend of friendList) {
    if (friend.toMemberId !== parseInt(leaderId)) {
      let isWait = friend.isAccept === "대기중" ? "wait" : "hidden";
      let isRefuse = friend.isAccept === "거절" ? "refuse" : "hidden";
      let deleteBtnClass = "delete-btn hidden";
      if (friendModifyBtn.textContent === "수정취소") deleteBtnClass = "delete-btn";
      let newFriendTemplate = `
        <div class="profile"> 
            <div>
                <div class="with-user">
                    <button class="${deleteBtnClass}" ><img class="delete-img" src="/image/startchallenge/delete.png" data-id="${friend.toMemberId}" ></button>
                    
                    <img class="img" src="${friend.profileImage}" alt="profile-img">
                    <div class="${isWait}">대기중</div>
                    <div class="${isRefuse}">거절</div>
                </div>
                <span>${friend.nickname}</span>
            </div>
        </div>
`;
      friendsSection.insertAdjacentHTML("beforeend", newFriendTemplate);
    }
  }

  friendModalSection.innerHTML = "";
  for (const friend of notInviList) {
    modalTemplate = `
        <div class="content">
        <div class="info">
         <img src="${friend.profileImage}" alt="프로필이미지" />
        <div>
          <span> ${friend.nickname}</span>
          <span> ${friend.name}</span>
        </div>
        </div>
        <input type="checkbox" class="checkbox" data-id="${friend.id}">
        `;
    friendModalSection.insertAdjacentHTML("beforeend", modalTemplate);
  }
}

//도전기간 수정 모달
let dateSection = document.querySelector(".period");
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
let selectAmPm = document.getElementById("new_start_time");
let alertChangeModal = document.getElementById("alert-change-modal");
let selectHour = document.getElementById("new_start_hour");
let startTime = 0;
let startDate = document.querySelector("#start_inside");
let endDate = document.querySelector("#end_inside");

endDate.addEventListener("input", function() {
  if (this.value) {
    endDateAlert.classList.add("hidden");
  }
});
dateBtn.onclick = function (e) {
  startTime = parseInt(selectHour.value);
  if (selectAmPm.value == "pm") {
    if (startTime != 12) startTime += 12;
  } else if (selectAmPm.value == "am") {
    if (startTime == 12) startTime = 0;
  }
  let currentDate = new Date();
  let currentHour = currentDate.getHours();
  let startDate = document.getElementById("start_inside").value;
  if (startDate === currentDate.toISOString().split("T")[0]) {
    if (startTime <= currentHour) {
      return;
    }
  }
  let endDate = document.getElementById("end_inside").value;
  if (endDate == "") {
    endAfterAlert.classList.add("hidden");
    endDateAlert.classList.remove("hidden");
    return;
  } else endDateAlert.classList.add("hidden");
  const data = {
    startDate: startDate,
    startTime: startTime,
    endDate: endDate,
    challengeId: challengeId,
  };
  fetch("/api/groupchallenge/date", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.json())
    .then((challenge) => {
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
                    <p>도전 기간</p>
                    <button class="modify-date">수정</button>
                </div>
                <div class="start-set">
                    <p>시작일</p>
                    <div>
                        <span id="startDate" data-date="${challenge.startDate}">${challenge.startDate}</span>
                        <span id="startTime" data-time="${challenge.startTime}">${challenge.startTime}시</span>
                    </div>
                </div>
                <div class="end-set">
                    <p>종료일</p>
                    <div>
                    <span>${challenge.endDate}</span>
                    </div>
                </div>
            `;
      dateSection.insertAdjacentHTML("beforeend", newDateTemplate);
    });
};

// ampm 변경시 value 값 변경 js
// startAmPmSelect.addEventListener("change", function () {
//   let startHourSelect = document.getElementById("new_start_hour");
//   let options = startHourSelect.options;
//   if (startAmPmSelect.value === "pm") {
//     // 나머지 select 요소들의 값을 +12로 변경

//     for (let i = 0; i < options.length; i++) {
//       let value = parseInt(options[i].value);
//       options[i].value = (value + 12).toString();
//     }
//   } else {
//     // am 선택 시 원래 value 값 유지
//     for (let i = 0; i < options.length; i++) {
//       let value = parseInt(options[i].value);
//       if (value > 12) {
//         options[i].value = (value - 12).toString();
//       }
//     }
//   }
// });



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

// 바로시작하기 버튼 모달뜨고 시작일 업데이트
let startBtn = document.getElementById("start-btn");
let startModal = document.getElementById("start-modal");
let alertErrorModal = document.getElementById("alert-error-modal");
let alertStartModal = document.getElementById("alert-start-modal");
let acceptedCount = 0;

startBtn.onclick = function () {
  startModal.classList.remove("hidden");
};

startModal.addEventListener("click", function (e) {
  if (e.target.classList.contains("closeBtn")) startModal.classList.add("hidden");
  else if (e.target.classList.contains("okBtn")) {
    let inviFriends = document.querySelectorAll(".profile");
    inviFriends.forEach((inviFriend) => {
      let isAccept = inviFriend.querySelector(".is-accept-value").value;
      if (isAccept === "수락") acceptedCount++;
    });
    //수락한 친구가 2명이상일때만 시작
    if (acceptedCount >= 1) {
      startModal.classList.add("hidden");

      fetch(`/groupChallenge/start-now?id=${challengeId}`, {
        method: "POST",
      }).then((response) => {
        if (response.ok) {
          alertStartModal.classList.remove("hidden");
          setTimeout(function () {
            window.location.href = "/main";
          }, 3000);
        }
      });
    } else {
      //아무도 초대를 수락하지 않을경우 시작안함
      startModal.classList.add("hidden");
      alertErrorModal.classList.remove("hidden");
      setTimeout(function () {
        alertErrorModal.classList.add("hidden");
      }, 3000);
    }
  }
});
//타이머
function updateTimer() {
  const startDateSpan = document.querySelector("#startDate");
  let startDate = startDateSpan.dataset.date;
  const startTimeSpan = document.querySelector("#startTime");
  let startTime = startTimeSpan.dataset.time;
  const future = Date.parse(`${startDate} ${startTime}:00:00`);
  const now = new Date();
  const diff = future - now;

  const days = Math.floor(diff / (1000 * 60 * 60 * 24));
  const hours = Math.floor(diff / (1000 * 60 * 60));
  const mins = Math.floor(diff / (1000 * 60));
  const secs = Math.floor(diff / 1000);

  const d = days;
  const h = hours - days * 24;
  const m = mins - hours * 60;
  const s = secs - mins * 60;

  document.getElementById("timer").innerHTML =
    "<div>" +
    d +
    "<span>Days</span></div>" +
    "<div>" +
    h +
    "<span>Hours</span></div>" +
    "<div>" +
    m +
    "<span>Minutes</span></div>" +
    "<div>" +
    s +
    "<span>Seconds</span></div>";
}
updateTimer();
setInterval(updateTimer, 1000);

history.pushState(null, null, document.URL);
window.addEventListener('popstate', () => {
    history.pushState(null, null, document.URL);
});

let isSafari = /^((?!chrome|android|windows).)*safari/i.test(navigator.userAgent.toLowerCase());
let dateInputs = document.querySelectorAll("input[type='date']");
if (isSafari)
  for (let i = 0; i < dateInputs.length; i++) {
    dateInputs[i].style.width = "210px"; // 각 요소에 적절한 가로 크기를 지정해보세요
    dateInputs[i].style.color = "#333333"; // 각 요소에 적절한 가로 크기를 지정해보세요
  }
let file = null;
let imageForm = document.querySelector("#image-form");
let recordImage = imageForm.dataset.image;

window.onload = function () {
  if (recordImage) {
    loadFile(null, recordImage);
  }
};
function loadFile(input, image) {
  let form = document.getElementById("image-form");
  form.style.visibility = "hidden";
  form.style.height = "0px";
  let newImage = document.getElementById("input-image");
  let imageBox = document.getElementById("image-box");
  imageBox.className = "image-box";
  newImage.className = "image-thumbnail";

  if (image != null) {
    console.log("1");
    newImage.src = image;
  } else {
    file = input.files[0]; //선택된 파일 가져오기
    newImage.src = URL.createObjectURL(file);
  }
  imageBox.style.marginTop = "20px";
}
let plusBtn = document.getElementById("plus-btn");
let minusBtn = document.getElementById("minus-btn");
plusBtn.addEventListener("click", function () {
  document.getElementById("achive-amount").value++;
  plusBtn.classList.add("push");
  setTimeout(() => {
    plusBtn.classList.remove("push");
  }, 100);
});

minusBtn.addEventListener("click", function () {
  if (document.getElementById("achive-amount").value > 0) {
    document.getElementById("achive-amount").value--;
    minusBtn.classList.add("push");
    setTimeout(() => {
      minusBtn.classList.remove("push");
    }, 100);
  }
});

/**삭제 모달**/
const deleteBtn = document.querySelector(".challenge-delete-btn");
const deleteModal = document.querySelector(".delete-modal");
const closeBtn = document.querySelector("#close-btn");
const giveUpBtn = document.querySelector("#give-up-btn");
const helpBtn = document.querySelector("#help");
const helpModal = document.querySelector(".help-modal");
const uniqueId = deleteBtn.dataset.uniqueId;
const unitName = deleteBtn.dataset.unitName;

helpBtn.addEventListener("click", () => {
  document.body.style.overflow = "hidden";
  helpModal.classList.remove("hidden");
});
window.addEventListener("click", (e) => {
  if (e.target.classList.contains("help-modal")) helpModal.classList.add("hidden");
});
deleteBtn.addEventListener("click", function () {
  document.body.style.overflow = "hidden";
  deleteModal.classList.remove("hidden");
});

closeBtn.addEventListener("click", function () {
  document.body.style.overflow = "auto";
  deleteModal.classList.add("hidden");
});

giveUpBtn.addEventListener("click", function () {
  location.href = `performance-records/delete?cid=${uniqueId}`;
});
let groupStatus = document.querySelector(".group-status");
const groupChallengeId = deleteBtn.dataset.groupChallengeId;
const friendTurnModal = document.querySelector("#friend-turn-modal-wrap");
const friendTurnModalContent = document.querySelector("#friend-turn-modal-content");
const friendRecordListSection = document.querySelector("#friend-record-list-section");
groupStatus.addEventListener("click", (e) => {
  document.body.style.overflow = "hidden";
  friendTurnModal.classList.remove("hidden");
  let memberId = e.target.dataset.memberId;
  fetch(`/challenge/${groupChallengeId}/performance-records/${memberId}`)
    .then((response) => response.json())
    .then((list) => {
      friendRecordListSection.innerHTML = "";
      for (record of list) {
        let template = null;
        if (record.result == "성공") {
          template = `
              <div class="turn-wrap">
                <div class="turn">
                  <span>${record.round}회차</span>
                </div>
                <p>${record.achvQuantity}${unitName}</p>
                <p style="color: #71B5CB;">성공</p>
              </div>
            `;
        } else if (record.result == "실패") {
          template = `
                <div class="turn-wrap">
                  <div class="turn">
                    <span>${record.round}회차</span>
                  </div>
                  <p>${record.achvQuantity}${unitName}</p>
                  <p style="color: rgb(219, 92, 92);">실패</p>
                </div>
              `;
        } else {
          template = `
                <div class="turn-wrap">
                  <div class="turn">
                    <span>${record.round}회차</span>
                  </div>
                  <p>${record.achvQuantity}${unitName}</p>
                  <p style="color: #3b3b3b;">진행중</p>
                </div>
              `;
        }

        friendRecordListSection.insertAdjacentHTML("beforeend", template);
      }
    });
  setTimeout(function () {
    friendTurnModalContent.classList.add("active");
  }, 20);
});

const friendSection = document.querySelector(".friends");
let periodSection = document.querySelector(".period-section");
friendSection.addEventListener("click", (e) => {
  if (!e.target.classList.contains("friend-round-btn")) return;
  let memberId = e.target.dataset.memberId;
  fetch(`/challenge/${groupChallengeId}/performance-records/${memberId}`)
    .then((response) => response.json())
    .then((list) => {
      friendRecordListSection.innerHTML = "";
      for (record of list) {
        let template = null;
        if (record.result == "성공") {
          template = `
              <div class="turn-wrap">
                <div class="turn">
                  <span>${record.round}회차</span>
                </div>
                <p>${record.achvQuantity}${unitName}</p>
                <p style="color: #71B5CB;">성공</p>
              </div>
            `;
        } else if (record.result == "실패") {
          template = `
                <div class="turn-wrap">
                  <div class="turn">
                    <span>${record.round}회차</span>
                  </div>
                  <p>${record.achvQuantity}${unitName}</p>
                  <p style="color: rgb(219, 92, 92);">실패</p>
                </div>
              `;
        } else {
          template = `
                <div class="turn-wrap">
                  <div class="turn">
                    <span>${record.round}회차</span>
                  </div>
                  <p>${record.achvQuantity}${unitName}</p>
                  <p style="color: #3b3b3b;">진행중</p>
                </div>
              `;
        }

        friendRecordListSection.insertAdjacentHTML("beforeend", template);
      }
    });
});
/**회차 모달**/
const turnBtn = document.querySelector(".goal div button");
const turnModal = document.querySelector("#turn-modal-wrap");
const turnModalContent = document.querySelector("#turn-modal-content");
const recordListSection = document.querySelector("#record-list-section");

const performanceRecordList = null;

turnBtn.addEventListener("click", function (e) {
  document.body.style.overflow = "hidden";
  fetch(`/challenge/performance-records?cid=${uniqueId}`)
    .then((response) => response.json())
    .then((list) => {
      recordListSection.innerHTML = "";

      for (record of list) {
        let template = null;
        if (record.result == "성공") {
          template = `
            <div class="turn-wrap">
              <div class="turn">
                <span>${record.round}회차</span>
              </div>
              <p>${record.achvQuantity}${unitName}</p>
              <p style="color: #71B5CB;">성공</p>
            </div>
          `;
        } else if (record.result == "실패") {
          template = `
              <div class="turn-wrap">
                <div class="turn">
                  <span>${record.round}회차</span>
                </div>
                <p>${record.achvQuantity}${unitName}</p>
                <p style="color: rgb(219, 92, 92);">실패</p>
              </div>
            `;
        } else {
          template = `
              <div class="turn-wrap">
                <div class="turn">
                  <span>${record.round}회차</span>
                </div>
                <p>${record.achvQuantity}${unitName}</p>
                <p style="color: #3b3b3b;">진행중</p>
              </div>
            `;
        }

        recordListSection.insertAdjacentHTML("beforeend", template);
      }

      turnModal.classList.remove("hidden");
      setTimeout(function () {
        turnModalContent.classList.add("active");
      }, 20);
    });
});

turnModal.addEventListener("click", function (e) {
  if (e.target.className != "turn-modal-close-btn") return;

  turnModalContent.classList.remove("active");
  setTimeout(function () {
    document.body.style.overflow = "auto";
    turnModal.classList.add("hidden");
  }, 300);
});
friendTurnModal.addEventListener("click", function (e) {
  if (e.target.className != "turn-modal-close-btn") return;

  friendTurnModalContent.classList.remove("active");
  setTimeout(function () {
    document.body.style.overflow = "auto";
    friendTurnModal.classList.add("hidden");
  }, 300);
});

/*저장하기 데이터 업데이트 & 메세지모달*/
const messageModalWrap = document.querySelector(".message-modal-wrap");
const messageModalContent = document.querySelector(".message-modal-content");
const saveBtn = document.querySelector("#save-btn");
const roundBtn = document.querySelector("#round");

saveBtn.addEventListener("click", function (e) {
  const recordForm = document.querySelector("#record-submit");
  const inputs = recordForm.elements;
  const impression = inputs["impression"].value;
  const achvQuantity = inputs["achvQuantity"].value;
  const id = inputs["id"].value;
  const round = roundBtn.dataset.round;

  const formData = new FormData();
  formData.append("impression", impression);
  formData.append("achvQuantity", achvQuantity);
  formData.append("id", id);
  formData.append("round", round);
  formData.append("uniqueId", uniqueId);
  formData.append("file", file); //loadFile에서 입력된 file 사용
  formData.append("result", false);

  fetch("/challenge/performance-records", {
    //FormData객체를 보낼때 header 부분은 브라우저가 자동으로 설정해줌
    method: "PUT",
    body: formData,
  }).then((response) => {
    if (response.ok)
      //HTTP 응답의 상태 코드가 200-299 사이에 있으면 response.ok는 true를 반환
      showMessage("저장되었습니다");
    else showMessage("저장에 실패했습니다");
  });
});

function showMessage(message) {
  messageModalContent.innerHTML = "<span>" + message + "</span>";
  messageModalWrap.classList.remove("hidden");

  setTimeout(function () {
    messageModalWrap.classList.add("hidden");
  }, 1000);
}

let nextRound = document.querySelector("#next-round");
let startDateString = nextRound.dataset.startDate;
let authFrequency = nextRound.dataset.authFrequency;
let round = nextRound.dataset.round;
let nextRoundDate = new Date(startDateString);
nextRoundDate.setDate(nextRoundDate.getDate() + authFrequency * round);
let currentDate = new Date();
let diffDays = getDaysDiff(currentDate, nextRoundDate);
console.log(nextRoundDate);
let formattedDate = `D - ${diffDays}`;
nextRound.textContent = formattedDate;
function getDaysDiff(startDate, endDate) {
  const oneDay = 24 * 60 * 60 * 1000; // 하루를 밀리초로 변환
  const diffInMilliseconds = endDate - startDate; // 두 날짜의 차이를 밀리초로 계산
  const diffInDays = Math.round(diffInMilliseconds / oneDay); // 밀리초를 일(day)로 변환하여 정수로 반올림
  return diffInDays;
}

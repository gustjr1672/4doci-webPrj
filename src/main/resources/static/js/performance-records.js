let file = null;
let imageForm = document.querySelector("#image-form");
let recordImage = imageForm.dataset.image;
window.onload = function () {
  console.log(imageForm);
  console.log(recordImage);
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

  let formSection = document.querySelector(".form");
  if (image != null) {
    console.log("1");
    newImage.src = image;
  } else {
    file = input.files[0]; //선택된 파일 가져오기
    newImage.src = URL.createObjectURL(file);
  }
  imageBox.style.marginTop = "20px";
}

document.getElementById("plus-btn").addEventListener("click", function () {
  document.getElementById("achive-amount").value++;
});

document.getElementById("minus-btn").addEventListener("click", function () {
  if (document.getElementById("achive-amount").value > 0)
    document.getElementById("achive-amount").value--;
});

/**삭제 모달**/
const deleteBtn = document.querySelector("header button");
const deleteModal = document.querySelector(".delete-modal");
const closeBtn = document.querySelector("#close-btn");
const giveUpBtn = document.querySelector("#give-up-btn");
const uniqueId = deleteBtn.dataset.uniqueId;
const unitName = deleteBtn.dataset.unitName;

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

/**회차 모달**/
const turnBtn = document.querySelector(".goal div button");
const turnModal = document.querySelector("#turn-modal-wrap");
const turnModalContent = document.querySelector("#turn-modal-content");
const recordListSection = document.querySelector("#record-list-section");
const performanceRecordList = null;

turnBtn.addEventListener("click", function (e) {
  document.body.style.overflow = "hidden";
  fetch(`challenge/performance-records?cid=${uniqueId}`)
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

      turnModal.style.display = "block";
      setTimeout(function () {
        turnModalContent.classList.add("active");
      }, 20);
    });
});

turnModal.addEventListener("click", function (e) {
  if (e.target.tagName != "BUTTON") return;

  turnModalContent.classList.remove("active");
  setTimeout(function () {
    document.body.style.overflow = "auto";
    turnModal.style.display = "none";
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

  fetch("challenge/performance-records", {
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

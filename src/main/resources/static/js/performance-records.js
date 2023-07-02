function loadFile(input) {
  let file = input.files[0]; //선택된 파일 가져오기
  let form = document.getElementById("image-form");
  form.style.visibility = "hidden";
  form.style.height = "0px";
  let newImage = document.getElementById("input-image");
  let imageBox = document.getElementById("image-box");
  imageBox.className = "image-box";
  newImage.className = "image-thumbnail";

  let formSection = document.querySelector(".form");
  newImage.src = URL.createObjectURL(file);
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

  deleteModal.classList.remove("hidden");

});

closeBtn.addEventListener("click", function () {

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

  fetch(`challenge/performance-records?cid=${uniqueId}`)
    .then(response => response.json())
    .then(list => {

      recordListSection.innerHTML = "";

      for (record of list) {
        let template = null;
        if (record.result) {
          template = `
            <div class="turn-wrap">
              <div class="turn">
                <span>${record.round}회차</span>
              </div>
              <p>${record.achvQuantity}${unitName}</p>
              <p style="color: #71B5CB;">성공</p>
            </div>
          `;


        } else {
            template = `
              <div class="turn-wrap">
                <div class="turn">
                  <span>${record.round}회차</span>
                </div>
                <p>${record.achvQuantity}${unitName}</p>
                <p style="color: rgb(219, 92, 92);">실패</p>
              </div>
            `;
        }

        recordListSection.insertAdjacentHTML("beforeend", template);

      }


      turnModal.style.display = "block";
      setTimeout(function () {
        turnModalContent.classList.add('active');
      }, 20);

    })

});

turnModal.addEventListener("click", function (e) {

  if (e.target.tagName != "BUTTON")
    return;

    turnModalContent.classList.remove("active");
  setTimeout(function () {
    turnModal.style.display = "none";
  }, 300);

});


/*저장하기 데이터 업데이트 & 메세지모달*/
const messageModalWrap = document.querySelector(".message-modal-wrap");
const messageModalContent = document.querySelector(".message-modal-content");
const saveBtn = document.querySelector("#save-btn");

saveBtn.addEventListener("click", function (e) {

  const recordForm = document.querySelector("#record-submit");
  const inputs = recordForm.elements;
  const impression = inputs["text-input"].value;
  const achvQuantity = inputs["aq"].value;
  const id = inputs["id"].value;

  const formData = { impression, achvQuantity, id, uniqueId };
  const jsonData = JSON.stringify(formData);

  fetch("challenge/performance-records", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: jsonData
  })
    .then(response => {
      if (response.ok)
        showMessage("저장되었습니다");
      else
        showMessage("저장에 실패했습니다");
    });

});

function showMessage(message) {

  messageModalContent.innerHTML = "<span>" + message + "</span>";
  messageModalWrap.classList.remove("hidden");

  setTimeout(function () {
    messageModalWrap.classList.add("hidden");
  }, 1000);

}
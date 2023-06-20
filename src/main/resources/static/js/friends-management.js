const btns = document.getElementById("btns");
const leftBtn = btns.querySelector(".left-btn");
const rightBtn = btns.querySelector(".right-btn");
const leftContent = document.querySelector("#left-content");
const rightContent = document.querySelector("#right-content");

function toggleContent(content) {
  if (content === "left") {
    leftBtn.classList.add("active");
    rightBtn.classList.remove("active");
    leftContent.classList.remove("hidden");
    rightContent.classList.add("hidden");
  } else if (content === "right") {
    rightBtn.classList.add("active");
    leftBtn.classList.remove("active");
    rightContent.classList.remove("hidden");
    leftContent.classList.add("hidden");
  }
}
let addSearchBtn = document.getElementById("add-search");
let addSearchInput = document.querySelector("input[name=n]");
let newfriendList = document.getElementById("new-friend-list");
addSearchBtn.addEventListener("click", () => {
  if (addSearchInput.value == "") return;
  newFriendListLoad(`/friendmanage/newfriend/search?n=${addSearchInput.value}`);
});

let newFriendList = document.getElementById("new-friend-list");

newFriendList.onclick = function (e) {
  if (e.target.id === "add-request") {
    let cancel = e.target.nextElementSibling;
    e.target.classList.add("hidden");
    cancel.classList.remove("hidden");
    fetch(`/friendmanage/newfriend/add?id=${e.target.dataset.id}`);
  } else if (e.target.id === "cancel") {
    let add = e.target.previousElementSibling;
    e.target.classList.add("hidden");
    add.classList.remove("hidden");
    fetch(`/friendmanage/newfriend/cancel?id=${e.target.dataset.id}`);
  }
};

function newFriendListLoad(url) {
  fetch(url)
    .then((response) => response.json())
    .then((list) => {
      newfriendList.innerHTML = "";
      Object.entries(list).map(([key, value]) => {
        let newFriendTemplate = `
        <div  class="content">
            <div class="info">
                <img src="/image/friends-management/progileImg2.png" alt="프로필이미지" />
                <div>
                    <span>${value.name}</span>
                    <span>${value.nickname}</span>
                </div>
            </div>
            <button data-id=${value.id} id='add-request' class="${
          value.state == "요청" ? "" : "hidden"
        }">요청</button>
            <button data-id=${value.id} id='cancel' class="${
          value.state == "요청취소" ? "" : "hidden"
        }">요청취소</button>
        </div>
        `;
        newfriendList.insertAdjacentHTML("beforeend", newFriendTemplate);
      });
    });
}

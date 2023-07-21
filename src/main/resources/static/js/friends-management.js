let socket = null;

document.addEventListener("DOMContentLoaded", function () {
  // 소켓 연결
  connectWs();
});
function connectWs() {
  let ws = new SockJS("/friendmanage");
  socket = ws;
  ws.onopen = function () {
    console.log("open");
  };

  ws.close = function () {
    console.log("close");
  };
}
window.addEventListener("load", () => {
  friendListBtn.click();
});
//모달
let modal = document.querySelector(".delete-modal");
let modalCancelBtn = document.querySelector(".modal-cancel-btn");
let modalDeleteBtn = document.querySelector(".modal-delete-btn");
let completeModal = document.querySelector(".complete-modal");
let modalCloseBtn = document.querySelector(".modal-close");
//토글버튼
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
  newFriendListLoad(`/friendmanage/newfriends/list?n=${addSearchInput.value}`);
});

let newFriendList = document.getElementById("new-friend-list");

newFriendList.onclick = function (e) {
  if (e.target.id === "add-request") {
    let cancel = e.target.nextElementSibling;
    e.target.classList.add("hidden");
    completeModal.classList.remove("hidden");
    cancel.classList.remove("hidden");
    fetch(`/friendmanage/newfriend/add?id=${e.target.dataset.id}`).then((response) => {
      if (socket) {
        let socketMsg = "request," + e.target.dataset.id + "," + "2," + "1";
        socket.send(socketMsg);
      }
    });
  } else if (e.target.id === "cancel") {
    let add = e.target.previousElementSibling;
    fetch(`/friendmanage/newfriend/cancel?id=${e.target.dataset.id}`).then((response) => {
      e.target.classList.add("hidden");
      add.classList.remove("hidden");
    });
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
                <img src="${value.profile}" alt="프로필이미지" />
                <div>
                    <span>${value.name}</span>
                    <span>${value.nickname}</span>
                </div>
            </div>
            <button data-id=${value.id} id='add-request' class="${
          value.state == "요청" ? "zoom-4 cursor" : "hidden"
        }">요청</button>
            <button data-id=${value.id} id='cancel' class="${
          value.state == "요청취소" ? "zoom-4 cursor" : "hidden"
        }">요청취소</button>
        </div>
        `;
        newfriendList.insertAdjacentHTML("beforeend", newFriendTemplate);
      });
    });
}
let friendListBtn = document.querySelector("#friend-list-btn");
friendListBtn.addEventListener("click", () => {
  fetch(`/friendmanage/friends/list`)
    .then((response) => response.json())
    .then((list) => {
      friendList.innerHTML = "";
      friendList.insertAdjacentHTML("beforeend", `<p >친구 ${list.length}명</p>`);
      if (list.length > 0) {
        for (const friend of list) {
          let friendListTemplate = `
      <div class="friend">
        <button class="info btn-hover cursor"  onclick="location.href = '/friendmanage/challenge?id=${friend.id}'">
          <img src="${friend.profileImage}" alt="프로필이미지" />
          <div class="user-name">
            <span> ${friend.name}</span>
            <span>${friend.nickname}</span>
          </div>
        </button>
        <button data-id="${friend.id}" id="friend-delete" class="delete zoom-4 cursor">삭제</button>
      </div>`;
          friendList.insertAdjacentHTML("beforeend", friendListTemplate);
        }
      } else {
        let noFriendTemplate = `<div class="no-friend">
        <div class="loader"></div>
        <div>새로운 친구를 추가해보세요</div>
      </div>`;
        friendList.insertAdjacentHTML("beforeend", noFriendTemplate);
      }
    });
});
let friendSearchBtn = document.getElementById("friend-search");
let friendSearchInput = document.querySelector("input[name=nickname]");
let friendList = document.getElementById("friend-list");
friendSearchBtn.addEventListener("click", () => {
  if (friendSearchInput.value == "") return;
  fetch(`/friendmanage/friends/list/${friendSearchInput.value}`)
    .then((response) => response.json())
    .then((list) => {
      friendList.innerHTML = "";
      friendList.insertAdjacentHTML("beforeend", `<p >친구 ${list.length}명</p>`);

      if (list.length > 0)
        for (const friend of list) {
          let friendListTemplate = `
        <div class="friend">
          <button class="info btn-hover cursor"  onclick="location.href = '/friendmanage/challenge?id=${friend.id}'">
            <img src="${friend.profileImage}" alt="프로필이미지" />
            <div class="user-name">
              <span> ${friend.name}</span>
              <span>${friend.nickname}</span>
            </div>
          </button>
          <button data-id="${friend.id}" id="friend-delete" class="delete zoom-4 cursor">삭제</button>
        </div>`;
          friendList.insertAdjacentHTML("beforeend", friendListTemplate);
        }
      else {
        console.log(1);
        let noFriendTemplate = `<div class="no-friend">
        <div class="loader"></div>
        <div>검색결과가 없습니다!</div>
        </div>`;
        friendList.insertAdjacentHTML("beforeend", noFriendTemplate);
      }
    });
});

friendList.onclick = function (e) {
  if (e.target.classList.contains("delete")) {
    modal.classList.remove("hidden");
    modalDeleteBtn.dataset.id = e.target.dataset.id;
  }
};
modalCancelBtn.addEventListener("click", () => {
  modalDeleteBtn.dataset.id = 0;
  modal.classList.add("hidden");
});
modalCloseBtn.addEventListener("click", () => {
  completeModal.classList.add("hidden");
});
modalDeleteBtn.addEventListener("click", (e) => {
  fetch(`/friendmanage/friends?id=${e.target.dataset.id}&n=${friendSearchInput.value}`, {
    method: "DELETE",
  })
    .then((response) => response.json())
    .then((list) => {
      modal.classList.add("hidden");
      completeModal.classList.remove("hidden");
      friendList.innerHTML = "";
      friendList.insertAdjacentHTML("beforeend", `<p >친구 ${list.length}명</p>`);
      if (list.length > 0)
        for (const friend of list) {
          let friendListTemplate = `
        <div class="friend">
          <button class="info">
            <img src="${friend.profileImage}" alt="프로필이미지" />
            <div class="user-name">
              <span> ${friend.name}</span>
              <span>${friend.nickname}</span>
            </div>
          </button>
          <button data-id=${friend.id} id="friend-delete" class="delete zoom-4 cursor">삭제</button>
        </div>`;
          friendList.insertAdjacentHTML("beforeend", friendListTemplate);
        }
      else {
        let noFriendTemplate = `<div class="no-friend">
        <div class="loader"></div>
        <div>새로운 친구를 추가해보세요</div>
        </div>`;
        friendList.insertAdjacentHTML("beforeend", noFriendTemplate);
      }
    });
});

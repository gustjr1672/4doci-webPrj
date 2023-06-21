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

let friendSearchBtn = document.getElementById("friend-search");
let friendSearchInput = document.querySelector("input[name=nickname]");
let friendList = document.getElementById("friend-list");
friendSearchBtn.addEventListener("click", () => {
  if (friendSearchInput.value == "") return;
  fetch(`/friendmanage/friend/search?n=${friendSearchInput.value}`)
    .then((response) => response.json())
    .then((list) => {
      console.log(list);
      friendList.innerHTML = "";
      friendList.insertAdjacentHTML("beforeend", `<p >친구 ${list.length}명</p>`);
      for (const friend of list) {
        let friendListTemplate = `
        <div class="friend">
          <button class="info">
            <img src="/image/friends-management/profile.png" alt="프로필이미지" />
            <div class="user-name">
              <span> ${friend.name}</span>
              <span>${friend.nickname}</span>
            </div>
          </button>
          <button data-id="${friend.id}" id="friend-delete" class="delete">삭제</button>
        </div>`;
        friendList.insertAdjacentHTML("beforeend", friendListTemplate);
      }
    });
});
friendList.onclick = function (e) {
  if (e.target.classList.contains("delete")) {
    fetch(`/friendmanage/friend/delete?id=${e.target.dataset.id}&n=${friendSearchInput.value}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((list) => {
        console.log(list);
        friendList.innerHTML = "";
        friendList.insertAdjacentHTML("beforeend", `<p >친구 ${list.length}명</p>`);
        for (const friend of list) {
          let friendListTemplate = `
        <div class="friend">
          <button class="info">
            <img src="/image/friends-management/profile.png" alt="프로필이미지" />
            <div class="user-name">
              <span> ${friend.name}</span>
              <span>${friend.nickname}</span>
            </div>
          </button>
          <button data-id=${friend.id} id="friend-delete" class="delete">삭제</button>
        </div>`;
          friendList.insertAdjacentHTML("beforeend", friendListTemplate);
        }
      });
  }
};

function openModal() {
  let bell = document.getElementById("bell");
  bell.src = "/image/header/whiteBell.png";
  var modal = document.getElementById("myModal");
  modal.style.display = "block";
  setTimeout(function () {
    modal.classList.add("open");
  }, 100);
}

function closeModal() {
  var modal = document.getElementById("myModal");
  modal.classList.remove("open");
  setTimeout(function () {
    modal.style.display = "none";
  }, 300);
}
function friendRequestLoad(url) {
  fetch(url)
    .then((response) => response.json())
    .then((list) => {
      friendRequest.innerHTML = "";
      for (const member of list) {
        let requestListTemplate = `
    <div class="content">
    <div class="info">
      <img src="/image/notification/progileImg2.png" alt="프로필이미지" />
      <div class="user-name">
        <span>${member.name}</span>
        <span>${member.nickname}</span>
      </div>
    </div>
    <div class="friend-request-btns">
      <button data-id="${member.id}" id="request-refuse" class="refuse">거절</button>
      <button data-id="${member.id}" id="request-accept">수락</button>
      `;
        friendRequest.insertAdjacentHTML("beforeend", requestListTemplate);
      }
    });
}

let friendRequest = document.getElementById("friend-request-content");

friendRequest.onclick = function (e) {
  if (e.target.id === "request-accept") {
    friendRequestLoad(`/notification/request/accept?id=${e.target.dataset.id}`);
  } else if (e.target.id === "request-refuse") {
    friendRequestLoad(`/notification/request/refuse?id=${e.target.dataset.id}`);
  }
};

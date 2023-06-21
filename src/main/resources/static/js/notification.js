const btns = document.getElementById("btns");
const communityBtn = btns.querySelector(".community-btn");
const groupInvitationBtn = btns.querySelector(".group-invitation-btn");
const friendRequestBtn = btns.querySelector(".friend-request-btn");

const communityContent = document.getElementById("community-content");
const invitationContent = document.getElementById("group-invitation-content");
const requestContent = document.getElementById("friend-request-content");

function toggleContent(content) {
  switch (content) {
    case "community":
      communityBtn.classList.add("active");
      groupInvitationBtn.classList.remove("active");
      friendRequestBtn.classList.remove("active");

      communityContent.classList.remove("hidden");
      invitationContent.classList.add("hidden");
      requestContent.classList.add("hidden");
      break;
    case "groupInvitation":
      communityBtn.classList.remove("active");
      groupInvitationBtn.classList.add("active");
      friendRequestBtn.classList.remove("active");

      communityContent.classList.add("hidden");
      invitationContent.classList.remove("hidden");
      requestContent.classList.add("hidden");
      break;
    case "friendRequest":
      communityBtn.classList.remove("active");
      groupInvitationBtn.classList.remove("active");
      friendRequestBtn.classList.add("active");

      communityContent.classList.add("hidden");
      invitationContent.classList.add("hidden");
      requestContent.classList.remove("hidden");
      break;
  }
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

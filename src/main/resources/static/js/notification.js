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
      break;
    case "groupInvitation":
      communityBtn.classList.remove("active");
      groupInvitationBtn.classList.add("active");
      friendRequestBtn.classList.remove("active");
      break;
    case "friendRequest":
      communityBtn.classList.remove("active");
      groupInvitationBtn.classList.remove("active");
      friendRequestBtn.classList.add("active");
      break;
  }
}
function friendRequestLoad(url, method) {
  fetch(url, {
    method: method,
  })
    .then((response) => response.json())
    .then((list) => {
      notification.innerHTML = "";
      for (const member of list) {
        let requestListTemplate = `
          <section id="friend-request-content" class="contents">
            <div class="content">
            <div class="info">
              <img src="${member.profileImage}" alt="프로필이미지" />
              <div class="user-name">
                <span>${member.name}</span>
                <span>${member.nickname}</span>
              </div>
            </div>
            <div class="friend-request-btns">
              <button data-id="${member.id}" id="request-refuse" class="refuse">거절</button>
              <button data-id="${member.id}" id="request-accept">수락</button>
          </section>
      `;
        notification.insertAdjacentHTML("beforeend", requestListTemplate);
      }
    });
}

let notificationBtns = document.querySelector(".notification");

notificationBtns.addEventListener("click", (e) => {
  if (e.target.id === "request-accept") {
    friendRequestLoad(`/notifications/request/${e.target.dataset.id}`, "POST");
  } else if (e.target.id === "request-refuse") {
    friendRequestLoad(`/notifications/request/${e.target.dataset.id}`, "DELETE");
  }
});
let bell = document.getElementById("bell");
bell.addEventListener("click", () => {
  console.log("1");
});
let btnSection = document.querySelector("#btns");
let notification = document.querySelector(".notification");
btnSection.addEventListener("click", (e) => {
  if (e.target.classList.contains("community-btn")) {
    notification.innerHTML = "";
    notification.insertAdjacentHTML("beforeend", commuNotiTemplate);
  } else if (e.target.classList.contains("group-invitation-btn")) {
    notification.innerHTML = "";
    notification.insertAdjacentHTML("beforeend", groupNotiTemplate);
  } else if (e.target.classList.contains("friend-request-btn")) {
    friendRequestLoad(`/notifications/request`, "GET");
  }
});

let commuNotiTemplate = `
  <section id="community-content" class="contents">
    <button class="content" onclick="location.href = 'comments.html' ">
      <div class="info">
        <img src="/image/notification/profile.png" alt="프로필이미지" />
        <div>
          <span>고민시작 님이 댓글을 남겼습니다.</span>
          <span class="time">1분전</span>
        </div>
      </div>
    </button>
    <button class="content">
      <div class="info">
        <img src="/image/notification/progileImg2.png" alt="프로필이미지" />
        <div>
          <span>재혁짱 님이 댓글을 남겼습니다.</span>
          <span class="time">11분전</span>
        </div>
      </div>
    </button>
    <button class="content">
      <div class="info">
        <img src="/image/notification/progileImg2.png" alt="프로필이미지" />
        <div>
          <span>재혁짱 님이 댓글을 남겼습니다.</span>
          <span class="time">17분전</span>
        </div>
      </div>
    </button>
  </section>`;

let groupNotiTemplate = ` 
        <section id="group-invitation-content" class="contents">
          <div class="content">
            <div class="info">
              <img src="/image/notification/profile.png" alt="프로필이미지" />
              <div>
                <span
                  >고민시작 님이 <br />
                  그룹도전에 초대했습니다.</span
                >
              </div>
            </div>
            <button onclick="location.href='group-invite.html'">보기</button>
          </div>
          <div class="content">
            <div class="info">
              <img src="/image/notification/progileImg2.png" alt="프로필이미지" />
              <div>
                <span
                  >고민시작 님이 <br />
                  그룹도전에 초대했습니다.</span
                >
              </div>
            </div>
            <button>보기</button>
          </div>
        </section>`;

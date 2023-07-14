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

function groupInvitationLoad(url) {
  fetch(url)
    .then((response) => response.json())
    .then((list) => {
      notification.innerHTML = "";
      for (const invitation of list) {
        let groupNotiTemplate = ` 
        <section id="group-invitation-content" class="contents">
          <div class="content">
            <div class="info">
              <img src="${invitation.profileImage}" alt="프로필이미지" />
              <div>
                <span
                  >${invitation.nickname} 님이 <br/>
                  그룹도전에 초대했습니다.</span
                >
              </div>
            </div>
            <button onclick="location.href='/groupChallenge/invite-request?id=${invitation.groupChallengeId}'">보기</button>
          </div>
        </section>`;
        notification.insertAdjacentHTML("beforeend", groupNotiTemplate);
      }
    });
}

function commentNotificationLoad(url) {

  fetch(url)
    .then(response => response.json())
    .then(commentNotiList => {
      notification.innerHTML = "";

      let deleteTemplate =`
      <button class="delete-comment-alarm-button">
      <span>전체 삭제</span>
      <img src="/image/community/dustbin.svg" alt="삭제">
      </button>
      `;

      notification.insertAdjacentHTML("beforeend", deleteTemplate);

      for (let commentNoti of commentNotiList) {
        ///community/feed로 url 요청 -> get매핑. 쿼리스트링으로 수행기록 id 인자 전달하기
        let commuNotiTemplate = `
        <section id="community-content" class="contents">
          <button class="content" onclick="location.href = '/community/feed?rid=${commentNoti.performanceRecordsId}&nid=${commentNoti.id}' ">
            <div class="info">
              <img src=${commentNoti.profileImage} alt="프로필이미지" />
              <div>
                <span>${commentNoti.nickName} 님이 댓글을 남겼습니다.</span>
                <span class="time">${commentNoti.timeMessage}</span>
              </div>
            </div>
          </button>
        </section>`;

        notification.insertAdjacentHTML("beforeend", commuNotiTemplate);
      }

      //알림 전체 삭제
      let deleteCommentAlarmBtn =document.querySelector(".delete-comment-alarm-button");

      deleteCommentAlarmBtn.addEventListener("click", (e) => {
        
        fetch(`/notifications/comment/${commentNotiList[0].toMemberId}`, {
          method: "DELETE"
        })
        .then(response => {
          if(!response.ok)
            alert("삭제에 실패했습니다");
          else{
            notification.innerHTML = "";
            notification.insertAdjacentHTML("beforeend", deleteTemplate);
          }
        });

      });

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
    commentNotificationLoad("/notifications/comment");
  } else if (e.target.classList.contains("group-invitation-btn")) {
    groupInvitationLoad(`/notifications/invite`);
  } else if (e.target.classList.contains("friend-request-btn")) {
    friendRequestLoad(`/notifications/request`, "GET");
  }
});

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

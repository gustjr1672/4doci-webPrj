let socket = null;

document.addEventListener("DOMContentLoaded", function () {
  // 소켓 연결
  connectWs();
});
function connectWs() {
  let ws = new SockJS("/groupChallenge");
  socket = ws;
  ws.onopen = function () {
    console.log("open");
  };

  ws.close = function () {
    console.log("close");
  };
}

let inviteBtn = document.getElementById("invite-btn");
let cancelBtn = document.getElementById("cancel-btn");
let inviteModal = document.getElementById("inviteModal");
let cancelModal = document.getElementById("cancelModal");
const form = document.querySelector("form");
   

cancelBtn.onclick = function(e){
  e.preventDefault();
  cancelModal.classList.remove("hidden");
}

inviteBtn.onclick = function(e){
  e.preventDefault();
  inviteModal.classList.remove("hidden");
}

inviteModal.addEventListener("click",function(e){
  if(e.target.classList.contains("closeBtn"))
    inviteModal.classList.add("hidden");
  else if(e.target.classList.contains("okBtn")){
    inviteModal.classList.add("hidden");
    {
      const selectedFriends = document.querySelectorAll('input[name="friend"]:checked');
      selectedFriends.forEach(function(friend){
          let socketMsg = "invite," + friend.value + "," + "2," + "1";
          socket.send(socketMsg);
      });
    }
    form.action = "/groupChallenge/group-invite/reg?action=invite"
    form.submit();

  }
})

cancelModal.addEventListener("click",function(e){
  if(e.target.classList.contains("closeBtn"))
    cancelModal.classList.add("hidden")
  else if(e.target.classList.contains("okBtn")){
    cancelModal.classList.add("hidden")
    form.action = "/groupChallenge/group-invite/reg?action=cancel"
    form.submit();

  }
})







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

inviteBtn.onclick = function(e){
    const selectedFriends = document.querySelectorAll('input[name="friend"]:checked');
    selectedFriends.forEach(function(friend){
        let socketMsg = "invite," + friend.value + "," + "2," + "1";
        socket.send(socketMsg);
    });
    
}


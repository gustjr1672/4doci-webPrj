let socket = null;

document.addEventListener("DOMContentLoaded", function () {
  // 소켓 연결
  connectWs();
});
function connectWs() {
  let ws = new SockJS("/main");
  socket = ws;
  ws.onopen = function () {
    console.log("open");
  };
  ws.onmessage = function (event) {
    let bell = document.getElementById("bell");
    bell.src = "/image/header/whiteBellAlarm.png";
  };

  ws.close = function () {
    console.log("close");
  };
}

function openModal() {
  let bell = document.getElementById("bell");
  bell.src = "/image/header/whiteBell.png";
  let modal = document.getElementById("myModal");
  modal.style.display = "block";
  let btn = document.querySelector(".community-btn");
  btn.click();
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

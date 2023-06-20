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

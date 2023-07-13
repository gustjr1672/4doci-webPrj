const btns = document.getElementById("btns");
const ongoingBtn = btns.querySelector(".ongoing-btn");
const pastBtn = btns.querySelector(".past-btn");
const ongoingContent = document.querySelector("#ongoing-content");
const pastContent = document.querySelector("#past-content");

function toggleContent(content) {
  if (content === "ongoing") {
    ongoingBtn.classList.add("active");
    pastBtn.classList.remove("active");
    ongoingContent.classList.remove("hidden");
    pastContent.classList.add("hidden");
  } else if (content === "past") {
    pastBtn.classList.add("active");
    ongoingBtn.classList.remove("active");
    pastContent.classList.remove("hidden");
    ongoingContent.classList.add("hidden");
  }
}

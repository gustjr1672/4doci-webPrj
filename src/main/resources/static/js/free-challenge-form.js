const challengeName = document.getElementById("challenge_name");

challengeName.oninput = function () {
  let input = challengeName.value;
  let alert = document.querySelector(".check-challenge-length");

  if (input.length > 30)
    alert.classList.remove("hidden");
  else 
    alert.classList.add("hidden");
};
let startDate = document.querySelector("#start_inside");
let endDate = document.querySelector("#end_inside");
let alert = document.querySelector(".alert");

let today = new Date().toISOString().split("T")[0];

startDate.setAttribute("min", today);
endDate.setAttribute("min", today);

startDate.addEventListener("change", () => {
  endDate.value = null;
});

endDate.addEventListener("change", () => {
  if (startDate.value >= endDate.value) {
    alert.classList.remove("hidden");
    endDate.value = null;
  } else {
    alert.classList.add("hidden");
  }
});
let submit = document.querySelector("#submit");
let modal = document.querySelector(".choice-modal");
let cancelBtn = document.querySelector(".modal-box div :first-child");
let startBtn = document.querySelector(".modal-box div :nth-child(2)");
let submitBtn = document.querySelector("#submit-button");
submit.addEventListener("click", () => {
  modal.classList.remove("hidden");
});
cancelBtn.addEventListener("click", () => {
  modal.classList.add("hidden");
});
startBtn.addEventListener("click", () => {
  modal.classList.add("hidden");
  submitBtn.click();
});
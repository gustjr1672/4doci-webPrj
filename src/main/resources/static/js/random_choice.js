let today = new Date().toISOString().split("T")[0];
let startDateInput = document.querySelector("#start-date");
let endDateInput = document.querySelector("#end-date");
startDateInput.setAttribute("min", today);

let inputs = document.querySelectorAll("label");

startDateInput.addEventListener("change", () => {
  let checked = document.querySelector("input[type=radio]:checked");
  if (checked) {
    let startDate = new Date(startDateInput.value);
    let period = parseInt(checked.dataset.period);
    let endDate = new Date(startDate.getTime() + period * 24 * 60 * 60 * 1000);

    endDateInput.value = formatDate(endDate);
  }
});

inputs.forEach(function (input) {
  if (endDateInput.value == null) {
  }
  input.addEventListener("click", handleStartDateChange);
});
function handleStartDateChange() {
  if (startDateInput.value === "") return;

  let startDate = new Date(startDateInput.value);
  let period = parseInt(this.dataset.period);
  let endDate = new Date(startDate.getTime() + period * 24 * 60 * 60 * 1000);

  endDateInput.value = formatDate(endDate);
}

function formatDate(date) {
  let year = date.getFullYear();
  let month = ("0" + (date.getMonth() + 1)).slice(-2);
  let day = ("0" + date.getDate()).slice(-2);
  return year + "-" + month + "-" + day;
}
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

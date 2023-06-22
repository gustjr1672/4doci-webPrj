let today = new Date().toISOString().split("T")[0];
let startDateInput = document.querySelector("#start-date");
let endDateInput = document.querySelector("#end-date");
startDateInput.setAttribute("min", today);

let inputs = document.querySelectorAll("label");

inputs.forEach(function (input) {
  input.addEventListener("click", handleStartDateChange);
});
function handleStartDateChange() {
  if (startDateInput.value === "") return;

  let startDate = new Date(startDateInput.value);
  let period = parseInt(this.dataset.period);
  console.log(period);
  let endDate = new Date(startDate.getTime() + period * 24 * 60 * 60 * 1000);

  console.log(formatDate(endDate));
  endDateInput.value = formatDate(endDate);
}

function formatDate(date) {
  let year = date.getFullYear();
  let month = ("0" + (date.getMonth() + 1)).slice(-2);
  let day = ("0" + date.getDate()).slice(-2);
  return year + "-" + month + "-" + day;
}

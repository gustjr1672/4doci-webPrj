function challengeAlert() {
  let input = document.getElementById("challenge_name").value;
  let alert = document.getElementById("check_challenge_length");

  if (input.length > 30) alert.textContent = "30자 이내로 작성해 주세요";
  else alert.textContent = "";
}
let startDate = document.querySelector("#start_inside");
let endDate = document.querySelector("#end_inside");
let alert = document.querySelector(".alert");

let today = new Date().toISOString().split("T")[0];

document.getElementById("start_inside").setAttribute("min", today);
document.getElementById("end_inside").setAttribute("min", today);

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

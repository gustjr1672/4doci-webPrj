const challengeName = document.getElementById("challenge_name");

challengeName.oninput = function () {
  let input = challengeName.value;
  let alert = document.getElementById("check_challenge_length");

  if (input.length == 30) alert.textContent = "30자 이내로 작성해 주세요";
  else alert.textContent = "";
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

// let startAmPmSelect = document.getElementById("start_time");

// // 첫 번째 select 요소의 값이 변경될 때 이벤트 처리
// startAmPmSelect.addEventListener("change", function () {
//   let startHourSelect = document.getElementById("start_hour");
//   let options = startHourSelect.options;
//   if (startAmPmSelect.value === "pm") {
//     // 나머지 select 요소들의 값을 +12로 변경

//     for (let i = 0; i < options.length; i++) {
//       let value = parseInt(options[i].value);
//       options[i].value = (value + 12).toString();
//     }
//   } else {
//     // am 선택 시 원래 value 값 유지
//     for (let i = 0; i < options.length; i++) {
//       let value = parseInt(options[i].value);
//       if (value > 12) {
//         options[i].value = (value - 12).toString();
//       }
//     }
//   }
// });
let submitBtn = document.querySelector("#submit-button");
let startTimeInput = document.querySelector("#start-time-hidden");
let selectAmPm = document.getElementById("start_time");
let selectHour = document.getElementById("start_hour");
let timeAlert = document.querySelector("#time-alert");
let hour = 0;
submitBtn.addEventListener("click", (e) => {
  hour = parseInt(selectHour.value);
  if (selectAmPm.value == "pm") {
    if (hour != 12) hour += 12;
  } else if (selectAmPm.value == "am") {
    if (hour == 12) hour = 0;
  }
  let currentDate = new Date();
  let currentHour = currentDate.getHours();
  startTimeInput.value = hour;
  console.log(startTimeInput.value);
  if (startDate.value === currentDate.toISOString().split("T")[0]) {
    if (startTimeInput.value <= currentHour || isNaN(startTimeInput.value)) {
      timeAlert.classList.remove("hidden");
      e.preventDefault();
      return;
    } else {
      if (e.target.form.checkValidity()) e.target.form.submit();
    }
  }
});

let acceptBtn = document.getElementById("accept-btn");
let refuseBtn = document.getElementById("refuse-btn");
let refuseModal = document.getElementById("refuse-modal");
let acceptModal = document.getElementById("accept-modal");
const form = document.querySelector("form");

acceptBtn.onclick = function(e){
    e.preventDefault();
    acceptModal.classList.remove("hidden");
  }
  
  refuseBtn.onclick = function(e){
    e.preventDefault();
    refuseModal.classList.remove("hidden");
  }

  acceptModal.addEventListener("click",function(e){
    if(e.target.classList.contains("closeBtn")){
        acceptModal.classList.add("hidden");
    }
    else if(e.target.classList.contains("okBtn")){
        acceptModal.classList.add("hidden");
      form.action = "/groupChallenge/invite-request/submit?action=accept"
      form.submit();
    }
  })

  refuseModal.addEventListener("click",function(e){
    if(e.target.classList.contains("closeBtn"))
        refuseModal.classList.add("hidden");
    else if(e.target.classList.contains("okBtn")){
        refuseModal.classList.add("hidden");
      form.action = "/groupChallenge/invite-request/submit?action=refuse"
      form.submit();
    }
  })

  function getDaysUntilStart(startDate) {
    const today = new Date();
    const start = new Date(startDate);
    const oneDay = 24 * 60 * 60 * 1000; // 1일을 밀리초로 계산
  
    const remainingDays = Math.round((start - today) / oneDay);
  
    if (remainingDays > 0) {
      return remainingDays;
    } else if (remainingDays === 0) {
      return 0;
    } else {
      return -1;
    }
  }
  const startDateSpan = document.getElementById("startDateText");
  const startDateText = startDateSpan.innerText;
  const daysLeft = getDaysUntilStart(startDateText);

  const daysLeftSpan = document.getElementById("dayLeftSpan");
  daysLeftSpan.innerText = `D - ${daysLeft}`;
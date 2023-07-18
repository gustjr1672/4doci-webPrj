
let exitBtn = document.getElementById("exit-btn");
let exitModal = document.getElementById("exit-modal");

exitBtn.onclick = function(e){
    exitModal.classList.remove("hidden");
  }

  exitModal.addEventListener("click",function(e){
        exitModal.classList.add("hidden");
    })

    function updateTimer() {
      const startDateSpan = document.querySelector("#startDate");
      let startDate = startDateSpan.dataset.date;
      const startTimeSpan = document.querySelector("#startTime");
      let startTime = startTimeSpan.dataset.time;
      const future = Date.parse(`${startDate} ${startTime}:00:00`);
      const now = new Date();
      const diff = future - now;
    
      const days = Math.floor(diff / (1000 * 60 * 60 * 24));
      const hours = Math.floor(diff / (1000 * 60 * 60));
      const mins = Math.floor(diff / (1000 * 60));
      const secs = Math.floor(diff / 1000);
    
      const d = days;
      const h = hours - days * 24;
      const m = mins - hours * 60;
      const s = secs - mins * 60;
    
      document.getElementById("timer").innerHTML =
        "<div>" +
        d +
        "<span>Days</span></div>" +
        "<div>" +
        h +
        "<span>Hours</span></div>" +
        "<div>" +
        m +
        "<span>Minutes</span></div>" +
        "<div>" +
        s +
        "<span>Seconds</span></div>";
    }
    updateTimer();
    setInterval(updateTimer, 1000);
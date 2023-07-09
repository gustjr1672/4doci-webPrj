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

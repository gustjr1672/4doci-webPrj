
let exitBtn = document.getElementById("exit-btn");
let exitModal = document.getElementById("exit-modal");

exitBtn.onclick = function(e){
    exitModal.classList.remove("hidden");
  }

  exitModal.addEventListener("click",function(e){
        exitModal.classList.add("hidden");
    })


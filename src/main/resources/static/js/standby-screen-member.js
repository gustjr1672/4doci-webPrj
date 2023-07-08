
let exitBtn = document.getElementById("exit-btn");
let exitModal = document.getElementById("exitModal");

exitBtn.onclick = function(e){
    e.preventDefault();
    exitModal.classList.remove("hidden");
  }

  exitModal.addEventListener("click",function(e){
    if(e.target.classList.contains("closeBtn"))
        exitModal.classList.add("hidden");
    else if(e.target.classList.contains("okBtn")){
        exitModal.classList.add("hidden");
      form.submit();
    }
    })


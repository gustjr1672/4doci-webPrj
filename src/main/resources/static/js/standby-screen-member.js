const modal = document.querySelector("#modal");
const exitBtn = document.querySelector(".exit-btn");
const cancellBtn = modal.querySelector(".cancell-btn");


exitBtn.addEventListener("click", ()=>{
    modal.style.display = "flex";
});

cancellBtn.addEventListener("click",()=>{
     modal.style.display = "none";
});


 



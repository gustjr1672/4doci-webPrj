const modal = document.querySelector("#modal");
const buttons = document.querySelector("#buttons");
const inviteBtn = buttons.querySelector(".invite-btn");
const closeBtn = modal.querySelector(".btns button:nth-child(2)");
const checkBoxes = modal.querySelectorAll(".content input");

inviteBtn.addEventListener("click", ()=>{
    modal.style.display = "flex";
});

closeBtn.addEventListener("click",()=>{
    modal.style.display = "none";
    checkBoxes.forEach(element => {
        if(element.checked)
            element.checked = false;
    });

});


 



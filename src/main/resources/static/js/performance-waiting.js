const uniqueId = document.getElementById("cid").dataset.uniqueId;

let giveUpButton = document.getElementById('challenge-give-up');

let giveUpModal = document.querySelector('.choice-modal');
let giveUpModalClose = document.getElementById('close-btn');
let giveUpModalOk = document.getElementById('give-up-btn');

let giveUpComplete = document.querySelector(".complete-modal");
let giveUpCompleteOk = giveUpComplete.querySelector(".modal-close");
giveUpButton.addEventListener('click',function (){
    giveUpModal.classList.remove('hidden');
})

giveUpModalClose.addEventListener('click',function (){
    giveUpModal.classList.add('hidden')
})
giveUpModalOk.addEventListener('click',function (){
    giveUpComplete.classList.remove("hidden");
})
giveUpCompleteOk.addEventListener('click',function (){
    location.href = `performance-records/delete?cid=${uniqueId}`;
})


let nowStartButton = document.getElementById('challenge-now-start');
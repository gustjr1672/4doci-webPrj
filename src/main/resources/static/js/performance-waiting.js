const uniqueId = document.getElementById("cid").dataset.uniqueId;

let giveUpButton = document.getElementById('challenge-give-up');
let nowStartButton = document.getElementById('challenge-now-start');

let checkModal = document.querySelector('.choice-modal');
let checkModalClose = document.getElementById('close-btn');
let giveUpBtn = document.getElementById('give-up-btn');
let startBtn = document.getElementById('start-btn');
let content = document.querySelector(".content");


let completeModal = document.querySelector(".complete-modal");
let completeModalClose = completeModal.querySelector(".modal-close");
let completeModalOk = completeModal.querySelector(".complete-ok");
let completeContent = completeModal.querySelector(".complete-content");
giveUpButton.addEventListener('click',function (){
    content.innerHTML = `도전을 포기하시겠습니까? <br> 포기할 시 해당 도전이 삭제됩니다.`;
    giveUpBtn.classList.remove("hidden");
    startBtn.classList.add("hidden");
    checkModal.classList.remove('hidden');
})

checkModalClose.addEventListener('click',function (){
    checkModal.classList.add('hidden')
})
giveUpBtn.addEventListener('click',function (){
    completeContent.innerHTML = "도전이 삭제되었습니다.";
    completeModal.classList.remove("hidden");
    completeModalClose.classList.remove("hidden");
})
completeModalClose.addEventListener('click',function (){
    location.href = `performance-records/delete?cid=${uniqueId}`;
})



// =========================바로시작하기==========================

nowStartButton.addEventListener('click', function (){
    content.innerHTML = "도전을 지금 바로 시작하시겠습니까?";
    giveUpBtn.classList.add("hidden");
    startBtn.classList.remove("hidden");
    checkModal.classList.remove('hidden');
})

startBtn.addEventListener('click',function (){
    let challengeId = uniqueId.split('_')[1];
    if (uniqueId.includes("CH")) {
        fetch(`/challenge/choice/${challengeId}`, {
            method: 'PUT'
        }
            .then(response => nowStartComplete()));
    }
    else
        if (uniqueId.includes("FC")) {
            fetch(`/challenge/freeChallenge/${challengeId}`,{
                method:'PUT'
            })
                .then(response =>nowStartComplete());
        }
    })

function nowStartComplete(){
    completeContent.innerHTML = "도전을 시작하였습니다.";
    completeModal.classList.remove("hidden");
    completeModalOk.classList.remove("hidden");
}

completeModalOk.addEventListener('click',function (){
    location.href = "/main";
})
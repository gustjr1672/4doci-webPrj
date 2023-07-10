const onGoingChallengeBtn = document.querySelector(".on-going");
const waitingChallengeBtn = document.querySelector(".waiting");
onGoingChallengeBtn.addEventListener('click', () => {
    waitingChallengeBtn.classList.remove("active");
    onGoingChallengeBtn.classList.add("active");
    bringOngoingChallenge();
});

waitingChallengeBtn.addEventListener('click', () => {
    onGoingChallengeBtn.classList.remove("active");
    waitingChallengeBtn.classList.add("active");
    bringWaitingChallenge();
});


function bringOngoingChallenge() {
    let chalListSection = document.querySelector(".chal-list");
    let chalList = chalListSection.querySelector(".chal-section");
    let userId = chalList.dataset.userId;
    fetch(`/api/all-challenges/${userId}`,)
        .then(response => response.json())
        .then(allChallengelist => {
            chalList.innerHTML = " ";

            for (let challenge of allChallengelist) {

                if (challenge.performanceRecordsId != null) {

                    let chalTemplate =
                        `<div class="btn-wrap" >
                            <button class="chal-btn" data-unique-id="${challenge.uniqueId}" data-challenge-state="진행중">
                            <span class="chal-title" >${challenge.name}</span>
                            <div class="prog-wrap">
                              <div>
                                <span class="achv-Quantity" > ${challenge.achvQuantity}</span>
                                <span>/</span>
                                <span >${challenge.goalQuantity} ${challenge.unitName}</span>
                                <span hidden="hidden"class="goal_quantity">${challenge.goalQuantity}</span>
                              </div>
                              <div class="prog-bar">
                                <div class="prog"
                                     data-achv-quantity="${challenge.achvQuantity}"
                                     data-goal-quantity="${challenge.goalQuantity}">
                                </div>
                              </div>
                            </div>
                          </button>
                          <button class="plus-btn"
                                  data-goal-quantity="${challenge.goalQuantity}"
                                  data-challenge-id="${challenge.uniqueId}">+</button>
                        <div>`

                    chalList.insertAdjacentHTML("beforeend", chalTemplate);
                }
                progBar();
            }
        });
}

function bringWaitingChallenge() {
    let chalListSection = document.querySelector(".chal-list");
    let chalList = chalListSection.querySelector(".chal-section");
    let userId = chalList.dataset.userId;
    fetch(`/api/all-challenges/${userId}`,)
        .then(response => response.json())
        .then(list => {
            chalList.innerHTML = " ";
            for (let challenge of list) {

                if (challenge.performanceRecordsId == null) {
                    let chalTemplate = `
                        <div class="btn-wrap">
                            <button class="chal-btn" data-unique-id="${challenge.uniqueId}" data-challenge-state="대기중">
                            <span class="chal-title">${challenge.name} </span>
                    </button>
                        <div class="chal-wait">도전<br>대기중</div>
                    </div>`

                    chalList.insertAdjacentHTML("beforeend", chalTemplate);
                }
            }
        })
}

function progBar() {
    let progressBars = document.querySelectorAll(".prog");

    for (let progressBar of progressBars) {

        let achvQuantity = parseInt(progressBar.dataset.achvQuantity);
        let goalQuantity = parseInt(progressBar.dataset.goalQuantity);
        progressBar.style.width = achvQuantity / goalQuantity * 100 + '%';
    }
}


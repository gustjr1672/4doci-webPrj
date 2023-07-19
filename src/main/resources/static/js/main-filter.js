const onGoingChallengeBtn = document.querySelector(".on-going");
const waitingChallengeBtn = document.querySelector(".waiting");

let noChallengeComment = document.querySelector('.no-ongoing-chal-wrap')
let content = document.querySelector('.no-chal');

let noWaitingComment = document.querySelector('.no-waiting-chal-wrap');
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

function AddNoChallengeComment(){
    let comment = "";
    noWaitingComment.innerHTML = '';

    noChallengeComment.innerHTML = `
        <div class="no-chal zero">
            <div class="typing-indicator">
                <div class="typing-circle"></div>
                <div class="typing-circle"></div>
                <div class="typing-circle"></div>
                <div class="typing-shadow"></div>
                <div class="typing-shadow"></div>
                <div class="typing-shadow"></div>
            </div>
            <span class="no-chal-title">진행중인 도전이 없습니다</span>
        </div>
    `;
}

function bringOngoingChallenge() {
    let chalListSection = document.querySelector(".chal-list");
    let chalList = chalListSection.querySelector(".chal-section");
    let userId = chalList.dataset.userId;
    let currentDate = chalList.dataset.nowDate;
    fetch(`/api/all-challenges/${userId}`,)
        .then(response => response.json())
        .then(allChallengelist => {
            chalList.innerHTML = " ";
            noWaitingComment.innerHTML = " ";
            if (content != null) {
                AddNoChallengeComment();
            }

            for (let challenge of allChallengelist) {

                if (challenge.performanceRecordsId != null && challenge.endDate > currentDate) {
                    let chalTemplate =
                        `<div class="btn-wrap" >
                            <button class="chal-btn cursor zoom-2" data-unique-id="${challenge.uniqueId}" data-challenge-state="진행중">
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
                          <button class="plus-btn cursor brighten"
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

            noChallengeComment.innerHTML = " ";

            chalList.innerHTML = " ";

            let count = 0;

            for (let challenge of list) {
                if (challenge.performanceRecordsId == null) {
                    count+=1;
                    let chalTemplate = `
                        <div class="btn-wrap">
                            <button class="chal-btn zoom-2" data-unique-id="${challenge.uniqueId}" data-challenge-state="대기중">
                            <span class="chal-title">${challenge.name} </span>
                    </button>
                        <div class="chal-wait">도전<br>대기중</div>
                    </div>`

                    chalList.insertAdjacentHTML("beforeend", chalTemplate);
                }
            }
            if (count === 0) {
                noWaitingComment.innerHTML = `
                <div class="no-chal">
                    <div class="typing-indicator">
                        <div class="typing-circle"></div>
                        <div class="typing-circle"></div>
                        <div class="typing-circle"></div>
                        <div class="typing-shadow"></div>
                        <div class="typing-shadow"></div>
                        <div class="typing-shadow"></div>
                    </div>
                    <span class="no-chal-title">대기중인 도전이 없습니다</span>
                </div>
            `;
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


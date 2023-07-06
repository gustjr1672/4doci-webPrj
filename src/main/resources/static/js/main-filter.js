const currentDate = new Date();

function applyFilter() {
    let selectedValue = filter.value;

    myChallengeList(selectedValue);
}


function myChallengeList(selectedValue) {
    let chalListSection = document.querySelector(".chal-list");
    let chalList = chalListSection.querySelector(".chal-section");
    let userId = chalList.dataset.userId;


    if (selectedValue === 'on-going') {
        fetch(`/api/all-challenges/${userId}`,)
            .then(response => response.json())
            .then(allChallengelist => {
                chalList.innerHTML = " ";

                for (let challenge of allChallengelist) {
                    console.log(challenge.performanceRecordsId);
                    let chalStartDate = new Date(challenge.startDate);

                    if (challenge.performanceRecordsId != null) {

                        let chalTemplate =
                            `<div class="btn-wrap" >
                            <button class="chal-btn" data-unique-id="${challenge.uniqueId}">
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
    } else if (selectedValue === 'waiting') {
        fetch(`/api/all-challenges/${userId}`,)
            .then(response => response.json())
            .then(list => {
                chalList.innerHTML = " ";
                for (let challenge of list) {
                    let chalStartDate = new Date(challenge.startDate);

                    if (challenge.performanceRecordsId == null) {
                        let chalTemplate = `                
                        <div class="btn-wrap">
                            <button class="chal-btn" data-unique-id="${challenge.uniqueId}">
                            <span class="chal-title">${challenge.name} </span>
                    </button>
                        <div class="chal-wait">도전<br>대기중</div>
                    </div>`

                        chalList.insertAdjacentHTML("beforeend", chalTemplate);
                    }
                }
            })
    }
}

function progBar() {
    let progressBars = document.querySelectorAll(".prog");

    for (let progressBar of progressBars) {

        let achvQuantity = parseInt(progressBar.dataset.achvQuantity);
        let goalQuantity = parseInt(progressBar.dataset.goalQuantity);
        progressBar.style.width = achvQuantity / goalQuantity * 100 + '%';
    }
}


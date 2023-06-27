
let challengeList = document.querySelector(".chal-list");

challengeList.onclick = ((e) => {
    if (e.target.className !== 'plus-btn') {
        return;
    }
    let button = e.target; // 클릭된 버튼
    let progressBar = button.parentNode.querySelector('.prog'); // 클릭된 버튼의 부모 요소에서 게이지를 찾음
    let goalQuantity = button.dataset.goalQuantity;
    let achvQuantity = button.parentNode.querySelector(".achv-Quantity");

    increaseProgress(progressBar,goalQuantity);

    let challengeTypeAndId =button.dataset.challengeId;
    let data = `cid=${challengeTypeAndId}`;

    (async () =>{
        await fetch("/challenge/achv-quantity",{
            method:'POST',
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(data)
        });

        let response = await fetch(`/challenge/achv-quantity?cid=${challengeTypeAndId}`);
        let nowAchvQuantity =  await response.json();
        achvQuantity.textContent = nowAchvQuantity;
    })();
});

function increaseProgress(progressBar,goalQuantity) {

    let progress = parseInt(progressBar.style.width) || 0;
    let increment = 100/goalQuantity; // 게이지가 (목표량 분의 100) 씩 증가하도록 설정
    let target = progress + increment;

    //게이지가 스르륵 채워지도록 함
    let interval = setInterval(() => {
        if(progress >= target)
            clearInterval(interval);
        else{
            progress ++;
            progressBar.style.width = progress + '%';
        }
    }, 8);
}

window.onload = function (){
    let progressBars = document.querySelectorAll(".prog");

    for (let progressBar of progressBars) {

        let achvQuantity = parseInt (progressBar.dataset.achvQuantity);
        let goalQuantity = parseInt(progressBar.dataset.goalQuantity);
        console.log( "달성량"+ achvQuantity);
        console.log("목표량 " + goalQuantity);
        progressBar.style.width = achvQuantity / goalQuantity *100 + '%';
    }
}
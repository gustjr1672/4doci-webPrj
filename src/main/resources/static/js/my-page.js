

function changeTONormalProfile(){
    let fileName = "/upload/image/profile/normal.png";

    let userImg = document.querySelector(".user-image");
    userImg.style.backgroundImage = `url(${fileName})`;

    let modal = document.querySelector('.modal');
    modal.classList.remove('show');

    let data = new URLSearchParams();
    data.append("fileName", fileName);

    fetch("/my-page/change/normal-profile",{
        method: 'POST',
        body: data
    })
}


function loadFile(input="normal") {
    let modal = document.querySelector('.modal');
    modal.classList.remove('show');

    let file = input.files[0]; //선택된 파일 가져오기
    let userImg = document.querySelector(".user-image");
    userImg.style.backgroundImage = `url("${URL.createObjectURL(file)}")`;

    const formData = new FormData();
    formData.append("profileImage", file);


    fetch("/my-page/change/new-profile",{
        method: 'POST',
        body: formData,
    })
}



document.addEventListener('DOMContentLoaded', function() {
    let modal = document.querySelector('.modal');
    let closeModalButton = modal.querySelector('.close');
    let changeImageButton = document.querySelector('.change-img-btn');
    let normalImageButton = document.querySelector('.normal-image');

    changeImageButton.addEventListener('click', function() {
        modal.classList.add('show'); // 모달에 show 클래스 추가하여 나타나도록 설정
    });

    closeModalButton.addEventListener('click', function() {
        modal.classList.remove('show'); // 모달에서 show 클래스 제거하여 사라지도록 설정
    });
    normalImageButton.addEventListener('click',()=>{
        changeTONormalProfile();
    });
});
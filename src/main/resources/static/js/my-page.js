function loadFile(input) {


    let file = input.files[0]; //선택된 파일 가져오기


    let filename = file.name;

    let userImg = document.querySelector(".user-image");
    userImg.style.backgroundImage = `url("${URL.createObjectURL(file)}")`;

    const formData = new FormData();
    formData.append("profileImage", file);
    formData.append("filename", filename);

    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = (() =>{
        if (xhr.readyState == XMLHttpRequest.DONE){
            if (xhr.status == 200){
            }
        }
    })

    xhr.open("Post", "/my-page/upload/profile",true);
    xhr.send(formData);

}
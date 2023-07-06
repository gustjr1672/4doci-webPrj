let main = document.querySelector("main");
let box = document.querySelector(".comment-box");
let commentModal = document.querySelector(".comment-modal");
let CommentContentList = document.querySelector(".comment-content-list");
let commentBtn =null;

main.addEventListener("click", function (e) {
    
    if (!(e.target.classList.contains("comment-button") ||
        e.target.parentElement.classList.contains("comment-button")))
    return;

    CommentContentList.innerHTML="";

    commentBtn = e.target;

    fetch(`/comment?rid=${commentBtn.dataset.recordId}`)
    .then(response => response.json())
    .then(list => {
        
        for(let comment of list){
        let template = `
        <div class="comment-content">
            <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
            <div class="comment-content-text">
              <div>
                <span class="comment-content-nickname">${comment.nickName}</span>
                <span>${comment.content}</span>
                <div class="comment-content-period">${comment.timeMessage}</div>
              </div>
            </div>
        </div>
        `

        CommentContentList.insertAdjacentHTML("beforeend", template);
        }

    });
        
    commentModal.classList.remove("hidden");
    document.body.style.overflow = "hidden";

    setTimeout(function () {
        box.classList.add("comment-box-show");
    }, 100);

});

commentModal.addEventListener("click", (e) => {

    if (e.target.className == "comment-modal") {

        box.classList.remove("comment-box-show");
        document.body.style.overflow = "auto";
        document.querySelector(".comment-footer input").value = null;

        setTimeout(function () {
            commentModal.classList.add("hidden");
        }, 1000);
    }

});


const commentRegBtn = document.querySelector(".comment-footer button");

commentRegBtn.addEventListener("click", (e) => {

    let content = document.querySelector(".comment-footer input").value;
    let memberId = commentRegBtn.dataset.memberId;
    let recordsId = commentBtn.dataset.recordId;

    let formData = new FormData();
    formData.append("content", content);
    formData.append("memberId", memberId);
    formData.append("performanceRecordsId", recordsId);

    fetch("/comment", {
        method: "POST",
        body: formData
    })
    .then(response =>{
        if(!response.ok) 
            throw new Error("등록에 실패했습니다");
        
        return response.json();
    }).then(list => {       

        CommentContentList.innerHTML="";

        for(let comment of list){
            let template = `
            <div class="comment-content">
                <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                <div class="comment-content-text">
                  <div>
                    <span class="comment-content-nickname">${comment.nickName}</span>
                    <span>${comment.content}</span>
                    <div class="comment-content-period">${comment.timeMessage}</div>
                  </div>
                </div>
            </div>
            `
            CommentContentList.insertAdjacentHTML("beforeend", template);
        }

        document.querySelector(".comment-footer input").value = null;

    })  
    .catch(error => alert(error.message));
    
    

});
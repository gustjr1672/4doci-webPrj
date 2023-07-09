let socket = null;

document.addEventListener("DOMContentLoaded", function () {
  // 소켓 연결
  connectWs();
});

function connectWs() {
  let ws = new SockJS("/community");
  socket = ws;
  ws.onopen = function () {
    console.log("open");
  };

  ws.close = function () {
    console.log("close");
  };
}

let main = document.querySelector("main");
let box = document.querySelector(".comment-box");
let commentModal = document.querySelector(".comment-modal");
let CommentContentList = document.querySelector(".comment-content-list");
let commentBtn = null;
let commentRegBtn = document.querySelector(".comment-footer button");
let userId = commentRegBtn.dataset.memberId;

/*댓글 불러오기*/
main.addEventListener("click", function (e) {

    if (!(e.target.classList.contains("comment-button") ||
        e.target.parentElement.classList.contains("comment-button")))
        return;

    CommentContentList.innerHTML = "";

    commentBtn = e.target;

    fetch(`/api/comments/${commentBtn.dataset.recordId}`)
        .then(response => response.json())
        .then(list => {
            let template = null;
            for (let comment of list) {

                if (comment.memberId == userId) {
                    template = `
                <div class="comment-content">
                    <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                    <div class="comment-content-text">
                    <div class="comment-content-section" id=${comment.id}>
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input" data-content="${comment.content}">${comment.content}</span>
                        <input type="text" value="${comment.content}" class="comment-content-input-edit hidden" >
                        <div class="comment-content-footer">
                            <div class="comment-content-period">${comment.timeMessage}</div>
                            <div class="comment-content-buttons">
                                <button class="comment-edit-button" data-id="${comment.id}">수정</button>
                                <button class="comment-delete-button" data-id="${comment.id}" data-record-id="${comment.performanceRecordsId}">삭제</button>
                            </div>
                            <div class="comment-edit-buttons hidden">
                                <button class="comment-edit-cancel-button" data-id="${comment.id}">취소</button>
                                <button class="comment-edit-fix-button" data-id="${comment.id}">완료</button>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>`
                } else {
                    template = `
                <div class="comment-content">
                    <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                    <div class="comment-content-text">
                    <div class="comment-content-section">
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input">${comment.content}</span>
                        <div class="comment-content-period">${comment.timeMessage}</div>
                    </div>
                    </div>
                </div>`
                }
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
        }, 400);
    }

});



/*댓글 등록*/
commentRegBtn.addEventListener("click", (e) => {

    let content = document.querySelector(".comment-footer input").value;

    if(!content)
        return;

    let recordsId = commentBtn.dataset.recordId;

    let formData = new FormData();
    formData.append("content", content);
    formData.append("memberId", userId);
    formData.append("performanceRecordsId", recordsId);

    fetch("/api/comments", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok)
                throw new Error("등록에 실패했습니다");

            return response.json();
        }).then(list => {
            
            CommentContentList.innerHTML = "";
            let template = null;
            for (let comment of list) {

                if (comment.memberId == userId) {
                    template = `
                    <div class="comment-content">
                    <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                    <div class="comment-content-text">
                    <div class="comment-content-section" id=${comment.id}>
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input" data-content="${comment.content}">${comment.content}</span>
                        <input type="text" value="${comment.content}" class="comment-content-input-edit hidden" >
                        <div class="comment-content-footer">
                            <div class="comment-content-period">${comment.timeMessage}</div>
                            <div class="comment-content-buttons">
                                <button class="comment-edit-button" data-id="${comment.id}">수정</button>
                                <button class="comment-delete-button" data-id="${comment.id}" data-record-id="${comment.performanceRecordsId}">삭제</button>
                            </div>
                            <div class="comment-edit-buttons hidden">
                                <button class="comment-edit-cancel-button" data-id="${comment.id}">취소</button>
                                <button class="comment-edit-fix-button" data-id="${comment.id}">완료</button>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>`
                } else {
                    template = `
                <div class="comment-content">
                    <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                    <div class="comment-content-text">
                    <div class="comment-content-section">
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input">${comment.content}</span>
                        <div class="comment-content-period">${comment.timeMessage}</div>
                    </div>
                    </div>
                </div>`
                }
                CommentContentList.insertAdjacentHTML("beforeend", template);
            }

            document.querySelector(".comment-footer input").value = null;

            let commentCount = Object.keys(list).length;
            let span = document.getElementById(list[0].performanceRecordsId);
            span.innerText = commentCount;

            //해당 피드 주인한테 알림 메세지 보내기
            if (socket) {     //소켓이 있으면(socket = ws 가 꽂아졌으면)
                
                if(userId == commentBtn.dataset.memberId)
                    return;

                let socketMsg = "request," + commentBtn.dataset.memberId + "," + "2," + "1";    //e.target.dataset.id : member id
                socket.send(socketMsg);
              }

        })
        .catch(error => alert(error.message));


});


/*댓글 삭제*/
CommentContentList.addEventListener("click", (e) => {
    
    if (!e.target.classList.contains("comment-delete-button"))
        return;

    //확인 모달 띄우기
    document.querySelector(".choice-modal").classList.remove("hidden");

    let commentDeleteButton = e.target;
    commentId = commentDeleteButton.dataset.id;
    let recordId = commentDeleteButton.dataset.recordId;

    let deleteCheckButton = document.querySelector(".delete-Confirm-button");
    let deleteCancelButton = document.querySelector(".delete-cancel-button");

    //확인 모달 취소 버튼
    deleteCancelButton.addEventListener("click", function cancel() {
        document.querySelector(".choice-modal").classList.add("hidden");
        
        deleteCancelButton.removeEventListener("click", cancel);    //CommentContentList.addEventListener의 내부에 있기 때문에 이벤트 제거 안하면 누적된다
    });

    //확인 모달 삭제 버튼
    deleteCheckButton.addEventListener("click", function action() {

    fetch(`/api/comments/${commentId}`, {
        method: "DELETE"
    })
        .then(response => {
            if (!response.ok)
                throw (new Error("삭제에 실패했습니다"));
            else
                return response.json();
        })
        .then(list => {
    
            CommentContentList.innerHTML = "";
            let template = null;
            for (let comment of list) {

                if (comment.memberId == userId) {
                    template = `
                    <div class="comment-content">
                    <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                    <div class="comment-content-text">
                    <div class="comment-content-section" id=${comment.id}>
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input" data-content="${comment.content}">${comment.content}</span>
                        <input type="text" value="${comment.content}" class="comment-content-input-edit hidden" >
                        <div class="comment-content-footer">
                            <div class="comment-content-period">${comment.timeMessage}</div>
                            <div class="comment-content-buttons">
                                <button class="comment-edit-button" data-id="${comment.id}">수정</button>
                                <button class="comment-delete-button" data-id="${comment.id}" data-record-id="${comment.performanceRecordsId}">삭제</button>
                            </div>
                            <div class="comment-edit-buttons hidden">
                                <button class="comment-edit-cancel-button" data-id="${comment.id}">취소</button>
                                <button class="comment-edit-fix-button" data-id="${comment.id}">완료</button>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>`
                } else {
                    template = `
            <div class="comment-content">
                <img class="comment-profile-img" src="${comment.profileImage}" alt="" />
                <div class="comment-content-text">
                <div class="comment-content-section">
                    <span class="comment-content-nickname">${comment.nickName}</span>
                    <span class="comment-content-input">${comment.content}</span>
                    <div class="comment-content-period">${comment.timeMessage}</div>
                </div>
                </div>
            </div>`
                }
                CommentContentList.insertAdjacentHTML("beforeend", template);
            }

            document.querySelector(".comment-footer input").value = null;
            
            let commentCount = 0;

            if(list)
                commentCount = Object.keys(list).length;

            let span = document.getElementById(recordId);
            span.innerText = commentCount;
            

        })
        .catch(error => alert(error.message));

        document.querySelector(".choice-modal").classList.add("hidden");

        deleteCheckButton.removeEventListener("click", action);
    });
});

/*댓글 수정*/
CommentContentList.addEventListener("click", (e) => {

    if (!e.target.classList.contains("comment-edit-button"))
        return;

    let commentEditBtn = e.target;

    //수정할 녀석의 comment-content-input과 comment-content-input-edit이 필요
    //commentId를 이용해 문서에서 해당 아이디를 가진 comment-content-section을 불러온다(section에 id를 부여해 놓는다)
    let commentId = commentEditBtn.dataset.id;
    let commentSection = document.getElementById(commentId);

    let commentInput = commentSection.querySelector(".comment-content-input");
    let commentInputEdit = commentSection.querySelector(".comment-content-input-edit");

    commentInput.classList.add("hidden");
    commentInputEdit.classList.remove("hidden");
    commentInputEdit.classList.add("slide-open")

    let commentContentButtons = commentSection.querySelector(".comment-content-buttons");
    let commentEditButtons = commentSection.querySelector(".comment-edit-buttons");

    commentContentButtons.classList.add("hidden");
    commentEditButtons.classList.remove("hidden");

    //다른 버튼들 막기
    let allButtons = CommentContentList.querySelectorAll("button");
    for(botton of allButtons){
        if(botton.dataset.id != commentId)
            botton.disabled = true;
     }

    //댓글 수정 완료 버튼
    let editFixBtn = commentSection.querySelector(".comment-edit-fix-button");
    editFixBtn.addEventListener("click", function edit(){
        
        let newContent = commentInputEdit.value;    //newContent를 이벤트 밖에 선언하면 기존 html의 value="${comment.content}"로 초기화 된다
        
        if(!newContent)
        return;

        let formData = new FormData();
        formData.append("id", commentId);
        formData.append("content", newContent);

        fetch("/api/comments", {
            method: "PUT",
            body: formData
        })
        .then(response => {
            if(!response.ok)
                throw(new Error("수정에 실패했습니다"));
            else
                return response.json();
        })
        .then(modifiedComment => {
            commentContentButtons.classList.remove("hidden");
            commentEditButtons.classList.add("hidden");
            commentInput.classList.remove("hidden");
            commentInputEdit.classList.add("hidden");
            //수정된 댓글로 span태그 텍스트와 data 변경
            commentInput.innerText = modifiedComment.content;
            commentInput.dataset.content = modifiedComment.content;
        })
        .catch(error => alert(error.message));

        for(botton of allButtons)
            botton.disabled = false;
        
        editFixBtn.removeEventListener("click", edit);
    });

    //댓글 수정 취소 버튼
    let editCancelBtn = commentEditButtons.querySelector(".comment-edit-cancel-button");
    editCancelBtn.addEventListener("click", function cancelEdit() {
        
        commentContentButtons.classList.remove("hidden");
        commentEditButtons.classList.add("hidden");
        commentInput.classList.remove("hidden");
        commentInputEdit.classList.add("hidden");
        commentInputEdit.value = commentInput.dataset.content;
        commentInputEdit.classList.remove("slide-open");
        
        for(botton of allButtons)
            botton.disabled = false;

        editCancelBtn.removeEventListener("click", cancelEdit);
    });
    
})



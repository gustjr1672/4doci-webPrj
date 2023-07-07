let main = document.querySelector("main");
let box = document.querySelector(".comment-box");
let commentModal = document.querySelector(".comment-modal");
let CommentContentList = document.querySelector(".comment-content-list");
let commentBtn = null;
let commentRegBtn = document.querySelector(".comment-footer button");
let userId = commentRegBtn.dataset.memberId;

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
                    <div class="comment-content-section">
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input">${comment.content}</span>
                        <div class="comment-content-footer">
                            <div class="comment-content-period">${comment.timeMessage}</div>
                            <div class="comment-content-buttons">
                                <button>수정</button>
                                <button class="comment-delete-button" data-id="${comment.id}">삭제</button>
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
        }, 1000);
    }

});




commentRegBtn.addEventListener("click", (e) => {

    let content = document.querySelector(".comment-footer input").value;
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
                    <div class="comment-content-section">
                        <span class="comment-content-nickname">${comment.nickName}</span>
                        <span class="comment-content-input">${comment.content}</span>
                        <div class="comment-content-footer">
                            <div class="comment-content-period">${comment.timeMessage}</div>
                            <div class="comment-content-buttons">
                                <button>수정</button>
                                <button class="comment-delete-button" data-id="${comment.id}">삭제</button>
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

        })
        .catch(error => alert(error.message));


});



CommentContentList.addEventListener("click", (e) => {

    if (!e.target.classList.contains("comment-delete-button"))
        return;

    document.querySelector(".choice-modal").classList.remove("hidden");

    let commentDeleteButton = e.target;
    commentId = commentDeleteButton.dataset.id;

    let deleteCheckButton = document.querySelector(".delete-check-button");
    deleteCheckButton.addEventListener("click", (e) => {

    

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
                <div class="comment-content-section">
                    <span class="comment-content-nickname">${comment.nickName}</span>
                    <span class="comment-content-input">${comment.content}</span>
                    <div class="comment-content-footer">
                        <div class="comment-content-period">${comment.timeMessage}</div>
                        <div class="comment-content-buttons">
                            <button>수정</button>
                            <button class="comment-delete-button" data-id="${comment.id}">삭제</button>
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

        })
        .catch(error => alert(error.message));

        document.querySelector(".choice-modal").classList.add("hidden");

    });
});
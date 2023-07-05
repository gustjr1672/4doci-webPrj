const modal = document.querySelector("#modal");
const buttons = document.querySelector("#buttons");

const closeBtn = modal.querySelector(".btns button:nth-child(2)");
const checkBoxes = modal.querySelectorAll(".content input");

const friendsSection = document.querySelector(".profile-wrap");
let profiles = document.querySelectorAll(".profile");
const friendModalSection = document.querySelector(".friends-list");
const submitBtn = document.querySelector(".submitBtn");
const choiceModal = document.querySelector(".choice-modal");

let leaderId = document.querySelector(".leaderId").value;
let challengeId = document.querySelector(".challengeId").value;
let userId;

friendsSection.addEventListener("click", function (e) {
    if (e.target.matches(".add-btn")) {
        modal.classList.remove("hidden");
    }
});
closeBtn.addEventListener("click", () => {
    modal.classList.add("hidden");
});

let selectedIds = []; // 모달에서 체크한 id 를 배열로 가져오는 함수
friendModalSection.addEventListener("change", function (e) {
    if (e.target.matches(".checkbox")) {
        if (e.target.checked)
            selectedIds.push(e.target.dataset.id);
        else {
            let index = selectedIds.indexOf(e.target.dataset.id);
            if (index !== -1)
                selectedIds.splice(index, 1);
        }
    }
})



submitBtn.addEventListener("click", function (e) {
    (async () => {
        modal.classList.add("hidden");
        let formdata = new FormData();
        formdata.append("friendList", selectedIds);
        formdata.append("challengeId", challengeId);
        selectedIds = [];
        const putResponse = await fetch("/api/groupchallenge", {
            method: 'PUT',
            body: formdata
        })
        if (putResponse.ok)
            await reprintFreindSection();
    })();
})



friendsSection.addEventListener("click", function (e) {
    if (e.target.classList.contains("delete-img")) {
        choiceModal.classList.remove("hidden");
        userId = e.target.dataset.id
    }
})


choiceModal.addEventListener("click", function (e) {
    if (e.target.classList.contains("closeBtn")) {
        choiceModal.classList.add("hidden");
        console.log("취소");
    }
    else if (e.target.classList.contains("cancelBtn")) {
        console.log("삭제");
        choiceModal.classList.add("hidden");
        console.log(userId);
        console.log(challengeId);
        (async () => {
            const deleteResponse = await fetch(`/api/groupchallenge/${challengeId}/members/${userId}`, {
                method: 'DELETE',
            })
            if (deleteResponse.ok)
                await reprintFreindSection();
        })();
    }
})


let button = document.querySelector(".modifyBtn");
function modify() {
    let profiles = document.querySelectorAll(".profile");

    if (button.textContent === "수정") {
        button.textContent = "수정취소";
        profiles.forEach((profile) => {
            let deleteBtn = profile.querySelector(".delete-btn");
            deleteBtn.classList.remove("hidden");
            deleteBtn.classList.add("shake-animation");

            setTimeout(() => {
                deleteBtn.classList.remove("shake-animation");
            }, 2000);
        })
    }
    else {
        button.textContent = "수정";

        profiles.forEach((profile) => {
            let deleteBtn = profile.querySelector(".delete-btn");
            deleteBtn.classList.add("hidden");
        });
    }
}


async function reprintFreindSection() {
    let getResponse = await fetch(`/api/groupchallenge/${challengeId}`);
    let data = await getResponse.json();
    console.log(data);
    const friendList = data.newFriendList;
    const notInviList = data.notInviList;
    console.log("친구목록", friendList);
    friendsSection.innerHTML = "";
    friendsSection.insertAdjacentHTML("beforeend",
        `<div class="add-friend">
            <button class="add-btn">+</button>
        </div>`);

    for (const friend of friendList) {
        if (friend.toMemberId !== parseInt(leaderId)) {
            let isAcceptClass = friend.isAccept === '대기중' ? 'blur' : 'hidden';
            let deleteBtnClass = 'delete-btn hidden';
            if(button.textContent === "수정취소")
                deleteBtnClass = 'delete-btn'
            let newFriendTemplate = `
        <div class="profile"> 
            <div>
                <div class="with-user">
                    <button class="${deleteBtnClass}" ><img class="delete-img" src="/image/startchallenge/delete.png" data-id="${friend.toMemberId}" ></button>
                    
                    <img class="img" src="${friend.profileImage}" alt="profile-img">
                    <div class="${isAcceptClass}">대기중</div>
                </div>
                <span>${friend.nickname}</span>
            </div>
        </div>
`
            friendsSection.insertAdjacentHTML("beforeend", newFriendTemplate);
        }
    }

    friendModalSection.innerHTML = "";
    for (const friend of notInviList) {
        modalTemplate = `
        <div class="content">
        <div class="info">
         <img src="${friend.profileImage}" alt="프로필이미지" />
        <div>
          <span> ${friend.nickname}</span>
          <span> ${friend.name}</span>
        </div>
        </div>
        <input type="checkbox" class="checkbox" data-id="${friend.id}">
        `
        friendModalSection.insertAdjacentHTML("beforeend", modalTemplate)
    }
}


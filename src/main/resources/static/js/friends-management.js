const btns = document.getElementById("btns");
const leftBtn = btns.querySelector(".left-btn");
const rightBtn = btns.querySelector(".right-btn");
const leftContent = document.querySelector("#left-content");
const rightContent = document.querySelector("#right-content");

function toggleContent(content) {
  if (content === "left") {
    leftBtn.classList.add("active");
    rightBtn.classList.remove("active");
    leftContent.classList.remove("hidden");
    rightContent.classList.add("hidden");
  } else if (content === "right") {
    rightBtn.classList.add("active");
    leftBtn.classList.remove("active");
    rightContent.classList.remove("hidden");
    leftContent.classList.add("hidden");
  }
}
let addSearchBtn = document.getElementById("add-search");
let addSearchInput = document.querySelector("input[name=n]");
let newfriendList = document.getElementById("new-friend-list");
addSearchBtn.addEventListener("click", () => {
  console.log(addSearchInput.value);
  if (addSearchInput.value == "") return;
  fetch(`/friendmanage/newfriend/search?n=${addSearchInput.value}`)
    .then((response) => response.json())
    .then((list) => {
      newfriendList.innerHTML = "";
      Object.entries(list).map(([key, value]) => {
        console.log(value);
        let newFriendTemplate = `
        <div  class="content">
            <div class="info">
                <img src="/image/friends-management/progileImg2.png" alt="프로필이미지" />
                <div>
                    <span>${value.name}</span>
                    <span>${value.nickname}</span>
                </div>
            </div>
            <button>요청</button>
        </div>
        `;
        newfriendList.insertAdjacentHTML("beforeend", newFriendTemplate);
      });
    });
});

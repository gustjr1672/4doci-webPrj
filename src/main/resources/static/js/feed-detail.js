function goBack() {
  window.history.back();
}

let commentBtn = document.querySelector(".comment");
let commentModal = document.querySelector(".comment-modal");
let commentBox = document.querySelector(".comment-box");

commentBtn.addEventListener("click", () => {

  commentModal.classList.remove("hidden");
  document.body.style.overflow = "hidden";
  
  setTimeout(() => {
    commentBox.classList.add("comment-box-show");
  }, 100);
    
});

commentModal.addEventListener("click", (e) =>{

  if(!e.target.classList.contains("comment-modal"))
    return;
    
    commentBox.classList.remove("comment-box-show");
    document.body.style.overflow = "auto";
    
    setTimeout(() => {
    commentModal.classList.add("hidden");
  }, 400);
})
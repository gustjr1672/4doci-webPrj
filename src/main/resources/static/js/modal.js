let scrollSection = document.querySelector(".help-modal-contents");
let page = 1;
let leftArrow = document.querySelector("#left-arrow-btn");
let rightArrow = document.querySelector("#right-arrow-btn");
leftArrow.addEventListener("click", () => {
  if (page == 1) return;
  scrollSection.classList.remove("page" + page);
  page -= 1;
  console.log(page);
  scrollSection.classList.add("page" + page);
});
rightArrow.addEventListener("click", () => {
  if (page == 4) return;
  scrollSection.classList.remove("page" + page);
  page += 1;
  console.log(page);
  scrollSection.classList.add("page" + page);
});

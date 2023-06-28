function loadFile(input) {
    let file = input.files[0]; //선택된 파일 가져오기
    let form = document.getElementById("image-form");
    form.style.visibility = "hidden";
    form.style.height = "0px";
    let newImage = document.getElementById("input-image");
    let imageBox = document.getElementById("image-box");
    imageBox.className = "image-box";
    newImage.className = "image-thumbnail";
  
    let formSection = document.querySelector(".form");
    newImage.src = URL.createObjectURL(file);
    imageBox.style.marginTop = "20px";
  }
  
  document.getElementById("plus-btn").addEventListener("click", function () {
    document.getElementById("achive-amount").value++;
  });
  
  document.getElementById("minus-btn").addEventListener("click", function () {
    if (document.getElementById("achive-amount").value > 0)
      document.getElementById("achive-amount").value--;
  });
  
  
  /**삭제 모달**/
  const deleteBtn = document.querySelector("header button");
  const deleteModal = document.querySelector("#delete-modal-wrap");
  const deleteModalContent = document.querySelector("#delete-modal-content");
  const closeBtn = document.querySelector(".close-btn");
  
  deleteBtn.addEventListener("click", function(e){
  
    deleteModal.style.display = "block";
    setTimeout(function() {
      deleteModalContent.classList.add('active');
    }, 20);
  
  });
  
  closeBtn.addEventListener("click", function(e){
    
    deleteModalContent.classList.remove("active");
    setTimeout(function () {
      deleteModal.style.display = "none";
    }, 300);
    
  });
  
  
  /**회차 모달**/
  const turnBtn = document.querySelector(".goal div button");
  const turnModal = document.querySelector("#turn-modal-wrap");
  const turnModalContent = document.querySelector("#turn-modal-content");
  const turnCloseBtn = turnModal.querySelector("button");
  
  turnBtn.addEventListener("click", function(e){
  
    turnModal.style.display = "block";
    setTimeout(function () {
      turnModalContent.classList.add('active');
    }, 20);
  
  });
  
  turnCloseBtn.addEventListener("click", function(e){
  
    turnModalContent.classList.remove("active");
    setTimeout(function () {
      turnModal.style.display = "none";
    }, 300);
  
  });
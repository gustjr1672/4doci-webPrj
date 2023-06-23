function validateCategory() {
  var categoryField = document.getElementById('category');
  if (categoryField.value === "0") {
    document.getElementById("category-error").innerHTML = "카테고리를 선택해주세요";
    return false;
  } else {
    document.getElementById("category-error").innerHTML = "";
    return true;
  }
}

function validateUnit() {
  var unitField = document.getElementById('unit');

  if (unitField.value === "0") {
    document.getElementById("unit-error").innerHTML = "목표단위를 선택해주세요";
    return false;
  } else {
    document.getElementById("unit-error").innerHTML = "";
    return true;
  }
}

const finishBtn = document.querySelector("button");
const form = document.querySelector("form");
finishBtn.onclick = function(e){
  if(!(validateCategory()) || !(validateUnit())){
    e.preventDefault();
    return;
  }
  form.submit();
}

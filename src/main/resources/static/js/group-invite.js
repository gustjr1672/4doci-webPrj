const groupName = document.getElementById("group_name");

groupName.oninput = function(){
    let input = groupName.value;
    let alert = document.getElementById("check_group_length");
  
    if(input.length > 20)
        alert.textContent="20자 이내로 작성해 주세요";
    else
        alert.textContent="";
  }

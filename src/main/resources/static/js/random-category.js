let submit = document.querySelector("#submit-button");
let inputs = document.querySelectorAll("input[name=options]");
window.onload = function () {
  let checked = document.querySelectorAll("input[name=options]:checked");
  if (checked.length == 0) submit.disabled = true;
};

inputs.forEach((input) => {
  input.addEventListener("change", function () {
    let checked = document.querySelectorAll("input[name=options]:checked");
    if (checked.length == 0) submit.disabled = true;
    else submit.disabled = false;
  });
});

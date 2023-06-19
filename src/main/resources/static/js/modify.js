const newCategory = document.getElementById("new-category");
const categoryId = document.getElementById("category-id");

function handleOnChangeCategory(category) {
    document.getElementById("new-category").style.display = "block";

    let name = category.options[category.selectedIndex].text;
    let id = category.options[category.selectedIndex].value;

    if (category.selectedIndex === 0) name ="";  //선택을 클릭했을 때 빈문자열 나오도록함
    newCategory.value = name;
    categoryId.value = id;
}

const newUnit = document.getElementById("new-unit");
const unitId = document.getElementById("unit-id");
function handleOnChangeUnit(unit) {
    document.getElementById("new-unit").style.display = "block";

    let name = unit.options[unit.selectedIndex].text;
    let id = unit.options[unit.selectedIndex].value;

    if (unit.selectedIndex === 0) name ="";  //선택을 클릭했을 때 빈문자열 나오도록함
    newUnit.value = name;
    unitId.value = id;
}
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
const newCategory = document.getElementById("new-category");
const categoryId = document.getElementById("category-id");

function handleOnChangeCategory(category) {
    document.getElementById("new-category").style.display = "block";

    let name = category.options[category.selectedIndex].text;
    let id = category.options[category.selectedIndex].value;

    if (category.selectedIndex === 0) name = "";  //선택을 클릭했을 때 빈문자열 나오도록함
    newCategory.value = name;
    categoryId.value = id;
}

const newUnit = document.getElementById("new-unit");
const unitId = document.getElementById("unit-id");
function handleOnChangeUnit(unit) {
    document.getElementById("new-unit").style.display = "block";

    let name = unit.options[unit.selectedIndex].text;
    let id = unit.options[unit.selectedIndex].value;

    if (unit.selectedIndex === 0) name = "";  //선택을 클릭했을 때 빈문자열 나오도록함
    newUnit.value = name;
    unitId.value = id;
}


let categoryFilter = document.querySelector(".filter");
let categorySelect = categoryFilter.querySelector("select");

categorySelect.onchange = (e) => { // 카테고리 선택시 url 생성 후 목록 출력
    e.preventDefault();
    let selectedOption = categorySelect.options[categorySelect.selectedIndex];
    let url = `/admin/api/randomchallenges?c=${selectedOption.dataset.id}`;
    console.log(selectedOption.dataset.id);
    challengeListLoad(url);
}

let challengelistForm = document.getElementById("challenge-list-form");
let challengelist = challengelistForm.querySelector(".challenge-list-wrap");
function challengeListLoad(url) {   // 랜덤도전 목록 생성
    fetch(url)
        .then(response => response.json())
        .then(list => {
            console.log(list);
            challengelist.innerHTML = "";

            for (let challenge of list) {
                let itemTemplate = `
                <form
                action="/admin/randomchallenge/edit/submit"
                class="single-form"
                method="post"
                id = "challenge-list-form"
                >
                    <div class="challenge-list-wrap">
                        <div class="challenge-list">
                            <input
                            value="${challenge.id}"
                            id="${challenge.id}"
                            name="selectedChallenge"
                            type="radio"
                            class="radio-btn"
                            />
                            <label for="${challenge.id}" class="radio-btn-label">
                                <span>${challenge.name}</span>
                            </label>
                        </div>
                    </div>
                </form>
                `;

                challengelist.insertAdjacentHTML("beforeend", itemTemplate);
            }
        })
}

let submitBtn = challengelistForm.querySelector("button");

challengelistForm.addEventListener("submit", function (e) { // 카테고리 미선택시 submit 버튼 비활성화
    if (categorySelect.value === "") {
        e.preventDefault();
    }
})
const checkAll = document.getElementById('checkAll');

// 전체 선택
checkAll.addEventListener('change', function (e) {
    let boardId = document.querySelectorAll('input[name=boardId]');
    for(let i = 0; i < boardId.length; i++){
        boardId[i].checked = e.target.checked;
    }
});


document.querySelector(".pagination").addEventListener("click",function (e) {


    const target = e.target;

    if (target.tagName !== 'A') {
        return;
    }
    const num = target.getAttribute("data-num");

    const formObj = document.querySelector("form[name=search]");

    formObj.innerHTML += `<input type='hidden' name='page' value='${num}'>`;

    formObj.submit();
},false)

// 다중 선택 삭제
const btnRemoveSelected = document.querySelector('.btnRemoveSelected');
const deleteSelected = document.querySelector("form[name=deleteSelected]");
btnRemoveSelected.addEventListener('click', function () {
    if(confirm("정말 삭제하시겠습니까?")){
        deleteSelected.submit();
    };
});


// 타겟 삭제
function targetDelete(boardId){
    if(confirm("정말 삭제하시겠습니까?")){
        let newForm = document.createElement('form');

        newForm.name = 'targetDelete';
        newForm.method = 'post';
        newForm.action = '/my/deleteTargetBoard';

        let input1 = document.createElement('input');

        // set attribute (input)
        input1.setAttribute("type", "hidden");
        input1.setAttribute("name", "boardId");
        input1.setAttribute("value", boardId);

        // append input (to form)
        newForm.appendChild(input1);

        // append form (to body)
        document.body.appendChild(newForm);

        // submit form
        newForm.submit();
    }
}
document.querySelector(".pagination").addEventListener("click",function (e) {

    const target = e.target;

    if (target.tagName !== 'A') {
        return;
    }
    const num = parseInt(target.getAttribute("data-num"));

    const formObj = document.querySelector("form");

    formObj.innerHTML += `<input type='hidden' name='page' value='${num}'>`;

    formObj.submit();
},false)

document.querySelector('.addboard').addEventListener('click',function (e) {
    location.href = '/board/add'
});

if(document.querySelector(".addnotice")) {
    document.querySelector('.addnotice').addEventListener('click',function (e) {
        location.href = '/board/addNotice'
    });
}


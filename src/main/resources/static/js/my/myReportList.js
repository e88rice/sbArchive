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
// ********************** 회원가입 약관동의 **********************
// 전체 체크박스
const checkboxes = document.querySelectorAll('input[name="agree"]');
// 전체 동의 체크 박스
const checkAll = document.getElementById('checkAll');


function checkSelectAll()  {
    // 선택된 체크박스가 전체 체크박스 갯수와 동일하면
    const checked = document.querySelectorAll('input[name="agree"]:checked');
    if(checkboxes.length === checked.length)  {
        checkAll.checked = true;
    }else {
        checkAll.checked = false;
    }
}

checkAll.addEventListener('change', function (e) {
    let agreeChk = document.querySelectorAll('input[name="agree"]');
    for(let i = 0; i < agreeChk.length; i++){
        agreeChk[i].checked = e.target.checked;
    }
});


const joinAuthBtn = document.querySelector('.joinAuthBtn');
const authForm = document.querySelector('form[name="joinAuthForm"]');

joinAuthBtn.addEventListener('click', function (e){
    e.stopPropagation();
    e.preventDefault();

    if(!agree1.checked || !agree2.checked){
        alert("필수 이용약관에 동의하셔야 회원가입을 할 수 있습니다.")
        return
    }

    authForm.submit();
});
// ********************** end 회원가입 약관동의 **********************
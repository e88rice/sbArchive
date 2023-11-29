const strongPasswordMessage = document.querySelector('.strongPassword-message');
const mismatchMessage = document.querySelector('.mismatch-message');


// 비밀번호 공백 제거
function noSpaceFormReset(obj) {
    let str_space = /\s/;  // 공백체크
    if(str_space.exec(obj.value)) { //공백 체크
        alert("비밀번호에 공백을 포함할 수 없습니다.\n다시 입력하세요.");
        obj.focus();
        obj.value = ''; //
        strongPasswordMessage.classList.add('hide');
        return false;
    }
}

let isPasswordChecked = false; // 비밀번호 강화
let isPasswordMatchChecked = false; // 비밀번호가 일치하면
function strongPassword (str) {
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/.test(str);
}
function isMatch (password1, password2) {
    return password1 === password2;
}
passwd.onkeyup = function () {
    // 값을 입력한 경우
    if (passwd.value.length !== 0) {
        if(strongPassword(passwd.value)) {
            strongPasswordMessage.classList.add('hide'); // 실패 메시지 Xx
            isPasswordChecked = true;
        }
        else {
            strongPasswordMessage.classList.remove('hide'); // 8글자 이상....
            isPasswordChecked = false;
        }
    }
        // 값을 입력하지 않은 경우 초기화
    // 모든 메시지를 가린다.
    else {
        strongPasswordMessage.classList.add('hide');
        isPasswordChecked = false;
    }
};

passwdCheck.onkeyup = function () {
    if (passwdCheck.value.length !== 0) {
        if(isMatch(passwd.value, passwdCheck.value)) {
            mismatchMessage.classList.add('hide'); // 실패 메시지 Xx
            isPasswordMatchChecked = true;
        }
        else {
            mismatchMessage.classList.remove('hide'); // 비밀번호가 일치하지 않습니다.
            isPasswordMatchChecked = false;
        }
    }
    else { // 값을 지웠을때 초기화
        mismatchMessage.classList.add('hide'); // 실패 메시지 Xx
        isPasswordMatchChecked = false;
    }
};

document.querySelector('.modifyBtn').addEventListener('click', function (e){
    e.preventDefault();
    e.stopPropagation();

    if(!isPasswordChecked){
        alert("비밀번호를 다시 설정해주세요")
        return;
    }
    if(!isPasswordMatchChecked){
        alert("비밀번호 재확인을 다시 입력해주세요")
        return;
    }

    document.querySelector('form').submit();
});

document.querySelector('.laterBtn').addEventListener('click', function (e){
    e.stopPropagation();
    e.preventDefault();

    location.href = "/index";
});
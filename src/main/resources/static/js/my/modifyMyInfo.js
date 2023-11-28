// 닉네임 공백 제거
function noNickNameSpaceForm(obj) {
    let str_space = /\s/;  // 공백체크
    if(str_space.exec(obj.value)) { //공백 체크
        alert("닉네임에 공백을 포함할 수 없습니다.\n다시 입력해주세요.");
        obj.focus();
        obj.value = '';
        nicknameChecked = false;
        return false;
    }
}

function nicknameLength(value) {
    return value.length >= 2 && value.length <= 8
}

function onlyNumberAndEnglishAndKorean(str) {
    return /^[A-Za-z0-9가-힣][A-Za-z0-9가-힣]*$/.test(str);
}

let nicknameChecked = false;

const nickname = document.getElementById('nickname');
const failureMessage5 = document.querySelector('.failure-message5');
const failureMessage6 = document.querySelector('.failure-message6');

nickname.onkeyup = function () {
    if (nickname.value.length !== 0) { // 닉네임을 작성하기 시작했을 때
        failureMessage5.classList.remove('hide');
        if (nicknameLength(nickname.value) === false){ // 닉네임 길이는 2~8글자
            failureMessage5.classList.remove('hide');
            failureMessage6.classList.add('hide');
            nicknameChecked = false;
        }
        else if(onlyNumberAndEnglishAndKorean(nickname.value) == false){ // 영어 한글 숫자만
            failureMessage5.classList.add('hide');
            failureMessage6.classList.remove('hide');
            nicknameChecked = false;
        }
        else if(nicknameLength(nickname.value) && onlyNumberAndEnglishAndKorean(nickname.value)){
            failureMessage5.classList.add('hide');
            failureMessage6.classList.add('hide');
            nicknameChecked = true;
        }
    } else { // 닉네임을 값을 다 지우면 초기화
        failureMessage5.classList.add('hide');
        failureMessage6.classList.add('hide');
        nicknameChecked = false;
    }
}

const btnModify = document.querySelector('.btnModify');
btnModify.addEventListener('click', function (e){
    e.preventDefault();
    e.stopPropagation();

    if(!nicknameChecked){
        alert("닉네임을 양식에 맞게 입력하세요");
        nickname.focus();
        return
    } else {
        document.querySelector('form').submit();
    }
})
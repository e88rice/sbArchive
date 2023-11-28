// 다시쓰기 클릭 시 초기화
function resetAuth() {
    email.readOnly = false;
    emailCheck.readOnly = false;

    userId.readOnly = true;
    passwd.readOnly = true;
    passwdCheck.readOnly = true;
    nickName.readOnly = true;

    auth_check.disabled = false;
    btnSendEmail.disabled = false;
}


// ***** email 인증키 보내기 *****
const btnSendEmail = document.querySelector('.btnSendEmail');
const email = document.getElementById("email");
let failureMessage = document.querySelector(".failure-message"); // 사용할 수 없는 이메일입니다.
const timerDiv = document.querySelector('.timer_div');

email.onkeyup = function () {
    // 값을 다 지우면 경고 메세지 없애기
    if (email.value.length === 0) {
        failureMessage.classList.add('hide');
    }
}

// 이메일 유효성 검사
const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;

function emailValidChk(email) {
    if(emailPattern.test(email) === false) {
        return false;
    } else {
        return true;
    }
}

// 인증키 유효시간 타이머
const Timer=document.getElementById('timer'); //스코어 기록창-분
let time = 180000;
let min = 3;
let sec = 60;
let interval;


Timer.innerText=min+":"+'00';

function timer(){
    interval = setInterval(() => {
        time = time - 1000; //1초씩 줄어듦
        min = time / (60 * 1000); //초를 분으로 나눠준다.

        if (sec > 0) { //sec=60 에서 1씩 빼서 출력해준다.
            sec = sec - 1;
            Timer.innerText = Math.floor(min) + ':' + sec;
            //실수로 계산되기 때문에 소숫점 아래를 버리고 출력해준다.

        }
        if (sec === 0) {
            // 0에서 -1을 하면 -59가 출력된다.
            // 그래서 0이 되면 바로 sec을 60으로 돌려주고 value에는 0을 출력하도록 해준다.
            sec = 60;
            Timer.innerText = Math.floor(min) + ':' + '00'
        }

    }, 1000); //1초마다
}

function reset(){
    clearInterval(interval);
    time = 180000;
    min = 3;
    sec = 60;
}


// 만료 시간과 함께 데이터를 저장
function setItemWithExpireTime(keyName, keyValue, tts) {
    // localStorage에 저장할 객체
    const obj = {
        value : keyValue,
        expire : Date.now() + tts
    }
    // 객체를 JSON 문자열로 변환
    const objString = JSON.stringify(obj);
    //setItem
    window.localStorage.setItem(keyName, objString);
}

// 만료 시간을 체크하며 데이터 읽기
function getItemWithExpireTime(keyName) {
    // localStorage 값 읽기 (문자열)
    const objString = window.localStorage.getItem(keyName);
    // null 체크
    if(!objString) {    return null;  }
    // 문자열을 객체로 변환
    const obj = JSON.parse(objString);
    // 현재 시간과 localStorage의 expire 시간 비교
    if(Date.now() > obj.expire) {
        // 만료시간이 지난 item 삭제
        window.localStorage.removeItem(keyName);
        // null 리턴
        return null;
    }
    // 만료기간이 남아있는 경우, value 값 리턴
    return obj.value;}

let mailTo = "";

btnSendEmail.addEventListener('click', function (e){
    e.preventDefault();

    if(emailValidChk(email.value) == false){
        alert("잘못 된 이메일 형식입니다")
        failureMessage.classList.remove('hide');
        email.focus();
        return;
    } else {
        const xhr = new XMLHttpRequest(); // ajax 작업을 위한 객체 생성
        xhr.open('POST', '/user/emailCheck?email=' + email.value );
        xhr.onload = function() {
            if (xhr.status === 200) {
                // 서버 응답이 성공인 경우
                console.log(xhr.response);
                if(xhr.response == 1){ // 서버에 이메일이 있는 경우
                    failureMessage.classList.remove('hide'); // 사용할 수 없는 이메일입니다.

                } else if (xhr.response == 0) { // 서버에 이메일이 없는 경우 인증키 보냄
                    failureMessage.classList.add('hide');

                    const xhr = new XMLHttpRequest(); // ajax 작업을 위한 객체 생성
                    mailTo = email.value;
                    console.log(mailTo);
                    xhr.open('GET', '/mail/sendConfirmMail?mailTo=' + mailTo );
                    xhr.onload = function() {
                        if (xhr.status === 200) {
                            // 서버 응답이 성공인 경우
                            reset();
                            alert("이메일로 인증키를 확인해주세요");
                            console.log(xhr.response); //(추후에 삭제하기)
                            setItemWithExpireTime("confirmKey", xhr.response, 180000)
                            timerDiv.classList.remove("hide");
                            timer();
                            setTimeout(function(){
                                reset();
                                timerDiv.classList.add("hide");
                            },180000);//3분이 되면 타이머를 삭제한다.

                        } else {
                            // 서버 응답이 실패인 경우
                            alert("이메일 인증키 전송에 실패했습니다");
                        }
                    };
                    xhr.send();
                }
            } else {
                // 서버 응답이 실패인 경우
                alert("서버 응답 실패");
            }
        };
        xhr.send();
    }

});

// ***** 이메일인증 완료하면 회원가입 폼 입력 열어주기 *****
const auth_check = document.getElementById("auth_check");
const emailCheck = document.getElementById("emailCheck");
let userId = document.getElementById("userId");
let passwd = document.getElementById("passwd");
let passwdCheck = document.getElementById("passwdCheck");
let nickName = document.getElementById("nickname");

auth_check.addEventListener("click", function (e){
    e.preventDefault();

    if(getItemWithExpireTime("confirmKey") == emailCheck.value) {
        alert("이메일 인증이 완료되었습니다")
        userId.readOnly = false;
        passwd.readOnly = false;
        passwdCheck.readOnly = false;
        nickName.readOnly = false;

        email.readOnly = true;
        email.value = mailTo;
        emailCheck.readOnly = true;

        auth_check.disabled = true;
        btnSendEmail.disabled = true;

        reset();
        timerDiv.classList.add("hide");

    } else {
        alert("인증 코드를 다시 확인해주세요")
    }
});





// ***** 회원가입 유효성 검사 *****
let failureMessage2 = document.querySelector(".failure-message2"); // 아이디는 4~12글자이어야 합니다
let failureMessage3 = document.querySelector(".failure-message3"); // 영어 또는 숫자만 가능합니다
let failureMessage4 = document.querySelector(".failure-message4"); // 이미 가입한 아이디 입니다
let successMessage = document.querySelector(".success-message"); // 아이디 성공
let strongPasswordMessage = document.querySelector(".strongPassword-message"); // 8글자 이상....
let mismatchMessage = document.querySelector(".mismatch-message") // 비밀번호가 일치하지 않습니다


// ***** 회원가입 유효성 검사 - 아이디 *****
// 아이디 공백 제거
function noSpaceForm(obj) {
    let str_space = /\s/;  // 공백체크
    if(str_space.exec(obj.value)) { //공백 체크
        alert("아이디에 공백을 포함할 수 없습니다.\n공백은 자동적으로 제거 됩니다.");
        obj.focus();
        obj.value = obj.value.replace(/\s| /gi,''); // 공백제거
        return false;
    }
}

function onlyNumberAndEnglish(str) {
    return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
}
function idLength(value) {
    return value.length >= 4 && value.length <= 12
}

// 아이디를 유효하게 작성했는가
let isIdValid=false;

userId.onkeydown = function () {
    isIdChecked = false;
}

userId.onchange = function () {
    console.log("isIdChecked = " + isIdChecked)
    console.log("isIdValid = " + isIdValid)
    isIdChecked = false;
    // 값을 입력한 경우
    if (userId.value.length !== 0) {
        // 영어 또는 숫자 외의 값을 입력했을 경우
        if(onlyNumberAndEnglish(userId.value) === false) {
            successMessage.classList.add('hide'); // 성공 메시지 Xx
            failureMessage2.classList.add('hide'); // 실패 메시지2 Xx
            failureMessage3.classList.remove('hide'); // 영어 또는 숫자만 가능합니다
            failureMessage4.classList.add('hide'); // 실패 메시지4 Xx
            isIdValid = false;
            isIdChecked = false;
        }
        // 글자 수가 4~12글자가 아닐 경우
        else if(idLength(userId.value) === false) {
            successMessage.classList.add('hide'); // 성공 메시지 Xx
            failureMessage2.classList.remove('hide'); // 아이디는 4~12글자이어야 합니다
            failureMessage3.classList.add('hide'); // 실패 메시지3 Xx
            failureMessage4.classList.add('hide'); // 실패 메시지4 Xx
            isIdValid = false;
            isIdChecked = false;
        }
        // 조건을 모두 만족할 경우
        else if(idLength(userId.value) || onlyNumberAndEnglish(userId.value)) {
            successMessage.classList.add('hide'); // 성공 메시지 Xx
            failureMessage2.classList.add('hide'); // 실패 메시지2 Xx
            failureMessage3.classList.add('hide'); // 실패 메시지3 Xx
            failureMessage4.classList.add('hide'); // 실패 메시지4 Xx
            isIdValid = true;
        }
    }
        // 값을 입력하지 않은 경우 (지웠을 때)
    // 모든 메시지를 가린다.
    else {
        successMessage.classList.add('hide');
        failureMessage2.classList.add('hide');
        failureMessage3.classList.add('hide');
        failureMessage4.classList.add('hide');
        isIdValid = false;
        isIdChecked = false;
    }
}

// ***** 회원가입 유효성 검사 - 아이디 중복체크 *****
let isIdChecked=false; //중복체크했을 땐 true로 변경


const idCheck = document.getElementById("id_check");
idCheck.addEventListener('click', function (e){
    e.preventDefault();
    if(isIdValid){
        const xhr = new XMLHttpRequest(); // ajax 작업을 위한 객체 생성
        xhr.open('POST', '/user/userIdCheck?userId=' + userId.value );
        xhr.onload = function() {
            if (xhr.status === 200) {
                // 서버 응답이 성공인 경우
                console.log(xhr.response);
                if(xhr.response == 1){ // 서버에 아이디가 있는 경우
                    successMessage.classList.add('hide');
                    failureMessage2.classList.add('hide');
                    failureMessage3.classList.add('hide');
                    failureMessage4.classList.remove('hide'); // 이미 가입한 아이디입니다.
                    isIdChecked=false;
                } else if (xhr.response == 0) { // 서버에 아이디가 없는 경우
                    successMessage.classList.remove('hide'); // 사용할 수 있는 아이디입니다
                    failureMessage2.classList.add('hide');
                    failureMessage3.classList.add('hide');
                    failureMessage4.classList.add('hide');
                    isIdChecked = true;
                }
            } else {
                // 서버 응답이 실패인 경우
                alert("서버응답실패");
            }
        };
        xhr.send();
    } else {
        alert("적합하지 않은 아이디입니다.");
        userId.focus();
    }
})

// ***** 회원가입 유효성 검사 - 비밀번호 *****
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
    isPasswordMatchChecked = false;
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

document.querySelector('.registerBtn').addEventListener('click', function (e){
    // 회원가입 요건 충족 못하면 회원가입으로 못넘어가게 해야함
    // switch 문으로 바꾸고 싶은데.. <- 이거 못하면 그냥.. if문 한개에 alert 한개로 퉁쳐서 가입을 막아야 함
    e.preventDefault();
    e.stopPropagation();

    if(!isIdChecked){
        alert("아이디 중복 검사를 진행해주세요")
        userId.focus();
        return;
    }
    if(!isPasswordChecked){
        alert("비밀번호를 다시 설정해주세요")
        passwd.focus();
        return;
    }
    if(!isPasswordMatchChecked){
        alert("비밀번호 재확인을 다시 입력해주세요")
        passwdCheck.focus();
        return;
    }
    if(!nicknameChecked){
        alert("닉네임을 다시 입력해주세요.");
        nickname.focus();
        return;
    }
    document.querySelector('form').submit();
});
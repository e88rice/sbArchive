let msg = [[${msg}]];

if(msg != null) {
    alert(msg);
}

let confirmKey = "";

// ***** email 인증키 보내기 *****
const btnSendEmail = document.getElementById("btnSendEmail");
const email = document.getElementById("email");
const userId = document.getElementById("userId");
let failureMessage = document.querySelector(".failure-message"); // 이메일 정보를 찾을 수 없습니다


btnSendEmail.addEventListener('click', function (e){
    e.preventDefault();

    const xhr = new XMLHttpRequest(); // ajax 작업을 위한 객체 생성
    xhr.open('POST', '/user/accountCheck?userId=' + userId.value + '&email=' + email.value );
    xhr.onload = function() {
        if (xhr.status === 200) {
            // 서버 응답이 성공인 경우
            console.log(xhr.response);
            if(xhr.response == 0){ // 서버에 이메일과 아이디가 없는 경우
                failureMessage.classList.remove('hide'); // 이메일 정보를 찾을 수 없습니다

            } else if (xhr.response == 1) { // 서버에 이메일과 아이디가 일치하는 경우 인증키 보냄
                failureMessage.classList.add('hide');

                const xhr = new XMLHttpRequest(); // ajax 작업을 위한 객체 생성
                let mailTo = email.value;
                console.log(mailTo);
                xhr.open('GET', '/mail/sendConfirmMail?mailTo=' + mailTo );
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // 서버 응답이 성공인 경우
                        alert("이메일로 인증키를 확인해주세요");
                        console.log(xhr.response); //(추후에 삭제하기)
                        confirmKey = xhr.response; //(controller 에서 반환된 confirmKey 불러오기)

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
});

// ***** 인증 완료하면  *****
const auth_check = document.getElementById("auth_check");
const emailCheck = document.getElementById("emailCheck");
const form = document.querySelector('form');

auth_check.addEventListener("click", function (e){
    e.preventDefault();

    console.log(confirmKey)
    if(confirmKey == emailCheck.value) {
        const xhr = new XMLHttpRequest(); // ajax 작업을 위한 객체 생성
        let mailTo = email.value;
        console.log(mailTo);
        xhr.open('GET', '/mail/sendTempPwMail?mailTo=' + mailTo );
        xhr.onload = function() {
            if (xhr.status === 200) {
                // 서버 응답이 성공인 경우
                alert("이메일로 임시 비밀번호를 발송했습니다");
                location.href="/user/login";

            } else {
                // 서버 응답이 실패인 경우
                alert("임시 비밀번호 전송에 실패했습니다");
            }
        };
        xhr.send();
    } else {
        alert("인증 코드를 다시 확인해주세요")
    }
});
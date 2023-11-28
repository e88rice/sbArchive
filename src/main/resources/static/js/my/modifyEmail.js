let isEmailAuth = false;
let confirmKey = "";

// ***** email 인증키 보내기 *****
const btnSendEmail = document.getElementById("btnSendEmail");
const email = document.getElementById("email");
let failureMessage = document.querySelector(".failure-message"); // 사용할 수 없는 이메일입니다.

email.onkeyup = function () {
    // 값을 다 지우면 경고 메세지 없애기
    if (email.value.length === 0) {
        failureMessage.classList.add('hide');
    }
}

btnSendEmail.addEventListener('click', function (e){
    e.preventDefault();

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



auth_check.addEventListener("click", function (e){
    e.preventDefault();

    console.log(confirmKey)
    if(confirmKey == emailCheck.value) {
        alert("이메일 인증이 완료되었습니다")
        isEmailAuth = true;

    } else {
        alert("인증 코드를 다시 확인해주세요")
    }
});


document.querySelector('.modifyBtn').addEventListener('click', function (e){
    e.preventDefault();
    e.stopPropagation();

    // 이메일 인증 먼저 진행해야 수정가능
    if(!isEmailAuth){
        alert("이메일 인증을 먼저 진행해주세요")
        return;
    }
    document.querySelector('form').submit();
});
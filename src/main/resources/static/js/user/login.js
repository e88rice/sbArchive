const btnGoogle = document.querySelector('.socialBtn_Google');
btnGoogle.addEventListener('click', function (e){
    self.location.href = "/oauth2/authorization/google";
});

const msg = [[${msg}]];
console.log(msg);

if(msg != null){
    alert(msg);
}

const btnKakao = document.querySelector('.socialBtn_Kakao');
btnKakao.addEventListener('click', function (e){
    self.location.href = "/oauth2/authorization/kakao";
});
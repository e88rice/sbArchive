/** 검색 관련 영역 **/

// 검색 버튼 눌렀을 때
const searchBtn = document.querySelector("#search_btn");
const query = document.querySelector("input[name=query]");
const resultTable = document.querySelector(".search_result_table");
const addModalContainer = new bootstrap.Modal(document.querySelector(".add_modal"));

addModalContainer.show(); // 페이지 로딩과 동시에 검색창 오픈


searchBtn.addEventListener("click", function (){
    const queryString = query.value;
    if(queryString.length <= 0){
        alert("검색어를 입력해주세요");
        return;
    }
    getSearchList(queryString).then(r => {
        printSearchList(r);
    }).catch(e => {
        console.log(e)
    });
})
// 파일 이름을 표시하는 div에 파일 이름을 전달하는 이벤트
const files = document.getElementById("files");
const uploadName = document.querySelector(".upload-name");
let fileNames = "";
files.addEventListener("change", function () {
    let fileName = "첨부된 파일 : " + files.files[0].name;
    if(files.files.length > 1) { // 2개 이상이 첨부되었다면
        fileName += "외 " + (files.files.length-1) + "개"
    }
    uploadName.value = fileName;
})

// 다시 검색 모달창을 여는 버튼
const researchBtn = document.querySelector(".researchBtn");
researchBtn.addEventListener("click", function (){
    addModalContainer.show();
})

function printSearchList(searchList) {
    let str = '';
    if(searchList && searchList.length > 0) {
        for(const search of searchList) { // 비동기 방식으로 API 검색 결과값을 받아온 뒤 뷰 페이지에 출력
            str += ` <tr class="search_result_wrap" data-title="${search.title}" data-addr="${search.address}" data-x="${search.xoffSet}" data-y="${search.yoffSet}">
                   <td class="search_result">${search.title}</a></td>
                   <td class="search_result">${search.category}</td>
                   <td class="search_result">${search.address}</td>
                  </tr>`
        }
    }
    resultTable.innerHTML = str;

    const resultElement = document.querySelectorAll(".search_result_wrap"); // 뷰 페이지에 출력 된 하나의 결과 정보 묶음
    resultElement.forEach( element => {
        element.addEventListener("click", function (){ // 검색 결과중 하나를 선택했다면
            const xOffSet = document.querySelector("input[name=xOffSet]"); // 해당 검색 결과의 x 좌표와
            const yOffSet = document.querySelector("input[name=yOffSet]"); // y 좌표를 미리 생성 해 둔 DOM에 담음
            const title = document.querySelector("input[name=title]");       // 가게명을 미리 생성 해 둔 DOM에 담음
            const address = document.querySelector("input[name=address]"); // 주소를 미리 생성 해 둔 DOM에 담음
            xOffSet.value = element.dataset.x;
            yOffSet.value = element.dataset.y;
            title.value = ((element.dataset.title).replace("<b>", "")).replace("</b>", "");
            address.value = element.dataset.addr;

            addModalContainer.hide(); // 검색 모달창 숨김
        })
    })

    document.querySelector("#addBtn").addEventListener('click', function () {
        const regForm = document.forms[0];
        if(files.files[0] === undefined) { // 등록된 파일이 없다면 알림 출력 후 리턴
            alert("가게의 사진을 첨부해주세요")
            return;
        }
        for(let i=0; i<files.files.length; i++) {
            let file = files.files[i];
            let fileSize = files.files[i].size;
            console.log(fileSize);
            console.log(50*1024*1024);
            if(imageCheck(file, fileSize) === "not-image") {
                alert("이미지 파일만 업로드 가능합니다")
                return;
            }
            if(imageCheck(file, fileSize) === "over-size") {
                alert("50MB 미만의 이미지 파일만 업로드 가능합니다")
                return;
            }
        }
        const formObj = new FormData();
        for(const file of files.files) {
            formObj.append('files', file);
        }
        addFiles(formObj);
        regForm.submit();
    })
}

function imageCheck(imgFile, fileSize) {
    var fileName = imgFile.name;
    var fileExt = fileName.substring(fileName.lastIndexOf(".")+1); // 확장자 구하기
    fileExt = fileExt.toLowerCase(); //  소문자 변화
    if ("jpg" != fileExt && "jpeg" != fileExt && "gif" != fileExt && "png" != fileExt && "bmp" != fileExt) {
        return "not-image";
    }
    var maxSize = 50 * 1024 * 1024;
    if (fileSize === maxSize) {
        alert("파일 사이즈는 5MB까지 가능");
        return "over-size";
    }
}


/** 검색 관련 영역 끝 **/

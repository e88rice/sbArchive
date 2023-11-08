// // 우편번호  API  스크립트
// document.querySelector('span.btnFindZipcode').addEventListener('click', execDaumPostcode);
//
// /*
// 카카오 우편번호 검색 가이드 페이지 :  https://postcode.map.daum.net/guide
// */
// function execDaumPostcode() {
//     /* 상황에 맞춰서 변경해야 하는 부분 */
//     // const zipcode = document.getElementById('zipCode');
//     const address = document.getElementById('address');
//     // const address02 = document.getElementById('address02');
//
//     /* 수정없이 사용 하는 부분 */
//     new daum.Postcode({
//         oncomplete: function (data) {
//             // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
//
//             // 각 주소의 노출 규칙에 따라 주소를 조합한다.
//             // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
//             var fullAddr = ''; // 최종 주소 변수
//             var extraAddr = ''; // 조합형 주소 변수
//
//             // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
//             if (data.userSelectedType === 'R') {
//                 // 사용자가 도로명 주소를 선택했을 경우
//                 fullAddr = data.roadAddress;
//             } else {
//                 // 사용자가 지번 주소를 선택했을 경우(J)
//                 fullAddr = data.jibunAddress;
//             }
//
//             // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
//             if (data.userSelectedType === 'R') {
//                 //법정동명이 있을 경우 추가한다.
//                 if (data.bname !== '') {
//                     extraAddr += data.bname;
//                 }
//                 // 건물명이 있을 경우 추가한다.
//                 if (data.buildingName !== '') {
//                     extraAddr += extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
//                 }
//                 // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
//                 fullAddr += extraAddr !== '' ? ' (' + extraAddr + ')' : '';
//             }
//
//             // 우편번호와 주소 정보를 해당 필드에 넣는다.
//             address.value = fullAddr;
//         },
//     }).open();
// }


/** 검색 관련 영역 **/

// 검색 버튼 눌렀을 때
const searchBtn = document.querySelector("#search-btn");
const query = document.querySelector("input[name=query]");
const resultTable = document.querySelector(".search-result-table");


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

function printSearchList(searchList) {
    let str = '';
    if(searchList && searchList.length > 0) {
        for(const search of searchList) { // 비동기 방식으로 API 검색 결과값을 받아온 뒤 뷰 페이지에 출력
            str += ` <tr class="search-result-wrap" data-title="${search.title}" data-addr="${search.address}" data-x="${search.xoffSet}" data-y="${search.yoffSet}">
                   <td class="search-result">${search.title}</a></td>
                   <td class="search-result">${search.category}</td>
                   <td class="search-result">${search.address}</td>
                  </tr>`
        }
    }
    resultTable.innerHTML = str;

    const resultElement = document.querySelectorAll(".search-result-wrap"); // 뷰 페이지에 출력 된 하나의 결과 정보 묶음
    resultElement.forEach( element => {
        element.addEventListener("click", function (){ // 검색 결과중 하나를 선택했다면
            const xOffSet = document.querySelector("input[name=xOffSet]"); // 해당 검색 결과의 x 좌표와
            const yOffSet = document.querySelector("input[name=yOffSet]"); // y 좌표를 미리 생성 해 둔 DOM에 담음
            const title = document.querySelector("input[name=title]");       // 가게명을 미리 생성 해 둔 DOM에 담음
            const address = document.querySelector("input[name=address]"); // 주소를 미리 생성 해 둔 DOM에 담음
            console.log("x 좌표 : " + element.dataset.x);
            console.log("y 좌표 : " + element.dataset.y);
            console.log("가게명 : " + element.dataset.title);
            console.log("주소 : " + element.dataset.addr);
            xOffSet.value = element.dataset.x;
            yOffSet.value = element.dataset.y;
            title.value = element.dataset.title;
            address.value = element.dataset.addr;

            // const regForm = document.forms[0];
            // regForm.submit();

        })
    })
}

/** 검색 관련 영역 끝 **/

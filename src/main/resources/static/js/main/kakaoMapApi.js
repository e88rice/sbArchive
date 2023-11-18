/** #######################################################################################
 ################################ 지도 객체 설정 및 생성 #####################################
 ########################################################################################## **/
const modalContainer = new bootstrap.Modal(document.querySelector(".modal_container")); // 모달 컨테이너
const modalBody = document.querySelector(".modal-body"); // 모달 컨텐츠 영역
const modalImgs = document.querySelector(".modal_footer_imgs"); // 모달 이미지들 영역
getSBList().then(r => {
    // 마커를 표시할 좌표, 해당 좌표의 title, 해당 좌표의 주소를 가진 객체 배열
    var positions = [];
    for(let i=0; i<r.length; i++){
        let item = {
            "title": r[i].title,
            "latlng": new kakao.maps.LatLng(parseFloat(r[i].yoffSet), parseFloat(r[i].xoffSet)),
            "content": "가게명 : " + r[i].title + "<br>" + r[i].address
        }
        if(r[i].files != null && r[i].files.length > 0) { // 만약 파일이 존재한다면
            item = {
                "title": r[i].title,
                "latlng": new kakao.maps.LatLng(parseFloat(r[i].yoffSet), parseFloat(r[i].xoffSet)),
                "content": "<div style='display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; width: 300px; height: 200px; padding: 10px;'> <img class='test-img' style='height: 80px; width: 80px;' src='/img/" +r[i].files[0] +"'>  " + " <br> <b> "+ r[i].title +" </b>  <br>" + r[i].address + "</div>"
            }
        }
        positions.push(item); // 객체 배열에 추가
    }
    var lastY = r[positions.length-1].yoffSet;
    var lastX = r[positions.length-1].xoffSet;
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            // center: new kakao.maps.LatLng(35.8661170068962, 128.593835998552), // 지도의 중심좌표
            center: new kakao.maps.LatLng(lastY, lastX), // 지도의 중심좌표 y, x
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    console.log(mapContainer);

    // 마커 이미지의 이미지 주소입니다
    var imageSrc = '/img/icon1.png';

    for (var i = 0; i < positions.length; i ++) {

        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(35, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);


        /** #######################################################################################
         ################################ 마커 마우스오버,아웃 이벤트 #################################
         ########################################################################################## **/

        for (var i = 0; i < positions.length; i ++) {
            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng, // 마커의 위치
                title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image : markerImage // 마커 이미지
            });

            // 마커에 표시할 인포윈도우를 생성합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: positions[i].content // 인포윈도우에 표시할 내용
            });



            // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
            // 이벤트 리스너로는 클로저를 만들어 등록합니다
            // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
            kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
            kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
            kakao.maps.event.addListener(marker, 'click', modalShow(r[i].signboardId));
        }

        // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
        function makeOverListener(map, marker, infowindow) {
            return function() {
                infowindow.open(map, marker);
            };
        }

        // 인포윈도우를 닫는 클로저를 만드는 함수입니다
        function makeOutListener(infowindow) {
            return function () {
                infowindow.close();
            };
        }

    }

    function modalShow(signboardId) {
        return function () {
            modalContainer.show();
            justGetSignboard(signboardId).then( r => {
                addInfoForm(r);
            })
        }
    }

    // 객체의 정보를 받고 HTML 요소로 추가
    function addInfoForm(signboard) {
        console.log("흐흠");
        console.log(signboard);
        let addDate = signboard.addDate; // 날짜 배열을 설정한 문자열로 변환
        let modDate = signboard.modDate;
        let date = addDate === modDate ? modDate : (modDate + " (수정됨)");

        let str = "<div class=\"modal_img_container\">\n" +
            "              <img src=\"/img/" + signboard.files[0] + "\">\n" +
            "            </div>\n" +
            "            <div class=\"modal_info_container\">\n" +
            "              <div id=\"map\"></div>\n" +
            "              <div class=\"modal_info modal_info_title\">\n" +
            "                <span class=\"modal_info_span\">가게명</span>\n" +
            "                <p> " + signboard.title + "</p>\n" +
            "              </div>\n" +
            "              <div class=\"modal_info modal_info_address\">\n" +
            "                <span class=\"modal_info_span\">주소</span>\n" +
            "                <p> " + signboard.address + "</p>\n" +
            "              </div>\n" +
            "              <div class=\"modal_info modal_info_nickname\">\n" +
            "                <span class=\"modal_info_span\">작성자</span>\n" +
            "                <p>" + signboard.nickname + "<img style='width: 20px; height: 20px' src='/img/icon1.png'></p>\n" +
            "              </div>\n" +
            "              <div class=\"modal_info modal_info_date\">\n" +
            "                <span class=\"modal_info_span\">작성일</span>\n" +
            "                <p>" + date + "</p>\n" +
            "              </div>\n" +
            "              <div class=\"modal_info_content\">\n" +
            "                " + signboard.content + "\n";
        "              </div>\n" +
        "            </div>";

        // 이미지 폼
        let imgs = "";
        for (let i = 0; i < signboard.files.length; i++) {
            imgs += "              <img class='footer_imgs' src=\"/img/" + signboard.files[i] + "\">\n";
        }
        modalBody.innerHTML = str; // 전달받은 signboard로 모달 컨텐츠 영역 교체
        modalImgs.innerHTML = imgs; // 전달받은 signboard의 이미지들로 푸터 영역 교체
        changeImage();
    }
    // hover시 해당 이미지로 메인 이미지가 바뀌는 이벤트 걸기
    function changeImage() {
        const footerImgs = document.querySelectorAll(".modal_footer_imgs img");
        footerImgs.forEach( img => {
            img.addEventListener("mouseenter", function (e){
                const mainImage = document.querySelector(".modal_img_container img")
                mainImage.src = img.src;
            })
        })
    }
}).catch(e => {
    console.log(e)
});

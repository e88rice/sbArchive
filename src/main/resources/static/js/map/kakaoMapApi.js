/** #######################################################################################
 ################################ 지도 객체 설정 및 생성 #####################################
 ########################################################################################## **/
const modalContainer = new bootstrap.Modal(document.querySelector(".modal_container")); // 모달 컨테이너
const modalBody = document.querySelector(".modal-body"); // 모달 컨텐츠 영역
const modalImgs = document.querySelector(".modal_footer_imgs"); // 모달 이미지들 영역
const mapSearchBar = document.querySelector("#map_search"); // 검색 영역
let isSearch = false;

var searchMapContainer = document.getElementById('map2'), // 지도를 표시할 div
    searchMapOption = {
        center: new kakao.maps.LatLng(126.8912251, 37.4515942), // 지도의 중심좌표
        level: 8 // 지도의 확대 레벨
    };
var searchMap = new kakao.maps.Map(searchMapContainer, searchMapOption); // 지도를 생성합니다
// 마커 이미지의 이미지 주소입니다
var imageSrc = '/images/img/favicon.png';
// var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
// 마커 이미지의 이미지 크기 입니다
var imageSize = new kakao.maps.Size(35, 35);

// 마커 이미지를 생성합니다
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

var searchMarkers = [];

getSB();

function getSB() {
    getSBList().then(r => {
        // 마커를 표시할 좌표, 해당 좌표의 title, 해당 좌표의 주소를 가진 객체 배열
        var positions = [];
        for (let i = 0; i < r.length; i++) {
            let item = {
                "title": r[i].title,
                "latlng": new kakao.maps.LatLng(parseFloat(r[i].yoffSet), parseFloat(r[i].xoffSet)),
                "content": "가게명 : " + r[i].title + "<br>" + r[i].address
            }
            if (r[i].files != null && r[i].files.length > 0) { // 만약 파일이 존재한다면
                item = {
                    "title": r[i].title,
                    "latlng": new kakao.maps.LatLng(parseFloat(r[i].yoffSet), parseFloat(r[i].xoffSet)),
                    "content": "<div style='display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; width: 300px; height: 220px; padding: 10px;'> <img class='test-img' style='height: 80px; width: 80px; margin-top: 10px' src='/images/signboard/" + r[i].files[0] + "'>  " + " <br> <b> " + r[i].title + " </b>  <br>" + r[i].address + "</div>"
                }
            }
            positions.push(item); // 객체 배열에 추가
        }
        var lastY = r[positions.length - 1].yoffSet;
        var lastX = r[positions.length - 1].xoffSet;


        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                // center: new kakao.maps.LatLng(35.8661170068962, 128.593835998552), // 지도의 중심좌표
                center: new kakao.maps.LatLng(lastY, lastX), // 지도의 중심좌표 y, x
                draggable: false,
                level: 3 // 지도의 확대 레벨
            };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        var zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.BOTTOMRIGHT);
        setDraggable();

        function setDraggable() {
            // 마우스 드래그로 지도 이동 가능여부를 설정합니다
            map.setDraggable(true);
        }

        /** #######################################################################################
         ################################ 마커 마우스오버,아웃 이벤트 #################################
         ########################################################################################## **/

        for (var i = 0; i < positions.length; i++) {
            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng, // 마커의 위치
                title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image: markerImage // 마커 이미지
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
            kakao.maps.event.addListener(marker, 'click', goRead(r[i].signboardId));
        }

    });
}

mapSearchBar.addEventListener("change", getSearch);

function getSearch() {
    // 장소 검색 객체를 생성합니다
    var ps = new kakao.maps.services.Places();

    if (mapSearchBar.value.trim() === "") { // 검색어가 없다면
        isSearch = false;
        document.getElementById("map").style.display = "block";
        document.getElementById("map2").style.display = "none";
        document.querySelector(".try_search_wrap").style.display = "block";
        document.querySelector(".infos_wrap").style.display = "none";
        document.querySelector(".s_page_wrap").style.display = "none";
        document.querySelector(".info_wrap").style.opacity = '0';
        return;
    }

    let keyword = mapSearchBar.value;

    // 키워드로 장소를 검색합니다
    getSearchSBList(keyword, 1).then(r => {

        if (r.length === 0) {
            alert("조회된 결과가 없습니다.");
            isSearch = false;
            document.getElementById("map").style.display = "block";
            document.getElementById("map2").style.display = "none";
            document.querySelector(".try_search_wrap").style.display = "block";
            document.querySelector(".infos_wrap").style.display = "none";
            document.querySelector(".s_page_wrap").style.display = "none";
            document.querySelector(".info_wrap").style.opacity = '0';
            return;
        }
        isSearch = true;
        document.querySelector(".s_page_wrap").style.display = "block";
        getSearchItems(keyword, 1);

        var searchItemPositions = [];
        for (let i = 0; i < r.dtoList.length; i++) {
            let item = {
                "title": r.dtoList[i].title,
                "latlng": new kakao.maps.LatLng(parseFloat(r.dtoList[i].yoffSet), parseFloat(r.dtoList[i].xoffSet)),
                "content": "가게명 : " + r.dtoList[i].title + "<br>" + r.dtoList[i].address
            }
            if (r.dtoList[i].files != null && r.dtoList[i].files.length > 0) { // 만약 파일이 존재한다면
                item = {
                    "title": r.dtoList[i].title,
                    "latlng": new kakao.maps.LatLng(parseFloat(r.dtoList[i].yoffSet), parseFloat(r.dtoList[i].xoffSet)),
                    "content": "<div style='display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; width: 300px; height: 220px; padding: 10px;'> <img class='test-img' style='height: 80px; width: 80px; margin-top: 10px' src='/images/signboard/" + r.dtoList[i].files[0] + "'>  " + " <br> <b> " + r.dtoList[i].title + " </b>  <br>" + r.dtoList[i].address + "</div>"
                }
            }
            searchItemPositions.push(item); // 객체 배열에 추가
        }

        var defaultMap = document.getElementById("map");
        defaultMap.style.display = 'none';
        searchMapContainer.style.display = "block";

        // 기존 마커배열들을 순회하며 마커 삭제
        searchMarkers.forEach(marker => {
            marker.setMap(null);
        })
        // 마커들의 배열 초기화
        searchMarkers.splice(0);

        ps.keywordSearch(keyword, placesSearchCB);


        // 키워드 검색 완료 시 호출되는 콜백함수 입니다
        function placesSearchCB(data, status, pagination) {
            if (status === kakao.maps.services.Status.OK) {

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                // LatLngBounds 객체에 좌표를 추가합니다
                var bounds = new kakao.maps.LatLngBounds();

                for (var i = 0; i < searchItemPositions.length; i++) {
                    displayMarker(searchItemPositions[i], r.dtoList[i].signboardId);
                    bounds.extend(searchItemPositions[i].latlng);
                }

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
                searchMap.setBounds(bounds);
            }
        }

        function displayMarker(place, signboardId) {

            var marker = new kakao.maps.Marker({
                map: searchMap, // 마커를 표시할 지도
                position: place.latlng, // 마커의 위치
                title: place.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image: markerImage // 마커 이미지
            });

            searchMarkers.push(marker);

            // 마커에 표시할 인포윈도우를 생성합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: place.content // 인포윈도우에 표시할 내용
            });

            // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
            // 이벤트 리스너로는 클로저를 만들어 등록합니다
            // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
            kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(searchMap, marker, infowindow));
            kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
            kakao.maps.event.addListener(marker, 'click', goRead(signboardId));
        }

        searchMap.relayout();
    });
}


// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
    return function () {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function () {
        infowindow.close();
    };
}

function goRead(signboardId) {
    return function () {
        self.location.href=`/signboard/read/1/${signboardId}?nat=map`;
    }
}

// function modalShow(signboardId) {
//     return function () {
//         modalContainer.show();
//         justGetSignboard(signboardId).then(r => {
//             addInfoForm(r);
//             // addSInfoForm(r); 이것도할까?
//         }).catch(e => {
//             console.log(e);
//         })
//     }
// }

// 객체의 정보를 받고 HTML 요소로 추가
// function addInfoForm(signboard) {
//     console.log("ㅎㅇ");
//     let addDate = signboard.addDate; // 날짜 배열을 설정한 문자열로 변환
//     let modDate = signboard.modDate;
//     let date = addDate === modDate ? modDate : (modDate + " (수정됨)");
//
//     let str = "<div class=\"modal_img_container\">\n" +
//         "              <img src='/images/signboard/" + signboard.files[0] + "'>\n" +
//         "            </div>\n" +
//         "            <div class=\"modal_info_container\">\n" +
//         "              <div id=\"map\"></div>\n" +
//         "              <div class=\"modal_info modal_info_title\">\n" +
//         "                <span class=\"modal_info_span\">가게명</span>\n" +
//         "                <p> " + signboard.title + "</p>\n" +
//         "              </div>\n" +
//         "              <div class=\"modal_info modal_info_address\">\n" +
//         "                <span class=\"modal_info_span\">주소</span>\n" +
//         "                <p> " + signboard.address + "</p>\n" +
//         "              </div>\n" +
//         "              <div class=\"modal_info modal_info_nickname\">\n" +
//         "                <span class=\"modal_info_span\">작성자</span>\n" +
//         "                <p>" + signboard.nickname + "\n" +
//         "              </div>\n" +
//         "              <div class=\"modal_info modal_info_date\">\n" +
//         "                <span class=\"modal_info_span\">작성일</span>\n" +
//         "                <p>" + date + "</p>\n" +
//         "              </div>\n" +
//         "              <div class=\"modal_info_content\">\n" +
//         signboard.content + "\n";
//     "              </div>\n" +
//     "            </div>";
//
//
//
//     // 이미지 폼
//     let imgs = "";
//     for (let i = 0; i < signboard.files.length; i++) {
//         imgs += "              <img src='/images/signboard/" + signboard.files[i] + "'>\n";
//     }
//     modalBody.innerHTML = str; // 전달받은 signboard로 모달 컨텐츠 영역 교체
//     modalImgs.innerHTML = imgs; // 전달받은 signboard의 이미지들로 푸터 영역 교체
//     changeImage();
// }

// hover시 해당 이미지로 메인 이미지가 바뀌는 이벤트 걸기
// function changeImage() {
//     const footerImgs = document.querySelectorAll(".modal_footer_imgs img");
//     footerImgs.forEach(img => {
//         img.addEventListener("mouseenter", function (e) {
//             const mainImage = document.querySelector(".modal_img_container img")
//             mainImage.src = img.src;
//         })
//     })
// }

// 검색 시 페이지 정보와 객체 정보들을 가져와 요소로 채워넣는 함수
function getSearchItems(keyword, page) {

    getSearchSBList(keyword, page).then(r => {
        getSearchItemsForm(r.dtoList);
        getSearchItemsPagingForm(r);
        searchItemsChangePage(r.end);
        showInfoWrap();
    })
}

function getSearchItemsForm(dtoList) {
    let itemsForm = "";
    for (let i = 0; i < dtoList.length; i++) {
        itemsForm += "                <div class=\"s_info_wrap\" data-id=\""+ dtoList[i].signboardId +"\">\n" +
            "                    <div class=\"info_box\">\n" +
            "                        <div>" + dtoList[i].title + "</div>\n" +
            "                        <div>" + dtoList[i].nickname + "</div>\n" +
            "                        <div>" + dtoList[i].content + "</div>\n" +
            "                    </div>\n" +
            "                    <div class=\"img_box\">\n" +
            "                        <img src=\"/images/signboard/" + dtoList[i].files[0] + "\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <hr>";
    }
    // 검색결과를 표시하지 않고 있을 때 보여주는 이미지 요소
    let trySearchWrap = document.querySelector(".try_search_wrap");
    if (trySearchWrap.style.display !== "none") trySearchWrap.style.display = "none";
    // 검색결과를 표시하는 요소들의 컨테이너
    let infosWrap = document.querySelector(".infos_wrap");
    infosWrap.style.display = "block";
    infosWrap.innerHTML = itemsForm;
}

function getSearchItemsPagingForm(response) {
    let start;
    let end;
    let last = response.last;
    end = Math.ceil(response.page / 5.0) * 5;
    start = end - (5 - 1);

    if (end > last) {
        end = last;
    }
    let prev = start > 1;
    let pageForm = "      <ul class=\"s_paging_wrap\">\n";
    // 이전 버튼이 존재한다면
    if (prev) {
        pageForm += "        <li class=\"s_page_prev\">\n" +
            "          <a data-num=\"" + (start - 1) + "\">\n" +
            "            <i class=\"fa-solid fa-chevron-left\" style=\"color: #3f4040;\"></i>\n" +
            "          </a>\n" +
            "        </li>\n";
    }
    for (let i = start; i <= end; i++) {
        pageForm += "          <li class=\"s_page_wrapper\">\n";
        if (response.page === i) {
            pageForm += "<a class='s_page active' data-num=\"" + i + "\">" + i + "</a>\n";
        } else {
            pageForm += "<a class='s_page' data-num=\"" + i + "\">" + i + "</a>\n";
        }
        pageForm += "          </li>\n";
    }
    if (response.next) {
        pageForm += "        <li class=\"s_page_next\">\n" +
            "          <a data-num=\"" + (end + 1) + "\">\n" +
            "            <i class=\"fa-solid fa-chevron-right\" style=\"color: #3f4040;\"></i>\n" +
            "          </a>\n" +
            "        </li>\n";
    }
    pageForm += "      </ul>";

    document.querySelector(".s_page_wrap").innerHTML = pageForm;
}

// 페이지 번호를 누르면 현재 키워드와 페이지를 받아서 키워드와 페이지로 아이템들을 표시해주는 함수를 호출함
function searchItemsChangePage(end) {
    if (end <= 1) {
        return;
    }
    let keyword = mapSearchBar;
    let page = document.getElementById("sPageNumber");

    const sPageWrap = document.querySelectorAll(".s_paging_wrap li > a");
    sPageWrap.forEach(sPage => {
        sPage.addEventListener("click", function () {
            page = sPage.dataset.num;
            getSearchItems(keyword.value, page)
        })
    })
}

function showInfoWrap() {
    const items = document.querySelectorAll(".s_info_wrap");
    items.forEach(item => {
        item.addEventListener("click", function (){
            const id = item.dataset.id;
            getItem(id).then(r => {
                document.querySelector(".info_wrap").style.opacity = '1';
                // document.querySelector("");
                addSInfoForm(r);
                tapChange();
            });
        })
    })
}

function addSInfoForm(response) {
    let date = response.addDate === response.modDate ? response.modDate : (response.modDate + " (수정됨)");
    let content = response.content !== "";

    let form = "<div class=\"img_box\">\n" +
        "                    <img src=\"/images/signboard/"+ response.files[0] +"\">\n" +
        "                </div>\n" +
        "                <div class=\"info_box\">\n" +
        "                    <div class=\"info_title\">"+ response.title +"</div>\n" +
        "                    <div class=\"info_date\">" + date + "</div>\n" +
        "                    <div class=\"info_nickname\">작성자 : " + response.nickname + "</div>\n" +
        "                    <div class=\"info_tap\">\n" +
        "                        <p class=\"tap_info active\">정보</p>\n" +
        "                        <p class=\"tap_images\">사진</p>\n" +
        "                    </div>\n" +
        "                    <div class=\"info_content\">" + response.content + "</div>\n" +
        "                    <div class=\"info_images\" style=\"display: none\">\n";
    for(let i=0; i<response.files.length; i++){
        form += "                        <img class='image' src=\"/images/signboard/"+ response.files[i] +"\">\n";
    }
    form += "                    </div>\n" +
        "                </div>";
    form += "                <div class=\"close_box\">\n" +
        "                    <box-icon class='close_info_btn' name='chevron-left'></box-icon>\n" +
        "                </div>";
    document.querySelector(".info_wrap").innerHTML = form;

    if(!content) {
        document.querySelector(".tap_info").style.display = "none";
        document.querySelector(".info_images").style.display = "block";
        document.querySelector(".tap_images").classList.add("active");
    }

    document.querySelectorAll(".image").forEach( image => {
        image.addEventListener("mouseenter", function (){
            document.querySelector(".info_wrap .img_box > img").src = image.src;
        })
    })

    function panTo() {
        // 이동할 위도 경도 위치를 생성합니다
        var moveLatLon = new kakao.maps.LatLng(response.yoffSet, response.xoffSet);

        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        searchMap.panTo(moveLatLon);
    }
    panTo();
}

function tapChange() {
    const tapInfo = document.querySelector(".tap_info");
    const tapImages = document.querySelector(".tap_images");

    const infoContent = document.querySelector(".info_content");
    const infoImages = document.querySelector(".info_images");

    tapInfo.addEventListener("click", function (){
        infoContent.style.display = "block";
        infoImages.style.display = "none";
        tapInfo.classList.toggle('active');
        tapImages.classList.toggle('active');
    })

    tapImages.addEventListener("click", function (){
        infoContent.style.display = "none";
        infoImages.style.display = "block";
        tapInfo.classList.toggle('active');
        tapImages.classList.toggle('active');
    })
    document.querySelector(".close_box").style.display = 'block';
    closeTap();
}

function closeTap() {
    document.querySelector(".close_box").addEventListener("click", function () {
        document.querySelector(".info_wrap").style.opacity = '0';
        document.querySelector(".close_box").style.display = 'none';
    })
}
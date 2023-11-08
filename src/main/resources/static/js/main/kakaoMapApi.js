/** #######################################################################################
 ################################ 지도 객체 설정 및 생성 #####################################
 ########################################################################################## **/

// 마커를 표시할 위치와 title 객체 배열입니다
// var positions = [
// //     // {
// //     //     title: '카카오',
// //     //     latlng: new kakao.maps.LatLng(33.450705, 126.570677),
// //     //     content: '카카오입니다'
// //     // },
// //     // {
// //     //     title: '생태연못',
// //     //     latlng: new kakao.maps.LatLng(33.450936, 126.569477),
// //     //     content: "생태연못입니다"
// //     // }
// ];


getSBList().then(r => {
    // 마커를 표시할 좌표, 해당 좌표의 title, 해당 좌표의 주소를 가진 객체 배열
    var positions = [];
    for(let i=0; i<r.length; i++){
        const item = {
            "title": r[i].title,
            "latlng": new kakao.maps.LatLng(parseFloat(r[i].yoffSet), parseFloat(r[i].xoffSet)),
            "content": "<img style='max-height: 50px; max-width: 50px;' src='/img/test1.jpg'>  " + " <br> " + "가게명 : " + r[i].title + "<br>" + r[i].address
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
    var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    for (var i = 0; i < positions.length; i ++) {

        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image : markerImage // 마커 이미지
        });

        /** #######################################################################################
         ################################ 마커 마우스오버,아웃 이벤트 #################################
         ########################################################################################## **/

        for (var i = 0; i < positions.length; i ++) {
            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng // 마커의 위치
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
        }

        // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
        function makeOverListener(map, marker, infowindow) {
            return function() {
                infowindow.open(map, marker);
            };
        }

        // 인포윈도우를 닫는 클로저를 만드는 함수입니다
        function makeOutListener(infowindow) {
            return function() {
                infowindow.close();
            };
        }

    }
}).catch(e => {
    console.log(e)
});

// console.log(positions[0]);
// console.log(positions.length);


    // var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    //     mapOption = {
    //         center: new kakao.maps.LatLng(35.8661170068962, 128.593835998552), // 지도의 중심좌표
    //         level: 3 // 지도의 확대 레벨
    //     };
    //
    // var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다



// 마커 이미지의 이미지 주소입니다
//     var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
//
//     for (var i = 0; i < positions.length; i ++) {
//
//         // 마커 이미지의 이미지 크기 입니다
//         var imageSize = new kakao.maps.Size(24, 35);
//
//         // 마커 이미지를 생성합니다
//         var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
//
//         // 마커를 생성합니다
//         var marker = new kakao.maps.Marker({
//             map: map, // 마커를 표시할 지도
//             position: positions[i].latlng, // 마커를 표시할 위치
//             title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
//             image : markerImage // 마커 이미지
//         });
//     }

    /** #######################################################################################
     ###################################### 마커 생성 ##########################################
     ########################################################################################## **/

// 마커가 표시될 위치
// var markerPosition  = new kakao.maps.LatLng(33.450701, 126.570667);
//
// // 마커를 생성하여 지도 객체에 찍어줌
// var marker = new kakao.maps.Marker({
//     position: markerPosition
// });
//
// // 마커가 지도 위에 표시되도록 설정
// marker.setMap(map);

// 아래 코드는 지도 위의 마커를 제거하는 코드
// marker.setMap(null);


    /** #######################################################################################
     ################################ 마커 클릭 이벤트 ##########################################
     ########################################################################################## **/


// // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
// var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
//     iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
//
// // 인포윈도우를 생성합니다
// var infowindow = new kakao.maps.InfoWindow({
//     content : iwContent,
//     removable : iwRemoveable
// });
//
// // 마커에 클릭이벤트를 등록합니다
// kakao.maps.event.addListener(marker, 'click', function() {
//     // 마커 위에 인포윈도우를 표시합니다
//     infowindow.open(map, marker);
// });

//     /** #######################################################################################
//      ################################ 마커 마우스오버,아웃 이벤트 #################################
//      ########################################################################################## **/
//
//     for (var i = 0; i < positions.length; i ++) {
//         // 마커를 생성합니다
//         var marker = new kakao.maps.Marker({
//             map: map, // 마커를 표시할 지도
//             position: positions[i].latlng // 마커의 위치
//         });
//
//         // 마커에 표시할 인포윈도우를 생성합니다
//         var infowindow = new kakao.maps.InfoWindow({
//             content: positions[i].content // 인포윈도우에 표시할 내용
//         });
//
//         // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
//         // 이벤트 리스너로는 클로저를 만들어 등록합니다
//         // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
//         kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
//         kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
//     }
//
// // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
//     function makeOverListener(map, marker, infowindow) {
//         return function() {
//             infowindow.open(map, marker);
//         };
//     }
//
// // 인포윈도우를 닫는 클로저를 만드는 함수입니다
//     function makeOutListener(infowindow) {
//         return function() {
//             infowindow.close();
//         };
//     }

    /** #######################################################################################
     ################################ 주소로 좌표를 얻어내고 마커 찍기 ###########################
     ########################################################################################## **/

// // 주소-좌표 변환 객체를 생성합니다
// var geocoder = new kakao.maps.services.Geocoder();
//
// // 주소로 좌표를 검색합니다
// geocoder.addressSearch('대구광역시 중구 중앙대로 366', function(result, status) {
//
//     // 정상적으로 검색이 완료됐으면
//     if (status === kakao.maps.services.Status.OK) {
//         console.log("y:" + result[0].y);
//         console.log("x:" + result[0].x);
//
//         // var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
//         var coords = new kakao.maps.LatLng(37.5192298, 126.9034700);
//
//         // 결과값으로 받은 위치를 마커로 표시합니다
//         var marker = new kakao.maps.Marker({
//             map: map,
//             position: coords
//         });
//
//         // 인포윈도우로 장소에 대한 설명을 표시합니다
//         var infowindow = new kakao.maps.InfoWindow({
//             content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
//         });
//         infowindow.open(map, marker);
//
//         // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
//         map.setCenter(coords);
//     }
// });

    /** #######################################################################################
     ################################ 지도 사용자 위치 기반으로 중심 설정 ###########################
     ########################################################################################## **/

// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
// if (navigator.geolocation) {
//
//     // GeoLocation을 이용해서 접속 위치를 얻어옵니다
//     navigator.geolocation.getCurrentPosition(function(position) {
//
//         var lat = position.coords.latitude, // 위도
//             lon = position.coords.longitude; // 경도
//
//         var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
//             message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
//
//         // 마커와 인포윈도우를 표시합니다
//         displayMarker(locPosition, message);
//
//     });
//
// } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
//
//     var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
//         message = 'geolocation을 사용할수 없어요..'
//
//     displayMarker(locPosition, message);
// }
//
// // 지도에 마커와 인포윈도우를 표시하는 함수입니다
// function displayMarker(locPosition, message) {
//
//     // 마커를 생성합니다
//     var marker = new kakao.maps.Marker({
//         map: map,
//         position: locPosition
//     });
//
//     var iwContent = message, // 인포윈도우에 표시할 내용
//         iwRemoveable = true;
//
//     // 인포윈도우를 생성합니다
//     var infowindow = new kakao.maps.InfoWindow({
//         content : iwContent,
//         removable : iwRemoveable
//     });
//
//     // 인포윈도우를 마커위에 표시합니다
//     infowindow.open(map, marker);
//
//     // 지도 중심좌표를 접속위치로 변경합니다
//     map.setCenter(locPosition);
// }


let isAnonymous = '';
getUsername().then(r => {
    console.log(r[0] === 'null');
    isAnonymous = r[0];
    console.log(isAnonymous);

    let receivedPage = 1; // 수신 처음 페이지는 1
    let sentPage = 1; // 발신 처음 페이지는 1

    // 현재 사용자가 로그인 상태라면
    if(isAnonymous !== 'null') {

        const receivedPageContainer = document.querySelector(".received_page_container");
        const sentPageContainer = document.querySelector(".sent_page_container");
        const messageBoxBtn = document.querySelector("#message-box");                       // 헤더 쪽지함 버튼
        const messageModal = new bootstrap.Modal(document.querySelector(".message_modal")); // 모달 컨테이너
        const receivedTab = document.querySelector(".message_nav_received");                // 받은 쪽지함 탭
        const sentTab = document.querySelector(".message_nav_sent");                        // 보낸 쪽지함 탭
        const receivedMessageContainer = document.querySelector(".received_message_container"); // 받은 쪽지함 컨텐츠 영역
        const sentMessageContainer = document.querySelector(".sent_message_container");         // 보낸 쪽지함 컨텐츠 영역
        const msgSelectRemoveBtn = document.getElementById("msgSelectRemove");                // 선택 삭제 버튼
        const msgAllSelectBtn = document.getElementById("msgAllSelect");                      // 전체 선택 버튼
        const msgViewModal = new bootstrap.Modal(document.querySelector(".message_view_modal")); // 쪽지 보는 모달 컨테이너
        const msgWriteBtn = document.getElementById("msgWrite");                                // 쪽지 보내기 버튼
        const msgWriteModal = new bootstrap.Modal(document.querySelector(".message_write_modal")); // 쪽지 작성 모달 컨테이너


        // 쪽지함 열기 이벤트
        messageBoxBtn.addEventListener("click", function (){
            messageModal.show();
            ReceivedMsgByPage(receivedPage, isAnonymous);
        })

        // 페이지와 유저의 아이디를 받아 받은 쪽지함을 갱신해주는 함수
        function ReceivedMsgByPage(rPage, Id) {
            getReceivedMessage(rPage, Id).then( r => { // 1페이지 기준
                if(r.dtoList !== null){
                    addMsgPagingForm("received", r); // 1페이지 기준 페이징
                    addMsgForm("received", r);       // 1페이지 기준 쪽지 리스트
                }
                getPageNum("received");          // 페이지 a 태그를 클릭하면 해당하는 타입의 페이지 넘버를 변경해줌
                viewModalShow();                 // 클릭 시 쪽지 뷰 관련 모달 오픈 및 기능 함수
                writeModalShow();               // 클릭 시 쪽지 작성 관련 모달 오픈 및 기능 함수
            }).catch( e => {
                console.log(e)
            })
        }

        // 페이지와 유저의 아이디를 받아 보낸 쪽지함을 갱신해주는 함수
        function SentMsgByPage(rPage, Id) {
            getSentMessage(rPage, Id).then( r => { // 1페이지 기준
                if(r.dtoList !== null) {
                    addMsgPagingForm("sent", r);       // 1페이지 기준 페이징
                    addMsgForm("sent", r);            // 1페이지 기준 쪽지 리스트
                }
                getPageNum("sent");               // 페이지 a 태그를 클릭하면 해당하는 타입의 페이지 넘버를 변경해줌
                viewModalShow();                 // 클릭 시 쪽지 뷰 관련 모달 오픈 및 기능 함수
                writeModalShow();               // 클릭 시 쪽지 작성 관련 모달 오픈 및 기능 함수
            }).catch( e => {
                console.log(e)
            })
        }

        // 받은 쪽지함 탭 열기 이벤트
        receivedTab.addEventListener("click", function (){

            receivedMessageContainer.classList.remove("disnone");     // 받은 쪽지함 = 디스플레이 none 으로 만들어주는 클래스 삭제
            receivedTab.classList.add("active");                            // 받은 쪽지함 = 선택 디자인 css를 만들어주는 클래스 추가
            receivedPageContainer.classList.remove("disnone");        // 받은 쪽지함 페이징 = 디스플레이 none 으로 만들어주는 클래스 삭제

            sentMessageContainer.classList.add("disnone");         // 보낸 쪽지함 = 디스플레이 none 으로 만들어주는 클래스 추가
            sentTab.classList.remove("active");                    // 보낸 쪽지함 = 선택 디자인 css를 만들어주는 클래스 삭제
            sentPageContainer.classList.add("disnone");             // 보낸 쪽지함 페이징 = 디스플레이 none 으로 만들어주는 클래스 추가

            receivedPage = 1;
            ReceivedMsgByPage(receivedPage, isAnonymous);        // 페이지와 id를 전달받아 객체를 받아 여러가지 설정을 해주는 함수
        })

        // 보낸 쪽지함 탭 열기 이벤트
        sentTab.addEventListener("click", function (){

            sentMessageContainer.classList.remove("disnone");   // 보낸 쪽지함 = 디스플레이 none 으로 만들어주는 클래스 삭제
            sentTab.classList.add("active");                           // 보낸 쪽지함 = 선택 디자인 css를 만들어주는 클래스 추가
            sentPageContainer.classList.remove("disnone");      // 보낸 쪽지함 페이징 = 디스플레이 none 으로 만들어주는 클래스 삭제

            receivedMessageContainer.classList.add("disnone");          // 받은 쪽지함 = 디스플레이 none 으로 만들어주는 클래스 추가
            receivedTab.classList.remove("active")              // 받은 쪽지함 = 선택 디자인 css를 만들어주는 클래스 삭제
            receivedPageContainer.classList.add("disnone");           // 받은 쪽지함 페이징 = 디스플레이 none 으로 만들어주는 클래스 추가

            sentPage = 1;
            SentMsgByPage(sentPage, isAnonymous);                       // 페이지와 id를 전달받아 객체를 받아 여러가지 설정을 해주는 함수
        })

        function addMsgPagingForm(type, response) {
            let str = "<div class=\"page_container\">\n" +
            "    <ul class=\"page_wrap\">\n";
            if(response.prev) {
                str +=             "      <li class=\"page_item\">\n" +
                "        <a class=\"msg_page_link\" data-num=\""+ (response.start-1) +"\"><i class=\"fa-solid fa-left-long\" style=\"color: #3f4040;\"></i>Prev</a>\n" +
                "      </li>\n";
            }
            for(let i=response.start; i<=response.end; i++) {
                if(response.page===i) {
                    str +=             "        <li class=\"page-item active\">\n" +
                    "          <a class=\"msg_page_link message_page\" data-num=\""+ i + "\">"+i+"</a>\n" +
                    "        </li>\n";
                } else {
                    str +=             "        <li class=\"page-item\">\n" +
                    "          <a class=\"msg_page_link\" data-num=\""+ i + "\">"+i+"</a>\n" +
                    "        </li>\n";
                }
            }
            if(response.next) {
                str +=             "      <li class=\"page-item\">\n" +
                "        <a class=\"msg_page_link\" data-num=\"" + (response.end+1) +"\">Next</a>\n" +
                "      </li>\n";
            }
            str +=             "    </ul>\n" +
            "  </div>";

            if(type === "received") {
                document.querySelector(".received_page_container").innerHTML = str;
            } else {
                document.querySelector(".sent_page_container").innerHTML = str;
            }
        }

        function addMsgForm(type, response) {
            let str = "            <tr>\n" +
            "              <th>선택</th>\n" +
            "              <th>";
            if(type === "received") str += "발신인";
            else str += "수신인";
            str +=  "</th>\n" +
            "              <th>내용</th>\n" +
            "              <th>날짜</th>\n";
            if(type === "sent") str += "              <th>확인</th>\n";
            str += "            </tr>";
            if(type === "received") {
                for(let i=0; i<response.dtoList.length; i++) {
                    let msg = response.dtoList[i];
                    if(msg.read) str += "            <tr style='font-weight: lighter; color: #9a9a9a' class=\"message_content_wrap\">\n";
                    else str += "            <tr class=\"message_content_wrap\">\n";
                    str += "              <td class=\"message_checkbox\" onclick=\"event.cancelBubble=true;\"> <input type=\"checkbox\" value=\"" + msg.index + "\"> </td>\n" +
                    "              <td class=\"message_sender\"> <p>" + msg.senderId + "</p> </td>\n" +
                    "              <td class=\"message_content\"> <p>" + msg.content + "</p> </td>\n"+
                    "              <td class=\"message_sendDate\"> <p> " + msg.sendDate[0]+"-"+msg.sendDate[1]+"-"+msg.sendDate[2] + "</p> </td>\n" +
                    "            </tr>"
                }
                document.querySelector(".received_message_content_container").innerHTML = str;
            } else {
                for(let i=0; i<response.dtoList.length; i++) {
                let msg = response.dtoList[i];
                str += "            <tr class=\"message_content_wrap\">\n" +
                "              <td class=\"message_checkbox\" onclick=\"event.cancelBubble=true;\"> <input type=\"checkbox\" value=\"" + msg.index + "\"> </td>\n" +
                "              <td class=\"message_receiver\"> <p>" + msg.receiverId + "</p> </td>\n" +
                "              <td class=\"message_content\"> <p>" + msg.content + "</p> </td>\n" +
                "              <td class=\"message_sendDate\"> <p> " + msg.sendDate[0]+"-"+msg.sendDate[1]+"-"+msg.sendDate[2] + "</p> </td>\n" +
                "              <td class=\"message_sendDate\"> <p> " + (msg.read ? "읽음" : "읽지않음") + "</p> </td>\n" +
                "            </tr>"
                }
                document.querySelector(".sent_message_content_container").innerHTML = str;
            }
        }

        // 전체 선택 버튼 누르면 체크가 토글 처리
        msgAllSelectBtn.addEventListener("click", function () {
            document.querySelectorAll(".message_checkbox input[type=checkbox]").forEach( message => {
                message.checked = true;
            })
        })

        // 선택 삭제 버튼을 누르면 선택된 것만 삭제 처리
        msgSelectRemoveBtn.addEventListener("click", function () {
            document.querySelectorAll(".message_checkbox input[type=checkbox]:checked").forEach( message => {
                let type = message.parentElement.nextElementSibling.className.split("_")[1]; // message_sender
                removeByIdxAndType(message.value, type);
                if(type === "sender") { // 받은 쪽지함에서 삭제했을 경우
                    ReceivedMsgByPage(receivedPage, isAnonymous); // 받은 쪽지함 갱신
                } else { // 보낸 쪽지함에서 삭제한 경우
                    SentMsgByPage(sentPage, isAnonymous); // 보낸 쪽지함 갱신
                }
            })
        })

        function removeByIdxAndType(index, type) {
            removeByIndexAndType(index, type).then(r => {
                console.log(r);
            }).catch(e => {
                console.log(e);
            })
        }

        function getPageNum(type) {
            const pageLinks = document.querySelectorAll(".msg_page_link");
            pageLinks.forEach(pageLink => {
                pageLink.addEventListener("click", function (e){
                    e.preventDefault();
                    if(type === "received") {
                        receivedPage = pageLink.dataset.num;
                        ReceivedMsgByPage(receivedPage, isAnonymous);
                    } else {
                        sentPage = pageLink.dataset.num;
                        SentMsgByPage(sentPage, isAnonymous);
                    }
                })
            })
        }

        function viewModalShow() {
            document.querySelectorAll(".message_content_wrap").forEach( wrap => {
                wrap.addEventListener("click", function (){
                    const Messageindex = parseInt(wrap.firstElementChild.firstElementChild.value);
                    const type = wrap.firstElementChild.nextElementSibling.className.split("_")[1];
                    msgViewModal.show();
                    console.log("index : " + Messageindex + " & type : " + type);
                    getMessage(Messageindex, type).then( r => {
                        console.log(r);
                        let str = "";
                        if(type === "receiver") {
                            str += "<div class=\"message_writer\"><span>받은 사람 </span> " + r.receiverId +"</div>\n" +
                            "                        <hr>\n" +
                            "                        <p class=\"message_view_p\">"+ r.content +"</p>";
                            document.querySelector(".message_view_modal_footer").innerHTML = "";
                        } else {
                            wrap.style.fontWeight = "lighter";
                            wrap.style.color = "#9a9a9a";
                            str += "<div class=\"message_writer\"><span>보낸 사람 </span> " + r.senderId + "</div>\n" +
                            "                        <hr>\n" +
                            "                        <p class=\"message_view_p\">"+ r.content +"</p>";
                            const replyBtn = "<div><button class=\"msgViewReplyBtn\">답장하기</button></div>";
                            document.querySelector(".message_view_modal_footer").innerHTML = replyBtn;
                            // 답장하기 클릭 시
                            document.querySelector(".msgViewReplyBtn").addEventListener("click", function (){
                                msgWriteModal.show(); // 쪽지 작성 폼 등장
                                SendMsg(r.senderId);
                            })
                        }
                        document.querySelector(".message_view_content_wrap").innerHTML = str;
                    }).catch(e => {
                        console.log(e);
                    })
                })
            })
        }

        function writeModalShow() {
            // 쪽지 보내기 버튼 클릭 시
            msgWriteBtn.addEventListener("click", function () {
                msgWriteModal.show();
                SendMsg(null);
            })
        }

        function SendMsg(receiverName) {
            const rcvName = document.querySelector(".msgReceiverName");
            const msgContent = document.querySelector(".msgContent");
            msgContent.value = "";
            rcvName.value = "";
            rcvName.readOnly = false;
            if(receiverName !== null) { // 답장하기 버튼으로 접근 시
                rcvName.value = receiverName;
                rcvName.readOnly = true; // 작성자 변경 불가능
            }
            document.querySelector(".sendMsg").cloneNode(true); // 클론 생성
            document.querySelector(".sendMsg").parentNode
                .replaceChild(document.querySelector(".sendMsg").cloneNode(true),
                    document.querySelector(".sendMsg")); // 버튼을 새로운 클론으로 교체
            document.querySelector(".sendMsg").addEventListener("click", function (){
                if(msgContent.value.length < 5) {
                    alert("내용은 최소 5자 이상 입력해야 합니다.")
                    return;
                }
                addMessage(rcvName.value, msgContent.value).then( r => {
                    console.log(r);
                    if(!r) alert("존재하지 않는 사용자입니다. \n다시 입력해주세요.");
                    else {
                        alert("쪽지 전송 완료");
                        msgWriteModal.hide();
                        rcvName.value = "";
                        msgContent.value = "";
                    }
                }).catch(e => {
                    console.log(e);
                })
            })
        }
    }
}).catch( e => {
    console.log(e);
})

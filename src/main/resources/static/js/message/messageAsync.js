// page와 받은 사람의 아이디를 전달해 해당 유저가 수신한 쪽지 리스트를 가져옴
async function getReceivedMessage(page, receiverId) {
    const result = await axios.get(`/message/getReceivedMsg/${page}/${receiverId}`);
    return result.data;
}

// page와 보낸 사람의 아이디를 전달해 해당 유저가 발신한 쪽지 리스트를 가져옴
async function getSentMessage(page, senderId) {
    const result = await axios.get(`/message/getSentMsg/${page}/${senderId}`);
    return result.data;
}

// 쪽지의 인덱스번호와 타입(보낸 사람, 받은 사람)을 기준으로 논리적 삭제 처리
async function removeByIndexAndType(index, type) {
    const result = await axios.delete(`/message/remove/${index}/${type}`);
    return result.data;
}

// 현재 로그인한 유저의 아이디값을 받아옴
async function getUsername() {
    const result = await axios.get(`/message/getUsername`);
    return result.data;
}

// 메세지 1개를 받아옴
async function getMessage(index, type) {
    const result = await axios.get(`/message/get/${index}/${type}`);
    return result.data;
}

// 메세지 1개를 전송함
async function addMessage(receiverId, content) {
    const result = await axios.put(`/message/add/${receiverId}/${content}`);
    return result.data;
}

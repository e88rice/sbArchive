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

async function removeByIndexAndType(index, type) {
    const result = await axios.delete(`/message/remove/${index}/${type}`);
    return result.data;
}

async function getUsername() {
    const result = await axios.get(`/message/getUsername`);
    return result.data;
}

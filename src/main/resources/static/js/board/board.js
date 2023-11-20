async function likeUp(boardId, userId) {
    await axios.post(`/boardLike/remove/${boardId}/${userId}`);
}

async function likeDown(boardId, userId) {
    await axios.post(`/boardLike/add/${boardId}/${userId}`);
}
async function getLike(boardId, userId) {
    await axios.get(`/boardLike/add/${boardId}/${userId}`);
}
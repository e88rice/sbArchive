
// 댓글 목록 출력(페이징 o)
async function getReplyList({boardId, replyDepth, page, size, goLast}) {
    const result=await axios.get(`/reply/list/${boardId}/${replyDepth}?page=${page}`, {params:{page, size}});
    if(goLast) {
        const total=result.data.total;
        const lastPage=parseInt(Math.ceil(total/size));

        return getReplyList({boardId:boardId, replyDepth:replyDepth, page:lastPage, size:size});
    }
    return result.data;
}

// 원댓글 등록
async function addReply(replyObj) {
    const response=await axios.post(`/reply/add`, replyObj);
    return response;
}

// 원댓글 등록 후 parentReplyId에 replyId 넣기
async function modifyReplyId(updateReplyObj) {
    const response=await axios.put(`/reply/replyId/${updateReplyObj.newReplyId}`, updateReplyObj);
    return response.data;
}

// 대댓글 등록
async function addReReply(replyObj) {
    const response=await axios.post(`/reply/addRe`, replyObj);
    return response;
}

// 대댓글 삭제
async function removeReReply(replyId) {
    const response=await axios.delete(`/reply/${replyId}`);
    return response.data;
}

// 원댓글 조회(modify용)
async function getReply(replyId) {
    const response=await axios.get(`/reply/${replyId}`);
    return response.data;
}

// 대댓글 목록 출력
async function getReReplies(boardId, parentReplyId, replyDepth) {
    const response = await axios.get(`/reply/list/re/${boardId}/${parentReplyId}/${replyDepth}`);
    return response.data;
}

// 원댓글 수정
async function modifyReply(replyObj) {
    const response=await axios.put(`/reply/${replyObj.replyId}`, replyObj);
    return response.data;
}
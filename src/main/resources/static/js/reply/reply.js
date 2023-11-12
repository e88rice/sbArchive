
// 댓글 목록 출력(페이징 o)
async function getReplyList({boardId, page, size, goLast}) {
    const result=await axios.get(`/reply/list/${boardId}?page=${page}`, {params:{page, size}});
    if(goLast) {
        const total=result.data.total;
        const lastPage=parseInt(Math.ceil(total/size));

        return getReplyList({boardId:boardId, page:lastPage, size:size});
    }
    return result.data;
}

// 댓글 등록
async function addReply(replyObj) {
    const response=await axios.post(`/reply/add`, replyObj);
    return response;
}

// 댓글 삭제
async function removeReply(replyId) {
    const response=await axios.delete(`/reply/${replyId}`);
    return response.data;
}
////////// 여기까지 함 //////////

// 댓글 조회(modify용)
async function getReply(replyId) {
    const response=await axios.get(`/reply/${replyId}`);
    return response.data;
}

// 댓글 수정
async function modifyReply(replyObj) {
    const response=await axios.put(`/reply/${replyObj.replyId}`, replyObj);
    return response.data;
}
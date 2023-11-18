// page와 index를 이용해 간판 게시글 객체 하나를 받아오는 비동기 함수
async function getSignboard(page, index) {
    const result = await axios.get(`/signboard/get/${page}/${index}`);
    return result.data;
}

// 간판 게시글의 PK값, 변경된 내용을 이용해서 수정 처리 후 결과값 1(성공), 0(실패)을 반환하는 함수
async function updateSignboard(signboardId, content, files) {
    const formData = new FormData();
    for(let i=0; i<files.length; i++){
        formData.append("files", files[i]);
    }

    const result = await axios({
        method: "POST",
        url: `/signboard/modify/${signboardId}/${content}`,
        mode: "cors",
        headers: {
            "Content-Type": "multipart/form-data",
        },
        data: formData,
    })
    console.log(result)
    return result.data;
}

// 간판 게시글의 PK값으로 해당 간판 게시글을 삭제 처리 후 결과값 1 이상(성공), 0(실패)을 반환하는 함수
async function deleteSignboard(signboardId) {
    const result = await axios.delete(`/signboard/remove/${signboardId}`);
    return result.data;
}

// 그냥 간판 하나 가져오기
async function justGetSignboard(signboardId) {
    const result = await axios.get(`/signboard/get/${signboardId}`);
    return result.data;
}

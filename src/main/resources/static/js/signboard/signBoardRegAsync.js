// 검색어의 데이터를 받아오는 비동기 함수
async function getSearchList(query) {
    const result = await axios.get(`/search/list/${query}`);
    return result.data;
}

async function addFiles(formObj) {
    const result = await axios.post(`/upload`, formObj, {
        headers : {
            'Content-Type' : 'multipart/form-data'
        }
    });
    return result.data;
}

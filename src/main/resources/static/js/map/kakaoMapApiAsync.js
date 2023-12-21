// 모든 간판 데이터를 받아오는 비동기 함수
async function getSBList() {
    const result = await axios.get(`/search/list`);
    return result.data;
}

// 검색된 모든 간판 데이터를 받아오는 비동기 함수
async function getSearchSBList(keyword, page) {
    const result = await axios.get(`/signboard/get/search/${keyword}/${page}`)
    return result.data;
}

async function getItem(signboardId){
    const result = await axios.get(`/signboard/get/${signboardId}`);
    return result.data;
}
// 검색어의 데이터를 받아오는 비동기 함수
async function getSearchList(query) {
    console.log(query)
    const result = await axios.get(`/search/list/${query}`);
    console.log(result)
    return result.data;
}
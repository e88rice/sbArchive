// 모든 간판 데이터를 받아오는 비동기 함수
async function getSBList() {
    const result = await axios.get(`/search/list`);
    console.log(result)
    return result.data;
}
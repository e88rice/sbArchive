// 사인보드 ID값으로 SingboardAllDTO를 가져오는 함수
async function readSignboard(signboardId) {
    const result = await axios.get(`/signboard/get/${signboardId}`);
    console.log(result)
    return result.data;
}
async function removeToken() {
    const result = await axios.post(`https://kapi.kakao.com/v1/user/unlink`);
    return result.data;
}

async function getToken() {
    const result = await axios.get(`https://kapi.kakao.com/v1/user/access_token_info`);
    return result.data;
}
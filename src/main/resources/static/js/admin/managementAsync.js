async function getUsers(page) {
    const result = await axios.get(`/admin/get/user/${page}`);
    return result.data;
}

async function getSignboards(page) {
    const result = await axios.get(`/admin/get/signboard/${page}`);
    return result.data;
}

async function getBoards(page) {
    const result = await axios.get(`/admin/get/board/${page}`);
    return result.data;
}

async function getReplies(page) {
    const result = await axios.get(`/admin/get/replies/${page}`);
    return result.data;
}

async function removeFromManger(type, typeId) {
    const result = await axios.delete(`/admin/remove/${type}/${typeId}`);
}
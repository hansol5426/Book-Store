import api from '../api/axiosApi';

export const bookApi = {

    adminList: async (page) => {
        const response = await api.get(`/api/v1/admin/book?page=${page}`);
        return response.data.response;
    },

    get: async (bookId) => {
        const response = await api.get(`/api/v1/admin/book/${bookId}`);
        return response.data.response;
    },

    create: async (formData) => {
        const response = await api.post('/api/v1/admin/book', formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
        return response.data.response;
    },

    update: async (formData) => {
        const response = await api.put('/api/v1/admin/book', formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
        return response.data.response;
    },

    delete: async (bookId) => {
        const response = await api.delete(`/api/v1/admin/book/${bookId}`);
        return response.data.response;
    },

    userList: async (page) => {
        const response = await api.get(`/api/v1/book?page=${page}`);
        return response.data.response;
    },

    userGet: async (bookId) => {
        const response = await api.get(`/api/v1/book/detail/${bookId}`);
        return response.data.response;
    },

    getBestsellers: async () => {
        const response = await api.get('/api/v1/book/bestsellers');
        return response.data.response;
    },

    getNewBooks: async () => {
        const response = await api.get('/api/v1/book/newBooks');
        return response.data.response;
    },

    search: async (searchType, searchText, page) => {
        const response = await api.get(`/api/v1/book/search?searchType=${searchType}&searchText=${searchText}&page=${page}`);
        return response.data.response;
    },

}
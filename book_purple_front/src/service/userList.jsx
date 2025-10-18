import api from '../api/axiosApi';

export const userApi = {

    list: async (page) => {
        const response = await api.get(`/api/v1/admin/user?page=${page}`);
        return response.data.response;
    },

    register: async (json) => {
        const response = await api.post('/api/v1/register', json);
        return response.data.response;
    },

    withdraw: async (userId) => {
        const response = await api.delete(`/api/v1/withdraw/${userId}`);
        return response.data.response;
    }

}
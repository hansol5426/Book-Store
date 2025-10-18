import api from '../api/axiosApi';

export const stockApi = {

    list: async (page) => {
        const response = await api.get(`/api/v1/admin/stock?page=${page}`);
        return response.data.response;
    },

    get: async (stockId) => {
        const response = await api.get(`/api/v1/admin/stock/${stockId}`);
        return response.data.response;
    }, 

    update: async (json) => {
        const response = await api.put('/api/v1/admin/stock',json);
        return response.data.response;
    }
    
}
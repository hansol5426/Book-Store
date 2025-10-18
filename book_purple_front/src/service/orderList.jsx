import api from '../api/axiosApi';

export const orderApi = {

    get: async () => {
        const response = await api.get('/api/v1/order');
        return response.data.response;
    },

    cart: async (json) => {
        const response = await api.post('/api/v1/order/cart', json);
        return response.data.response;
    },

    direct: async (json) => {
        const response = await api.post('/api/v1/order/direct', json);
        return response.data.response;
    }

}
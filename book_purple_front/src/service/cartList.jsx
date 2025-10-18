import api from '../api/axiosApi';

export const cartApi = {

    get: async () => {
        const response = await api.get('/api/v1/cart');
        return response.data.response; 
    },

    add: async (json) => {
        const response = await api.post('/api/v1/cart', json);
        return response.data.response; 
    },

    delete: async (citemIds) => {
        const response = await api.delete('/api/v1/cart', {data:citemIds});
        return response.data.response; 
    },
    
}
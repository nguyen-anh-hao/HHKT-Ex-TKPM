import axios from 'axios';
import Cookies from 'js-cookie';

const savedLocale = Cookies.get('NEXT_LOCALE') || 'en';

const api = axios.create({
    baseURL: 'http://localhost:9000/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
        'Accept-Language': savedLocale,
    },
});

export default api;
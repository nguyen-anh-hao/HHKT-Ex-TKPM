import axios, { AxiosError, AxiosInstance } from 'axios';
import Cookies from 'js-cookie';

// Constants
const API_BASE_URL = 'http://localhost:9000/api';
const REQUEST_TIMEOUT = 10000; // 10 seconds

/**
 * Creates a configured Axios instance for API requests
 * @returns A configured Axios instance
 */
const createApiClient = (): AxiosInstance => {
  const savedLocale = Cookies.get('NEXT_LOCALE') || 'vi';

  const instance = axios.create({
    baseURL: API_BASE_URL,
    timeout: REQUEST_TIMEOUT,
    headers: {
      'Content-Type': 'application/json',
      'Accept-Language': savedLocale,
    },
  });

  // Request interceptor
  instance.interceptors.request.use(
    (config) => {
      // You could add auth tokens here if needed
      return config;
    },
    (error) => Promise.reject(error)
  );

  // Response interceptor with improved error categorization
  instance.interceptors.response.use(
    (response) => response,
    (error: AxiosError) => {
      // Global error handling with better categorization
      if (!error.response) {
        // Network errors (no response from server)
        console.error('Network error:', error.message);
      } else if (error.response.status === 401) {
        // Authentication errors
        console.error('Authentication error:', error.response.data);
      } else if (error.response.status === 403) {
        // Authorization errors
        console.error('Authorization error:', error.response.data);
      } else if (error.response.status === 404) {
        // Resource not found
        console.error('Resource not found:', error.response.data);
      } else if (error.response.status >= 500) {
        // Server errors
        console.error('Server error:', error.response.data);
      } else {
        // Other client errors
        console.error(`Error ${error.response.status}:`, error.response.data);
      }
      
      return Promise.reject(error);
    }
  );

  return instance;
};

const api = createApiClient();

export default api;
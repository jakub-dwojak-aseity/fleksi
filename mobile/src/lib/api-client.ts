import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Constants from 'expo-constants';

const API_BASE_URL = Constants.expoConfig?.extra?.apiUrl || 'http://localhost:8080/api/v1';

class ApiClient {
  private instance: AxiosInstance;
  private authToken: string | null = null;

  constructor() {
    this.instance = axios.create({
      baseURL: API_BASE_URL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    this.setupInterceptors();
    this.loadAuthToken();
  }

  private setupInterceptors() {
    // Request interceptor to add auth token
    this.instance.interceptors.request.use(
      async (config) => {
        if (this.authToken) {
          config.headers.Authorization = `Bearer ${this.authToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    // Response interceptor for global error handling
    this.instance.interceptors.response.use(
      (response) => response,
      async (error) => {
        if (error.response?.status === 401) {
          await this.clearAuthToken();
          // Optionally trigger navigation to login screen
        }
        return Promise.reject(error);
      }
    );
  }

  private async loadAuthToken() {
    try {
      const token = await AsyncStorage.getItem('authToken');
      if (token) {
        this.authToken = token;
      }
    } catch (error) {
      console.error('Failed to load auth token:', error);
    }
  }

  async setAuthToken(token: string) {
    this.authToken = token;
    try {
      await AsyncStorage.setItem('authToken', token);
    } catch (error) {
      console.error('Failed to save auth token:', error);
    }
  }

  async clearAuthToken() {
    this.authToken = null;
    try {
      await AsyncStorage.removeItem('authToken');
    } catch (error) {
      console.error('Failed to clear auth token:', error);
    }
  }

  getInstance(): AxiosInstance {
    return this.instance;
  }

  // Helper methods for common HTTP operations
  async get<T>(url: string, config?: AxiosRequestConfig) {
    const response = await this.instance.get<T>(url, config);
    return response.data;
  }

  async post<T>(url: string, data?: any, config?: AxiosRequestConfig) {
    const response = await this.instance.post<T>(url, data, config);
    return response.data;
  }

  async put<T>(url: string, data?: any, config?: AxiosRequestConfig) {
    const response = await this.instance.put<T>(url, data, config);
    return response.data;
  }

  async delete<T>(url: string, config?: AxiosRequestConfig) {
    const response = await this.instance.delete<T>(url, config);
    return response.data;
  }
}

export const apiClient = new ApiClient();
export default ApiClient;
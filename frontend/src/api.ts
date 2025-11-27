import axios from "axios";

const API_BASE = import.meta.env.VITE_API_BASE || "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE,
  headers: { "Content-Type": "application/json" },
});

// Add Authorization header to all requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("authToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Get current user ID from localStorage
const getCurrentUserId = () => localStorage.getItem("userId") || "";

// ------------ Auth ------------
export const login = (email: string, password: string) =>
  api.post("/auth/login", { email, password });

// ------------ Products ------------
export const getProducts = () => api.get("/products");
export const getProduct = (id: string) => api.get(`/products/${id}`);

export const createProduct = (payload: {
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
}) => api.post("/products", payload);

export const updateProduct = (
  id: string,
  payload: { name: string; description: string; price: number; stockQuantity: number }
) => api.put(`/products/${id}`, payload);

export const deleteProduct = (id: string) => api.delete(`/products/${id}`);

// ------------ Cart ------------
export const getCart = () => api.get(`/cart/${getCurrentUserId()}`);

export const addToCart = (productId: string, quantity: number = 1) =>
  api.post("/cart", {
    userId: getCurrentUserId(),
    productId,
    quantity,
  });

export const updateCartItem = (itemId: string, quantity: number) =>
  api.put(`/cart/${getCurrentUserId()}/item/${itemId}`, { quantity });

export const deleteCartItem = (itemId: string) =>
  api.delete(`/cart/${getCurrentUserId()}/item/${itemId}`);

// ------------ Orders ------------
export const placeOrder = () =>
  api.post(`/orders/${getCurrentUserId()}`);

export const getOrders = () => api.get(`/orders/${getCurrentUserId()}`);

export default api;

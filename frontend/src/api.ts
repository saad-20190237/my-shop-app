import axios from "axios";

const API_BASE = import.meta.env.VITE_API_BASE || "http://localhost:8080/api";


const CURRENT_USER_ID = "11111111-1111-1111-1111-111111111111";

const api = axios.create({
  baseURL: API_BASE,
  headers: { "Content-Type": "application/json" },
});

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
export const getCart = () => api.get(`/cart/${CURRENT_USER_ID}`);

export const addToCart = (productId: string, quantity: number = 1) =>
  api.post("/cart", {
    userId: CURRENT_USER_ID,
    productId,
    quantity,
  });

export const updateCartItem = (itemId: string, quantity: number) =>
  api.put(`/cart/${CURRENT_USER_ID}/item/${itemId}`, { quantity });

export const deleteCartItem = (itemId: string) =>
  api.delete(`/cart/${CURRENT_USER_ID}/item/${itemId}`);

// ------------ Orders ------------
export const placeOrder = () =>
  api.post(`/orders/${CURRENT_USER_ID}`);


export const getOrders = () => api.get(`/orders/${CURRENT_USER_ID}`);

export default api;

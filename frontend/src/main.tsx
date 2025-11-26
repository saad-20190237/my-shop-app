import React from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import App from "./App";
import CartPage from "./pages/CartPage";
import OrdersPage from "./pages/OrdersPage";
import AdminAddProduct from "./pages/AdminAddProduct";
import "./index.css";

createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BrowserRouter>
      <header className="app-nav">
        <div className="brand">
          <Link to="/" style={{ color: "inherit", textDecoration: "none" }}>
            MyShop
          </Link>
        </div>

        <Link to="/">Products</Link>
        <Link to="/cart">Cart</Link>
        <Link to="/orders">My Orders</Link>

        <Link to="/admin" style={{ marginLeft: "16px" }}>
          Admin
        </Link>
      </header>

      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/orders" element={<OrdersPage />} />

        {/* create new product */}
        <Route path="/admin" element={<AdminAddProduct />} />
        {/* edit existing product */}
        <Route path="/admin/:id" element={<AdminAddProduct />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

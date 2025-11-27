// src/main.tsx
import React from "react";
import { createRoot } from "react-dom/client";
import {
  BrowserRouter,
  Routes,
  Route,
  Link,
  useNavigate,
} from "react-router-dom";
import App from "./App";
import CartPage from "./pages/CartPage";
import OrdersPage from "./pages/OrdersPage";
import AdminAddProduct from "./pages/AdminAddProduct";
import LoginPage from "./pages/LoginPage";
import ProtectedRoute from "./components/ProtectedRoute";
import "./index.css";

function MainLayout({ children }: { children: React.ReactNode }) {
  const navigate = useNavigate();

  // نقرأ القيم من localStorage كل رندر
  const token = localStorage.getItem("authToken");
  const userRole = localStorage.getItem("userRole");
  const isLoggedIn = !!token;

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("userId");
    localStorage.removeItem("userRole");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("userName");
    navigate("/login");
  };

  return (
    <>
      <header className="app-nav">
        <div className="brand">
          <Link to="/" style={{ color: "inherit", textDecoration: "none" }}>
            MyShop
          </Link>
        </div>

        {isLoggedIn ? (
          <>
            <Link to="/">Products</Link>
            <Link to="/cart">Cart</Link>
            <Link to="/orders">My Orders</Link>

            {userRole === "ADMIN" && (
              <Link to="/admin" style={{ marginLeft: "16px" }}>
                Admin
              </Link>
            )}

            <button
              onClick={handleLogout}
              style={{
                marginLeft: "auto",
                padding: "8px 12px",
                backgroundColor: "#ef4444",
                color: "white",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              Logout
            </button>
          </>
        ) : (
          <Link to="/login" style={{ marginLeft: "auto" }}>
            Login
          </Link>
        )}
      </header>

      {children}
    </>
  );
}

createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BrowserRouter>
      <MainLayout>
        <Routes>
          <Route path="/login" element={<LoginPage />} />

          {/* User / common routes */}
          <Route path="/" element={<ProtectedRoute element={<App />} />} />
          <Route
            path="/cart"
            element={<ProtectedRoute element={<CartPage />} />}
          />
          <Route
            path="/orders"
            element={<ProtectedRoute element={<OrdersPage />} />}
          />

          {/* Admin routes - only accessible to ADMIN role */}
          <Route
            path="/admin"
            element={
              <ProtectedRoute
                element={<AdminAddProduct />}
                requiredRole="ADMIN"
              />
            }
          />
          <Route
            path="/admin/:id"
            element={
              <ProtectedRoute
                element={<AdminAddProduct />}
                requiredRole="ADMIN"
              />
            }
          />
        </Routes>
      </MainLayout>
    </BrowserRouter>
  </React.StrictMode>
);

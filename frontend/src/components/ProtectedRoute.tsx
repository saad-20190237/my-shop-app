// src/components/ProtectedRoute.tsx
import React from "react";
import { Navigate } from "react-router-dom";

interface ProtectedRouteProps {
  element: React.ReactElement;
  requiredRole?: "ADMIN" | "USER";
}

export default function ProtectedRoute({
  element,
  requiredRole,
}: ProtectedRouteProps) {
  const token = localStorage.getItem("authToken");
  const userRole = localStorage.getItem("userRole") as
    | "ADMIN"
    | "USER"
    | null;

  // Not logged in
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Check role if required
  if (requiredRole && userRole !== requiredRole) {
    return (
      <div className="page">
        <h2>Access Denied</h2>
        <p>You don't have permission to access this page.</p>
      </div>
    );
  }

  return element;
}

import React, { useEffect, useState } from "react";
import { getOrders } from "../api";

const USER_ID = 1;

export default function OrdersPage() {
  const [orders, setOrders] = useState<any[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    getOrders(USER_ID)
      .then((r) => setOrders(r.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="orders-page">
      <h1 className="orders-title">My Orders</h1>

      {loading && <div>Loading...</div>}

      {!loading && orders.length === 0 && (
        <div className="orders-empty">You don’t have any orders yet.</div>
      )}

      {!loading && orders.length > 0 && (
        <div className="orders-list">
          {orders.map((o) => (
            <div key={o.id} className="order-card">
              {/* Header */}
              <div className="order-header">
                <div className="order-main-info">
                  <span className="order-id">
                    Order #{String(o.id).substring(0, 8)}
                  </span>
                  <span className="order-date">
                    {new Date(o.createdAt).toLocaleString()}
                  </span>
                </div>
                <span
                  className={`order-status order-status-${String(
                    o.status || "NEW"
                  ).toLowerCase()}`}
                >
                  {o.status || "NEW"}
                </span>
              </div>

              {/* Meta */}
              <div className="order-meta">
                <div>
                  <span className="order-meta-label">Payment:</span>{" "}
                  <span>{o.paymentMethod || "Cash on Delivery"}</span>
                </div>
                <div>
                  <span className="order-meta-label">Total:</span>{" "}
                  <span className="order-total">${o.totalAmount}</span>
                </div>
              </div>

              {/* Items */}
              <div className="order-items">
                <div className="order-items-title">Items</div>
                <table className="order-items-table">
                  <thead>
                    <tr>
                      <th>Product</th>
                      <th>Qty</th>
                      <th>Price</th>
                      <th>Line total</th>
                    </tr>
                  </thead>
                  <tbody>
                    {o.items.map((it: any) => (
                      <tr key={it.productId}>
                        <td>{it.productName}</td>
                        <td>{it.quantity}</td>
                        <td>${it.unitPrice}</td>
                        <td>${it.lineTotal}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

import React, { useEffect, useState } from "react";
import { getCart, updateCartItem, deleteCartItem, placeOrder } from "../api";

type CartItem = {
  id: string;
  productId: string;
  productName: string;
  unitPrice: number;
  quantity: number;
  lineTotal: number;
};

export default function CartPage() {
  const [items, setItems] = useState<CartItem[]>([]);
  const [loading, setLoading] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      const r = await getCart(); // 👈 مبقاش نبعت USER_ID
      setItems(r.data);
    } catch (e) {
      console.error(e);
    }
    setLoading(false);
  };

  useEffect(() => {
    load();
  }, []);

  const changeQty = async (itemId: string, qty: number) => {
    if (qty <= 0) return; 
    try {
      await updateCartItem(itemId, qty); 
      await load();
    } catch (e) {
      console.error(e);
      alert("Update failed");
    }
  };

  const remove = async (itemId: string) => {
    if (!confirm("Remove item?")) return;
    try {
      await deleteCartItem(itemId); 
      await load();
    } catch (e) {
      console.error(e);
      alert("Delete failed");
    }
  };

  const buyCOD = async () => {
    if (items.length === 0) return alert("Cart is empty");
    try {
      await placeOrder(); 
      alert("Order placed (Cash on Delivery)");
      await load();
    } catch (e) {
      console.error(e);
      alert("Order failed");
    }
  };

  if (loading) return <div className="page">Loading cart...</div>;

  return (
    <div className="page">
      <h2>Your Cart</h2>

      {items.length === 0 ? (
        <div>No items in cart</div>
      ) : (
        <div className="cart-list">
          {items.map((it) => (
            <div key={it.id} className="cart-item">
              <div className="left">
                <div>
                  <strong>{it.productName}</strong>
                  <div style={{ color: "#475569" }}>
                    Price: ${it.unitPrice}
                  </div>
                  <div style={{ color: "#64748b" }}>
                    Item total: ${it.lineTotal}
                  </div>
                </div>
              </div>

              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  gap: 12,
                }}
              >
                <div className="qty-controls">
                  <button
                    className="btn"
                    onClick={() => changeQty(it.id, it.quantity - 1)}
                  >
                    -
                  </button>
                  <span>{it.quantity}</span>
                  <button
                    className="btn"
                    onClick={() => changeQty(it.id, it.quantity + 1)}
                  >
                    +
                  </button>
                </div>

                <button
                  className="btn btn-ghost"
                  onClick={() => remove(it.id)}
                >
                  Remove
                </button>
              </div>
            </div>
          ))}

          <div style={{ marginTop: 12 }}>
            <button className="btn btn-primary" onClick={buyCOD}>
              Buy (Pay on Delivery)
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

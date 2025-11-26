import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { createProduct, getProduct, updateProduct, deleteProduct } from "../api";

type FormState = {
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
};

export default function AdminAddProduct() {
  const { id } = useParams();            
  const isEdit = Boolean(id);
  const navigate = useNavigate();        

  const [form, setForm] = useState<FormState>({
    name: "",
    description: "",
    price: 0,
    stockQuantity: 0,
  });

  const [loading, setLoading] = useState(false);

  
  useEffect(() => {
    if (!isEdit || !id) return;

    setLoading(true);
    getProduct(id)
      .then(res => {
        const p = res.data;
        setForm({
          name: p.name,
          description: p.description ?? "",
          price: p.price,
          stockQuantity: p.stockQuantity,
        });
      })
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [isEdit, id]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: name === "price" || name === "stockQuantity" ? Number(value) : value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      if (isEdit && id) {
        await updateProduct(id, form);
        alert("Product updated successfully!");
      } else {
        await createProduct(form);
        alert("Product created successfully!");
        setForm({ name: "", description: "", price: 0, stockQuantity: 0 });
      }
    } catch (err) {
      console.error(err);
      alert("Failed to save product");
    }
  };

  const handleDelete = async () => {
    if (!id) return;
    if (!confirm("Are you sure you want to delete this product?")) return;

    try {
      await deleteProduct(id);
      alert("Product deleted successfully!");
      navigate("/");      // رجّعه لصفحة المنتجات
    } catch (err) {
      console.error(err);
      alert("Failed to delete product");
    }
  };

  if (loading) return <div className="page">Loading...</div>;

  return (
    <div className="page admin-page">
      <h2 className="page-title">
        {isEdit ? "Edit Product" : "Add New Product"}
      </h2>

      <form onSubmit={handleSubmit} className="admin-card">
        <div className="field">
          <label>Product Name</label>
          <input
            name="name"
            placeholder="e.g. Laptop"
            value={form.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="field">
          <label>Description</label>
          <textarea
            name="description"
            placeholder="Write product details..."
            value={form.description}
            onChange={handleChange}
            rows={4}
          />
        </div>

        <div className="field">
          <label>Price</label>
          <input
            name="price"
            type="number"
            min={0}
            step="0.01"
            value={form.price}
            onChange={handleChange}
            required
          />
        </div>

        <div className="field">
          <label>Stock Quantity</label>
          <input
            name="stockQuantity"
            type="number"
            min={0}
            value={form.stockQuantity}
            onChange={handleChange}
            required
          />
        </div>

        <div style={{display:"flex", gap:12, marginTop:8}}>
          <button className="btn btn-primary admin-btn" type="submit">
            {isEdit ? "Update Product" : "Create Product"}
          </button>

          {isEdit && (
            <button
              type="button"
              className="btn btn-ghost"
              style={{color:"#ef4444", borderColor:"#ef4444"}}
              onClick={handleDelete}
            >
              Delete
            </button>
          )}
        </div>
      </form>
    </div>
  );
}

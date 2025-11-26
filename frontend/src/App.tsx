import React, { useEffect, useState } from "react";
import { getProducts, addToCart } from "./api";
import { useNavigate } from "react-router-dom";

type Product = { 
  id: string;                  
  name: string; 
  description?: string; 
  price: number; 
  stockQuantity: number;      
};



export default function App(){
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();   

  useEffect(()=>{
    setLoading(true);
    getProducts()
      .then(r => setProducts(r.data))
      .catch(console.error)
      .finally(()=>setLoading(false));
  }, []);

const handleAdd = async (p: Product) => {
  if (p.stockQuantity <= 0) return alert("Out of stock");

  try {
    await addToCart(p.id, 1);
    alert("Added to cart ✅");
  } catch (e: any) {
    console.error(e);
    const msg =
      e?.response?.data ||
      e?.message ||
      "Failed to add to cart";

    alert(msg);
  }
};



  if(loading) return <div className="page">Loading...</div>;

  return (
    <div className="page">
      <h2>Products</h2>

      <div className="product-grid" style={{marginTop:12}}>
        {products.map(p => {
          const inStock = p.stockQuantity > 0;

          return (
            <div key={p.id} className="product-card">
              <h3>{p.name}</h3>
              <p className="description">{p.description ?? 'No description'}</p>

              <div className="meta">
                <div>
                  <div>
                    Price: <strong>${p.price}</strong>
                  </div>

                  <div style={{color: inStock ? '#0b5fff' : '#ef4444'}}>
                    Stock: {p.stockQuantity}
                  </div>
                </div>

                <div style={{display:'flex',flexDirection:'column',justifyContent:'center',gap:8}}>
                  <button
                    className="btn btn-primary"
                    onClick={()=>handleAdd(p)}
                    disabled={!inStock}
                  >
                    Add
                  </button>

                  
                  <button
                    className="btn btn-ghost"
                    onClick={()=>navigate(`/admin/${p.id}`)}
                  >
                    Details
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

from datetime import date, timedelta

def them_du_lieu(db):
  
    print("\n--- 2. Thêm dữ liệu mẫu ---")
    try:
        db.products.delete_many({})
        db.orders.delete_many({})

        products = [
            {"product_id": "SP001", "name": "Áo thun cao cấp", "price": 250000.0, "stock": 50},
            {"product_id": "SP002", "name": "Quần Jean rách", "price": 450000.0, "stock": 30},
            {"product_id": "SP003", "name": "Giày thể thao", "price": 1200000.0, "stock": 15},
            {"product_id": "SP004", "name": "Mũ lưỡi trai", "price": 150000.0, "stock": 40},
            {"product_id": "SP005", "name": "Kính râm thời trang", "price": 350000.0, "stock": 8},
        ]
        db.products.insert_many(products)
        print(f"Đã thêm {len(products)} sản phẩm.")

        today = date.today()
        orders = [
            {"order_id": "DH001", "customer_name": "Nguyễn Văn An", "product_id": "SP001", "quantity": 2, "total_price": 500000.0, "order_date": (today - timedelta(days=5)).isoformat()},
            {"order_id": "DH002", "customer_name": "Trần Thị Bích", "product_id": "SP003", "quantity": 1, "total_price": 1200000.0, "order_date": (today - timedelta(days=4)).isoformat()},
            {"order_id": "DH003", "customer_name": "Lê Minh Tuấn", "product_id": "SP002", "quantity": 1, "total_price": 450000.0, "order_date": (today - timedelta(days=4)).isoformat()},
            {"order_id": "DH004", "customer_name": "Nguyễn Văn An", "product_id": "SP004", "quantity": 1, "total_price": 150000.0, "order_date": (today - timedelta(days=3)).isoformat()},
            {"order_id": "DH005", "customer_name": "Phạm Thị Lan", "product_id": "SP005", "quantity": 2, "total_price": 700000.0, "order_date": (today - timedelta(days=3)).isoformat()},
            {"order_id": "DH006", "customer_name": "Trần Thị Bích", "product_id": "SP001", "quantity": 3, "total_price": 750000.0, "order_date": (today - timedelta(days=2)).isoformat()},
            {"order_id": "DH007", "customer_name": "Nguyễn Văn An", "product_id": "SP002", "quantity": 1, "total_price": 450000.0, "order_date": (today - timedelta(days=2)).isoformat()},
            {"order_id": "DH008", "customer_name": "Lê Minh Tuấn", "product_id": "SP005", "quantity": 1, "total_price": 350000.0, "order_date": (today - timedelta(days=1)).isoformat()},
            {"order_id": "DH009", "customer_name": "Trần Thị Bích", "product_id": "SP004", "quantity": 1, "total_price": 150000.0, "order_date": today.isoformat()},
            {"order_id": "DH010", "customer_name": "Nguyễn Văn An", "product_id": "SP003", "quantity": 1, "total_price": 1200000.0, "order_date": today.isoformat()},
            {"order_id": "DH011", "customer_name": "Phạm Thị Lan", "product_id": "SP001", "quantity": 1, "total_price": 250000.0, "order_date": today.isoformat()},
            {"order_id": "DH012", "customer_name": "Nguyễn Văn An", "product_id": "SP004", "quantity": 1, "total_price": 80000.0, "order_date": today.isoformat()},
        ]
        result = db.orders.insert_many(orders)
        print(f"Đã thêm {len(result.inserted_ids)} đơn hàng.")
    except Exception as e:
        print(f"Đã xảy ra lỗi khi thêm dữ liệu: {e}")

def truy_van_don_hang(db, customer_name):
  
    print(f"\n--- 3. Truy vấn đơn hàng của '{customer_name}' và đơn hàng giá trị cao ---")
    
    print(f"Đơn hàng của {customer_name}:")
    customer_orders = db.orders.find({"customer_name": {"$eq": customer_name}}).sort("total_price", -1).limit(5)
    count = 0
    for order in customer_orders:
        print(f"\t- Mã đơn: {order['order_id']}, Sản phẩm: {order['product_id']}, Tổng: {order['total_price']:,.0f} VNĐ")
        count += 1
    if count == 0:
        print("\tKhông tìm thấy đơn hàng nào.")

    print("\nCác đơn hàng có giá trị trên 500,000 VNĐ:")
    high_value_orders = db.orders.find({"total_price": {"$gt": 500000}}).sort("total_price", -1).limit(5)
    count = 0
    for order in high_value_orders:
        print(f"\t- Mã đơn: {order['order_id']}, Khách hàng: {order['customer_name']}, Tổng: {order['total_price']:,.0f} VNĐ")
        count += 1
    if count == 0:
        print("\tKhông tìm thấy đơn hàng nào.")

def cap_nhat_don_hang(db, order_id_to_update, new_quantity):
 
    print(f"\n--- 4. Cập nhật đơn hàng {order_id_to_update} ---")
    try:
        order = db.orders.find_one({"order_id": order_id_to_update})
        if not order:
            print(f"Không tìm thấy đơn hàng có mã '{order_id_to_update}'.")
            return

        product = db.products.find_one({"product_id": order["product_id"]})
        if not product:
            print(f"Không tìm thấy sản phẩm có mã '{order['product_id']}'.")
            return

        new_total_price = product["price"] * new_quantity
        result = db.orders.update_one(
            {"order_id": order_id_to_update},
            {"$set": {"quantity": new_quantity, "total_price": new_total_price}}
        )

        if result.modified_count > 0:
            print(f"Đã cập nhật đơn hàng '{order_id_to_update}'.")
        else:
            print("Không có thay đổi nào được thực hiện.")
    except Exception as e:
        print(f"Đã xảy ra lỗi khi cập nhật đơn hàng: {e}")

def xoa_don_hang(db):
 
    print("\n--- 5. Xóa các đơn hàng giá trị thấp ---")
    try:
        query = {"total_price": {"$lt": 100000}}
        result = db.orders.delete_many(query)
        print(f"Đã xóa {result.deleted_count} đơn hàng có giá trị dưới 100,000 VNĐ.")
    except Exception as e:
        print(f"Đã xảy ra lỗi khi xóa đơn hàng: {e}")

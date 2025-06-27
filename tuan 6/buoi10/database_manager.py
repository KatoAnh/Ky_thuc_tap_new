import pymongo
from pymongo import MongoClient

def ket_noi_db(mongo_uri, db_name):

    try:
        client = MongoClient(mongo_uri, serverSelectionTimeoutMS=5000)
        client.server_info() 
        print("Kết nối MongoDB thành công!")
        return client[db_name]
    except pymongo.errors.ServerSelectionTimeoutError as err:
        print(f"Lỗi: Không thể kết nối đến server MongoDB. Vui lòng kiểm tra lại.\n{err}")
        exit()

def thiet_lap_database(db):
   
    print("\n--- 1. Thiết lập cơ sở dữ liệu và collection ---")
    try:
        if "products" not in db.list_collection_names():
            db.create_collection("products")
            print("Collection 'products' đã được tạo.")
        else:
            print("Collection 'products' đã tồn tại.")

        if "orders" not in db.list_collection_names():
            db.create_collection("orders")
            print("Collection 'orders' đã được tạo.")
        else:
            print("Collection 'orders' đã tồn tại.")
    except Exception as e:
        print(f"Đã xảy ra lỗi khi thiết lập database: {e}")

def don_dep_database(db):
 
    print("\n--- 7. Dọn dẹp cơ sở dữ liệu ---")
    try:
        if "orders" in db.list_collection_names():
            db.orders.drop()
            print("Đã xóa collection 'orders'.")
        if "products" in db.list_collection_names():
            db.products.drop()
            print("Đã xóa collection 'products'.")
    except Exception as e:
        print(f"Đã xảy ra lỗi khi dọn dẹp: {e}")

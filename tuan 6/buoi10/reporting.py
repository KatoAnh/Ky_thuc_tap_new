
def tao_bao_cao(db):
   
    print("\n--- 6. Tạo báo cáo cửa hàng ---")
    try:
        pipeline = [
            {
                "$group": {
                    "_id": "$product_id",
                    "total_revenue": {"$sum": "$total_price"}
                }
            },
            {
                "$sort": {"total_revenue": -1}
            }
        ]
        revenue_report = list(db.orders.aggregate(pipeline))
        print("Báo cáo doanh thu theo sản phẩm:")
        if not revenue_report:
            print("\tKhông có dữ liệu doanh thu.")
        else:
            for item in revenue_report:
                print(f"\t- Sản phẩm {item['_id']}: Doanh thu {item['total_revenue']:,.0f} VNĐ")
        
        low_stock_count = db.products.count_documents({"stock": {"$lt": 10}})
        print(f"\nThống kê sản phẩm sắp hết hàng (dưới 10):")
        print(f"\t- Số lượng sản phẩm tồn kho thấp: {low_stock_count} sản phẩm.")

    except Exception as e:
        print(f"Đã xảy ra lỗi khi tạo báo cáo: {e}")

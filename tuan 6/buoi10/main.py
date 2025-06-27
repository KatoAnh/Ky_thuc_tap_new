
import config
import database_manager
import order_manager
import reporting

def main():

    # Kết nối tới DB
    db = database_manager.ket_noi_db(config.MONGO_URI, config.DB_NAME)

    database_manager.thiet_lap_database(db)

    order_manager.them_du_lieu(db)
    
    order_manager.truy_van_don_hang(db, "Nguyễn Văn An")

    order_manager.cap_nhat_don_hang(db, "DH006", 2)

    order_manager.xoa_don_hang(db)

    reporting.tao_bao_cao(db)
   

    print("\nChương trình đã thực thi xong.")


if __name__ == "__main__":
    main()

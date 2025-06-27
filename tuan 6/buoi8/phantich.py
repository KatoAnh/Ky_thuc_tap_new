import numpy as np
from scipy import stats
import matplotlib.pyplot as plt

def pt_coban(data):
    print("\n--- PHÂN TÍCH CƠ BẢN ---")
    for i, dl_tuan in enumerate(data):
        gio = dl_tuan[:, 0]
        nv = dl_tuan[:, 1]
        
        print(f"\n Tuần {i + 1}:")
        print(f"- Giờ làm trung bình: {np.mean(gio):.2f}")
        print(f"- Độ lệch chuẩn giờ: {np.std(gio):.2f}")
        print(f"- Tổng nhiệm vụ: {int(np.sum(nv))}")
        print(f"- NV xuất sắc: TV {np.argmax(nv) + 1} ({int(np.max(nv))} NV)")

def pt_nangcao(data):
    print("\n--- PHÂN TÍCH NÂNG CAO ---")
    
    tat_ca_gio = data[:, :, 0].flatten()
    tat_ca_nv = data[:, :, 1].flatten()
    
    ket_qua_hoiquy = stats.linregress(tat_ca_gio, tat_ca_nv)
    
    nguong = 2 * np.std(tat_ca_gio)
    gio_tb = np.mean(tat_ca_gio)
    ngoai_lai = tat_ca_gio[(tat_ca_gio < gio_tb - nguong) | (tat_ca_gio > gio_tb + nguong)]
    
    print("\n Hồi quy (Giờ làm & Nhiệm vụ):")
    print(f"- Độ dốc: {ket_qua_hoiquy.slope:.4f}")
    print(f"- Tương quan: {ket_qua_hoiquy.rvalue:.4f}")
    
    if ngoai_lai.size > 0:
        print(f"- Giá trị ngoại lai (giờ làm): {np.round(ngoai_lai, 2)}")
    
    return ket_qua_hoiquy

def ve_bieu_do(data):
    print("\n--- ĐANG VẼ BIỂU ĐỒ ---")
    
    tat_ca_gio = data[:, :, 0].flatten()
    tat_ca_nv = data[:, :, 1].flatten()

    plt.figure(figsize=(9, 6))
    plt.scatter(tat_ca_gio, tat_ca_nv, alpha=0.7, label='Dữ liệu thực tế')
    
    kq_hoiquy = stats.linregress(tat_ca_gio, tat_ca_nv)
    plt.plot(tat_ca_gio, kq_hoiquy.intercept + kq_hoiquy.slope*tat_ca_gio, 'r', label='Đường hồi quy')

    plt.title('Mối quan hệ giữa Giờ làm và Số nhiệm vụ hoàn thành')
    plt.xlabel('Số giờ làm việc')
    plt.ylabel('Số nhiệm vụ hoàn thành')
    plt.grid(True)
    plt.legend()
    
    plt.show()
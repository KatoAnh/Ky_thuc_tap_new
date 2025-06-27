using System.Windows;

namespace Buoi7
{
    public partial class App : Application
    {
        private void Application_DispatcherUnhandledException(object sender, System.Windows.Threading.DispatcherUnhandledExceptionEventArgs e)
        {
         
            string errorMessage = $"Đã xảy ra một lỗi nghiêm trọng khiến ứng dụng không thể khởi động:\n\n" +
                                  $"Chi tiết lỗi: {e.Exception.Message}\n\n" +
                                  $"Gợi ý: Vui lòng thử xóa tệp 'TodoDatabase.db' trong thư mục dự án và chạy lại.";

            MessageBox.Show(errorMessage, "Lỗi Khởi Động Nghiêm Trọng", MessageBoxButton.OK, MessageBoxImage.Error);

            e.Handled = true;

            Current.Shutdown();
        }
    }
}

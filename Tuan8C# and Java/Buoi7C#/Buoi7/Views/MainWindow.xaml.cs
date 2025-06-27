using System.Windows;
using Buoi7.ViewModels;

namespace Buoi7.Views
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private async void MainWindow_OnLoaded(object sender, RoutedEventArgs e)
        {
            if (DataContext is TodoViewModel viewModel)
            {
                await viewModel.LoadDataAsync();
            }
        }
    }
}

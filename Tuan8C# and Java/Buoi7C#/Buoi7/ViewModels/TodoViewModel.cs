using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Data;
using System.Windows.Input;
using Buoi7.Data;
using Buoi7.Models;
using Microsoft.EntityFrameworkCore;

namespace Buoi7.ViewModels
{
    public class TodoViewModel : BaseViewModel
    {
        private AppDbContext _context;
        private string _newTodoTitle = "";
        private DateTime _newTodoDeadline = DateTime.Today;
        private TodoItem? _selectedTodo;
        private string _searchText = "";
        private string _selectedFilter = "Tất cả";

        public ObservableCollection<TodoItem> AllTodos { get; set; }
        public ICollectionView FilteredTodos { get; private set; }

        public string NewTodoTitle { get => _newTodoTitle; set { _newTodoTitle = value; OnPropertyChanged(); } }
        public DateTime NewTodoDeadline { get => _newTodoDeadline; set { _newTodoDeadline = value; OnPropertyChanged(); } }
        public TodoItem? SelectedTodo
        {
            get => _selectedTodo;
            set { _selectedTodo = value; if (value != null) { NewTodoTitle = value.Title; NewTodoDeadline = value.Deadline; } OnPropertyChanged(); }
        }
        public string SearchText { get => _searchText; set { _searchText = value; OnPropertyChanged(); FilteredTodos.Refresh(); } }
        public string SelectedFilter { get => _selectedFilter; set { _selectedFilter = value; OnPropertyChanged(); FilteredTodos.Refresh(); } }
        public string[] FilterOptions { get; } = { "Tất cả", "Hoàn thành", "Chưa hoàn thành" };

        public ICommand AddTodoCommand { get; }
        public ICommand UpdateTodoCommand { get; }
        public ICommand DeleteTodoCommand { get; }
        public ICommand ToggleStatusCommand { get; }

        public TodoViewModel()
        {
            AllTodos = new ObservableCollection<TodoItem>();
            FilteredTodos = CollectionViewSource.GetDefaultView(AllTodos);
            FilteredTodos.Filter = FilterItems;

            AddTodoCommand = new RelayCommand(AddTodo, CanAddTodo);
            UpdateTodoCommand = new RelayCommand(UpdateTodo, CanUpdateTodo);
            DeleteTodoCommand = new RelayCommand(DeleteTodo, CanDeleteTodo);
            ToggleStatusCommand = new RelayCommand(ToggleStatus);
        }

        public async Task LoadDataAsync()
        {
            try
            {
                _context = new AppDbContext();
                await _context.Database.EnsureCreatedAsync();
                var items = await _context.TodoItems.ToListAsync();
                AllTodos.Clear();
                foreach (var item in items)
                {
                    AllTodos.Add(item);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Không thể tải dữ liệu từ cơ sở dữ liệu: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private bool FilterItems(object item)
        {
            if (item is not TodoItem todo) return false;
            bool matchesFilter = SelectedFilter switch { "Hoàn thành" => todo.IsCompleted, "Chưa hoàn thành" => !todo.IsCompleted, _ => true, };
            bool matchesSearch = string.IsNullOrWhiteSpace(SearchText) || todo.Title.Contains(SearchText, StringComparison.OrdinalIgnoreCase);
            return matchesFilter && matchesSearch;
        }

        private void AddTodo(object? parameter)
        {
            var newTodo = new TodoItem { Title = NewTodoTitle, Deadline = NewTodoDeadline, IsCompleted = false };
            _context.TodoItems.Add(newTodo);
            _context.SaveChanges();
            AllTodos.Add(newTodo);
            NewTodoTitle = string.Empty;
        }

        private bool CanAddTodo(object? parameter) => !string.IsNullOrWhiteSpace(NewTodoTitle);
        private bool CanUpdateTodo(object? parameter) => SelectedTodo != null && !string.IsNullOrWhiteSpace(NewTodoTitle);
        private bool CanDeleteTodo(object? parameter) => SelectedTodo != null;

        private void UpdateTodo(object? parameter)
        {
            if (SelectedTodo == null) return;
            SelectedTodo.Title = NewTodoTitle;
            SelectedTodo.Deadline = NewTodoDeadline;
            _context.TodoItems.Update(SelectedTodo);
            _context.SaveChanges();
            FilteredTodos.Refresh();
        }

        private void DeleteTodo(object? parameter)
        {
            if (SelectedTodo == null) return;
            _context.TodoItems.Remove(SelectedTodo);
            _context.SaveChanges();
            AllTodos.Remove(SelectedTodo);
        }

        private void ToggleStatus(object? parameter)
        {
            if (parameter is TodoItem todo)
            {
                _context.TodoItems.Update(todo);
                _context.SaveChanges();
            }
        }
    }
}

using Microsoft.EntityFrameworkCore;
using Buoi7.Models;

namespace Buoi7.Data
{
    public class AppDbContext : DbContext
    {
        public DbSet<TodoItem> TodoItems { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite("Data Source=TodoDatabase.db");
        }
    }
}

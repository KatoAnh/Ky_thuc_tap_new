using System;
using System.ComponentModel.DataAnnotations;

namespace Buoi7.Models
{
    public class TodoItem
    {
        [Key]
        public int Id { get; set; }

        [Required]
        [MaxLength(200)]
        public string Title { get; set; } = string.Empty;

        public DateTime Deadline { get; set; }

        public bool IsCompleted { get; set; }
    }
}

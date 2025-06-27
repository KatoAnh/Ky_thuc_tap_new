using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace QuanLyHocVien.Models
{
    public class Enrollment
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public int StudentId { get; set; }

        [Required]
        public int CourseId { get; set; }

        public DateTime EnrollDate { get; set; }

 
        [ForeignKey("StudentId")]
        public virtual Student Student { get; set; } = default!;

        [ForeignKey("CourseId")]
        public virtual Course Course { get; set; } = default!;
    }
}

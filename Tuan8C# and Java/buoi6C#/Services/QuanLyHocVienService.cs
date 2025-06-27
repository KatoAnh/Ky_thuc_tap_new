using Microsoft.EntityFrameworkCore;
using QuanLyHocVien.Data;
using QuanLyHocVien.Models;

namespace QuanLyHocVien.Services
{
    public class QuanLyHocVienService
    {
        private readonly AppDbContext _context;

        public QuanLyHocVienService(AppDbContext context)
        {
            _context = context;
        }

        public void AddStudent(Student student)
        {
            _context.Students.Add(student);
            _context.SaveChanges();
        }

        public void AddCourse(Course course)
        {
            _context.Courses.Add(course);
            _context.SaveChanges();
        }

        public void EnrollStudentInCourse(int studentId, int courseId)
        {
            var enrollment = new Enrollment
            {
                StudentId = studentId,
                CourseId = courseId,
                EnrollDate = DateTime.UtcNow
            };
            _context.Enrollments.Add(enrollment);
            _context.SaveChanges();
        }

        public Student? GetStudentById(int id)
        {
            return _context.Students.Find(id);
        }

        public void UpdateStudent(Student student)
        {
            _context.Students.Update(student);
            _context.SaveChanges();
        }

        public void DeleteStudent(int studentId)
        {
            var student = _context.Students.Find(studentId);
            if (student != null)
            {
                _context.Students.Remove(student);
                _context.SaveChanges();
            }
        }

        public List<Course> GetAllCourses()
        {
            return _context.Courses.AsNoTracking().ToList();
        }

        public List<Student> GetAllStudents()
        {
            return _context.Students.AsNoTracking().ToList();
        }

        public List<Student> GetStudentsInCourse(int courseId)
        {
            return _context.Enrollments
                .Where(e => e.CourseId == courseId)
                .Select(e => e.Student)
                .ToList();
        }
        
        public List<Course> GetCoursesWithMoreThanNStudents(int studentCount)
        {
            return _context.Courses
                .Where(c => c.Enrollments.Count > studentCount)
                .ToList();
        }
        
        public List<Student> GetStudentsFilteredByName(string name)
        {
             return _context.Students
                .Where(s => s.FullName.ToLower().Contains(name.ToLower()))
                .OrderBy(s => s.FullName)
                .ToList();
        }

        public List<Student> GetAllStudentsSortedByName()
        {
            return _context.Students
                .OrderBy(s => s.FullName)
                .ToList();
        }

        public List<Student> GetAllStudentsWithEnrollments()
        {
            return _context.Students
                .Include(s => s.Enrollments)
                .ThenInclude(e => e.Course)
                .ToList();
        }
        
        public List<string> GetCourseTitlesOnly()
        {
            return _context.Courses
                .AsNoTracking()
                .Select(c => c.Title)
                .ToList();
        }
    }
}

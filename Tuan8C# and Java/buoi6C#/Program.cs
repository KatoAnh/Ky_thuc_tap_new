using QuanLyHocVien.Data;
using QuanLyHocVien.Models;
using QuanLyHocVien.Services;
using System.Globalization;

public class Program
{
    private static void Main(string[] args)
    {
        Console.OutputEncoding = System.Text.Encoding.UTF8;
        using var context = new AppDbContext();
        var service = new QuanLyHocVienService(context);

        Console.WriteLine("Đảm bảo CSDL đã được tạo...");
        context.Database.EnsureCreated();
        
        SeedDatabase(context);

        Console.WriteLine("CSDL đã sẵn sàng.");

        while (true)
        {
            Console.WriteLine("\n--- HỆ THỐNG QUẢN LÝ HỌC VIÊN ---");
            Console.WriteLine("1. Thêm học viên mới");
            Console.WriteLine("2. Thêm khóa học mới");
            Console.WriteLine("3. Ghi danh học viên vào khóa học");
            Console.WriteLine("4. Hiển thị DS học viên trong một khóa học");
            Console.WriteLine("5. Hiển thị DS khóa học có nhiều hơn N học viên");
            Console.WriteLine("6. Tìm học viên theo tên");
            Console.WriteLine("7. Hiển thị tất cả học viên và các khóa đã đăng ký (Tối ưu)");
            Console.WriteLine("8. Hiển thị chỉ tên các khóa học (Tối ưu)");
            Console.WriteLine("9. Hiển thị DS tất cả khóa học");
            Console.WriteLine("10. Hiển thị DS tất cả học viên");
            Console.WriteLine("0. Thoát");
            Console.Write("Chọn chức năng: ");

            string? choice = Console.ReadLine();
            switch (choice)
            {
                case "1":
                    AddStudentUI(service);
                    break;
                case "2":
                    AddCourseUI(service);
                    break;
                case "3":
                    EnrollStudentUI(service);
                    break;
                case "4":
                    ShowStudentsInCourseUI(service);
                    break;
                case "5":
                    ShowCoursesWithStudentCountUI(service);
                    break;
                case "6":
                    FindStudentByNameUI(service);
                    break;
                case "7":
                    ShowAllStudentsWithEnrollmentsUI(service);
                    break;
                case "8":
                    ShowCourseTitlesOnlyUI(service);
                    break;
                case "9":
                    ShowAllCoursesUI(service);
                    break;
                case "10":
                    ShowAllStudentsUI(service);
                    break;
                case "0":
                    return;
                default:
                    Console.WriteLine("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }

    private static void SeedDatabase(AppDbContext context)
    {
        if (!context.Students.Any())
        {
            context.Students.AddRange(
                new Student { FullName = "Nguyễn Văn An", Email = "an.nv@example.com", BirthDate = new DateTime(2000, 1, 15) },
                new Student { FullName = "Trần Thị Bình", Email = "binh.tt@example.com", BirthDate = new DateTime(2001, 5, 20) }
            );
        }

        if (!context.Courses.Any())
        {
             context.Courses.AddRange(
                new Course { Title = "Lập trình C# cơ bản", Level = 1, Duration = 40 },
                new Course { Title = "ASP.NET Core Web API", Level = 3, Duration = 60 }
            );
        }
        
        context.SaveChanges();
    }

    private static void AddStudentUI(QuanLyHocVienService service)
    {
        try
        {
            Console.Write("Nhập họ tên học viên: ");
            string name = Console.ReadLine() ?? "";
            if (string.IsNullOrWhiteSpace(name))
            {
                Console.WriteLine("=> Tên không được để trống.");
                return;
            }

            Console.Write("Nhập email: ");
            string email = Console.ReadLine() ?? "";
            if (string.IsNullOrWhiteSpace(email))
            {
                Console.WriteLine("=> Email không được để trống.");
                return;
            }

            Console.Write("Nhập ngày sinh (ví dụ: 2000-12-25): ");
            string dateString = Console.ReadLine() ?? "";
            
            string[] formats = { "yyyy-MM-dd", "yyyy-M-d", "yyyy/MM/dd", "yyyy/M/d", "dd-MM-yyyy", "d-M-yyyy", "dd/MM/yyyy", "d/M/yyyy" };

            if (!DateTime.TryParseExact(dateString, formats, CultureInfo.InvariantCulture, DateTimeStyles.None, out DateTime birthDate))
            {
                Console.WriteLine("=> Định dạng ngày sinh không hợp lệ hoặc không được hỗ trợ.");
                return;
            }

            var newStudent = new Student { FullName = name, Email = email, BirthDate = birthDate };
            service.AddStudent(newStudent);
            Console.WriteLine("=> Thêm học viên thành công!");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Lỗi: {ex.Message}");
            if(ex.InnerException != null)
            {
                Console.WriteLine($"Chi tiết lỗi: {ex.InnerException.Message}");
            }
        }
    }

    private static void AddCourseUI(QuanLyHocVienService service)
    {
        try
        {
            Console.Write("Nhập tên khóa học: ");
            string title = Console.ReadLine() ?? "";
            if (string.IsNullOrWhiteSpace(title))
            {
                Console.WriteLine("=> Tên khóa học không được để trống.");
                return;
            }

            Console.Write("Nhập cấp độ (1-5): ");
            if (!int.TryParse(Console.ReadLine(), out int level))
            {
                Console.WriteLine("=> Cấp độ phải là một con số.");
                return;
            }

            Console.Write("Nhập thời lượng (giờ): ");
            if (!int.TryParse(Console.ReadLine(), out int duration))
            {
                Console.WriteLine("=> Thời lượng phải là một con số.");
                return;
            }
            
            var newCourse = new Course { Title = title, Level = level, Duration = duration };
            service.AddCourse(newCourse);
            Console.WriteLine("=> Thêm khóa học thành công!");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Lỗi: {ex.Message}");
            if(ex.InnerException != null)
            {
                Console.WriteLine($"Chi tiết lỗi: {ex.InnerException.Message}");
            }
        }
    }

     private static void EnrollStudentUI(QuanLyHocVienService service)
    {
        try
        {
            Console.Write("Nhập ID học viên: ");
            if (!int.TryParse(Console.ReadLine(), out int studentId))
            {
                Console.WriteLine("=> ID học viên phải là một con số.");
                return;
            }

            Console.Write("Nhập ID khóa học: ");
            if (!int.TryParse(Console.ReadLine(), out int courseId))
            {
                Console.WriteLine("=> ID khóa học phải là một con số.");
                return;
            }

            service.EnrollStudentInCourse(studentId, courseId);
            Console.WriteLine("=> Ghi danh thành công!");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Lỗi khi ghi danh: {ex.Message}");
            if(ex.InnerException != null)
            {
                Console.WriteLine($"Chi tiết lỗi: {ex.InnerException.Message}");
            }
        }
    }

    private static void ShowStudentsInCourseUI(QuanLyHocVienService service)
    {
        try
        {
            Console.Write("Nhập ID khóa học để xem danh sách học viên: ");
            if (!int.TryParse(Console.ReadLine(), out int courseId))
            {
                Console.WriteLine("=> ID khóa học phải là một con số.");
                return;
            }

            var students = service.GetStudentsInCourse(courseId);

            Console.WriteLine($"\n--- Danh sách học viên trong khóa học (ID: {courseId}) ---");
            if (!students.Any())
            {
                Console.WriteLine("Không có học viên nào trong khóa học này.");
                return;
            }
            foreach (var student in students)
            {
                Console.WriteLine($"- ID: {student.Id}, Tên: {student.FullName}, Email: {student.Email}");
            }
        }
        catch (Exception ex)
        {
             Console.WriteLine($"Lỗi: {ex.Message}");
        }
    }

    private static void ShowCoursesWithStudentCountUI(QuanLyHocVienService service)
    {
        try
        {
            Console.Write("Hiển thị khóa học có nhiều hơn bao nhiêu học viên: ");
            if (!int.TryParse(Console.ReadLine(), out int count))
            {
                Console.WriteLine("=> Số lượng phải là một con số.");
                return;
            }

            var courses = service.GetCoursesWithMoreThanNStudents(count);
            
            Console.WriteLine($"\n--- Danh sách khóa học có nhiều hơn {count} học viên ---");
            if (!courses.Any())
            {
                Console.WriteLine("Không có khóa học nào thỏa mãn điều kiện.");
                return;
            }
            foreach (var course in courses)
            {
                 Console.WriteLine($"- ID: {course.Id}, Tên: {course.Title}, Số HV: {course.Enrollments.Count}");
            }
        }
        catch (Exception ex)
        {
             Console.WriteLine($"Lỗi: {ex.Message}");
        }
    }
    
    private static void FindStudentByNameUI(QuanLyHocVienService service)
    {
        Console.Write("Nhập tên học viên cần tìm: ");
        string name = Console.ReadLine() ?? "";
        if (string.IsNullOrWhiteSpace(name))
        {
            Console.WriteLine("=> Vui lòng nhập tên để tìm kiếm.");
            return;
        }
        var students = service.GetStudentsFilteredByName(name);

        Console.WriteLine($"\n--- Kết quả tìm kiếm cho '{name}' ---");
         if (!students.Any())
        {
            Console.WriteLine("Không tìm thấy học viên nào.");
            return;
        }
        foreach (var student in students)
        {
            Console.WriteLine($"- ID: {student.Id}, Tên: {student.FullName}, Email: {student.Email}");
        }
    }
    
    private static void ShowAllStudentsWithEnrollmentsUI(QuanLyHocVienService service)
    {
        Console.WriteLine("\n--- Danh sách học viên và các khóa đã đăng ký ---");
        var students = service.GetAllStudentsWithEnrollments();
        foreach (var student in students)
        {
            Console.WriteLine($"\n* Học viên: {student.FullName} (Email: {student.Email})");
            if (student.Enrollments.Any())
            {
                Console.WriteLine("  Các khóa đã đăng ký:");
                foreach (var enrollment in student.Enrollments)
                {
                    Console.WriteLine($"  - {enrollment.Course.Title} (Cấp độ: {enrollment.Course.Level})");
                }
            }
            else
            {
                Console.WriteLine("  Chưa đăng ký khóa học nào.");
            }
        }
    }

    private static void ShowCourseTitlesOnlyUI(QuanLyHocVienService service)
    {
        Console.WriteLine("\n--- Danh sách tên các khóa học (Tối ưu bằng Select & AsNoTracking) ---");
        var titles = service.GetCourseTitlesOnly();
        foreach (var title in titles)
        {
            Console.WriteLine($"- {title}");
        }
    }
    
    private static void ShowAllCoursesUI(QuanLyHocVienService service)
    {
        Console.WriteLine("\n--- Danh sách tất cả các khóa học ---");
        var courses = service.GetAllCourses();
        if (!courses.Any())
        {
            Console.WriteLine("=> Chưa có khóa học nào trong hệ thống.");
            return;
        }

        Console.WriteLine("{0,-5} {1,-30} {2,-10}", "ID", "Tên khóa học", "Cấp độ");
        Console.WriteLine("--------------------------------------------------");
        foreach (var course in courses)
        {
            Console.WriteLine("{0,-5} {1,-30} {2,-10}", course.Id, course.Title, course.Level);
        }
    }
    
    private static void ShowAllStudentsUI(QuanLyHocVienService service)
    {
        Console.WriteLine("\n--- Danh sách tất cả các học viên ---");
        var students = service.GetAllStudents();
        if (!students.Any())
        {
            Console.WriteLine("=> Chưa có học viên nào trong hệ thống.");
            return;
        }

        Console.WriteLine("{0,-5} {1,-25} {2,-25} {3,-15}", "ID", "Họ Tên", "Email", "Ngày Sinh");
        Console.WriteLine("---------------------------------------------------------------------");
        foreach (var student in students)
        {
            Console.WriteLine("{0,-5} {1,-25} {2,-25} {3,-15:yyyy-MM-dd}", student.Id, student.FullName, student.Email, student.BirthDate);
        }
    }
}

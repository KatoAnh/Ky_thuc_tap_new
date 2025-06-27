public class Person {
    private static int nextId = 1;
    private int id;
    private String fullName;
    private String email;
    private String phoneNumber;

    public Person(String fullName, String email, String phoneNumber) {
        this.id = nextId++;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

  
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
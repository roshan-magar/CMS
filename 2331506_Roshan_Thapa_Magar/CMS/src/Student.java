public class Student {
    private String Name;
    private String Email;
    private long Phone;
    private String Address;
    private String Password;
    private String UserType;

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public long getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getPassword() {
        return Password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(long phone) {
        Phone = phone;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Object getUserType() {
        return UserType;
    }
}

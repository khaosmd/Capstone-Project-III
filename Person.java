//imports the scanner class to accept user input
//Create the Person class
public class Person {

    //Attributes for constructor
    private String role;
    private String name;
    private String tel;
    private String email;
    private String address;

    // Constructor
    public Person(String role, String name, String tel, String email, String address) {
        this.role = role;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.address = address;
    }

    //toString method

    public String toString() {
            String output = "\nRole: " + role;
            output += "\nName:" + name;
            output += "\nTelephone number:" + tel;
            output += "\nEmail Address:" + email;
            output += "\nAddress:" + address;

            return output;
    }

    //setters and getters
    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

}




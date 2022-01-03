package lv.kristaps.tactictool;

public class User {
    public String name, email;

    public User() {

    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail() {
        this.email = email;
    }

    public void setName() {
        this.name = name;
    }

}

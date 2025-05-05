import java.util.*;

public class User {  
    protected String username;
    protected String password;
    protected String email;

    User(String username, String password, String email) {
      this.username = username;
      this.password = password;
      this.email = email;
    }

    String getUserName() {
      return this.username;
    }

    String getPassword() {
      return this.password;
    }

    String getEmail() {
      return this.email;
    }
    
    public static User login(Vector<User> users, String username, String password) {
        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
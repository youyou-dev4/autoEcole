
package Modele;


public class UtilisateurInfo {
     private String username;
    private String role;
    private String date;
    private int id;
    private String password;

    public UtilisateurInfo(String username, String role, String date, int id, String password) {
        this.username = username;
        this.role = role;
        this.date = date;
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getDate() {
        return date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}

package learn.myCookbook.models;

public class UserRole {
    public final int number_of_user_roles = 2;

    private int userRoleId;
    private String name;

    public UserRole() {
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

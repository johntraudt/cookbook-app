package learn.myCookbook.models;

public class UserRole {
    public static final int NUMBER_OF_USER_ROLES = 2;

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

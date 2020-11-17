package learn.myCookbook.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Login {
    private int userId;
    private String userName;
    private String passwordHash;

    private UserRole role;

    @Min(value = 1, message = "Invalid user role id.")
    @Max(value = UserRole.NUMBER_OF_USER_ROLES, message = "invalid user role id.")
    private int userRoleId;

    public Login() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }
}

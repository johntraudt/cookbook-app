package learn.myCookbook.models;

import javax.validation.constraints.*;
import java.util.List;

public class AppUser {

    @Min(value = 0, message = "User id must not be negative.")
    private int userId;

    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username is required.")
    private String userName;

    @NotNull(message = "Email is required.")
    @NotBlank(message = "Email is required.")
    private String email;

    private boolean isActive;

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String passwordHash;

    @NotNull(message = "First name is required.")
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotNull(message = "Last name is required.")
    @NotBlank(message = "Last name is required.")
    private String lastName;

    private UserRole role;

    @Min(value = 1, message = "Invalid user role id.")
    @Max(value = UserRole.NUMBER_OF_USER_ROLES, message = "invalid user role id.")
    private int userRoleId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<String> getRoles() {
        return List.of(this.role.getName());
    }

}

package by.iba.diploma_101_back.helpers;

public class ApiResponse {
    private String message;
    private String cookie;

    private String role;
    public ApiResponse() {}

    public String getCookie() {
        return cookie;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

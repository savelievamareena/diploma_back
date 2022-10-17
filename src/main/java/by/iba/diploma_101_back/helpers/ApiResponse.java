package by.iba.diploma_101_back.helpers;

public class ApiResponse {
    private String message;
    private String cookie;
    public ApiResponse() {}

    public String getCookie() {
        return cookie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

package by.iba.diploma_101_back.helpers;

public class ApiResponse {
    private Boolean response;
    private String message;

    public ApiResponse() {}

    public Boolean getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

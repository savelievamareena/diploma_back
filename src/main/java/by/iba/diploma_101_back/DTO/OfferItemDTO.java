package by.iba.diploma_101_back.DTO;


public class OfferItemDTO {
    private int price;
    private String serviceTitle;
    private String departmentTitle;

    public int getPrice() {
        return price;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public String getDepartmentTitle() {
        return departmentTitle;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        this.departmentTitle = departmentTitle;
    }
}

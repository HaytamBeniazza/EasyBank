package dto;

public class ClientDTO {
    private String code;
    private String address;
    private int userId; // Assuming this is the reference to the associated user

    // Constructors
    public ClientDTO() {
    }

    public ClientDTO(String code, String address, int userId) {
        this.code = code;
        this.address = address;
        this.userId = userId;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                '}';
    }
}

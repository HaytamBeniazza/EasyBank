package dto;

import java.util.Date;

public class ClientDTO extends UserDTO{
    private String code;
    private String address;
    private UserDTO userDTO;
    private int userId;

    // Constructors
    public ClientDTO() {
    }

    public ClientDTO(String code, String address, String firstName, String lastName, Date birthDate, int phone) {
        super(firstName, lastName, birthDate, phone);
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
    public void setUser(UserDTO userDTO){
        this.userDTO = userDTO;
    }
    public UserDTO getUser(){
        return userDTO;
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
        return "code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", first name=" + firstName + '\'' +
                ", last name=" + lastName + '\'' +
                ", phone=" + phone;
    }
}

package dto;

import java.util.Date;

public class EmployeeDTO extends UserDTO {
    private String registerNumber;
    private Date recruitmentDate;
    private String email;

    // Constructors
    public EmployeeDTO() {
    }

    public EmployeeDTO(String registerNumber, Date recruitmentDate, String email, String firstName, String lastName, Date birthDate, int phone) {
        super(firstName, lastName, birthDate, phone);
        this.registerNumber = registerNumber;
        this.recruitmentDate = recruitmentDate;
        this.email = email;
    }

    // Getters and Setters for employee-specific properties
    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Date getRecruitmentDate() {
        return recruitmentDate;
    }

    public void setRecruitmentDate(Date recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "registerNumber='" + registerNumber + '\'' +
                ", recruitmentDate=" + recruitmentDate +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}

package dao;

import dto.EmployeeDTO;
import dto.UserDTO;

import java.util.Date;
import java.util.List;

public interface EmployeeDAO {
    EmployeeDTO addEmployee(EmployeeDTO employee);
    int deleteEmployee(String registerNumber);
    EmployeeDTO updateEmployee(EmployeeDTO employee);
    EmployeeDTO getEmployeeByRegisterNumber(String registerNumber);
    public List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> findEmployeesBySearchQuery(String searchQuery);
}

package dao;

import dto.ClientDTO;
import dto.EmployeeDTO;
import dto.UserDTO;

import java.util.List;

public interface UserDAO {
    UserDTO addUser(UserDTO user);
    UserDTO updateUser(UserDTO user);

}

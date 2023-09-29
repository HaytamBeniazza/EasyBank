package dao;

import dto.UserDTO;

public interface UserDAO {
    UserDTO addUser(UserDTO user);
    UserDTO updateUser(UserDTO user);
}

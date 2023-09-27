package dao;
import dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO {
    private Connection connection;

    public EmployeeDAOImpl(){
        connection = DatabaseConnection.getConnection();
    }


    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employee) {
        try {
            // Define the SQL query to insert a new employee
            String insertEmployeeQuery = "INSERT INTO Employee (registerNumber, recruitmentDate, email, userId) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(insertEmployeeQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getRegisterNumber());
            preparedStatement.setDate(2, new java.sql.Date(employee.getRecruitmentDate().getTime()));
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setInt(4, employee.getId());

            // Execute the insertion query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                // Employee was successfully added to the database
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    String generatedRegisterNumber = generatedKeys.getString(1); // Assuming the registerNumber is a string
                    employee.setRegisterNumber(generatedRegisterNumber);
                }
                return employee;
            } else {
                // Insertion failed
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

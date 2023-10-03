package dao;
import dto.EmployeeDTO;
import util.Tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    @Override
    public int deleteEmployee(String registerNumber) {
        try {
            // Define the SQL query to delete an employee by registerNumber
            String deleteEmployeeQuery = "DELETE FROM Employee WHERE registerNumber = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(deleteEmployeeQuery);
            preparedStatement.setString(1, registerNumber);

            // Execute the deletion query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return the number of rows affected
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return a negative value to indicate deletion failure
        }
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        try {
            // Define the SQL query to update an employee by registerNumber
            String updateEmployeeQuery = "UPDATE Employee SET registernumber = ?, recruitmentdate = ?, email = ? WHERE registerNumber = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(updateEmployeeQuery);
            preparedStatement.setString(1, employee.getRegisterNumber());
            preparedStatement.setDate(2, new java.sql.Date(employee.getRecruitmentDate().getTime()));
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getRegisterNumber());

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if a row was affected (employee updated)
            if (rowsAffected > 0) {
                return employee; // Return the updated employee
            } else {
                return null; // Update failed, return null
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Update failed due to an exception
        }

}

    @Override
    public EmployeeDTO getEmployeeByRegisterNumber(String registerNumber){
//        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            // Define the SQL query to retrieve an employee by registerNumber and join with User table
            String query = "SELECT e.registerNumber, e.recruitmentDate, e.email, u.firstName, u.lastName, u.birthDate, u.phone " +
                    "FROM Employee e " +
                    "INNER JOIN Useer u ON e.userId = u.id " +
                    "WHERE e.registerNumber = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, registerNumber);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Extract data from the result set
                String regNumber = resultSet.getString("registerNumber");
                java.util.Date recruitmentDate = resultSet.getDate("recruitmentDate");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                java.util.Date birthDate = resultSet.getDate("birthDate");
                int phone = resultSet.getInt("phone");

                // Create and return an EmployeeDTO object with the retrieved data
                EmployeeDTO employee = new EmployeeDTO(regNumber, recruitmentDate, email, firstName, lastName, birthDate, phone);
                employee.setRegisterNumber(registerNumber);
                return employee;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<EmployeeDTO> findEmployeesBySearchQuery(String searchQuery) {
//        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            if(Tools.isDate(searchQuery)){
                Date dateSearch = Tools.stringToDate(searchQuery);

                String sql = "SELECT * FROM Employee e " +
                        "INNER JOIN Useer u ON e.userId = u.id " +
                        "WHERE " +
                        "   u.birthDate = ? OR " +
                        "   e.recruitmentDate = ? OR ";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDate(1, (java.sql.Date) dateSearch);
                preparedStatement.setDate(2, (java.sql.Date) dateSearch);

                ResultSet resultSet = preparedStatement.executeQuery();

                List<EmployeeDTO> matchingEmployees = new ArrayList<>();

                while (resultSet.next()) {
                    // Extract data from the result set
                    String regNumber = resultSet.getString("registerNumber");
                    Date recruitmentDateResult = resultSet.getDate("recruitmentDate");
                    String emailResult = resultSet.getString("email");
                    String firstNameResult = resultSet.getString("firstName");
                    String lastNameResult = resultSet.getString("lastName");
                    Date birthDateResult = resultSet.getDate("birthDate");
                    int phoneResult = resultSet.getInt("phone");

                    // Create an EmployeeDTO object with the retrieved data
                    EmployeeDTO employee = new EmployeeDTO(regNumber, recruitmentDateResult, emailResult, firstNameResult, lastNameResult, birthDateResult, phoneResult);

                    matchingEmployees.add(employee);
                }

                return matchingEmployees;
            }else{
                String query = "SELECT * FROM Employee e " +
                        "INNER JOIN Useer u ON e.userId = u.id " +
                        "WHERE " +
                        "   u.firstName LIKE ? OR " +
                        "   u.lastName LIKE ? OR " +
                        "   e.email LIKE ? OR " +
                        "   e.registerNumber LIKE ? OR " +
                        "   u.phone = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                for (int i = 1; i <= 4; i++) {
                    preparedStatement.setString(i, "%" + searchQuery + "%");
                }
                preparedStatement.setInt(5, Tools.tryParse(searchQuery));
                ResultSet resultSet = preparedStatement.executeQuery();

                List<EmployeeDTO> matchingEmployees = new ArrayList<>();

                while (resultSet.next()) {
                    // Extract data from the result set
                    String regNumber = resultSet.getString("registerNumber");
                    Date recruitmentDateResult = resultSet.getDate("recruitmentDate");
                    String emailResult = resultSet.getString("email");
                    String firstNameResult = resultSet.getString("firstName");
                    String lastNameResult = resultSet.getString("lastName");
                    Date birthDateResult = resultSet.getDate("birthDate");
                    int phoneResult = resultSet.getInt("phone");

                    // Create an EmployeeDTO object with the retrieved data
                    EmployeeDTO employee = new EmployeeDTO(regNumber, recruitmentDateResult, emailResult, firstNameResult, lastNameResult, birthDateResult, phoneResult);

                    matchingEmployees.add(employee);
                }

                return matchingEmployees;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Query execution failed
        }
    }






    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employees = new ArrayList<>();

        try {
            // Define the SQL query to retrieve all employees and join with User table
            String query = "SELECT e.registerNumber, e.recruitmentDate, e.email, u.firstName, u.lastName, u.birthDate, u.phone FROM Employee e JOIN Useer u ON e.userId = u.id";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Extract data from the result set for each employee
                String regNumber = resultSet.getString("registerNumber");
                java.util.Date recruitmentDate = resultSet.getDate("recruitmentDate");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                java.util.Date birthDate = resultSet.getDate("birthDate");
                int phone = resultSet.getInt("phone");

                // Create an EmployeeDTO object with the retrieved data
                EmployeeDTO employee = new EmployeeDTO(regNumber, recruitmentDate, email, firstName, lastName, birthDate, phone);

                // Add the employee to the list
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}

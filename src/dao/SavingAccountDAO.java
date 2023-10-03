package dao;

import dto.CurrentAccountDTO;
import dto.SavingAccountDTO;
import dto.EmployeeDTO;

import java.util.List;

public interface SavingAccountDAO {
    SavingAccountDTO createAccount(SavingAccountDTO account);
    int deleteAccount(String accountNumber);
    List<SavingAccountDTO> getAccountsByClient(String clientCode);
    SavingAccountDTO getAccountByNumber(String accountNumber);
}

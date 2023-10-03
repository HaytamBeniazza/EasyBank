package dao;

import dto.CurrentAccountDTO;

import java.util.List;

public interface CurrentAccountDAO {
    CurrentAccountDTO createAccount(CurrentAccountDTO account);
    int deleteAccount(String accountNumber);
    List<CurrentAccountDTO> getAccountsByClient(String clientCode);
    CurrentAccountDTO getAccountByNumber(String accountNumber);
}

package dao;

import dto.ClientDTO;

public interface ClientDAO {
    ClientDTO addClient(ClientDTO client);
    public int deleteClient(String code);
}

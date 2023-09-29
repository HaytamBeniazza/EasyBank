package dao;

import dto.ClientDTO;

import java.util.List;

public interface ClientDAO {
    ClientDTO addClient(ClientDTO client);
    public int deleteClient(String code);
    List<ClientDTO> getAllClients();
}

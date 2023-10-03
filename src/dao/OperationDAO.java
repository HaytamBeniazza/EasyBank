package dao;

import dto.OperationDTO;

public interface OperationDAO {
    public OperationDTO createOperationSaving(OperationDTO operation);
    OperationDTO createOperationCurrent(OperationDTO operation);
}

package org.example.footballmanager.service;

import org.example.footballmanager.dto.TransferDto;
import org.example.footballmanager.entity.Transfer;

import java.util.List;

public interface TransferService {
    Transfer createTransfer(TransferDto transferDto);

    Transfer getTransferById(Long id);

    List<Transfer> getAllTransfers();
}
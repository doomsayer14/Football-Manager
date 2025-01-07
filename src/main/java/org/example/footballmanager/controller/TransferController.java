package org.example.footballmanager.controller;

import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.example.footballmanager.dto.TransferDto;
import org.example.footballmanager.entity.Transfer;
import org.example.footballmanager.mapper.TransferMapper;
import org.example.footballmanager.service.TransferService;
import org.example.footballmanager.validation.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transfer")
public class TransferController {

    private final TransferService transferService;

    private final TransferMapper transferMapper;

    private final ResponseErrorValidation responseErrorValidation;

    public TransferController(TransferService transferService, TransferMapper transferMapper, ResponseErrorValidation responseErrorValidation) {
        this.transferService = transferService;
        this.transferMapper = transferMapper;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createTransfer(@Valid @RequestBody TransferDto transferDto,
                                                 BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Transfer transfer = transferService.createTransfer(transferDto);
        TransferDto createdTransfer = transferMapper.transferToTransferDto(transfer);
        return new ResponseEntity<>(createdTransfer, HttpStatus.CREATED);
    }

    @GetMapping("/{transferId}")
    public ResponseEntity<TransferDto> getTransferById(@PathVariable String transferId) {
        Transfer transfer = transferService.getTransferById(Long.parseLong(transferId));
        TransferDto transferDto = transferMapper.transferToTransferDto(transfer);
        return ResponseEntity.ok(transferDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<TransferDto>> getAllTransfers() {
        List<TransferDto> transferDtoList = transferService.getAllTransfers()
                .stream()
                .map(transferMapper::transferToTransferDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transferDtoList);
    }

}
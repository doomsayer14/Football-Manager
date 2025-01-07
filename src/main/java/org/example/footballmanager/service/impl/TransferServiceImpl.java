package org.example.footballmanager.service.impl;

import org.example.footballmanager.dto.TransferDto;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Team;
import org.example.footballmanager.entity.Transfer;
import org.example.footballmanager.exception.TransferNotFoundException;
import org.example.footballmanager.repository.TransferRepository;
import org.example.footballmanager.service.PlayerService;
import org.example.footballmanager.service.TeamService;
import org.example.footballmanager.service.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    private final TeamService teamService;

    private final PlayerService playerService;

    public TransferServiceImpl(TransferRepository transferRepository, TeamService teamService, PlayerService playerService) {
        this.transferRepository = transferRepository;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Transfer createTransfer(TransferDto transferDto) {
        Player player = transferDto.getPlayer();
        Team newTeam = transferDto.getNewTeam();
        Team oldTeam = player.getTeam();

        if (transferDto.getNewTeam().getBalance().isLessThan(player.getTransferPrice())) {
            throw new IllegalStateException("New team does not have enough balance for the transfer");
        }

        teamService.transferFunds(player.getTransferPrice(), oldTeam, newTeam);
        teamService.changeTeamSquad(player, oldTeam, newTeam);
        playerService.changePlayerTeam(player, newTeam);

        Transfer transfer = Transfer.builder()
                .player(player)
                .price(player.getTransferPrice())
                .newTeam(newTeam)
                .transferDate(LocalDate.now())
                .build();
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id)
                .orElseThrow(() -> new TransferNotFoundException(
                        "Player cannot be found with id = " + id));
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }
}

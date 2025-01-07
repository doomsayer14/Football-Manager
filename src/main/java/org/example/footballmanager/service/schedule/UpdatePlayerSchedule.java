package org.example.footballmanager.service.schedule;

import org.example.footballmanager.entity.Player;
import org.example.footballmanager.service.PlayerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePlayerSchedule {

    private final PlayerService playerService;

    public UpdatePlayerSchedule(PlayerService playerService) {
        this.playerService = playerService;
    }

    //we could add here some parallelism, but updateAge and calculateTransferPrice can't run simultaneously
    @Scheduled(cron = "0 0 * * *")
    public void updatePlayerAgeAndTransferPrice() {
        List<Player> playerList = playerService.getAllPlayers();
        playerList.forEach(playerService::updateAge);
        playerList.forEach(playerService::calculateTransferPrice);
    }

}
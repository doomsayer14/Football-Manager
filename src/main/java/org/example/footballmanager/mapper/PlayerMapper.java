package org.example.footballmanager.mapper;

import org.example.footballmanager.dto.PlayerDto;
import org.example.footballmanager.entity.Player;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PlayerMapper {
    public PlayerDto playerToPlayerDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .secondName(player.getSecondName())
                .position(player.getPosition())
                .transferPrice(BigDecimal.valueOf(player.getTransferPrice().getNumber().intValue()))
                .dateOfBirth(player.getDateOfBirth())
                .age(player.getAge())
                .careerStart(player.getCareerStart())
                .team(player.getTeam())
                .transfers(player.getTransfers())
                .build();
    }
}

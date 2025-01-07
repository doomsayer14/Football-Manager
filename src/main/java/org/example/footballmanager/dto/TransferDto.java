package org.example.footballmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Team;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    @NotNull
    private Long id;
    @NotNull
    private Player player;
    @NotNull
    private Team newTeam;
    @NotNull
    private LocalDate transferDate;
}

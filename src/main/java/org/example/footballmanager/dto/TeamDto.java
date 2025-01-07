package org.example.footballmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.footballmanager.entity.Player;
import org.example.footballmanager.entity.Transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    @NotNull
    private Long id;
    private String teamName;
    private String country;
    private BigDecimal balance;
    private BigDecimal commission;
    private List<Player> playerList = new ArrayList<>();
    private Set<Transfer> transfers = new LinkedHashSet<>();
}

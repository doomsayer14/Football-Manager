package org.example.footballmanager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.footballmanager.annotation.ValidPosition;
import org.example.footballmanager.entity.Team;
import org.example.footballmanager.entity.Transfer;
import org.example.footballmanager.entity.enums.Position;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    @NotNull
    private Long id;
    @Size(min = 1, max = 20)
    private String firstName;
    @Size(min = 1, max = 20)
    private String secondName;
    @ValidPosition
    private Position position;
    private Money transferPrice;
    private LocalDate dateOfBirth;
    private Integer age;
    private LocalDate careerStart;
    private Team team;
    private Set<Transfer> transfers = new LinkedHashSet<>();
}

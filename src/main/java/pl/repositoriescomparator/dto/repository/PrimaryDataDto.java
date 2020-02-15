package pl.repositoriescomparator.dto.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PrimaryDataDto {

    private int forksNumber;
    private int starsNumber;
    private int watchersNumber;
}

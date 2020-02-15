package pl.repositoriescomparator.dto.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LatestReleaseDateDto {

    private Instant latestReleaseDate;
}

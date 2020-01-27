package pl.repositoriescomparator.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RepositoryDto {

    @Setter(AccessLevel.NONE)
    private boolean isEmpty = false;

    private int forksNumber;
    private int starsNumber;
    private int watchersNumber;
    private Instant latestReleaseDate;
    private int openPullRequestsNumber;
    private int closedPullRequestsNumber;
    private int mergedPullRequestsNumber;

    private RepositoryDto(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return isEmpty;
    }

    public static RepositoryDto empty() {
        return new RepositoryDto(true);
    }
}

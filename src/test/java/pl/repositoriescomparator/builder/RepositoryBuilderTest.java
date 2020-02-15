package pl.repositoriescomparator.builder;

import org.junit.Test;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.dto.repository.*;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryBuilderTest {

    private final RepositoryBuilder builder = new RepositoryBuilder();

    @Test
    public void testItBuildsRepositoryDtoFromGivenDtos() {
        Instant now = Instant.now();
        LatestReleaseDateDto latestReleaseDateDto = new LatestReleaseDateDto(now);
        MergedPullRequestsNumberDto mergedPullRequestsNumberDto = new MergedPullRequestsNumberDto(1);
        ClosedPullRequestsNumberDto closedPullRequestsNumberDto = new ClosedPullRequestsNumberDto(2);
        OpenPullRequestsNumberDto openPullRequestsNumberDto = new OpenPullRequestsNumberDto(3);
        PrimaryDataDto primaryDataDto = new PrimaryDataDto(4, 5, 6);

        RepositoryDto builtDto = builder
                .withLatestReleaseDate(latestReleaseDateDto)
                .withMergedPullRequestsNumber(mergedPullRequestsNumberDto)
                .withClosedPullRequestsNumber(closedPullRequestsNumberDto)
                .withOpenPullRequestsNumber(openPullRequestsNumberDto)
                .withPrimaryData(primaryDataDto)
                .build();

        assertThat(builtDto.getLatestReleaseDate()).isEqualTo(now);
        assertThat(builtDto.getMergedPullRequestsNumber()).isEqualTo(1);
        assertThat(builtDto.getClosedPullRequestsNumber()).isEqualTo(2);
        assertThat(builtDto.getOpenPullRequestsNumber()).isEqualTo(3);
        assertThat(builtDto.getForksNumber()).isEqualTo(4);
        assertThat(builtDto.getStarsNumber()).isEqualTo(5);
        assertThat(builtDto.getWatchersNumber()).isEqualTo(6);
        assertThat(builder.build()).isEqualToComparingFieldByField(new RepositoryDto());
    }
}

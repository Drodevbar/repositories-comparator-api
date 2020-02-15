package pl.repositoriescomparator.service;

import org.junit.Test;
import pl.repositoriescomparator.builder.RepositoryBuilder;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.dto.repository.*;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RepositoryServiceTest {

    private final PrimaryDataRepositoryInterface primaryDataRepositoryService = mock(PrimaryDataRepositoryInterface.class);
    private final LatestReleaseDateRepositoryInterface latestReleaseDataRepositoryService = mock(LatestReleaseDateRepositoryInterface.class);
    private final PullRequestsNumberRepositoryInterface pullRequestsDataRepositoryService = mock(PullRequestsNumberRepositoryInterface.class);
    private final RepositoryBuilder builder = new RepositoryBuilder();
    private final RepositoryService service = new RepositoryService(
            primaryDataRepositoryService,
            latestReleaseDataRepositoryService,
            pullRequestsDataRepositoryService,
            builder
    );

    @Test
    public void testItBuildsDto_WhenPresentOptionalDtoReturnedFromInjectedService() {
        Instant now = Instant.now();
        PrimaryDataDto primaryDataDto = new PrimaryDataDto(1, 2, 3);
        LatestReleaseDateDto latestReleaseDateDto = new LatestReleaseDateDto(now);
        ClosedPullRequestsNumberDto closedPullRequestsNumberDto = new ClosedPullRequestsNumberDto(4);
        OpenPullRequestsNumberDto openPullRequestsNumberDto = new OpenPullRequestsNumberDto(5);
        MergedPullRequestsNumberDto mergedPullRequestsNumberDto = new MergedPullRequestsNumberDto(6);
        when(primaryDataRepositoryService.withPrimaryData(any(), any())).thenReturn(Optional.of(primaryDataDto));
        when(latestReleaseDataRepositoryService.withLatestReleaseDate(any(), any())).thenReturn(Optional.of(latestReleaseDateDto));
        when(pullRequestsDataRepositoryService.withClosedPullRequestsNumber(any(), any())).thenReturn(Optional.of(closedPullRequestsNumberDto));
        when(pullRequestsDataRepositoryService.withOpenPullRequestsNumber(any(), any())).thenReturn(Optional.of(openPullRequestsNumberDto));
        when(pullRequestsDataRepositoryService.withMergedPullRequestsNumber(any(), any())).thenReturn(Optional.of(mergedPullRequestsNumberDto));

        RepositoryDto builtDto = service.build("foo", "bar");

        assertThat(builtDto.isEmpty()).isFalse();
        assertThat(builtDto.getLatestReleaseDate()).isEqualTo(now);
        assertThat(builtDto.getForksNumber()).isEqualTo(1);
        assertThat(builtDto.getStarsNumber()).isEqualTo(2);
        assertThat(builtDto.getWatchersNumber()).isEqualTo(3);
        assertThat(builtDto.getClosedPullRequestsNumber()).isEqualTo(4);
        assertThat(builtDto.getOpenPullRequestsNumber()).isEqualTo(5);
        assertThat(builtDto.getMergedPullRequestsNumber()).isEqualTo(6);
    }

    @Test
    public void testItBuildsEmptyDto_WhenEmptyOptionalDtoReturnedFromInjectedService() {
        when(primaryDataRepositoryService.withPrimaryData(any(), any())).thenReturn(Optional.empty());

        RepositoryDto builtDto = service.build("foo", "bar");

        assertThat(builtDto.isEmpty()).isTrue();
        verify(latestReleaseDataRepositoryService, times(0)).withLatestReleaseDate(any(), any());
        verify(pullRequestsDataRepositoryService, times(0)).withClosedPullRequestsNumber(any(), any());
        verify(pullRequestsDataRepositoryService, times(0)).withOpenPullRequestsNumber(any(), any());
        verify(pullRequestsDataRepositoryService, times(0)).withClosedPullRequestsNumber(any(), any());
    }
}

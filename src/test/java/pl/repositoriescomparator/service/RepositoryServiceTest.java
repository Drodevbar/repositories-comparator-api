package pl.repositoriescomparator.service;

import org.junit.Test;
import pl.repositoriescomparator.dto.RepositoryDto;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RepositoryServiceTest {

    private final PrimaryDataRepositoryInterface primaryDataRepositoryService = mock(PrimaryDataRepositoryInterface.class);
    private final LatestReleaseDateRepositoryInterface latestReleaseDataRepositoryService = mock(LatestReleaseDateRepositoryInterface.class);
    private final PullRequestsNumberRepositoryInterface pullRequestsDataRepositoryService = mock(PullRequestsNumberRepositoryInterface.class);
    private final RepositoryService service = new RepositoryService(
            primaryDataRepositoryService,
            latestReleaseDataRepositoryService,
            pullRequestsDataRepositoryService
    );

    @Test
    public void testItBuildsRepositoryDto() {
        RepositoryDto dto = new RepositoryDto();

        when(primaryDataRepositoryService.hasError()).thenReturn(false);

        int forksNumber = 1;
        dto.setForksNumber(forksNumber);
        when(primaryDataRepositoryService.withPrimaryData(any(), anyString(), anyString())).thenReturn(dto);

        Instant date = Instant.parse("2020-01-01T12:12:12Z");
        dto.setLatestReleaseDate(date);
        when(latestReleaseDataRepositoryService.withLatestReleaseDate(any(), anyString(), anyString())).thenReturn(dto);

        int openPullRequestsNumber = 2;
        dto.setOpenPullRequestsNumber(openPullRequestsNumber);
        when(pullRequestsDataRepositoryService.withOpenPullRequestsNumber(any(), anyString(), anyString())).thenReturn(dto);

        int closedPullRequestsNumber = 3;
        dto.setClosedPullRequestsNumber(closedPullRequestsNumber);
        when(pullRequestsDataRepositoryService.withClosedPullRequestsNumber(any(), anyString(), anyString())).thenReturn(dto);

        int mergedPullRequestsNumber = 4;
        dto.setMergedPullRequestsNumber(mergedPullRequestsNumber);
        when(pullRequestsDataRepositoryService.withMergedPullRequestsNumber(any(), anyString(), anyString())).thenReturn(dto);

        RepositoryDto returnedDto = service.build("foo", "bar");

        assertThat(returnedDto).isEqualToComparingFieldByField(dto);

        verify(primaryDataRepositoryService, times(1)).withPrimaryData(any(), eq("foo"), eq("bar"));
        verify(primaryDataRepositoryService, times(1)).hasError();
        verify(latestReleaseDataRepositoryService, times(1)).withLatestReleaseDate(any(), eq("foo"), eq("bar"));
        verify(pullRequestsDataRepositoryService, times(1)).withOpenPullRequestsNumber(any(), eq("foo"), eq("bar"));
        verify(pullRequestsDataRepositoryService, times(1)).withClosedPullRequestsNumber(any(), eq("foo"), eq("bar"));
        verify(pullRequestsDataRepositoryService, times(1)).withMergedPullRequestsNumber(any(), eq("foo"), eq("bar"));
    }

    @Test
    public void testItBuildEmptyRepositoryDto_WhenErrorOccurred() {
        when(primaryDataRepositoryService.hasError()).thenReturn(true);

        RepositoryDto returnedDto = service.build("foo", "bar");

        assertThat(returnedDto.isEmpty()).isTrue();

        verify(primaryDataRepositoryService, times(1)).withPrimaryData(any(), eq("foo"), eq("bar"));
        verify(primaryDataRepositoryService, times(1)).hasError();
        verify(latestReleaseDataRepositoryService, times(0)).withLatestReleaseDate(any(), anyString(), anyString());
        verify(pullRequestsDataRepositoryService, times(0)).withOpenPullRequestsNumber(any(), anyString(), anyString());
        verify(pullRequestsDataRepositoryService, times(0)).withClosedPullRequestsNumber(any(), anyString(), anyString());
        verify(pullRequestsDataRepositoryService, times(0)).withMergedPullRequestsNumber(any(), anyString(), anyString());
    }
}

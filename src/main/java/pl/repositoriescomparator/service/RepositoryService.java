package pl.repositoriescomparator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.repositoriescomparator.dto.RepositoryDto;

@Service
public class RepositoryService {

    private final PrimaryDataRepositoryInterface primaryDataRepositoryService;
    private final LatestReleaseDateRepositoryInterface latestReleaseDataRepositoryService;
    private final PullRequestsNumberRepositoryInterface pullRequestsDataRepositoryService;

    @Autowired
    public RepositoryService(
            PrimaryDataRepositoryInterface primaryDataRepositoryService,
            LatestReleaseDateRepositoryInterface latestReleaseRepositoryService,
            PullRequestsNumberRepositoryInterface pullRequestsRepositoryService
    ) {
        this.primaryDataRepositoryService = primaryDataRepositoryService;
        this.latestReleaseDataRepositoryService = latestReleaseRepositoryService;
        this.pullRequestsDataRepositoryService = pullRequestsRepositoryService;
    }

    public RepositoryDto build(String owner, String name) {
        RepositoryDto dto = new RepositoryDto();
        dto = primaryDataRepositoryService.withPrimaryData(dto, owner, name);

        if (primaryDataRepositoryService.hasError()) {
            return RepositoryDto.empty();
        }

        dto = latestReleaseDataRepositoryService.withLatestReleaseDate(dto, owner, name);
        dto = pullRequestsDataRepositoryService.withClosedPullRequestsNumber(dto, owner, name);
        dto = pullRequestsDataRepositoryService.withMergedPullRequestsNumber(dto, owner, name);
        dto = pullRequestsDataRepositoryService.withOpenPullRequestsNumber(dto, owner, name);

        return dto;
    }
}

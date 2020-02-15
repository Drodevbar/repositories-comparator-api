package pl.repositoriescomparator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.repositoriescomparator.builder.RepositoryBuilder;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.dto.repository.*;

import java.util.Optional;

@Service
public class RepositoryService {

    private final PrimaryDataRepositoryInterface primaryDataRepositoryService;
    private final LatestReleaseDateRepositoryInterface latestReleaseDataRepositoryService;
    private final PullRequestsNumberRepositoryInterface pullRequestsDataRepositoryService;
    private final RepositoryBuilder builder;

    @Autowired
    public RepositoryService(
            PrimaryDataRepositoryInterface primaryDataRepositoryService,
            LatestReleaseDateRepositoryInterface latestReleaseRepositoryService,
            PullRequestsNumberRepositoryInterface pullRequestsRepositoryService,
            RepositoryBuilder builder
    ) {
        this.primaryDataRepositoryService = primaryDataRepositoryService;
        this.latestReleaseDataRepositoryService = latestReleaseRepositoryService;
        this.pullRequestsDataRepositoryService = pullRequestsRepositoryService;
        this.builder = builder;
    }

    public RepositoryDto build(String owner, String name) {
        Optional<PrimaryDataDto> primaryDataDto = primaryDataRepositoryService.withPrimaryData(owner, name);

        if (primaryDataDto.isEmpty()) {
            return RepositoryDto.empty();
        }

        Optional<LatestReleaseDateDto> latestReleaseDateDto = latestReleaseDataRepositoryService.withLatestReleaseDate(owner, name);
        Optional<ClosedPullRequestsNumberDto> closedPullRequestsNumberDto = pullRequestsDataRepositoryService.withClosedPullRequestsNumber(owner, name);
        Optional<MergedPullRequestsNumberDto> mergedPullRequestsNumberDto = pullRequestsDataRepositoryService.withMergedPullRequestsNumber(owner, name);
        Optional<OpenPullRequestsNumberDto> openPullRequestsNumberDto = pullRequestsDataRepositoryService.withOpenPullRequestsNumber(owner, name);

        return builder
                .withPrimaryData(primaryDataDto.get())
                .withLatestReleaseDate(latestReleaseDateDto.orElse(new LatestReleaseDateDto()))
                .withClosedPullRequestsNumber(closedPullRequestsNumberDto.orElse(new ClosedPullRequestsNumberDto()))
                .withMergedPullRequestsNumber(mergedPullRequestsNumberDto.orElse(new MergedPullRequestsNumberDto()))
                .withOpenPullRequestsNumber(openPullRequestsNumberDto.orElse(new OpenPullRequestsNumberDto()))
                .build();
    }
}

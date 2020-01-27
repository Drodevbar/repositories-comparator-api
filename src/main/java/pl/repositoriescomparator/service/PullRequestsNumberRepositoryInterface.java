package pl.repositoriescomparator.service;

import pl.repositoriescomparator.dto.RepositoryDto;

public interface PullRequestsNumberRepositoryInterface extends StatusAwareRepositoryInterface {

    RepositoryDto withOpenPullRequestsNumber(RepositoryDto dto, String owner, String name);
    RepositoryDto withClosedPullRequestsNumber(RepositoryDto dto, String owner, String name);
    RepositoryDto withMergedPullRequestsNumber(RepositoryDto dto, String owner, String name);
}

package pl.repositoriescomparator.service;

import pl.repositoriescomparator.dto.repository.ClosedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.MergedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.OpenPullRequestsNumberDto;

import java.util.Optional;

public interface PullRequestsNumberRepositoryInterface {

    Optional<OpenPullRequestsNumberDto> withOpenPullRequestsNumber(String owner, String name);
    Optional<ClosedPullRequestsNumberDto> withClosedPullRequestsNumber(String owner, String name);
    Optional<MergedPullRequestsNumberDto> withMergedPullRequestsNumber(String owner, String name);
}

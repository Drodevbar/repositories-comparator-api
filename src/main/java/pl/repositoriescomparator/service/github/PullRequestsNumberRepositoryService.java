package pl.repositoriescomparator.service.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.repositoriescomparator.builder.repository.PullRequestsNumberBuilder;
import pl.repositoriescomparator.dto.repository.ClosedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.MergedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.OpenPullRequestsNumberDto;
import pl.repositoriescomparator.integration.github.search.GithubSearchIssuesClient;
import pl.repositoriescomparator.service.PullRequestsNumberRepositoryInterface;

import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class PullRequestsNumberRepositoryService implements PullRequestsNumberRepositoryInterface {

    private static final String TYPE_PULL_REQUESTS = "pr";

    private final GithubSearchIssuesClient issuesClient;
    private final PullRequestsNumberBuilder repositoryBuilder;

    @Autowired
    public PullRequestsNumberRepositoryService(GithubSearchIssuesClient issuesResponse, PullRequestsNumberBuilder repositoryBuilder) {
        this.issuesClient = issuesResponse;
        this.repositoryBuilder = repositoryBuilder;
    }

    public Optional<OpenPullRequestsNumberDto> withOpenPullRequestsNumber(String owner, String name) {
        try {
            issuesClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = issuesClient.getResponseByTypeAndState(TYPE_PULL_REQUESTS, "open");

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            return Optional.of(repositoryBuilder.withOpenPullRequestsNumber(httpResponse.body()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ClosedPullRequestsNumberDto> withClosedPullRequestsNumber(String owner, String name) {
        try {
            issuesClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = issuesClient.getResponseByTypeAndState(TYPE_PULL_REQUESTS, "closed");

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            return Optional.of(repositoryBuilder.withClosedPullRequestsNumber(httpResponse.body()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<MergedPullRequestsNumberDto> withMergedPullRequestsNumber(String owner, String name) {
        try {
            issuesClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = issuesClient.getResponseByTypeAndState(TYPE_PULL_REQUESTS, "merged");

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            return Optional.of(repositoryBuilder.withMergedPullRequestsNumber(httpResponse.body()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

package pl.repositoriescomparator.service.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import pl.repositoriescomparator.builder.RepositoryBuilder;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.integration.github.search.GithubSearchIssuesClient;
import pl.repositoriescomparator.service.PullRequestsNumberRepositoryInterface;

import java.net.http.HttpResponse;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PullRequestsNumberRepositoryService extends StatusAwareRepositoryService implements PullRequestsNumberRepositoryInterface {

    private static final String TYPE_PULL_REQUESTS = "pr";

    private final GithubSearchIssuesClient issuesClient;
    private final RepositoryBuilder repositoryBuilder;

    @Autowired
    public PullRequestsNumberRepositoryService(GithubSearchIssuesClient issuesResponse, RepositoryBuilder repositoryBuilder) {
        this.issuesClient = issuesResponse;
        this.repositoryBuilder = repositoryBuilder;
    }

    public RepositoryDto withOpenPullRequestsNumber(RepositoryDto dto, String owner, String name) {
        try {
            issuesClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = issuesClient.getResponseByTypeAndState(TYPE_PULL_REQUESTS, "open");

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                setError();

                return dto;
            }

            return repositoryBuilder.withOpenPullRequestsNumber(dto, httpResponse.body());
        } catch (Exception e) {
            setError();

            return dto;
        }
    }

    public RepositoryDto withClosedPullRequestsNumber(RepositoryDto dto, String owner, String name) {
        try {
            issuesClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = issuesClient.getResponseByTypeAndState(TYPE_PULL_REQUESTS, "closed");

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                setError();

                return dto;
            }

            return repositoryBuilder.withClosedPullRequestsNumber(dto, httpResponse.body());
        } catch (Exception e) {
            setError();

            return dto;
        }
    }

    public RepositoryDto withMergedPullRequestsNumber(RepositoryDto dto, String owner, String name) {
        try {
            issuesClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = issuesClient.getResponseByTypeAndState(TYPE_PULL_REQUESTS, "merged");

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                setError();

                return dto;
            }

            return repositoryBuilder.withMergedPullRequestsNumber(dto, httpResponse.body());
        } catch (Exception e) {
            setError();

            return dto;
        }
    }
}

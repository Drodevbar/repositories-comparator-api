package pl.repositoriescomparator.integration.github;

import org.springframework.beans.factory.annotation.Value;
import pl.repositoriescomparator.integration.ConfigurableRepositoryInterface;

import java.net.http.HttpClient;
import java.time.Duration;

abstract public class GithubClient implements ConfigurableRepositoryInterface {

    protected final HttpClient httpClient;
    @Value("${integration.github.baseuri}")
    protected String uri;
    protected String repository;

    public GithubClient() {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public GithubClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setRepository(String owner, String repositoryName) {
        repository = owner + '/' + repositoryName;
    }
}

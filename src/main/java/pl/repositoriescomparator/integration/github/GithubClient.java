package pl.repositoriescomparator.integration.github;

import pl.repositoriescomparator.integration.ConfigurableRepositoryInterface;

import java.net.http.HttpClient;
import java.time.Duration;

abstract public class GithubClient implements ConfigurableRepositoryInterface {

    private static final String API_BASE_URI = "https://api.github.com";

    protected final HttpClient httpClient;
    protected String uri = API_BASE_URI;
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

package pl.repositoriescomparator.integration.github;

import org.springframework.beans.factory.annotation.Value;
import pl.repositoriescomparator.integration.ConfigurableRepositoryInterface;

import java.net.http.HttpClient;
import java.time.Duration;

abstract public class GithubClient implements ConfigurableRepositoryInterface {

    protected final HttpClient httpClient;
    protected String uri;
    protected String repository;

    public GithubClient(@Value("${integration.github.baseuri}") final String uri) {
        this.uri = uri;
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public void setRepository(String owner, String repositoryName) {
        repository = owner + '/' + repositoryName;
    }
}

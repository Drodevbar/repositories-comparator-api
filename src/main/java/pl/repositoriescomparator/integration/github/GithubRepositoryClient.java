package pl.repositoriescomparator.integration.github;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GithubRepositoryClient extends GithubClient {

    private static final String API_REPOS = "repos";
    private static final String API_LATEST_RELEASE = "releases/latest";

    public GithubRepositoryClient() {
        super();
        uri += '/' + API_REPOS;
    }

    public HttpResponse<String> getPrimaryResponse() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + '/' + repository))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getLatestReleaseResponse() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + '/' + repository + '/' + API_LATEST_RELEASE))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

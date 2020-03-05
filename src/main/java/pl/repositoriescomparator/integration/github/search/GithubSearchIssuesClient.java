package pl.repositoriescomparator.integration.github.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.repositoriescomparator.integration.github.GithubSearchClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GithubSearchIssuesClient extends GithubSearchClient {

    private static final String API_ISSUES = "issues";

    public GithubSearchIssuesClient(@Value("${integration.github.baseuri}") final String uri) {
        super(uri);
        this.uri += '/' + API_ISSUES;
    }

    public HttpResponse<String> getResponseByTypeAndState(String type, String state) throws Exception {
        String queryRepo = "+repo:" + repository;
        String queryType = "+type:" + type;
        String queryState = state.isEmpty() ? "" : "+is:" + state;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "?q=" + queryRepo + queryType + queryState))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

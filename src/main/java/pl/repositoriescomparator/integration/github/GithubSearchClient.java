package pl.repositoriescomparator.integration.github;

import org.springframework.beans.factory.annotation.Value;

public class GithubSearchClient extends GithubClient {

    private static final String API_SEARCH = "search";

    public GithubSearchClient(@Value("${integration.github.baseuri}") final String uri) {
        super(uri);
        this.uri += '/' + API_SEARCH;
    }
}

package pl.repositoriescomparator.integration.github;

public class GithubSearchClient extends GithubClient {

    private static final String API_SEARCH = "search";

    public GithubSearchClient() {
        super();
        uri += '/' + API_SEARCH;
    }
}

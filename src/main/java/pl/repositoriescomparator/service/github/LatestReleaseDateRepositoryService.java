package pl.repositoriescomparator.service.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.repositoriescomparator.builder.repository.LatestReleaseDateBuilder;
import pl.repositoriescomparator.dto.repository.LatestReleaseDateDto;
import pl.repositoriescomparator.integration.github.GithubRepositoryClient;
import pl.repositoriescomparator.service.LatestReleaseDateRepositoryInterface;

import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class LatestReleaseDateRepositoryService implements LatestReleaseDateRepositoryInterface {

    private final GithubRepositoryClient repositoryClient;
    private final LatestReleaseDateBuilder repositoryBuilder;

    @Autowired
    public LatestReleaseDateRepositoryService(GithubRepositoryClient repositoryClient, LatestReleaseDateBuilder repositoryBuilder) {
        this.repositoryClient = repositoryClient;
        this.repositoryBuilder = repositoryBuilder;
    }

    public Optional<LatestReleaseDateDto> withLatestReleaseDate(String owner, String name) {
        try {
            repositoryClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = repositoryClient.getLatestReleaseResponse();

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            return Optional.of(repositoryBuilder.withLatestReleaseDate(httpResponse.body()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

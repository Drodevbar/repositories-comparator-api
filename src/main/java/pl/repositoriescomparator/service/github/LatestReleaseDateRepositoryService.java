package pl.repositoriescomparator.service.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.repositoriescomparator.builder.RepositoryBuilder;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.integration.github.GithubRepositoryClient;
import pl.repositoriescomparator.service.LatestReleaseDateRepositoryInterface;

import java.net.http.HttpResponse;

@Service
public class LatestReleaseDateRepositoryService extends StatusAwareRepositoryService implements LatestReleaseDateRepositoryInterface {

    private final GithubRepositoryClient repositoryClient;
    private final RepositoryBuilder repositoryBuilder;

    @Autowired
    public LatestReleaseDateRepositoryService(GithubRepositoryClient repositoryClient, RepositoryBuilder repositoryBuilder) {
        this.repositoryClient = repositoryClient;
        this.repositoryBuilder = repositoryBuilder;
    }

    public RepositoryDto withLatestReleaseDate(RepositoryDto dto, String owner, String name) {
        try {
            repositoryClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = repositoryClient.getLatestReleaseResponse();

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                setError();

                return dto;
            }

            return repositoryBuilder.withLatestReleaseDate(dto, httpResponse.body());
        } catch (Exception e) {
            setError();

            return dto;
        }
    }
}

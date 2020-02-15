package pl.repositoriescomparator.service.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.repositoriescomparator.builder.repository.PrimaryDataBuilder;
import pl.repositoriescomparator.dto.repository.PrimaryDataDto;
import pl.repositoriescomparator.integration.github.GithubRepositoryClient;
import pl.repositoriescomparator.service.PrimaryDataRepositoryInterface;

import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class PrimaryDataRepositoryService implements PrimaryDataRepositoryInterface {

    private final GithubRepositoryClient repositoryClient;
    private final PrimaryDataBuilder repositoryBuilder;

    @Autowired
    public PrimaryDataRepositoryService(GithubRepositoryClient repositoryClient, PrimaryDataBuilder repositoryBuilder) {
        this.repositoryClient = repositoryClient;
        this.repositoryBuilder = repositoryBuilder;
    }

    public Optional<PrimaryDataDto> withPrimaryData(String owner, String name) {
        try {
            repositoryClient.setRepository(owner, name);
            HttpResponse<String> httpResponse = repositoryClient.getPrimaryResponse();

            if (httpResponse.statusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            return Optional.of(repositoryBuilder.withPrimaryData(httpResponse.body()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

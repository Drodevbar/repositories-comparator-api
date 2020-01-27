package pl.repositoriescomparator.service;

import pl.repositoriescomparator.dto.RepositoryDto;

public interface LatestReleaseDateRepositoryInterface extends StatusAwareRepositoryInterface {

    RepositoryDto withLatestReleaseDate(RepositoryDto dto, String owner, String name);
}

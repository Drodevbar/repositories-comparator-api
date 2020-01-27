package pl.repositoriescomparator.service;

import pl.repositoriescomparator.dto.RepositoryDto;

public interface PrimaryDataRepositoryInterface extends StatusAwareRepositoryInterface {

    RepositoryDto withPrimaryData(RepositoryDto dto, String owner, String name);
}

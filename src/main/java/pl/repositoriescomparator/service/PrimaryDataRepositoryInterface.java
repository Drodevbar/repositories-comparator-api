package pl.repositoriescomparator.service;

import pl.repositoriescomparator.dto.repository.PrimaryDataDto;

import java.util.Optional;

public interface PrimaryDataRepositoryInterface {

    Optional<PrimaryDataDto> withPrimaryData(String owner, String name);
}

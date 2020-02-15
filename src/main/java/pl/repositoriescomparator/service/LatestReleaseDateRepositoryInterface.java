package pl.repositoriescomparator.service;

import pl.repositoriescomparator.dto.repository.LatestReleaseDateDto;

import java.util.Optional;

public interface LatestReleaseDateRepositoryInterface {

    Optional<LatestReleaseDateDto> withLatestReleaseDate(String owner, String name);
}

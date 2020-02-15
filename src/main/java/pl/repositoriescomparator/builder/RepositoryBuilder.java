package pl.repositoriescomparator.builder;

import org.springframework.stereotype.Component;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.dto.repository.*;

@Component
public class RepositoryBuilder {

    private RepositoryDto dto = new RepositoryDto();

    public RepositoryBuilder withPrimaryData(PrimaryDataDto primaryDataDto) {
        dto.setForksNumber(primaryDataDto.getForksNumber());
        dto.setStarsNumber(primaryDataDto.getStarsNumber());
        dto.setWatchersNumber(primaryDataDto.getWatchersNumber());

        return this;
    }

    public RepositoryBuilder withLatestReleaseDate(LatestReleaseDateDto latestReleaseDateDto) {
        dto.setLatestReleaseDate(latestReleaseDateDto.getLatestReleaseDate());

        return this;
    }

    public RepositoryBuilder withOpenPullRequestsNumber(OpenPullRequestsNumberDto openPullRequestsNumberDto) {
        dto.setOpenPullRequestsNumber(openPullRequestsNumberDto.getNumber());

        return this;
    }

    public RepositoryBuilder withClosedPullRequestsNumber(ClosedPullRequestsNumberDto closedPullRequestsNumberDto) {
        dto.setClosedPullRequestsNumber(closedPullRequestsNumberDto.getNumber());

        return this;
    }

    public RepositoryBuilder withMergedPullRequestsNumber(MergedPullRequestsNumberDto mergedPullRequestsNumberDto) {
        dto.setMergedPullRequestsNumber(mergedPullRequestsNumberDto.getNumber());

        return this;
    }

    public RepositoryDto build() {
        RepositoryDto builtDto = dto;
        dto = new RepositoryDto();

        return builtDto;
    }
}

package pl.repositoriescomparator.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.repositoriescomparator.dto.RepositoryDto;

import java.time.Instant;

@Component
public class RepositoryBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public RepositoryDto withPrimaryData(RepositoryDto dto, String json) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        dto.setStarsNumber(jsonNode.get("stargazers_count").asInt());
        dto.setForksNumber(jsonNode.get("forks_count").asInt());
        dto.setWatchersNumber(jsonNode.get("watchers_count").asInt());

        return dto;
    }

    public RepositoryDto withLatestReleaseDate(RepositoryDto dto, String json) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        String dateString = jsonNode.get("published_at").asText();
        dto.setLatestReleaseDate(Instant.parse(dateString));

        return dto;
    }

    public RepositoryDto withOpenPullRequestsNumber(RepositoryDto dto, String json) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        dto.setOpenPullRequestsNumber(jsonNode.get("total_count").asInt());

        return dto;
    }

    public RepositoryDto withClosedPullRequestsNumber(RepositoryDto dto, String json) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        dto.setClosedPullRequestsNumber(jsonNode.get("total_count").asInt());

        return dto;
    }

    public RepositoryDto withMergedPullRequestsNumber(RepositoryDto dto, String json) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        dto.setMergedPullRequestsNumber(jsonNode.get("total_count").asInt());

        return dto;
    }
}

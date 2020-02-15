package pl.repositoriescomparator.builder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.repositoriescomparator.dto.repository.LatestReleaseDateDto;

import java.time.Instant;

@Component
public class LatestReleaseDateBuilder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LatestReleaseDateDto withLatestReleaseDate(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        String dateString = jsonNode.get("published_at").asText();

        LatestReleaseDateDto dto = new LatestReleaseDateDto();
        dto.setLatestReleaseDate(Instant.parse(dateString));

        return dto;
    }
}

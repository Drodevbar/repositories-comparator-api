package pl.repositoriescomparator.builder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.repositoriescomparator.dto.repository.PrimaryDataDto;

@Component
public class PrimaryDataBuilder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PrimaryDataDto withPrimaryData(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        PrimaryDataDto dto = new PrimaryDataDto();
        dto.setStarsNumber(jsonNode.get("stargazers_count").asInt());
        dto.setForksNumber(jsonNode.get("forks_count").asInt());
        dto.setWatchersNumber(jsonNode.get("watchers_count").asInt());

        return dto;
    }
}

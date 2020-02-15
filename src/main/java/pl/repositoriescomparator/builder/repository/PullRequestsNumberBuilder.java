package pl.repositoriescomparator.builder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.repositoriescomparator.dto.repository.ClosedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.MergedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.OpenPullRequestsNumberDto;

@Component
public class PullRequestsNumberBuilder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenPullRequestsNumberDto withOpenPullRequestsNumber(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        OpenPullRequestsNumberDto dto = new OpenPullRequestsNumberDto();
        dto.setNumber(jsonNode.get("total_count").asInt());

        return dto;
    }

    public ClosedPullRequestsNumberDto withClosedPullRequestsNumber(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        ClosedPullRequestsNumberDto dto = new ClosedPullRequestsNumberDto();
        dto.setNumber(jsonNode.get("total_count").asInt());

        return dto;
    }

    public MergedPullRequestsNumberDto withMergedPullRequestsNumber(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        MergedPullRequestsNumberDto dto = new MergedPullRequestsNumberDto();
        dto.setNumber(jsonNode.get("total_count").asInt());

        return dto;
    }
}

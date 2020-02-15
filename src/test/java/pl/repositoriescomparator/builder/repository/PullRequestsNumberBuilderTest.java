package pl.repositoriescomparator.builder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import pl.repositoriescomparator.dto.repository.ClosedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.MergedPullRequestsNumberDto;
import pl.repositoriescomparator.dto.repository.OpenPullRequestsNumberDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PullRequestsNumberBuilderTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PullRequestsNumberBuilder builder = new PullRequestsNumberBuilder();

    @Test
    public void testItBuildsDtoWithOpenPullRequestsNumber() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 1);
        String json = objectNode.toString();

        OpenPullRequestsNumberDto dto = builder.withOpenPullRequestsNumber(json);

        assertThat(dto.getNumber()).isEqualTo(1);
    }

    @Test
    public void testItBuildsDtoWithClosedPullRequestsNumber() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 1);
        String json = objectNode.toString();

        ClosedPullRequestsNumberDto dto = builder.withClosedPullRequestsNumber(json);

        assertThat(dto.getNumber()).isEqualTo(1);
    }

    @Test
    public void testItBuildsDtoWithMergedPullRequestsNumber() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 1);
        String json = objectNode.toString();

        MergedPullRequestsNumberDto dto = builder.withMergedPullRequestsNumber(json);

        assertThat(dto.getNumber()).isEqualTo(1);
    }

    @Test
    public void testItThrowsException_WhenInvalidJsonStringProvided() {
        String invalidJsonString = "foo";

        assertThatExceptionOfType(JsonProcessingException.class)
                .isThrownBy(() -> builder.withClosedPullRequestsNumber(invalidJsonString));
    }
}

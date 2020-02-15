package pl.repositoriescomparator.service.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pl.repositoriescomparator.builder.repository.LatestReleaseDateBuilder;
import pl.repositoriescomparator.dto.repository.LatestReleaseDateDto;
import pl.repositoriescomparator.integration.github.GithubRepositoryClient;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LatestReleaseDateRepositoryServiceTest {

    private static final String DATETIME = "2020-01-01T12:12:12Z";

    private final GithubRepositoryClient client = mock(GithubRepositoryClient.class);
    private final LatestReleaseDateBuilder builder = new LatestReleaseDateBuilder();
    private final LatestReleaseDateRepositoryService service = new LatestReleaseDateRepositoryService(client, builder);
    @SuppressWarnings("unchecked")
    private final HttpResponse<String> httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testItBuildsDto_WhenResponseIsSuccessful() throws Exception {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("published_at", DATETIME);
        when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());
        when(httpResponse.body()).thenReturn(objectNode.toString());
        when(client.getLatestReleaseResponse()).thenReturn(httpResponse);

        Optional<LatestReleaseDateDto> builtDto = service.withLatestReleaseDate("foo", "bar");

        assertThat(builtDto.isPresent()).isTrue();
        assertThat(builtDto.get()).isEqualToComparingFieldByField(new LatestReleaseDateDto(Instant.parse(DATETIME)));
    }

    @Test
    public void testItBuildsEmptyOptional_WhenResponseIsNotSuccessful() throws Exception {
        when(httpResponse.statusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Optional<LatestReleaseDateDto> builtDto = service.withLatestReleaseDate("foo", "bar");

        assertThat(builtDto.isPresent()).isFalse();
    }

    @Test
    public void testItBuildsEmptyOptional_WhenInvalidJsonProvided() throws Exception {
        when(httpResponse.statusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(httpResponse.body()).thenReturn("invalid json string");

        Optional<LatestReleaseDateDto> builtDto = service.withLatestReleaseDate("foo", "bar");

        assertThat(builtDto.isPresent()).isFalse();
    }
}

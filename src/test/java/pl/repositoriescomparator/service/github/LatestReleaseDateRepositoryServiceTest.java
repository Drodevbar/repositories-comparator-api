package pl.repositoriescomparator.service.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import pl.repositoriescomparator.builder.RepositoryBuilder;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.integration.github.GithubRepositoryClient;

import java.net.http.HttpResponse;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LatestReleaseDateRepositoryServiceTest {

    private final GithubRepositoryClient repositoryClient = mock(GithubRepositoryClient.class);
    private final RepositoryBuilder repositoryBuilder = mock(RepositoryBuilder.class);
    private final LatestReleaseDateRepositoryService service = new LatestReleaseDateRepositoryService(repositoryClient, repositoryBuilder);
    @SuppressWarnings("unchecked")
    private final HttpResponse<String> httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testItBuildsRepositoryDtoWithLatestReleaseDate_WhenResponseIsSuccessful() throws Exception {
        RepositoryDto dto = new RepositoryDto();
        ObjectNode objectNode = objectMapper.createObjectNode();
        String dateString = "2020-01-01T12:12:12Z";
        objectNode.put("published_at", dateString);
        String jsonString = objectNode.toString();
        RepositoryDto modifiedDto = new RepositoryDto();
        modifiedDto.setLatestReleaseDate(Instant.parse(dateString));

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(jsonString);
        when(repositoryClient.getLatestReleaseResponse()).thenReturn(httpResponse);
        when(repositoryBuilder.withLatestReleaseDate(dto, jsonString)).thenReturn(modifiedDto);

        RepositoryDto returnedDto = service.withLatestReleaseDate(dto, "foo", "bar");

        assertThat(returnedDto.getLatestReleaseDate()).isEqualTo(modifiedDto.getLatestReleaseDate());
        assertThat(service.hasError()).isFalse();

        verify(repositoryClient, times(1)).setRepository("foo", "bar");
        verify(repositoryClient, times(1)).getLatestReleaseResponse();
        verify(repositoryBuilder, times(1)).withLatestReleaseDate(dto, jsonString);
    }

    @Test
    public void testItReturnsNotModifiedDto_WhenResponseFails() throws Exception {
        RepositoryDto dto = new RepositoryDto();
        Instant latestReleaseDate = dto.getLatestReleaseDate();
        when(httpResponse.statusCode()).thenReturn(404);
        when(repositoryClient.getLatestReleaseResponse()).thenReturn(httpResponse);

        dto = service.withLatestReleaseDate(dto, "foo", "bar");

        assertThat(dto.getLatestReleaseDate()).isEqualTo(latestReleaseDate);
        assertThat(service.hasError()).isTrue();

        verify(repositoryClient, times(1)).setRepository("foo", "bar");
        verify(repositoryClient, times(1)).getLatestReleaseResponse();
        verify(repositoryBuilder, times(0)).withLatestReleaseDate(any(), anyString());
    }

    @Test
    public void testItReturnsNotModifiedDto_WhenResponseJsonIsInvalid() throws Exception {
        RepositoryDto dto = new RepositoryDto();
        Instant latestReleaseDate = dto.getLatestReleaseDate();
        String invalidJsonString = "foo";

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(invalidJsonString);
        when(repositoryClient.getLatestReleaseResponse()).thenReturn(httpResponse);
        when(repositoryBuilder.withLatestReleaseDate(dto, invalidJsonString)).thenThrow(JsonProcessingException.class);

        dto = service.withLatestReleaseDate(dto, "foo", "bar");

        assertThat(dto.getLatestReleaseDate()).isEqualTo(latestReleaseDate);
        assertThat(service.hasError()).isTrue();

        verify(repositoryClient, times(1)).setRepository("foo", "bar");
        verify(repositoryClient, times(1)).getLatestReleaseResponse();
        verify(repositoryBuilder, times(1)).withLatestReleaseDate(dto, invalidJsonString);
    }
}

package pl.repositoriescomparator.resource;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.exception.RepositoryNotFoundException;
import pl.repositoriescomparator.service.RepositoryService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class RepositoryResourceTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);
    private final RepositoryResource repositoryResource = new RepositoryResource(repositoryService);

    @Test
    public void testItReturnsRepository_WhenFound() {
        RepositoryDto dto = new RepositoryDto();
        dto.setForksNumber(1);
        when(repositoryService.build("foo", "bar")).thenReturn(dto);

        ResponseEntity responseEntity = repositoryResource.getRepository("foo", "bar");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(dto);
        verify(repositoryService, times(1)).build("foo", "bar");
    }

    @Test
    public void testItThrowsNotFound_WhenRepositoryNotFound() {
        when(repositoryService.build("foo", "bar")).thenReturn(RepositoryDto.empty());

        assertThatExceptionOfType(RepositoryNotFoundException.class)
                .isThrownBy(() -> repositoryResource.getRepository("foo", "bar"))
                .withMessage("Given repository not found");

        verify(repositoryService, times(1)).build("foo", "bar");
    }
}

package pl.repositoriescomparator.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryDtoTest {

    @Test
    public void testIsEmptyReturnsTrueForEmptyDto() {
        RepositoryDto emptyDto = RepositoryDto.empty();

        assertThat(emptyDto.isEmpty()).isTrue();
    }

    @Test
    public void testIsEmptyReturnsFalseForNotEmptyDto() {
        RepositoryDto dto = new RepositoryDto();

        assertThat(dto.isEmpty()).isFalse();
    }
}

package pl.repositoriescomparator.resource;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminResourceTest {

    private final AdminResource adminResource = new AdminResource();

    @Test
    public void testPing() {
        String response = adminResource.ping();

        assertThat(response).isEqualTo("pong");
    }
}

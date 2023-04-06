package softuni.photostore.servicesTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.photostore.model.view.StatsView;
import softuni.photostore.service.StatsService;
import softuni.photostore.service.impl.StatsServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StatsTest {
    private StatsService sut;

    @BeforeEach
    public void setup() {
        sut = new StatsServiceImpl();
    }

    @Test
    public void testOnRequest() {
        sut.onRequest();
        StatsView stats = sut.getStats();
        assertThat(stats).isNotNull();
        assertThat(stats.getAnonRequests()).isEqualTo(0);
        assertThat(stats.getAuthRequests()).isEqualTo(1);
        assertThat(stats.getTotalRequests()).isEqualTo(1);
    }

    @Test
    public void testGetStats() {
        StatsView stats = sut.getStats();
        assertThat(stats).isNotNull();
        assertThat(stats.getAnonRequests()).isEqualTo(0);
        assertThat(stats.getAuthRequests()).isEqualTo(0);
        assertThat(stats.getTotalRequests()).isEqualTo(0);
    }
}

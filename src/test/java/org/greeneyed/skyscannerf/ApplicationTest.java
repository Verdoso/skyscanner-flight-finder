package org.greeneyed.skyscannerf;

import org.greeneyed.skyscannerf.service.SkyScannerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

    @MockBean
    SkyScannerService skyScannerService;

    @Test
    void contextLoads() {
    }

}

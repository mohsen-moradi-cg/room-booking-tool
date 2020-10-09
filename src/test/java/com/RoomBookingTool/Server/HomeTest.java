package com.RoomBookingTool.Server;

import com.RoomBookingTool.Server.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeTest {

    @Value("${app.version}")
    public String appVersion;

    @Autowired
    private HomeController homeController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
       assertThat(homeController).isNotNull();
    }

    @Test
    public void testAppVersion() {
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/",
                String.class)).contains("1.0.0");
    }
}

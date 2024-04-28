package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

public abstract class AbstractWiremockTest {
    protected static WireMockServer wireMockServer;
    protected static String baseUrl;

    @BeforeAll
    public static void setUpWireMockServer() {
        wireMockServer = new WireMockServer(3000);
        wireMockServer.start();
        baseUrl = "http://localhost:" + wireMockServer.port();
    }

    @AfterEach
    public void resetMocks() {
        wireMockServer.resetAll();
    }

    @AfterAll
    public static void stopWireMockServer() {
        wireMockServer.stop();
    }
}

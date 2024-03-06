package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class WireMockBaseTest {

    protected static WireMockServer wireMockServer;
    protected WebClient webClient;

    @BeforeAll
    static void globalSetup() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @BeforeEach
    void setup() {
        webClient = WebClient.builder().baseUrl(wireMockServer.baseUrl()).build();
    }

    @AfterEach
    void tearDown() {
        WireMock.resetAllRequests();
    }

    @AfterAll
    static void globalTearDown() {
        wireMockServer.stop();
    }
}

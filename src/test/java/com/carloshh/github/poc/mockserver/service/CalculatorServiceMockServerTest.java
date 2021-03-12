package com.carloshh.github.poc.mockserver.service;

import com.carloshh.github.poc.mockserver.client.CalculatorClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(properties = { "calculator-service-client-url=localhost:9090" })
@DisplayName("Integration tests with mock server for calculator-service")
class CalculatorServiceMockServerTest {

	@Autowired
	private CalculatorClient calculatorClient;

	@Autowired
	private CalculatorService calculatorService;

	private static ClientAndServer mockServer;

	@BeforeAll
	public static void startServer() {
		mockServer = startClientAndServer(9090);
	}

	@AfterAll
	public static void stopServer() {
		mockServer.stop();
	}

	@Test
	@DisplayName("Mock work as expected")
	void testMockCalculatorService1() {
		new MockServerClient("127.0.0.1", 9090)
				.when(
						request()
								.withMethod("GET")
								.withPath("/add")
								.withQueryStringParameters(new Parameter("a", "1"), new Parameter("b", "1")),
						exactly(1))
				.respond(
						response()
								.withStatusCode(200)
								.withHeaders(new Header(HttpHeaders.CONTENT_TYPE, 	MediaType.APPLICATION_JSON_VALUE))
								.withBody("2")
								.withDelay(TimeUnit.SECONDS,1)
				);

		var result = calculatorService.calculate(1, 1);

		assertThat(result).isEqualTo(2);
	}

	@Test
	@DisplayName("Mock work as expected with multiple tests")
	void testMockCalculatorService2() {
		new MockServerClient("127.0.0.1", 9090)
				.when(
						request()
								.withMethod("GET")
								.withPath("/add")
								.withQueryStringParameters(new Parameter("a", "2"), new Parameter("b", "2")),
						exactly(1))
				.respond(
						response()
								.withStatusCode(200)
								.withHeaders(new Header(HttpHeaders.CONTENT_TYPE,	MediaType.APPLICATION_JSON_VALUE))
								.withBody("4")
								.withDelay(TimeUnit.SECONDS,1)
				);

		var result = calculatorService.calculate(2, 2);

		assertThat(result).isEqualTo(4);
	}

}

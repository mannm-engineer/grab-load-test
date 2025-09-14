package com.grab.test.load;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GrabServiceSimulation extends Simulation {

  // Define HTTP protocol (adjust base URL to your Spring Boot app)
  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:8080");

  // Scenario for getting drivers
  private ScenarioBuilder getDriversScenarioBuilder = scenario("Load Test: Get Drivers")
    .exec(
      http("Get Drivers")
        .get("/drivers")
        .check(status().is(200)) // expect OK
    );

  {
    setUp(
      getDriversScenarioBuilder.injectOpen(
        rampUsers(60000).during(60) // 10 users over 10 seconds
      )).protocols(httpProtocol);
  }
}

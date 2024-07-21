package org.sanjiv.requestvalidator.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HealthCheckHandler implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckHandler.class);

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info("Handling HealthCheck");
    routingContext.response().end("OK");
  }
}

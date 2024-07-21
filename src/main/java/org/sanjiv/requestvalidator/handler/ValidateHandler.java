package org.sanjiv.requestvalidator.handler;

import com.google.inject.Inject;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.sanjiv.requestvalidator.model.Request;
import org.sanjiv.requestvalidator.service.ValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateHandler implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ValidateHandler.class);

  @Inject ValidateService validateService;

  @Override
  public void handle(RoutingContext routingContext) {
    LOGGER.info("Handling Validate");
    Request request = new Request();
    validateService
        .validate(request)
        .onComplete(
            res -> {
              if (res.succeeded()) {
                routingContext.response().end("OK");
              } else {
                routingContext.response().end("NOT OK");
              }
            });
  }
}

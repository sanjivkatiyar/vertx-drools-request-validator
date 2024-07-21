package org.sanjiv.requestvalidator.router;

import com.google.inject.Inject;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.micrometer.PrometheusScrapingHandler;
import org.sanjiv.requestvalidator.handler.HealthCheckHandler;
import org.sanjiv.requestvalidator.handler.ValidateHandler;

public class AppRouter {

  @Inject HealthCheckHandler healthCheckHandler;
  @Inject ValidateHandler validateHandler;

  public Router prepareRoutes() {

    Vertx vertx = Vertx.vertx();
    final Router router = Router.router(vertx);
    router.get("/health").handler(healthCheckHandler);
    router.post("/validate").handler(validateHandler);
    router.get("/metrics").handler(PrometheusScrapingHandler.create());
    return router;
  }
}

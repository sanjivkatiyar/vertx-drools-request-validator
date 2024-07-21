package org.sanjiv.requestvalidator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.sanjiv.requestvalidator.guice.AppModule;
import org.sanjiv.requestvalidator.logger.EventLogger;
import org.sanjiv.requestvalidator.router.AppRouter;

public class StartUp extends AbstractVerticle {

  public static final EventLogger LOGGER = EventLogger.getLogger(StartUp.class);

  public static void main(String[] args) {

    PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    new UptimeMetrics().bindTo(registry);
    Vertx vertx =
        Vertx.vertx(
            new VertxOptions()
                .setMetricsOptions(
                    new MicrometerMetricsOptions()
                        .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
                        .setJvmMetricsEnabled(true)
                        .setMicrometerRegistry(registry)
                        .setEnabled(true)));
    vertx.exceptionHandler(
        err -> {
          LOGGER.error("Unhandled: ", err.getCause());
        });

    vertx.deployVerticle(
        new StartUp(),
        ar -> {
          if (ar.failed()) {
            LOGGER.error("Failed to deploy: ", ar.cause());
          }
        });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Injector injector = Guice.createInjector(new AppModule());
    AppRouter appRouter = injector.getInstance(AppRouter.class);

    vertx
        .createHttpServer()
        .requestHandler(appRouter.prepareRoutes())
        .listen(
            8888,
            http -> {
              if (http.succeeded()) {
                startPromise.complete();
              } else {
                startPromise.fail(http.cause());
              }
            });
  }
}

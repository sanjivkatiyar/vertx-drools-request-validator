package org.sanjiv.requestvalidator.service;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import org.kie.api.KieServices;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.StatelessKieSession;
import org.sanjiv.requestvalidator.constant.KieConstants;
import org.sanjiv.requestvalidator.kie.KieSessionProvider;
import org.sanjiv.requestvalidator.model.Request;
import org.sanjiv.requestvalidator.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateService {

  public static final Logger LOGGER = LoggerFactory.getLogger(ValidateService.class);

  @Inject private KieSessionProvider kieSessionProvider;

  public Future<Response> validate(Request request) {
    Promise<Response> promise = Promise.promise();
    StatelessKieSession kieSession;
    try {
      kieSession =
          kieSessionProvider.getKieSession(
              KieConstants.KSESSION + KieConstants.REQUEST_VALIDATE_FLOW);
      final KieCommands kieCommands = KieServices.Factory.get().getCommands();

      kieSession.execute(
          kieCommands.newBatchExecution(
              Lists.newArrayList(
                  kieCommands.newInsert(request),
                  kieCommands.newStartProcess(KieConstants.REQUEST_VALIDATE_FLOW),
                  kieCommands.newFireAllRules(),
                  kieCommands.newDispose())));
      LOGGER.error("BPM execution success");

      promise.complete();
    } catch (Exception ex) {
      LOGGER.error("Failed executing BPM ", ex);

      promise.fail(ex.getCause());
    }
    return promise.future();
  }
}

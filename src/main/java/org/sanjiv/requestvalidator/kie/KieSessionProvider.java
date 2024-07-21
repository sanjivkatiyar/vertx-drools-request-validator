package org.sanjiv.requestvalidator.kie;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.sanjiv.requestvalidator.constant.KieConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieSessionProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(KieSessionProvider.class);

  private final KieContainer kieContainer;

  private final Map<String, StatelessKieSession> sessions = new HashMap<>();

  public KieSessionProvider() {
    kieContainer = KieServices.Factory.get().newKieClasspathContainer();
    createSession();
  }

  private void createSession() {
    Instant start = Instant.now();

    LOGGER.info("Starting Kie session");

    sessions.put(
        KieConstants.REQUEST_VALIDATE_FLOW,
        kieContainer.newStatelessKieSession(
            KieConstants.KSESSION + KieConstants.REQUEST_VALIDATE_FLOW));

    LOGGER.info("Started Kie Session in {} ms", Duration.between(start, Instant.now()).toMillis());
  }

  public StatelessKieSession getKieSession(String flowName) {

    if (sessions.containsKey(flowName)) {
      LOGGER.info("Returning existing kie session..");
      return sessions.get(flowName);
    }

    StatelessKieSession kieSession = kieContainer.newStatelessKieSession(flowName);
    sessions.put(flowName, kieSession);
    LOGGER.info("Returning new kie session..");
    return kieSession;
  }
}

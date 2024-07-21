package org.sanjiv.requestvalidator.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.slf4j.Log4jLogger;
import org.apache.logging.slf4j.Log4jMarkerFactory;

public class EventLogger extends Log4jLogger {
  private static final LoggerContext loggerContext = LogManager.getContext(false);
  private final EventCodeWrapper eventCodeWrapper;

  public EventLogger(EventCodeWrapper logger, String name) {
    super(new Log4jMarkerFactory(), logger, name);
    eventCodeWrapper = logger;
  }

  public EventLogger withAdditionalField(String key, String value) {
    eventCodeWrapper.setAdditionalField(key, value);
    return this;
  }

  public EventLogger withEventCode(String eventCode) {
    eventCodeWrapper.setEventCode(eventCode);
    return this;
  }

  public EventLogger withErrorCode(String errorCode) {
    eventCodeWrapper.setErrorCode(errorCode);
    return this;
  }

  public static EventLogger getLogger(String name) {
    return new EventLogger(
        new EventCodeWrapper(FQCN, loggerContext.getLogger(name), name, null), name);
  }

  public static EventLogger getLogger(Class<?> clazz) {
    return getLogger(clazz.getName());
  }
}

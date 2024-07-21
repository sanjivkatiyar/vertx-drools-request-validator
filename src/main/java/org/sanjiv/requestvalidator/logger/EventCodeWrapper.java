package org.sanjiv.requestvalidator.logger;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;
import org.slf4j.MDC;

public class EventCodeWrapper extends ExtendedLoggerWrapper {
  private String fcqn;
  private transient ThreadLocal<Map<String, String>> tags;

  public EventCodeWrapper(
      String fcqn, ExtendedLogger logger, String name, MessageFactory messageFactory) {
    super(logger, name, messageFactory);
    this.fcqn = fcqn;
    tags = ThreadLocal.withInitial(HashMap::new);
  }

  public void logMessage(
      final String fcqn,
      final Level level,
      final Marker marker,
      final Message message,
      final Throwable t) {
    Map<String, String> tagMap = tags.get();
    try {
      tagMap.forEach(MDC::put);
      super.logMessage(this.fcqn, level, marker, message, t);
    } finally {
      tagMap.keySet().forEach(MDC::remove);
      tags.remove();
    }
  }

  public void setAdditionalField(String key, String value) {
    tags.get().put(key, value);
  }

  public void setEventCode(String eventCode) {
    tags.get().put("eventCode", eventCode);
  }

  public void setErrorCode(String errorCode) {
    tags.get().put("errorCode", errorCode);
  }
}

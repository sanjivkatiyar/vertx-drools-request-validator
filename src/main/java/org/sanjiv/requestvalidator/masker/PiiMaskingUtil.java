package org.sanjiv.requestvalidator.masker;

import java.lang.reflect.Field;
import org.sanjiv.requestvalidator.logger.EventLogger;

public class PiiMaskingUtil {

  public static final EventLogger LOGGER = EventLogger.getLogger(PiiMaskingUtil.class);

  /**
   * Masks PII fields in the given object.
   *
   * @param obj the object to be masked
   * @return a string representation of the object with PII fields masked
   */
  public static String maskPii(Object obj) {
    if (obj == null) {
      return "null";
    }

    StringBuilder result = new StringBuilder(obj.getClass().getSimpleName() + " {");

    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        String fieldName = field.getName();
        Object value = field.get(obj);
        if (field.isAnnotationPresent(Mask.class)) {
          value = maskValue(value);
        }
        result.append(fieldName).append("=").append(value).append(", ");
      } catch (IllegalAccessException e) {
        // Handle the exception as per your need
        LOGGER.error("Exception while masking: " + e);
      }
    }

    if (result.length() > obj.getClass().getSimpleName().length() + 2) {
      result.setLength(result.length() - 2); // Remove the trailing comma and space
    }

    result.append("}");
    return result.toString();
  }

  /**
   * Masks the value of a PII field.
   *
   * @param value the original value
   * @return the masked value
   */
  private static String maskValue(Object value) {
    if (value == null) {
      return "null";
    }
    String strValue = value.toString();
    int length = strValue.length();
    if (length <= 4) {
      return "****";
    } else {
      return "****" + strValue.substring(length - 4);
    }
  }
}

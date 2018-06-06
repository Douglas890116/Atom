package com.maven.utility;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseEnumUtil {
  @SuppressWarnings("rawtypes")
  public static LinkedHashMap<String, String> enumToMap(Class clazz) throws IllegalArgumentException, IllegalAccessException {
    LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
    if (clazz.isEnum()) {
      Enum[] enums = (Enum[]) clazz.getEnumConstants();
      LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
      for (Enum t : enums) {
        for (Field field : Reflector.getClassFields(t.getClass())) {
          if (field.getName().equals("code")) {
            Object obj = field.get(t);
            enumToMap_putMap(map, t.name(), String.valueOf(obj), 0);
          } else if (field.getName().equals("name")) {
            Object obj = field.get(t);
            enumToMap_putMap(map, t.name(), String.valueOf(obj), 1);
          }
        }
      }
      for (Map.Entry<String, String[]> entity : map.entrySet()) {
        String[] v = entity.getValue();
        result.put(v[0], v[1]);
      }
    }
    return result;
  }

  private static void enumToMap_putMap(Map<String, String[]> map, String name, String value, int index) {
    if (map.containsKey(name)) {
      String[] v = map.get(name);
      v[index] = value;
    } else {
      String[] v = new String[2];
      v[index] = value;
      map.put(name, v);
    }
  }
}

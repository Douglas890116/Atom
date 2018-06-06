package com.maven.utility;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Reflector {

  private static Logger logger= LogManager.getLogger(Reflector.class.getName());

  private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new HashMap<Class<?>, List<Field>>();

  private static final Map<Class<?>, Map<String, Method[]>> CLASS_METHOD_CACHE = new HashMap<Class<?>, Map<String, Method[]>>();

  private static final Map<Class<?>, Map<String, Field>> CLASS_FIELD_KV_CACHE = new HashMap<Class<?>, Map<String, Field>>();

  public static Map<String, Field> getClassFieldMap(Class<?> clazz) {
    Map<String, Field> fields = CLASS_FIELD_KV_CACHE.get(clazz);
    if (fields == null) {
      synchronized (clazz) {
        if (!CLASS_FIELD_KV_CACHE.containsKey(clazz)) {
          CLASS_FIELD_KV_CACHE.put(clazz, fields = collectFieldMap(clazz));
        } else {
          fields = CLASS_FIELD_KV_CACHE.get(clazz);
        }
      }
    }
    return fields;
  }

  public static List<Field> getClassFields(Class<?> clazz) {
    List<Field> fields = CLASS_FIELD_CACHE.get(clazz);
    if (fields == null) {
      synchronized (clazz) {
        if (!CLASS_FIELD_CACHE.containsKey(clazz)) {
          CLASS_FIELD_CACHE.put(clazz, fields = collectFields(clazz));
        } else {
          fields = CLASS_FIELD_CACHE.get(clazz);
        }
      }
    }
    return fields;
  }

  public static Map<String, Method[]> getClassMethods(Class<?> clazz) {
    Map<String, Method[]> methods = CLASS_METHOD_CACHE.get(clazz);
    if (methods == null) {
      synchronized (clazz) {
        if (!CLASS_METHOD_CACHE.containsKey(clazz)) {
          CLASS_METHOD_CACHE.put(clazz, methods = collectMethods(clazz));
        } else {
          methods = CLASS_METHOD_CACHE.get(clazz);
        }
      }
    }
    return methods;
  }

  public static Method getter(Class<?> clazz, Field field) {
    Map<String, Method[]> map = getClassMethods(clazz);
    Method[] methods = map.get(field.getName());
    return methods[1];
  }

  public static Method setter(Class<?> clazz, Field field) {
    Map<String, Method[]> map = getClassMethods(clazz);
    Method[] methods = map.get(field.getName());
    return methods[0];
  }

  private static Map<String, Method[]> collectMethods(Class<?> clazz) {
    Map<String, Method[]> result = new HashMap<String, Method[]>();

    List<Field> fields = new ArrayList<Field>();
    List<Method> methods = new ArrayList<Method>();

    Class<?> superClazz = clazz;
    while (superClazz != null && superClazz != Object.class) {
      fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
      methods.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
      superClazz = superClazz.getSuperclass();
    }

    for (Field field : fields) {
      if (field.getName().equals("dbname")) {
        continue;
      }
      int mod = field.getModifiers();
      if (Modifier.isFinal(mod)) {
        continue;
      }
      if (result.containsKey(field.getName())) {
        continue;
      }
      for (Method method : methods) {
        if (collect(method, field, result)) {
          break;
        }
      }
    }
    return result;
  }

  private static boolean collect(Method method, Field field, final Map<String, Method[]> cache) {
    String fieldname = field.getName().toLowerCase();
    String methodname = method.getName();
    if (methodname.startsWith("set")) {
      String setTmp = methodname.substring("set".length()).toLowerCase();
      if (fieldname.equals(setTmp)) {
        Method[] methods = cache.get(field.getName());
        if (methods == null) {
          methods = new Method[2];
          cache.put(field.getName(), methods);
        }
        return isFill(methods, 0, method);
      }
    } else if (methodname.startsWith("get") || methodname.startsWith("is")) {
      String getTmp = methodname.substring("get".length()).toLowerCase();
      String getTmp1 = methodname.substring("is".length()).toLowerCase();
      if (fieldname.equals(getTmp) || fieldname.equals(getTmp1)) {
        Method[] methods = cache.get(field.getName());
        if (methods == null) {
          methods = new Method[2];
          cache.put(field.getName(), methods);
        }
        return isFill(methods, 1, method);
      }
    }
    return false;
  }

  private static boolean isFill(final Method[] methods, int i, Method m) {
    methods[i] = m;
    return (methods[0] == null || methods[1] == null) ? false : true;
  }

  @SuppressWarnings("unused")
  private static List<Field> collectDeclaredFields(Class<?> clazz) {
    List<Field> list = new ArrayList<Field>();
    AccessibleObject[] fields = clazz.getDeclaredFields();
    for (AccessibleObject ao : fields) {
      ao.setAccessible(true);
      Field field = (Field) ao;
      int mod = field.getModifiers();
      if (Modifier.isFinal(mod)) {
        continue;
      }
      if (field.getName().equals("dbname")) {
        continue;
      }
      list.add(field);
    }
    return list;
  }

  private static List<Field> collectFields(Class<?> clazz) {
    List<Field> list = new ArrayList<Field>();
    List<AccessibleObject> fields = new ArrayList<AccessibleObject>();
    Class<?> superClazz = clazz;
    while (superClazz != null && superClazz != Object.class) {
      fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
      superClazz = superClazz.getSuperclass();
    }
    for (AccessibleObject ao : fields) {
      ao.setAccessible(true);
      Field field = (Field) ao;
      int mod = field.getModifiers();
      if (Modifier.isFinal(mod)) {
        continue;
      }
      list.add(field);
    }
    return list;
  }

  private static Map<String, Field> collectFieldMap(Class<?> clazz) {
    Map<String, Field> map = new HashMap<String, Field>();
    List<AccessibleObject> fields = new ArrayList<AccessibleObject>();
    Class<?> superClazz = clazz;
    while (superClazz != null && superClazz != Object.class) {
      fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
      superClazz = superClazz.getSuperclass();
    }
    for (AccessibleObject ao : fields) {
      ao.setAccessible(true);
      Field field = (Field) ao;
      int mod = field.getModifiers();
      if (Modifier.isFinal(mod)) {
        continue;
      }
      map.put(field.getName(), field);
    }
    return map;
  }

  public static Object newObjectOnField(final ResultSet rs, Class<?> clazz) throws SQLException {
    Object clazzObj = null;
    try {
      clazzObj = clazz.newInstance();
    } catch (Exception e) {
      logger.error(clazz.getName() + " .newInstance fail", e);
    }
    if (clazzObj != null) {
      List<Field> fields = getClassFields(clazz);
      try {
        for (Field field : fields) {
          Object value = rs.getObject(field.getName());
          if (value == null) {
            continue;
          }
          field.set(clazzObj, value);
        }
      } catch (Exception e) {
        logger.warn(clazz.getName() + " field.set() fail", e);
        throw new SQLException(e);
      }
    }
    return clazzObj;
  }

  public static Object newObjectOnMethod(final ResultSet rs, Class<?> clazz) throws SQLException {
    Object clazzObj = null;
    try {
      clazzObj = clazz.newInstance();
    } catch (Exception e) {
      logger.error(clazz.getName() + " .newInstance fail", e);
    }
    if (clazzObj != null) {
      Map<String, Method[]> methods = getClassMethods(clazz);
      try {
        for (Map.Entry<String, Method[]> entry : methods.entrySet()) {
          Object value = rs.getObject(entry.getKey());
          if (value == null) {
            continue;
          }
          entry.getValue()[0].invoke(clazzObj, new Object[] {
            value });
        }
      } catch (Exception e) {
        logger.warn(clazz.getName() + " Field Setter Method fail", e);
        throw new SQLException(e);
      }
    }
    return clazzObj;
  }

  public static Object getValue(final Object obj, Field field) {
    Object value = null;
    Method method = getter(obj.getClass(), field);
    try {
      // value = method.invoke(entity, null);
      value = method.invoke(obj, new Object[] {});
    } catch (Exception e) {
      logger.warn(obj.getClass().getName() + " reflect get " + field.getName() + " value fail");
    }
    return value;
  }

  public static Object getValue(final Object obj, String fieldName) {
    Object value = null;
    Field field = getClassFieldMap(obj.getClass()).get(fieldName);
    if (field != null) {
      try {
        value = field.get(obj);
      } catch (Exception e) {
        logger.warn(obj.getClass().getName() + " reflect get " + fieldName + " value fail");
      }
    }
    return value;
  }

  public static Object getDeepValue(final Object obj, String fieldName) {
    String[] fields = fieldName.split("\\.");
    Object value = obj;
    int num = fields.length;
    int i = 0;
    while (num > i) {
      String name = fields[i++];
      Field field = getClassFieldMap(value.getClass()).get(name);
      if (field == null) {
        value = null;
        break;
      } else {
        try {
          value = field.get(value);
        } catch (Exception e) {
          logger.warn(value.getClass().getName() + " reflect get " + field.getName() + " value fail");
        }
      }
    }
    return value;
  }

  public static List<List<Object>> fieldsValues(final List<Object> objs) {
    List<List<Object>> result = new ArrayList<List<Object>>();
    for (Object obj : objs) {
      result.add(fieldsValue(obj));
    }
    return result;
  }

  public static List<Object> fieldsValue(final Object obj) {
    List<Field> fields = getClassFields(obj.getClass());
    List<Object> result = new ArrayList<Object>();
    for (Field field : fields) {
      Object value = getValue(obj, field);
      result.add(value);
    }
    return result;
  }

  public static Map<Field, Object> fieldsValue_(final Object obj) {
    List<Field> fields = getClassFields(obj.getClass());
    Map<Field, Object> result = new LinkedHashMap<Field, Object>();
    for (Field field : fields) {
      result.put(field, getValue(obj, field));
    }
    return result;
  }

  public static List<Map<Field, Object>> fieldsValues_(final List<Object> objs) {
    List<Map<Field, Object>> result = new ArrayList<Map<Field, Object>>();
    for (Object obj : objs) {
      result.add(fieldsValue_(obj));
    }
    return result;
  }

  public static void clone(final Map<String, Object> map, Object obj) {
    if (map == null || map.isEmpty() || obj == null) {
      return;
    }
    Map<String, Field> cache = getClassFieldMap(obj.getClass());
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      Field field = cache.get(key);
      if (field != null) {
        try {
          field.set(obj, value);
        } catch (Exception e) {
          logger.warn(obj.getClass().getName() + " field[" + key + "].set() fail", e);
        }
      }
    }
  }

  public static Object clone(final Map<String, Object> map, Class<?> clazz) {
    if (map == null || map.isEmpty() || clazz == null) {
      return null;
    }
    Object obj = null;
    try {
      obj = clazz.newInstance();
    } catch (Exception e) {
      logger.error(clazz.getName() + " .newInstance fail", e);
    }
    clone(map, obj);
    return obj;
  }

  public static boolean isAllFieldsNotNull(final Object obj) {
    List<Field> fields = getClassFields(obj.getClass());
    try {
      for (Field field : fields) {
        if (field.get(obj) == null) {
          return false;
        }
      }
    } catch (Exception e) {
      logger.error(obj + " get field value fail", e);
      return false;
    }
    return true;
  }

  @SuppressWarnings("rawtypes")
  public static boolean isAllFieldsNotNull(final Object obj, String properies) {
    if (properies == null || properies.trim().equals(""))
      return true;
    String[] _properies = properies.split(",");

    if (obj instanceof Map) {
      Map map = (Map) obj;
      for (String key : _properies) {
        if (map.get(key) == null) {
          return false;
        }
      }
    } else {
      Map<String, Field> fields = getClassFieldMap(obj.getClass());
      try {
        for (int i = 0; i < _properies.length; i++) {
          if (fields.get(_properies[i]).get(obj) == null) {
            return false;
          }
        }
      } catch (Exception e) {
        logger.error(obj + " get field value fail", e);
        return false;
      }
    }
    return true;
  }

  public static void clone(Object target, Object source) {
    if (target == null || source == null) {
      return;
    }
    Map<String, Field> targetMap = getClassFieldMap(target.getClass());
    Map<String, Field> sourceMap = getClassFieldMap(source.getClass());
    for (Map.Entry<String, Field> entry : sourceMap.entrySet()) {
      Field field = targetMap.get(entry.getKey());
      if (field != null) {
        try {
          Object value = entry.getValue().get(source);
          field.set(target, value);
        } catch (Exception e) {
          logger.warn(target.getClass().getName() + " | " + source.getClass().getName() + " clone field value fail", e);
        }
      }
    }
  }

  public static Object merge(Class<?> clazz, Object... objs) {
    Object clazzObj = null;
    try {
      clazzObj = clazz.newInstance();
    } catch (Exception e) {
      logger.error(clazz.getName() + " .newInstance fail", e);
    }
    if (clazzObj != null && objs.length != 0) {
      for (Object obj : objs) {
        clone(clazzObj, obj);
      }
    }
    return clazzObj;
  }

  public static void cloneIncludeEnum(Object target, Object source) {
    if (target == null || source == null) {
      return;
    }
    Map<String, Field> targetMap = getClassFieldMap(target.getClass());
    Map<String, Field> sourceMap = getClassFieldMap(source.getClass());
    for (Map.Entry<String, Field> entry : sourceMap.entrySet()) {
      Field field = targetMap.get(entry.getKey());
      if (field != null) {
        try {
          Object value = entry.getValue().get(source);
          if (value == null) {
            continue;
          }
          Class<?> clazzS = entry.getValue().getType();
          Class<?> clazzT = field.getType();
          if (clazzS.isEnum() && !clazzT.isEnum()) {
            Method method = null;
            try {
              method = clazzS.getMethod("getCode", new Class<?>[] {});
            } catch (NoSuchMethodException e) {
            }
            if (method == null) {
              continue;
            }
            value = method.invoke(value, new Object[] {});
          } else if (clazzT.isEnum() && !clazzS.isEnum()) {
            Method method = null;
            try {
              method = clazzT.getMethod("getEnum", value.getClass());
            } catch (NoSuchMethodException e) {
              if (value.getClass() == Integer.class) {
                method = clazzT.getMethod("getEnum", int.class);
              }
            }
            if (method == null) {
              continue;
            }
            value = method.invoke(null, value);
          }
          field.set(target, value);
        } catch (Exception e) {
          logger.warn(target.getClass().getName() + " | " + source.getClass().getName() + " clone field value fail", e);
        }
      }
    }
  }

  public static Object cloneIncludeEnum(Map<String, Object> source, Class<?> clazz) {
    if (source == null || source.isEmpty() || clazz == null) {
      return null;
    }
    Object clazzObj = null;
    try {
      clazzObj = clazz.newInstance();
    } catch (Exception e) {
      logger.error(clazz.getName() + " .newInstance fail", e);
      return null;
    }

    Map<String, Field> targetMap = getClassFieldMap(clazz);
    for (Map.Entry<String, Object> entry : source.entrySet()) {
      Object obj = entry.getValue();
      if (obj == null) {
        continue;
      }
      Field field = targetMap.get(entry.getKey());
      if (field != null) {
        try {
          Class<?> clazzS = entry.getValue().getClass();
          Class<?> clazzT = field.getType();
          if (clazzS.isEnum() && !clazzT.isEnum()) {
            Method method = null;
            try {
              method = clazzS.getMethod("getCode", new Class<?>[] {});
            } catch (NoSuchMethodException e) {
            }
            if (method == null) {
              continue;
            }
            obj = method.invoke(obj, new Object[] {});
          } else if (clazzT.isEnum() && !clazzS.isEnum()) {
            Method method = null;
            try {
              method = clazzT.getMethod("getEnum", obj.getClass());
            } catch (NoSuchMethodException e) {
              if (obj.getClass() == Integer.class) {
                method = clazzT.getMethod("getEnum", int.class);
              }
            }
            if (method == null) {
              continue;
            }
            obj = method.invoke(null, obj);
          }
          field.set(clazzObj, obj);
        } catch (Exception e) {
          logger.warn(e.getMessage(), e);
        }
      }
    }
    return clazzObj;
  }

  public static Object mergeIncludeEnum(Class<?> clazz, Object... objs) {
    Object clazzObj = null;
    try {
      clazzObj = clazz.newInstance();
    } catch (Exception e) {
      logger.error(clazz.getName() + " .newInstance fail", e);
    }
    if (clazzObj != null && objs.length != 0) {
      for (Object obj : objs) {
        cloneIncludeEnum(clazzObj, obj);
      }
    }
    return clazzObj;
  }

  @SuppressWarnings({
      "unchecked", "rawtypes" })
  public static <T> List<T> fetchFieldValue(List<?> list, String field) {
    List<T> result = new ArrayList<T>();
    if (list == null || list.isEmpty() || field == null || field.trim().length() == 0) {
      return result;
    }
    for (Object obj : list) {
      Object value = null;
      if (obj instanceof Map) {
        value = ((Map) obj).get(field);
      } else {
        value = getValue(obj, field);
      }
      if (value != null) {
        result.add((T) value);
      }
    }
    return result;
  }

  @SuppressWarnings({
      "unchecked", "rawtypes" })
  public static <T> List<T> fetchDeepFieldValue(List<?> list, String field) {
    List<T> result = new ArrayList<T>();
    if (list == null || list.isEmpty() || field == null || field.trim().length() == 0) {
      return result;
    }
    for (Object obj : list) {
      Object value = null;
      if (obj instanceof Map) {
        value = ((Map) obj).get(field);
      } else {
        value = getDeepValue(obj, field);
      }
      if (value != null) {
        result.add((T) value);
      }
    }
    return result;
  }
  
  @SuppressWarnings("all")
  public static void setClassField(Class<?> clazz, String fieldName, Long val, Object t) {
    Map<String, Field> map = Reflector.getClassFieldMap(clazz);
    Field attribute = (Field)map.get(fieldName);
    if(attribute != null){
      Method getMethod = Reflector.getter(clazz, attribute);
      try {
        Object value = getMethod.invoke(t, null);
        if(value == null){
          Method setMethod = Reflector.setter(clazz, attribute);
          setMethod.invoke(t, val);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

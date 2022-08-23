package com.demo.lab.utils;

import com.demo.lab.base.Entity;
import com.demo.lab.base.HandleException;
import com.demo.lab.base.ResponseFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class JSONUtils {

    private static Object scriptObjToMap(Object scriptObj) {
        if (scriptObj instanceof ScriptObjectMirror) {
            ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) scriptObj;
            if (scriptObjectMirror.isArray()) {
                List<Object> list = new ArrayList<>();
                for (Map.Entry<String, Object> entry : scriptObjectMirror.entrySet()) {
                    list.add(scriptObjToMap(entry.getValue()));
                }
                return list;
            } else {
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<String, Object> entry : scriptObjectMirror.entrySet()) {
                    map.put(entry.getKey(), scriptObjToMap(entry.getValue()));
                }
                return map;
            }
        } else {
            return scriptObj;
        }
    }

    public static  <T extends Entity> T mapToObject(Map<String, Object> map, Class<T> bodyType) {
        try {
            T result = bodyType.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                try {
                    Field declaredField = result.getClass().getDeclaredField(entry.getKey());
                    declaredField.setAccessible(true);
                    declaredField.set(result, entry.getValue());
                } catch (NoSuchFieldException noSuchFieldException) {

                }
            }
            return result;
        } catch (Exception e) {
            throw new HandleException(e.getMessage());
        }
    }

    public static Map<String, Object> fromString(String string) {
        try {
            ScriptEngine jsonEngine = new ScriptEngineManager().getEngineByName("nashorn");
            jsonEngine.put("body", string);
            ScriptObjectMirror scriptObj = (ScriptObjectMirror) jsonEngine.eval("JSON.parse(body)");
            Map<String, Object> map = (Map<String, Object>) scriptObjToMap(scriptObj);
            return map;
        } catch (ScriptException e) {
            throw new HandleException(e.getMessage());
        }
    }

    public static String toJsonString(Object object) {
        if (object == null) {
            return "\"\"";
        }
        if (object instanceof Boolean) {
            return object.toString();
        }
        if (object instanceof Number) {
            return String.valueOf(object);
        }
        if (object instanceof String) {
            return "\"" + object + "\"";
        }
        if (object instanceof List) {
            List<Object> list = (List<Object>) object;
            return list.stream().map(obj -> toJsonString(obj)).collect(Collectors.joining(",", "[", "]"));
        }
        if (!(object instanceof JSONObject)) {
            throw new HandleException(ResponseFactory.internalError("please provide JSONObject or " +
                "simple date type to stringify"));
        }

        Field[] superFields = object.getClass().getSuperclass().getDeclaredFields();
        Field[] declaredFields = object.getClass().getDeclaredFields();

        List<Field> list = new ArrayList<>();
        list.addAll(Arrays.asList(superFields));
        list.addAll(Arrays.asList(declaredFields));

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < list.size(); i++) {
            Field field = list.get(i);
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(String.format("\"%s\": %s", field.getName(), toJsonString(value)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}");
        return sb.toString();
    }
}

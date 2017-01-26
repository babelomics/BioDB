package org.babelomics.biodb.lib.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by mmedina on 18/11/16.
 */
public class ObjectMap implements Map<String, Object>, Serializable {
    private Map<String, Object> objectMap;
    protected ObjectMapper jsonObjectMapper = new ObjectMapper();

    public ObjectMap() {
        this.objectMap = new LinkedHashMap();
    }

    public ObjectMap(int size) {
        this.objectMap = new LinkedHashMap(size);
    }

    public ObjectMap(String key, Object value) {
        this.objectMap = new LinkedHashMap();
        this.objectMap.put(key, value);
    }

    public ObjectMap(Map<String, Object> map) {
        this.objectMap = new LinkedHashMap(map);
    }

    public ObjectMap(String json) {
        try {
            this.objectMap = new LinkedHashMap();
            this.objectMap.putAll((Map) this.jsonObjectMapper.readValue(json, this.objectMap.getClass()));
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public String toJson() {
        try {
            return this.jsonObjectMapper.writeValueAsString(this.objectMap);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public String safeToString() {
        Iterator iter = this.objectMap.keySet().iterator();
        StringBuilder sb = new StringBuilder("{\n");

        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (!key.equals("result")) {
                sb.append("\t" + key + ": " + this.objectMap.get(key) + ",\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public String getString(String field) {
        return this.getString(field, "");
    }

    public String getString(String field, String defaultValue) {
        return field != null && this.objectMap.containsKey(field) ? (this.objectMap.get(field) != null ? this.objectMap.get(field).toString() : null) : defaultValue;
    }

    public int getInt(String field) {
        return this.getInt(field, 0);
    }

    public int getInt(String field, int defaultValue) {
        if (field != null && this.objectMap.containsKey(field)) {
            Object obj = this.objectMap.get(field);
            if (obj instanceof Number) {
                return ((Number) obj).intValue();
            }

            if (obj instanceof String) {
                try {
                    return Integer.parseInt((String) obj);
                } catch (NumberFormatException var5) {
                    ;
                }
            }
        }

        return defaultValue;
    }

    public long getLong(String field) {
        return this.getLong(field, 0L);
    }

    public long getLong(String field, long defaultValue) {
        if (field != null && this.objectMap.containsKey(field)) {
            Object obj = this.objectMap.get(field);
            if (obj instanceof Number) {
                return ((Number) obj).longValue();
            }

            if (obj instanceof String) {
                try {
                    return Long.parseLong((String) obj);
                } catch (NumberFormatException var6) {
                    ;
                }
            }
        }

        return defaultValue;
    }

    public float getFloat(String field) {
        return this.getFloat(field, 0.0F);
    }

    public float getFloat(String field, float defaultValue) {
        if (field != null && this.objectMap.containsKey(field)) {
            Object obj = this.objectMap.get(field);
            if (obj instanceof Number) {
                return ((Number) obj).floatValue();
            }

            if (obj instanceof String) {
                try {
                    return Float.parseFloat((String) obj);
                } catch (NumberFormatException var5) {
                    ;
                }
            }
        }

        return defaultValue;
    }

    public double getDouble(String field) {
        return this.getDouble(field, 0.0D);
    }

    public double getDouble(String field, double defaultValue) {
        if (field != null && this.objectMap.containsKey(field)) {
            Object obj = this.objectMap.get(field);
            if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            }

            if (obj instanceof String) {
                try {
                    return Double.parseDouble((String) obj);
                } catch (NumberFormatException var6) {
                    ;
                }
            }
        }

        return defaultValue;
    }

    public boolean getBoolean(String field) {
        return this.getBoolean(field, false);
    }

    public boolean getBoolean(String field, boolean defaultValue) {
        if (field != null && this.objectMap.containsKey(field)) {
            Object obj = this.objectMap.get(field);
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue();
            }

            if (obj instanceof String) {
                return Boolean.parseBoolean((String) this.objectMap.get(field));
            }
        }

        return defaultValue;
    }

    public <T> T get(String field, Class<T> clazz) {
        return this.get(field, clazz, null);
    }

    public <T> T get(String field, Class<T> clazz, T defaultValue) {
        if (this.objectMap.containsKey(field)) {
            Object obj = this.objectMap.get(field);
            if (clazz.isInstance(obj)) {
                return clazz.cast(obj);
            }
        }

        return defaultValue;
    }

    public List<Object> getList(String field) {
        return this.getList(field, (List) null);
    }

    public List<Object> getList(String field, List<Object> defaultValue) {
        return field != null && this.objectMap.containsKey(field) ? (List) this.objectMap.get(field) : defaultValue;
    }

    public List<String> getAsStringList(String field) {
        return this.getAsStringList(field, ",");
    }

    public List<String> getAsStringList(String field, String separator) {
        List list = this.getAsList(field, separator);
        if (!list.isEmpty() && list.get(0) instanceof String) {
            return list;
        } else {
            ArrayList stringList = new ArrayList(list.size());
            Iterator var5 = list.iterator();

            while (var5.hasNext()) {
                Object o = var5.next();
                stringList.add(o == null ? null : o.toString());
            }

            return stringList;
        }
    }

    public List<Integer> getAsIntegerList(String field) {
        return this.getAsIntegerList(field, ",");
    }

    public List<Integer> getAsIntegerList(String field, String separator) {
        List list = this.getAsList(field, separator);
        if (!list.isEmpty() && list.get(0) instanceof Integer) {
            return list;
        } else {
            ArrayList integerList = new ArrayList(list.size());

            int i;
            for (Iterator var5 = list.iterator(); var5.hasNext(); integerList.add(Integer.valueOf(i))) {
                Object o = var5.next();
                if (o instanceof Integer) {
                    i = ((Integer) o).intValue();
                } else {
                    i = Integer.parseInt(o.toString());
                }
            }

            return integerList;
        }
    }

    public <T> List<T> getAsList(String field, Class<T> clazz) {
        return this.getAsList(field, clazz, (List) null);
    }

    public <T> List<T> getAsList(String field, Class<T> clazz, List<T> defaultValue) {
        return field != null && this.objectMap.containsKey(field) ? (List) this.objectMap.get(field) : defaultValue;
    }

    public List<Object> getAsList(String field) {
        return this.getAsList(field, ",");
    }

    public List<Object> getAsList(String field, String separator) {
        Object value = this.get(field);
        return value == null ? Collections.emptyList() : (value instanceof List ? (List) value : Arrays.asList(value.toString().split(separator)));
    }

    public Map<String, Object> getMap(String field) {
        return this.getMap(field, (Map) null);
    }

    public Map<String, Object> getMap(String field, Map<String, Object> defaultValue) {
        return field != null && this.objectMap.containsKey(field) ? (Map) this.objectMap.get(field) : defaultValue;
    }


    public <T> Map<String, T> getMapAs(String field, Class<T> clazz) {
        return this.getMapAs(field, clazz, (Map) null);
    }


    public <T> Map<String, T> getMapAs(String field, Class<T> clazz, Map<String, T> defaultValue) {
        return field != null && this.objectMap.containsKey(field) ? (Map) this.objectMap.get(field) : defaultValue;
    }

    public int size() {
        return this.objectMap.size();
    }

    public boolean isEmpty() {
        return this.objectMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.objectMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.objectMap.containsValue(value);
    }

    public Object get(Object key) {
        return this.objectMap.get(key);
    }

    public Object put(String key, Object value) {
        return this.objectMap.put(key, value);
    }

    public Object remove(Object key) {
        return this.objectMap.remove(key);
    }

    public void putAll(Map<? extends String, ?> m) {
        this.objectMap.putAll(m);
    }

    public void clear() {
        this.objectMap.clear();
    }

    public Set<String> keySet() {
        return this.objectMap.keySet();
    }

    public Collection<Object> values() {
        return this.objectMap.values();
    }

    public Set<Entry<String, Object>> entrySet() {
        return this.objectMap.entrySet();
    }
}

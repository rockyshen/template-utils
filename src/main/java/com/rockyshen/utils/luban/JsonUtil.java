package com.rockyshen.utils.luban;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.NullNode;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2025/12/15 10:59
 */
public final class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final String ERROR_JSON = "%s not a json string";
    private static final String PARANTHESES_LEFT = "{";
    private static final String PARANTHESES_RIGHT = "}";
    private static final String BRACKET_LEFT = "[";
    private static final String BRACKET_RIGHT = "]";
    private static ObjectMapper mapper;

    private JsonUtil() {
    }

    public static boolean isJson(String text) {
        if (EmptyUtil.isEmpty(text)) {
            return false;
        } else {
            try {
                if (text.startsWith("{") && text.endsWith("}")) {
                    mapper.readValue(text, new TypeReference<Map<String, Object>>() {
                    });
                    return true;
                }

                if (text.startsWith("[") && text.endsWith("]")) {
                    mapper.readValue(text, new TypeReference<List<Map<String, Object>>>() {
                    });
                    return true;
                }
            } catch (IOException var2) {
                logger.error(String.format("%s not a json string", text), var2);
            }

            return false;
        }
    }

    public static JsonTypeEnum isJsonWithType(String text) {
        if (EmptyUtil.isNull(text)) {
            return JsonTypeEnum.NOT_JSON;
        } else {
            try {
                if (text.startsWith("{") && text.endsWith("}")) {
                    mapper.readValue(text, new TypeReference<Map<String, Object>>() {
                    });
                    return JsonTypeEnum.OBJECT;
                }

                if (text.startsWith("[") && text.endsWith("]")) {
                    mapper.readValue(text, new TypeReference<List<Map<String, Object>>>() {
                    });
                    return JsonTypeEnum.ARRAY;
                }
            } catch (IOException var2) {
                logger.error(String.format("%s not a json string", text), var2);
            }

            return JsonTypeEnum.NOT_JSON;
        }
    }

    public static <T> String toJson(T t) {
        if (EmptyUtil.isNull(t)) {
            return "";
        } else {
            try {
                return mapper.writeValueAsString(t);
            } catch (JsonProcessingException var2) {
                logger.error(String.format("%s not a json string", t), var2);
                return "";
            }
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, Level.ERROR);
    }

    public static <T> T fromJson(String json, Class<T> clazz, Level exceptionLogLevel) {
        if (!EmptyUtil.isEmpty(json) && !EmptyUtil.isNull(clazz)) {
            try {
                return mapper.readValue(json, clazz);
            } catch (IOException var4) {
                log(json, exceptionLogLevel, var4);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJson(String json, JavaType valueType) {
        if (!EmptyUtil.isEmpty(json) && !EmptyUtil.isNull(valueType)) {
            try {
                return mapper.readValue(json, valueType);
            } catch (IOException var3) {
                logger.error(String.format("%s not a json string", json), var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public static Map<String, Object> toMap(String json) {
        if (EmptyUtil.isEmpty(json)) {
            return null;
        } else {
            try {
                return (Map)mapper.readValue(json, new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException var2) {
                logger.error(String.format("%s not a json string", json), var2);
                return null;
            }
        }
    }

    public static Map<String, Object> toMap(Object jsonObject) {
        if (EmptyUtil.isNull(jsonObject)) {
            return null;
        } else {
            String jsonString = toJson(jsonObject);
            return EmptyUtil.isEmpty(jsonString) ? null : toMap(jsonString);
        }
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (!EmptyUtil.isEmpty(json) && !EmptyUtil.isNull(clazz)) {
            try {
                return (List)mapper.readValue(json, getCollectionType(List.class, clazz));
            } catch (IOException var3) {
                logger.error(String.format("%s not a json string", json), var3);
                return Lists.newArrayList();
            }
        } else {
            return Lists.newArrayList();
        }
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static List<Map<String, Object>> toList(String json) {
        if (EmptyUtil.isEmpty(json)) {
            return Lists.newArrayList();
        } else {
            try {
                return (List)mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (IOException var2) {
                logger.error(String.format("%s not a json string", json), var2);
                return Lists.newArrayList();
            }
        }
    }

    public static JsonNode toTreeNode(String json) {
        if (EmptyUtil.isEmpty(json)) {
            return NullNode.getInstance();
        } else {
            try {
                return mapper.readTree(json);
            } catch (IOException var2) {
                logger.error(String.format("%s not a json string", json), var2);
                return NullNode.getInstance();
            }
        }
    }

    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    private static void log(String json, Level exceptionLogLevel, Throwable e) {
        switch (exceptionLogLevel) {
            case INFO:
                logger.info(String.format("%s not a json string", json), e);
                break;
            case WARN:
                logger.warn(String.format("%s not a json string", json), e);
                break;
            case DEBUG:
                logger.debug(String.format("%s not a json string", json), e);
                break;
            case TRACE:
                logger.trace(String.format("%s not a json string", json), e);
                break;
            case ERROR:
            default:
                logger.error(String.format("%s not a json string", json), e);
        }

    }

    static {
        mapper = (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).setSerializationInclusion(JsonInclude.Include.NON_NULL).findAndRegisterModules();
        SimpleModule localDateModule = new SimpleModule();
        localDateModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        localDateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mapper.registerModule(localDateModule);
        SimpleModule localTimeModule = new SimpleModule();
        localTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
        localTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        mapper.registerModule(localTimeModule);
        SimpleModule localDateTimeModule = new SimpleModule();
        localDateTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        localDateTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(localDateTimeModule);
    }
}

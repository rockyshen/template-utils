package com.rockyshen.utils.luban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 对比两个对象，对比结果封装在DiffItem中
 * @author rockyshen
 * @date 2025/12/15 10:57
 */
public final class CompareUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompareUtil.class);

    public CompareUtil() {
    }

    public static List<DiffItem> compareObj(Object entity1, Object entity2) {
        return compareObj(entity1, entity2, (Set)null, (String)null);
    }

    public static List<DiffItem> compareObj(Object entity1, Object entity2, Set<String> ignoredFields) {
        return compareObj(entity1, entity2, ignoredFields, (String)null);
    }

    public static List<DiffItem> compareObj(Object entity1, Object entity2, Set<String> ignoredFields, String parentKey) {
        if (entity1 == null) {
            return new ArrayList(0);
        } else {
            Map<String, String> orgMap = object2Map(entity1, parentKey);
            Map<String, String> grayMap = object2Map(entity2, parentKey);
            Set<String> lowerIgnoreFields = EmptyUtil.isEmpty(ignoredFields) ? null : (Set)ignoredFields.stream().map(String::toLowerCase).collect(Collectors.toSet());
            return compare(orgMap, grayMap, lowerIgnoreFields, parentKey);
        }
    }

    private static List<DiffItem> compare(Map<String, String> entityFieldMap1, Map<String, String> entityFieldMap2, Set<String> ignoredFieldSet, String parentKey) {
        if (EmptyUtil.isEmpty(entityFieldMap1)) {
            return new ArrayList(0);
        } else if (EmptyUtil.isEmpty(entityFieldMap2)) {
            String jsonString = JsonUtil.toJson(entityFieldMap1);
            final DiffItem newDiffItem = new DiffItem(parentKey, jsonString);
            return new ArrayList<DiffItem>(1) {
                {
                    this.add(newDiffItem);
                }
            };
        } else {
            List<DiffItem> result = new ArrayList();
            entityFieldMap1.entrySet().stream().filter((entry) -> {
                return !isIgnored((String)entry.getKey(), ignoredFieldSet);
            }).forEach((entry) -> {
                try {
                    if (entry.getValue() == null) {
                        return;
                    }

                    String grayValue = (String)entityFieldMap2.get(entry.getKey());
                    if (((String)entry.getValue()).equals(grayValue)) {
                        return;
                    }

                    if (JsonUtil.isJson((String)entry.getValue())) {
                        result.addAll(compareJson((String)entry.getValue(), grayValue, ignoredFieldSet, (String)entry.getKey()));
                        return;
                    }

                    result.add(new DiffItem((String)entry.getKey(), (String)entry.getValue(), grayValue));
                } catch (Exception var5) {
                    LOGGER.warn("FailedEntity:{}", JsonUtil.toJson(entry));
                    LOGGER.warn("CompareFailed", var5);
                }

            });
            return result;
        }
    }

    private static boolean isIgnored(String key, Set<String> ignoredKeys) {
        if (key == null) {
            return true;
        } else {
            String formatKey = toTagFormat(key).toLowerCase();
            return EmptyUtil.isNotEmpty(ignoredKeys) && ignoredKeys.contains(formatKey);
        }
    }

    public static String toTagFormat(String key) {
        return key == null ? null : key.replaceAll("\\[[\\d+]\\]", "");
    }

    private static Map<String, String> object2Map(Object source, String parentKey) {
        if (source == null) {
            return new HashMap(0);
        } else {
            Map<String, String> result = new HashMap();
            if (source instanceof HashMap) {
                HashMap tmp = (HashMap)source;
                tmp.keySet().stream().filter((k) -> {
                    return k != null && tmp.get(k) != null;
                }).forEach((k) -> {
                    result.put(composeKey(parentKey, k.toString()), formatValue(tmp.get(k)));
                });
                return result;
            } else {
                try {
                    Class<?> sourceClass = source.getClass();
                    Field[] sourceFiled = sourceClass.getDeclaredFields();
                    Field[] var5 = sourceFiled;
                    int var6 = sourceFiled.length;

                    for(int var7 = 0; var7 < var6; ++var7) {
                        Field field = var5[var7];
                        field.setAccessible(true);
                        Object value = field.get(source);
                        if (value != null && !field.getName().equalsIgnoreCase("dataCreatetime") && !field.getName().equalsIgnoreCase("dataLasttime") && !field.getName().equalsIgnoreCase("hashCode")) {
                            result.put(composeKey(parentKey, field.getName()), value.toString());
                        }
                    }
                } catch (IllegalAccessException var10) {
                    LOGGER.error("", var10);
                }

                return result;
            }
        }
    }

    private static String formatValue(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return obj instanceof String ? (String)obj : JsonUtil.toJson(obj);
        }
    }

    private static String composeKey(String parent, String key) {
        return EmptyUtil.isEmpty(parent) ? key : String.format("%s.%s", parent, key);
    }

    public static List<DiffItem> compareJson(final String fieldValue1, final String fieldValue2, Set<String> ignoredFieldSet, final String parentKey) {
        Object originObj = JsonUtil.fromJson(fieldValue1, Object.class);
        if (originObj == null) {
            return null;
        } else if (!JsonUtil.isJson(fieldValue2)) {
            return new ArrayList<DiffItem>(1) {
                {
                    this.add(new DiffItem(parentKey + ".json-type-mismatch", fieldValue1, fieldValue2));
                }
            };
        } else {
            Object grayObj = JsonUtil.fromJson(fieldValue2, Object.class);
            if (originObj instanceof LinkedHashMap) {
                return (List)(grayObj instanceof LinkedHashMap ? compareObj(originObj, grayObj, ignoredFieldSet, parentKey) : new ArrayList<DiffItem>(1) {
                    {
                        this.add(new DiffItem(parentKey + ".json-type-mismatch", fieldValue1, fieldValue2));
                    }
                });
            } else if (originObj instanceof ArrayList) {
                return (List)(grayObj instanceof ArrayList ? compareList((ArrayList)originObj, (ArrayList)grayObj, ignoredFieldSet, parentKey, fieldValue1, fieldValue2) : new ArrayList<DiffItem>(1) {
                    {
                        this.add(new DiffItem(parentKey + ".json-type-mismatch", fieldValue1, fieldValue2));
                    }
                });
            } else {
                throw new RuntimeException("WrongPasring");
            }
        }
    }

    public static List<DiffItem> compareList(List entityList1, List entityList2, Set<String> ignoredFieldSet, String parentKey) {
        return compareList(entityList1, entityList2, ignoredFieldSet, parentKey, (String)null, (String)null);
    }

    private static List<DiffItem> compareList(final List entityList1, final List entityList2, Set<String> ignoredFieldSet, final String parentKey, final String originStr, final String grayStr) {
        if (EmptyUtil.isEmpty(entityList1)) {
            return new ArrayList(0);
        } else if (entityList1.size() != entityList2.size()) {
            return new ArrayList<DiffItem>(2) {
                {
                    this.add(new DiffItem(parentKey + ".list-count", String.valueOf(entityList1.size()), String.valueOf(entityList2.size())));
                    this.add(new DiffItem(parentKey + ".list-size-mismatch", originStr == null ? JsonUtil.toJson(entityList1) : originStr, originStr == null ? JsonUtil.toJson(entityList2) : grayStr));
                }
            };
        } else {
            List<DiffItem> result = new ArrayList();

            for(int i = 0; i < entityList1.size(); ++i) {
                Object originItem = entityList1.get(i);
                Object grayItem = entityList2.get(i);
                result.addAll(compareObj(originItem, grayItem, ignoredFieldSet, parentKey + String.format("[%s]", i)));
            }

            return result;
        }
    }
}
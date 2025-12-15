package com.rockyshen.utils.luban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author rockyshen
 * @date 2025/12/15 10:58
 */
public final class EmptyUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyUtil.class);

    private EmptyUtil() {
    }

    public static <T> boolean isNull(Collection<T> collection) {
        return collection == null;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotNull(Collection<T> collection) {
        return !isNull(collection);
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <K, V> boolean isNull(Map<K, V> map) {
        return map == null;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNotNull(Map<K, V> map) {
        return !isNull(map);
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static <T> T ifNullThen(T value, Supplier<? extends T> valueSupplier) {
        return isNull(value) ? valueSupplier.get() : value;
    }

    public static <T> boolean ifNotNullThen(T value, Consumer<T> valueConsumer) {
        if (isNotNull(value)) {
            valueConsumer.accept(value);
            return true;
        } else {
            return false;
        }
    }

    public static <T, K> boolean ifNotNullThen(T valueSource, Consumer<K> valueConsumer, Function<T, K> valueConvert) {
        if (isNotNull(valueSource)) {
            try {
                K convertedValue = valueConvert.apply(valueSource);
                valueConsumer.accept(convertedValue);
                return true;
            } catch (Exception var4) {
                LOGGER.warn("EmptyUtil.ifNotNullThen value convert failed!", var4);
            }
        }

        return false;
    }

    public static <T, K> boolean ifNotNullThen(T valueSource, Consumer<K> valueConsumer, Function<T, K> valueConvert, K defaultValue) {
        if (ifNotNullThen(valueSource, valueConsumer, valueConvert)) {
            return true;
        } else {
            valueConsumer.accept(defaultValue);
            return false;
        }
    }

    public static <T> T ifNullThrow(T t) {
        if (isNull(t)) {
            throw new RuntimeException("No value present");
        } else {
            return t;
        }
    }

    public static <T, X extends RuntimeException> T ifNullThrow(T t, Supplier<? extends X> exceptionSupplier) {
        if (isNotNull(t)) {
            return t;
        } else {
            throw (RuntimeException)exceptionSupplier.get();
        }
    }

    public static <T extends Map> T ifEmptyThrow(T t) {
        if (isEmpty(t)) {
            throw new RuntimeException("No item present");
        } else {
            return t;
        }
    }

    public static <T extends Map, X extends RuntimeException> T ifEmptyThrow(T t, Supplier<? extends X> exceptionSupplier) {
        if (isNotEmpty(t)) {
            return t;
        } else {
            throw (RuntimeException)exceptionSupplier.get();
        }
    }

    public static <T extends Collection> T ifEmptyThrow(T t) {
        if (isEmpty(t)) {
            throw new RuntimeException("No item present");
        } else {
            return t;
        }
    }

    public static <T extends Collection, X extends RuntimeException> T ifEmptyThrow(T t, Supplier<? extends X> exceptionSupplier) {
        if (isNotEmpty(t)) {
            return t;
        } else {
            throw (RuntimeException)exceptionSupplier.get();
        }
    }

    public static <T> void ifNotNullThrow(T t) {
        if (isNotNull(t)) {
            throw new RuntimeException("No value present");
        }
    }

    public static <T, X extends Throwable> void ifNotNullThrow(T t, Supplier<? extends X> exceptionSupplier) {
        if (isNotNull(t)) {
            throw new RuntimeException("Value present");
        }
    }

    public static <T extends Map> void ifNotEmptyThrow(T t) {
        if (isNotEmpty(t)) {
            throw new RuntimeException("Item present");
        }
    }

    public static <T extends Map, X extends RuntimeException> void ifNotEmptyThrow(T t, Supplier<? extends X> exceptionSupplier) {
        if (isNotEmpty(t)) {
            throw (RuntimeException)exceptionSupplier.get();
        }
    }

    public static <T extends Collection> void ifNotEmptyThrow(T t) {
        if (isNotEmpty(t)) {
            throw new RuntimeException("No item present");
        }
    }

    public static <T extends Collection, X extends RuntimeException> void ifNotEmptyThrow(T t, Supplier<? extends X> exceptionSupplier) {
        if (isNotEmpty(t)) {
            throw (RuntimeException)exceptionSupplier.get();
        }
    }
}


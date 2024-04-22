package org.basic.feimi.domain.specification;

import org.basic.feimi.domain.common.Constants;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.Arrays;

public class CommonSpecifications {

    /**
     * Equal検索
     *
     * @param <T>
     * @param key   : attribute名
     * @param value : 検索値
     * @return
     */
    public static <T> Specification<T> equal(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.equal(root.get(key), value);
    }

    public static <T> Specification<T> equalJson(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) ->
                cb.equal(cb.function("JSON_EXTRACT",String.class,root.get(key),cb.literal("$.id")),value);
    }

    public static <T> Specification<T> equalJsonArr(final String key,  final String json,final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) ->
                cb.equal(cb.function("JSON_CONTAINS",String.class,root.get(key),cb.literal(json)),value);
    }

    /**
     * Left joinのあるEqual検索
     *
     * @param <T>
     * @param key        : attribute名
     * @param value      : 検索値
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> equal(final String key, final Object value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.equal(join.get(key), value);
        };
    }

    /**
     * notEqual検索
     *
     * @param <T>
     * @param key   : attribute名
     * @param value : 検索値
     * @return
     */
    public static <T> Specification<T> notEqual(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.notEqual(root.get(key), value);
    }

    /**
     * notEqual検索
     *
     * @param <T>
     * @param key        : attribute名
     * @param value      : 検索値
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> notEqual(final String key, final Object value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.notEqual(join.get(key), value);
        };
    }


    /**
     * simple not deleted 検索
     *
     * @param <T>
     * @return
     */
    public static <T> Specification<T> notDeleted() {
        return (Specification<T>) (root, query, cb) -> cb.equal(root.get("deleted"), Constants.DeletedFlag.NOT_DELETED);
    }

    public static <T> Specification<T> Deleted() {
        return (Specification<T>) (root, query, cb) -> cb.equal(root.get("deleted"), Constants.DeletedFlag.DELETED);
    }

    /**
     * greaterThanOrEqualTo検索
     *
     * @param key   : attribute名
     * @param value : 検索値value
     * @return
     */
    public static <T> Specification<T> greaterThanOrEqualTo(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(key), (Comparable) value);
    }

    /**
     * greaterThanOrEqualTo検索
     *
     * @param key        : attribute名
     * @param value      : 検索値value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> greaterThanOrEqualTo(final String key, final Object value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.greaterThanOrEqualTo(join.get(key), (Comparable) value);
        };
    }

    /**
     * lessThanOrEqualTo検索
     *
     * @param key   : attribute名
     * @param value : 検索値value
     * @return
     */
    public static <T> Specification<T> lessThanOrEqualTo(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.lessThanOrEqualTo(root.get(key), (Comparable) value);
    }

    /**
     * lessThanOrEqualTo検索
     *
     * @param key        : attribute名
     * @param value      : 検索値value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> lessThanOrEqualTo(final String key, final Object value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.lessThanOrEqualTo(join.get(key), (Comparable) value);
        };
    }

    /**
     * greaterThan検索
     *
     * @param key   : attribute名
     * @param value : 検索値value
     * @return
     */
    public static <T> Specification<T> greaterThan(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.greaterThan(root.get(key), (Comparable) value);
    }

    /**
     * greaterThan検索
     *
     * @param key        : attribute名
     * @param value      : 検索値value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> greaterThan(final String key, final Object value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.greaterThan(join.get(key), (Comparable) value);
        };
    }

    /**
     * lessThan検索
     *
     * @param key   : attribute名
     * @param value : 検索値value
     * @return
     */
    public static <T> Specification<T> lessThan(final String key, final Object value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.lessThan(root.get(key), (Comparable) value);
    }

    /**
     * lessThan検索
     *
     * @param key        : attribute名
     * @param value      : 検索値value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> lessThan(final String key, final Object value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.lessThan(join.get(key), (Comparable) value);
        };
    }

    /**
     * In検索
     *
     * @param key   : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T, DataT> Specification<T> inIncludeNull(final String key, final DataT[] value) {
        return (Specification<T>) (root, query, cb) -> cb.in(root.get(key)).value(Arrays.asList(value));
    }

    /**
     * In検索
     *
     * @param key   : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T, DataT> Specification<T> in(final String key, final DataT[] value) {
        return value == null || value.length == 0 ? null : (Specification<T>) (root, query, cb) -> cb.in(root.get(key)).value(Arrays.asList(value));
    }

    /**
     * In検索
     *
     * @param key        : attribute名
     * @param value      : 検索値 value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T, DataT> Specification<T> in(final String key, final DataT[] value, final String... joinTables) {
        return value == null || value.length == 0 ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.in(join.get(key)).value(Arrays.asList(value));
        };
    }

    /**
     * Not in検索
     *
     * @param key   : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T, DataT> Specification<T> notIn(final String key, final DataT[] value) {
        return value == null || value.length == 0 ? null : (Specification<T>) (root, query, cb) -> cb.not(cb.in(root.get(key)).value(Arrays.asList(value)));
    }

    /**
     * like検索
     *
     * @param key   : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T> Specification<T> like(final String key, final String value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> cb.like(root.get(key), "%" + value + "%");
    }

    /**
     * like検索
     *
     * @param key        : attribute名
     * @param value      : 検索値 value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> like(final String key, final String value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            return cb.like(join.get(key), "%" + value + "%");
        };
    }

    /**
     * like column1+column2+... 検索
     *
     * @param keys  : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T> Specification<T> likeMultiFieldsConcat(final String[] keys, final String value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Expression<String> concatenated = cb.concat(root.get(keys[0]), root.get(keys[1]));
            for (int i = 2; i < keys.length; i++) {
                concatenated = cb.concat(concatenated, root.get(keys[i]));
            }
            return cb.like(concatenated, "%" + value + "%");
        };
    }

    /**
     * like column1+column2+... 検索
     *
     * @param keys       : attribute名
     * @param value      : 検索値 value
     * @param joinTables : joinのattribute名
     * @return
     */
    public static <T> Specification<T> likeMultiFieldsConcat(final String[] keys, final String value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            Expression<String> concatenated = cb.concat(join.get(keys[0]), join.get(keys[1]));
            for (int i = 2; i < keys.length; i++) {
                concatenated = cb.concat(concatenated, join.get(keys[i]));
            }
            return cb.like(concatenated, "%" + value + "%");
        };
    }

    /**
     * like column1+" "+column2+" "... or like column1+column2 検索
     *
     * @param keys  : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T> Specification<T> likeMultiFieldsConcatOrWithSpace(final String[] keys, final String value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Expression<String> concatenated = cb.concat(root.get(keys[0]), " ");
            for (int i = 1; i < keys.length; i++) {
                concatenated = cb.concat(concatenated, root.get(keys[i]));
                if (i < keys.length - 1) {
                    concatenated = cb.concat(concatenated, " ");
                }
            }

            Expression<String> concatenated2 = cb.concat(root.get(keys[0]), root.get(keys[1]));
            for (int i = 2; i < keys.length; i++) {
                concatenated2 = cb.concat(concatenated2, root.get(keys[i]));
            }
            return cb.or(cb.like(concatenated, "%" + value + "%"), cb.like(concatenated2, "%" + value + "%"));
        };
    }

    /**
     * like column1+" "+column2+" "... or like column1+column2 検索
     *
     * @param keys  : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T> Specification<T> likeMultiFieldsConcatOrWithSpace(final String[] keys, final String value, final String... joinTables) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Path<Object> join = root.get(joinTables[0]);
            for (int i = 1; i < joinTables.length; i++) {
                join = join.get(joinTables[i]);
            }
            Expression<String> concatenated = cb.concat(join.get(keys[0]), " ");
            for (int i = 1; i < keys.length; i++) {
                concatenated = cb.concat(concatenated, join.get(keys[i]));
                if (i < keys.length - 1) {
                    concatenated = cb.concat(concatenated, " ");
                }
            }

            Expression<String> concatenated2 = cb.concat(join.get(keys[0]), join.get(keys[1]));
            for (int i = 2; i < keys.length; i++) {
                concatenated2 = cb.concat(concatenated2, join.get(keys[i]));
            }
            return cb.or(cb.like(concatenated, "%" + value + "%"), cb.like(concatenated2, "%" + value + "%"));
        };
    }

    /**
     * like (xxx-xxxx -> %xxxxxxx%) 検索
     *
     * @param key   : attribute名
     * @param value : 検索値 value
     * @return
     */
    public static <T> Specification<T> likeZipcode(final String key, final String value) {
        return value == null ? null : (Specification<T>) (root, query, cb) -> {
            Expression<String> dashRemoved = cb.function("REGEXP_REPLACE", String.class, root.get(key), cb.literal("-"), cb.literal(""));
            return cb.like(dashRemoved, "%" + value + "%");
        };
    }
}

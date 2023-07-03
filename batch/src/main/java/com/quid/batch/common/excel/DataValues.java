package com.quid.batch.common.excel;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

class DataValues<Data> {

    final List<FieldValue<Data>> fieldValue;
    final HeaderValue<Data> headerValue;

    DataValues(List<Data> values, Internationalization lang) {
        this.headerValue = new HeaderValue<>(values.get(0), lang);
        this.fieldValue = values.stream().map(FieldValue::new).collect(toList());
    }

    static class FieldValue<Data> {

        final List<String> list;

        FieldValue(Data data) {
            Class<?> clazz = data.getClass();
            this.list = stream(clazz.getDeclaredFields())
                .filter(FieldValue::exclude)
                .map(field -> getString(data, field))
                .collect(toList());
        }

        private static boolean exclude(Field field) {
            return !field.isAnnotationPresent(ExcelExclude.class);
        }

        Integer size() {
            return list.size();
        }

        String get(Integer index) {
            return list.get(index);
        }

        private String getString(Data data, Field field) {
            field.setAccessible(true);
            try {
                if (field.get(data) == null)
                    return "";
                return String.valueOf(field.get(data));
            } catch (IllegalAccessException e) {
                return "";
            } finally {
                field.setAccessible(false);
            }
        }
    }

    static class HeaderValue<Data> {

        final List<String> collect;

        HeaderValue(Data data, Internationalization lang) {
            Class<?> clazz = data.getClass();
            this.collect = headers(clazz, lang);
        }

        private static List<String> headers(Class<?> aClass, Internationalization lang) {
            return stream(aClass.getAnnotation(FieldsHeaders.class).i18n())
                .filter(headers -> headers.code().equals(lang))
                .findFirst()
                .map(Headers::fields)
                .map(Arrays::asList)
                .orElseThrow(() -> new RuntimeException("Not found headers"));
        }

        String get(Integer index) {
            return collect.get(index);
        }

    }


}

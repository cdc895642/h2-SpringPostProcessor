package com.example.boot.h2postprocessor.processor;

import com.example.boot.h2postprocessor.entity.Student;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import javax.persistence.Column;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class MyPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
//    Class type = bean.getClass();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
            if (myAnnotation != null) {
                field.setAccessible(true);
//        Object value = ReflectionUtils.getField(field, bean);
                Class type = myAnnotation.value();
                Field[] entityFields = type.getDeclaredFields();

                for (Field f : entityFields) {
                    System.out.println("!!!!!!!!!!!!!!!!! name - " + f.getName());
                    Column column = f.getAnnotation(Column.class);
                    if (column != null) {
                        System.out.println("!!!!!!!!!!!!!!!!! column -" + column.name());
                    }
                }
                ReflectionUtils.setField(field, bean, createRowMapper(type));
            }
        }
        return bean;
    }

    private Object createRowMapper(Class type) {
        System.out.println("createRowMapper - " + type.getName());

        Field[] entityFields = type.getDeclaredFields();
        RowMapper rowMapper = (resultSet, rowNum) -> {

            Constructor<?> ctr = null;
            Object entity = null;
            try {
                ctr = type.getConstructor();
                entity = ctr.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            for (Field f : entityFields) {
                Column column = f.getAnnotation(Column.class);
                if (column != null) {
                    f.setAccessible(true);
                    Class cl = f.getType();
                    Object dbV = resultSet.getObject(column.name());
                    Object value = null;
                    if (dbV instanceof Number) {
                        value = NarrovingNumberConversion(cl, (Number) dbV);
                    } else {
                        value = dbV;
                    }

                    ReflectionUtils.setField(f, entity, value);
                }
            }
            return entity;

//            Student student = new Student();
//            student.setId(resultSet.getLong("id"));
//            student.setName(resultSet.getString("name"));
//            student.setPassportNumber(resultSet.getString("passport_number"));
//            return student;

        };
        return rowMapper;

    }

    private Number NarrovingNumberConversion(Class<?> outputType,
            Number value) {

        if (value == null) {
            return null;
        }

        if (Byte.class.equals(outputType)) {
            return value.byteValue();
        }
        if (Short.class.equals(outputType)) {
            return value.shortValue();
        }
        if (Integer.class.equals(outputType)) {
            return value.intValue();
        }
        if (Long.class.equals(outputType)) {
            return value.longValue();
        }
        if (Float.class.equals(outputType)) {
            return value.floatValue();
        }
        if (Double.class.equals(outputType)) {
            return value.doubleValue();
        }

        return value;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}

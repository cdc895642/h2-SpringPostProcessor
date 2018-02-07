package com.example.boot.h2postprocessor.processor;

import com.example.boot.h2postprocessor.entity.Student;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import javax.persistence.Column;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class MyPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
//    Class type = bean.getClass();
    Field[] fields = bean.getClass().getDeclaredFields();
    for (Field field : fields) {
      MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
      if (myAnnotation != null) {
        field.setAccessible(true);
//        Object value = ReflectionUtils.getField(field, bean);
        Class type=myAnnotation.value();
        Field[] entityFields=type.getDeclaredFields();
        for (Field f:entityFields){
          Column column=f.getAnnotation(Column.class);
          if (column!=null){
            System.out.println("!!!!!!!!!!!!!!!!!1"+column.name());
          }
        }
        ReflectionUtils.setField(field, bean, createRowMapper(bean));
      }
    }
    return bean;
  }

  private Object createRowMapper(Object bean) {

    RowMapper rowMapper = (resultSet, rowNum) -> {
      Student student = new Student();
      student.setId(resultSet.getLong("id"));
      student.setName(resultSet.getString("name"));
      student.setPassportNumber(resultSet.getString("passport_number"));
      return student;
    };
    return rowMapper;

  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
    return bean;
  }
}

package com.ssjavaacademy.www.messengerattachments.validators;


import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.NotValidJsonBodyException;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;

public class JsonRequestValidator {
    public static <T> void validateJsonInput(T object) throws NotValidJsonBodyException {
        Class cl = object.getClass();
        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(Column.class) != null && field.getAnnotation(Column.class).nullable()) {
                continue;
            }
            if (field.getAnnotation(OneToMany.class) != null) {
                continue;
            }
            try {
                if (!field.getName().contains("Id")) {
                    if (field.get(object) == null || field.get(object).toString().equals("")) {
                        throw new NotValidJsonBodyException(field.getName() + " is empty");
                    }

                } else {
                    if (field.get(object) != null && field.get(object).equals(0)) {
                        throw new NotValidJsonBodyException(field.getName() + " is empty");
                    }
                }
            } catch (IllegalAccessException ignored) {
            }
        }
    }
}

package org.example.Start;

import java.lang.reflect.Field;

public class ReflectionExample {
    /**
     * Method that retrieves the properties of an object
     * @param object
     */
    public static void retrieveProperties(Object object) {
        if(object == null) {
            return;
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }
}

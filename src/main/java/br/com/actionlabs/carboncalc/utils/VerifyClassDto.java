package br.com.actionlabs.carboncalc.utils;

import java.lang.reflect.Field;
import java.util.List;

public class VerifyClassDto<T> {

    public static <T> void verify(T dto, List<String> optionalFields) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(dto);
            
            if (optionalFields != null && optionalFields.contains(field.getName())) {
                continue;
            }

            if (value == null) {
                throw new IllegalArgumentException("Field '" + field.getName() + "' cannot be null.");
            }
            
            if (value instanceof String && ((String) value).isEmpty()) {
                throw new IllegalArgumentException("Field '" + field.getName() + "' cannot be empty.");
            }

            if (!field.getType().isAssignableFrom(value.getClass())) {
                throw new IllegalArgumentException("Field '" + field.getName() + "' has incorrect type. Expected: " 
                                                    + field.getType().getSimpleName() + ", but got: " 
                                                    + value.getClass().getSimpleName());
            }
        }
    }
}

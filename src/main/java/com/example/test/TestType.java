package com.example.test;

import com.example.App;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class TestType {
    public static void main(String[] args) {
        App app = new App();
        boolean instance = Object.class.isInstance(app);
        System.out.println(instance);

        Type genericSuperclass = BBB.class.getGenericSuperclass();
        System.out.println(genericSuperclass);
        TypeVariable<Class<BBB>>[] parameters = BBB.class.getTypeParameters();
        System.out.println(parameters);


        Type superclass = BBB.class.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) superclass;
            Type[] typeArguments = type.getActualTypeArguments();
            System.out.println(typeArguments[0]);
        } else {
            System.out.println(superclass);
        }

    }

}

class BBB<T> extends ArrayList<String>{

}

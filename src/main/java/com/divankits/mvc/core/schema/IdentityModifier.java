package com.divankits.mvc.core.schema;


import com.divankits.mvc.IModel;
import com.divankits.mvc.core.ModelModifier;
import com.divankits.mvc.core.ModifyOnce;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;


@ModifyOnce
public class IdentityModifier extends ModelModifier<Identity> {

    private static HashMap<Class, Integer> counter = new HashMap<>();

    public IdentityModifier(Identity modifier) {
        super(modifier);
    }

    public static int getRandomHex(int min, int max) {

        return min + ((int) (Math.random() * (max - min)));

    }

    @Override
    public Object modify(IModel model, Field field) {

        Object value = null, newValue = null;

        try {

            value = model.getFieldValue(field.getName());

            if (value != null)
                return value;

            Identity.GenerateType type = getModifier().value();

            switch (type) {

                case Guid:

                    newValue = UUID.randomUUID().toString();
                    break;

                case RandomNumber:

                    newValue = getRandomHex(0x0, 0xffffff);
                    break;

                case Indexing:

                    Class clazz = model.getClass();

                    if (!counter.containsKey(clazz))
                        counter.put(clazz, -1);

                    int count = counter.get(clazz).intValue();
                    counter.remove(clazz);
                    counter.put(clazz, ++count);

                    newValue = count;

                    break;

            }

            value = newValue.toString();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return value;

    }

    @Override
    public Object restore(IModel model, Field field) {

        Object value = null;

        try {

            value = model.getFieldValue(field.getName());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return value;

    }

    public void seed(Class Entity , int begin){

        Class clazz = Entity.getClass();

        if (!counter.containsKey(clazz)) {
            counter.put(clazz, begin);
            return;
        }

        counter.remove(clazz);
        counter.put(clazz, begin);

    }

}
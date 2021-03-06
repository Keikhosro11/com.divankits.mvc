package com.divankits.mvc.security.crypto;

import com.divankits.mvc.core.ModelModifier;
import com.divankits.mvc.generic.PropertyInfo;
import com.divankits.mvc.net.Serializer;

public class Base64Modifier extends ModelModifier<Base64> {

    public Base64Modifier(Base64 modifier) {
        super(modifier);
    }

    @Override
    public Object modify(PropertyInfo property) {

        Object value = null;

        try {

            value = property.getValue();

            if (value == null)
                return value;

            int flag = getModifier().value();

            Base64.Type type = getModifier().type();

            switch (type) {

                case String:

                    value = android.util.Base64.encodeToString(Serializer.serialize(value), flag);

                    break;

                case ByteArray:

                    value = android.util.Base64.encode(Serializer.serialize(value), flag);

                    break;

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        return value;
    }

    @Override
    public Object restore(PropertyInfo property) {

        Object value = null;

        try {

            value = property.getValue();

            if (value == null)
                return value;

            int flag = getModifier().value();

            Base64.Type type = getModifier().type();

            switch (type) {

                case String:

                    value = android.util.Base64.decode((String) value, flag);

                    break;

                case ByteArray:

                    value = android.util.Base64.encode(Serializer.serialize(value), flag);

                    break;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return value;

    }

}

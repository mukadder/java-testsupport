package com.example;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by MHP on 04/08/2015.
 */
public class AvoidNullPointerException {

    public static void main(String[] args) {
        String unknownObject = null;

        unknownObject = "hola";

        if ("knownObject".equals(unknownObject)) {
            System.out.println(unknownObject);
        }

        String.valueOf(unknownObject); // vs unknownObject.toString()

        // StringUtils methods are null safe
        StringUtils.isEmpty(null);
        StringUtils.isBlank(null);
        StringUtils.isNumeric(null);
        StringUtils.isAllUpperCase(null);


        AvoidNullPointerException obj = new AvoidNullPointerException();
        obj.avengers("iron");
        obj.transformers("fly");
    }

    // Avoid return null
    public List<String> avengers(String filter) {
        List<String> result = Collections.EMPTY_LIST;

        return result;
    }

    // Use of @NotNull and @Nullable
    @NotNull
    public List<String> transformers(@Nullable String filter) {
        List result = Collections.EMPTY_LIST;
        return result;
    }
}

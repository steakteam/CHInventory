package com.github.entrypointkr.chinventory;

import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;

import java.util.Optional;

/**
 * Created by JunHyeong Lim on 2018-06-27
 */
public class ArrayWrapper {
    private final CArray array;

    public static <T> Optional<T> cast(Construct construct, Class<T> type) {
        return Optional.ofNullable(construct)
                .filter(type::isInstance)
                .map(type::cast);
    }

    public ArrayWrapper(CArray array) {
        this.array = array;
    }

    public CArray getArray() {
        return array;
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        return array.isAssociative() && array.containsKey(key) ?
                cast(array.get(key, Target.UNKNOWN), type) :
                Optional.empty();
    }

    public <T> Optional<T> get(int index, Class<T> type) {
        return array.size() > index ?
                cast(array.get(index, Target.UNKNOWN), type) :
                Optional.empty();
    }
}

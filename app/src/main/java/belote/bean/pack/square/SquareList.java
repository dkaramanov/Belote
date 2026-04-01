/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.square;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * SquareList class. Wrapper class of system collection used to hold and access Square instances.
 *
 * @author Dimitar Karamanov
 */
public final class SquareList implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 2716681164194070603L;

    /**
     * Internal container collection.
     */
    private final ArrayList<Square> collection = new ArrayList<Square>();

    /**
     * Appends Square element to the end of the collection.
     *
     * @param square - element to be appended to this list.
     */
    public void add(final Square square) {
        collection.add(square);
    }

    /**
     * Removes all of the elements from the collection.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Returns whether the collection is empty.
     *
     * @return boolean true if the collection is empty false otherwise.
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Returns a string representation of the object. The return name is based on class short name. This method has to be used only for debug purpose when the
     * project is not compiled with obfuscating. Don't use this method to represent the object. When the project is compiled with obfuscating the class name is
     * not the same.
     *
     * @return String a string representation of the object.
     */
    @NonNull
    public String toString() {
        final StringBuffer sb = new StringBuffer();

        for (final Square square : list()) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            sb.append(square.toString());
        }
        return sb.toString();
    }

    public Iterable<Square> list() {
        return collection;
    }
}

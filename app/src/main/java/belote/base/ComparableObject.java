/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.base;

import java.io.Serializable;

/**
 * ComparableObject class. Base class of all comparable objects.
 *
 * @author Dimitar Karamanov
 */
public abstract class ComparableObject<T extends ComparableObject<T>> implements Serializable, Comparable<T> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 5914683970428793375L;

    /**
     * Constructor.
     */
    public ComparableObject() {
        super();
    }

    /**
     * Compares the first object with the second checking for null value.
     *
     * @param a first specified object.
     * @param b second specified object.
     * @return int value which may be: = 0 if both specified objects are equal or null > 0 if first object is not null and bigger than the second specified
     * object or the second is null < 0 if second object is not null and bigger than the first specified object or the first is null
     */
    public static <E extends ComparableObject<E>> int compare(final E a, final E b) {
        if (a == null) {
            return b == null ? 0 : -1;
        } else {
            return b == null ? 1 : a.compareTo(b);
        }
    }

    /**
     * Returns the class name of the object extracted from it's full name.
     *
     * @return short class name.
     */
    public final String getClassShortName() {
        final int pos = getClass().getName().lastIndexOf('.');
        if (pos > -1) {
            return getClass().getName().substring(pos + 1);
        }
        return getClass().getName();
    }
}

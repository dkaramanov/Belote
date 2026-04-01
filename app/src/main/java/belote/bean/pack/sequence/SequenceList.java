/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.sequence;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * SequencesList Class. Wrapper class of system collection used to hold and access Sequence instances.
 *
 * @author Dimitar Karamanov
 */
public final class SequenceList implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 6748182747989159923L;

    /**
     * Internal container.
     */
    private final ArrayList<Sequence> data = new ArrayList<Sequence>();

    /**
     * Adds a sequence to the list.
     *
     * @param sequence specified sequence.
     */
    public void add(final Sequence sequence) {
        data.add(sequence);
    }

    /**
     * Clears the list contents.
     */
    public void clear() {
        data.clear();
    }

    /**
     * Returns true if the list is empty false otherwise.
     *
     * @return boolean true if the list is empty false otherwise.
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns a string representation of the object. The return name is based on class short name. This method has to be used only for debug purpose when the
     * project is not compiled with obfuscating. Don't use this method to represent the object. When the project is compiled with ofbuscating the class name is
     * not the same.
     *
     * @return String a string representation of the object.
     */
    @NonNull
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (final Sequence sequence : list()) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            sb.append(sequence.toAnonymousString());
        }
        return sb.toString();
    }

    public Iterable<Sequence> list() {
        return data;
    }
}

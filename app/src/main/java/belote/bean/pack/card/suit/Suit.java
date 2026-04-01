/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.card.suit;

import androidx.annotation.NonNull;

import java.util.Locale;

import belote.base.ComparableObject;

/**
 * Suit class Represents Card's suit.
 *
 * @author Dimitar Karamanov
 */
public abstract class Suit extends ComparableObject<Suit> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -7817406518667306126L;

    /**
     * Internal suit constant.
     */
    private final int id;

    /**
     * Constructor.
     *
     * @param id suit constant.
     */
    Suit(final int id) {
        this.id = id;
    }

    /**
     * Returns HTML suit's tag.
     *
     * @return String HTML suit's tag.
     */
    public final String getSuitImageTag() {
        return "<img src='../src/data/pictures/" + getClassShortName().toLowerCase(Locale.ENGLISH) + ".png'>";
    }

    /**
     * Returns suit's color.
     *
     * @return String suit's color.
     */
    public abstract String getSuitColor();

    /**
     * Returns a string representation of the object. The return name is based on class short name. This method has to be used only for debug purpose when the
     * project is not compiled with obfuscating. Don't use this method to represent the object. When the project is compiled with obfuscating the class name is
     * not the same.
     *
     * @return String a string representation of the object.
     */
    @NonNull
    public final String toString() {
        return getClassShortName();
    }

    /**
     * Returns suit's order.
     *
     * @return int suit's order.
     */
    public final int getSuitOrder() {
        return id;
    }

    /**
     * Returns hash code.
     *
     * @return int hash code.
     */
    public final int hashCode() {
        int hash = 5;
        hash = 71 * hash + id;
        return hash;
    }

    /**
     * The method checks if this Suit and specified object (Suit) are equal.
     *
     * @param obj specified object.
     * @return boolean true if this Suit is equal to specified object and false otherwise.
     */
    public final boolean equals(final Object obj) {
        if (obj instanceof Suit) {
            return id == ((Suit) obj).id;
        }
        return false;
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param suit specified object.
     * @return int value which may be: = 0 if this object and the specified object are equal > 0 if this object is bigger than the specified object < 0 if this
     * object is less than the specified object
     */
    @Override
    public final int compareTo(final Suit suit) {
        return id - suit.id;
    }
}

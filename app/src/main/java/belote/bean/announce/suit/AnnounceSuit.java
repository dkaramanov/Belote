/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.announce.suit;

import androidx.annotation.NonNull;

import belote.base.ComparableObject;

/**
 * AnnounceSuit class.
 *
 * @author Dimitar Karamanov
 */
public abstract class AnnounceSuit extends ComparableObject<AnnounceSuit> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -2463912940977232511L;


    /**
     * AnnounceSuit type.
     */
    private final int type;

    /**
     * Constructor.
     *
     * @param type Type.
     */
    AnnounceSuit(final int type) {
        this.type = type;
    }

    /**
     * Returns announce type.
     *
     * @return int announce type.
     */
    public final int getType() {
        return type;
    }

    /**
     * Trump suit classes are Club, Diamond, Heart and Spade. If the objects is instance of some of them the result is true otherwise is false.
     *
     * @return boolean true if is color suit false otherwise.
     */
    public abstract boolean isTrumpSuit();

    public abstract boolean isColorless();

    /**
     * Returns the game base points for that announce suit. The points are used in double and redouble calculation.
     *
     * @return int belote game base points.
     */
    public abstract int getBasePoints();

    /**
     * Returns a string representation of the object. The return name is based on class short name. This method has to be used only for debug purpose when the
     * project is not compiled with ofbuscating. Don't use this method to represent the object. When the project is compiled with ofbuscating the class name is
     * not the same.
     *
     * @return String a string representation of the object.
     */
    @NonNull
    public final String toString() {
        return getClassShortName();
    }

    /**
     * Compares this announce suit with the specified object(announce suit) for order.
     *
     * @param announceSuit specified object (announce suit).
     * @return int value which may be: = 0 if this announce suit and the specified object(announce suit) are equal > 0 if this announce suit is bigger than the
     * specified object(announce suit) < 0 if this announce suit is less than the specified object(announce suit)
     */
    public final int compareTo(AnnounceSuit announceSuit) {
        return type - announceSuit.type;
    }

    /**
     * The method checks if this announce suit and specified object (announce suit) are equal.
     *
     * @param obj specified object.
     * @return boolean true if this announce suit is equal to specified object and false otherwise.
     */
    public final boolean equals(Object obj) {
        if (obj instanceof AnnounceSuit) {
            final AnnounceSuit announceSuit = (AnnounceSuit) obj;
            return type == announceSuit.type;
        }

        return false;
    }

    /**
     * The method returns announce suit hash code.
     *
     * @return int announce suit hash code value.
     */
    public final int hashCode() {
        return type;
    }
}

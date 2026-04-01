/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.bean.pack.card.rank;

import androidx.annotation.NonNull;

import belote.base.ComparableObject;

/**
 * Rank class Represents card's rank which has one of the following values 7, 8, 9, 10, J, Q, K, A.
 *
 * @author Dimitar Karamanov
 */
public abstract class Rank extends ComparableObject<Rank> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8435657626968675818L;

    /**
     * AT rank's constant.
     */
    private final int atRank;

    /**
     * NT rank's constant.
     */
    private final int ntRank;

    /**
     * Default (standard) rank's constant.
     */
    private final int stRank;

    /**
     * Rank's AT points.
     */
    private final int atPoints;

    /**
     * Rank's NT points.
     */
    private final int ntPoints;

    /**
     * Rank's square (4 cards) points.
     */
    private final int sqPoints;

    /**
     * Constructor.
     *
     * @param stRank   default rank value.
     * @param atRank   AT rank value.
     * @param ntRank   NT rank value.
     * @param atPoints AT rank's points.
     * @param ntPoints NT rank's points.
     * @param eqPoints equal cards rank points.
     */
    Rank(final int stRank, final int atRank, final int ntRank, final int atPoints, final int ntPoints, final int eqPoints) {
        this.stRank = stRank;
        this.atRank = atRank;
        this.ntRank = ntRank;
        this.atPoints = atPoints;
        this.ntPoints = ntPoints;
        this.sqPoints = eqPoints;
    }

    /**
     * Returns a string representation of the object. The return name is based on class short name. This method has to be used only for debug purpose when the
     * project is not compiled with obfuscating. Don't use this method to represent the object. When the project is compiled with ofbuscating the class name is
     * not the same.
     *
     * @return String a string representation of the object.
     */
    @NonNull
    public final String toString() {
        return getClassShortName();
    }

    /**
     * Returns a rank's string first sign representation.
     *
     * @return String a rank's string sign representation.
     */
    public final String getRankSign() {
        final String longName = getClassShortName();
        if (!longName.isEmpty()) {
            return longName.substring(0, 1);
        }
        return "";
    }

    /**
     * Returns hash code.
     *
     * @return hash code.
     */
    public final int hashCode() {
        return stRank;
    }

    /**
     * The method checks if this rank and specified object (rank) are equal.
     *
     * @param obj specified object.
     * @return boolean true if this rank is equal to specified object and false otherwise.
     */
    public final boolean equals(Object obj) {
        if (obj instanceof Rank) {
            return stRank == ((Rank) obj).stRank;
        }
        return false;
    }

    /**
     * Compares this rank with the specified rank for order using standard rank's value.
     *
     * @param compRank specified object.
     * @return int value which may be: = 0 if this rank and the specified rank are equal > 0 if this rank is bigger than the specified rank < 0 if this rank is
     * less than the specified rank
     */
    public final int compareTo(final Rank compRank) {
        return stRank - compRank.stRank;
    }

    /**
     * Compares this rank with the specified rank for order using AT rank's value.
     *
     * @param obj specified object.
     * @return int value which may be: = 0 if this rank and the specified rank are equal > 0 if this rank is bigger than the specified rank < 0 if this rank is
     * less than the specified rank
     */
    public final int compareToAT(final Object obj) {
        final Rank compRank = (Rank) obj;
        return atRank - compRank.atRank;
    }

    /**
     * Compares this rank with the specified rank for order using NT rank's value.
     *
     * @param obj specified object.
     * @return int value which may be: = 0 if this rank and the specified rank are equal > 0 if this rank is bigger than the specified rank < 0 if this rank is
     * less than the specified rank
     */
    public final int compareToNT(final Object obj) {
        final Rank compRank = (Rank) obj;
        return ntRank - compRank.ntRank;
    }

    /**
     * Returns AT points.
     *
     * @return int rank's trump points.
     */
    public final int getTrumpPoints() {
        return atPoints;
    }

    /**
     * Returns NT points.
     *
     * @return int rank's not trump points.
     */
    public final int getNotTrumpPoints() {
        return ntPoints;
    }

    /**
     * Returns square points.
     *
     * @return int rank's square points.
     */
    public final int getSquarePoints() {
        return sqPoints;
    }

    /**
     * Returns standard rank order.
     *
     * @return int standard rank's order.
     */
    public final int getSTRankOrder() {
        return stRank;
    }

    /**
     * Returns NT rank order.
     *
     * @return int not trump rank's order.
     */
    public final int getNTRankOrder() {
        return ntRank;
    }

    /**
     * Returns AT rank order.
     *
     * @return int all trump rank's order.
     */
    public final int getATRankOrder() {
        return atRank;
    }
}

/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package com.karamanov.beloteGame.gui.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;

import com.karamanov.beloteGame.R;

import java.util.Hashtable;

import belote.bean.pack.card.Card;
import belote.bean.pack.card.rank.Rank;
import belote.bean.pack.card.rank.Ranks;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.Suits;

/**
 * PictureDecorator class.
 *
 * @author Dimitar Karamanov
 */
public final class PictureDecorator {

    /**
     * Couples hashtable.
     */
    private final Hashtable<Suit, Integer> couples = new Hashtable<Suit, Integer>();

    /**
     * Suits container.
     */
    private final Hashtable<Suit, Integer> suits = new Hashtable<Suit, Integer>();

    /**
     * Ranks container.
     */
    private final Hashtable<Rank, Hashtable<Suit, Integer>> ranks = new Hashtable<Rank, Hashtable<Suit, Integer>>();

    /**
     * Context.
     */
    private final Context context;

    /**
     * Constructor.
     */
    public PictureDecorator(Context context) {
        this.context = context;

        suits.put(Suits.Club, R.drawable.club);
        suits.put(Suits.Diamond, R.drawable.diamond);
        suits.put(Suits.Heart, R.drawable.heart);
        suits.put(Suits.Spade, R.drawable.spade);

        Hashtable<Suit, Integer> aces = new Hashtable<Suit, Integer>();
        aces.put(Suits.Club, R.drawable.aceclub);
        aces.put(Suits.Diamond, R.drawable.acediamond);
        aces.put(Suits.Heart, R.drawable.aceheart);
        aces.put(Suits.Spade, R.drawable.acespade);
        ranks.put(Ranks.Ace, aces);

        Hashtable<Suit, Integer> kings = new Hashtable<Suit, Integer>();
        kings.put(Suits.Club, R.drawable.kingclub);
        kings.put(Suits.Diamond, R.drawable.kingdiamond);
        kings.put(Suits.Heart, R.drawable.kingheart);
        kings.put(Suits.Spade, R.drawable.kingspade);
        ranks.put(Ranks.King, kings);

        Hashtable<Suit, Integer> queens = new Hashtable<Suit, Integer>();
        queens.put(Suits.Club, R.drawable.queenclub);
        queens.put(Suits.Diamond, R.drawable.queendiamond);
        queens.put(Suits.Heart, R.drawable.queenheart);
        queens.put(Suits.Spade, R.drawable.queenspade);
        ranks.put(Ranks.Queen, queens);

        Hashtable<Suit, Integer> jacks = new Hashtable<Suit, Integer>();
        jacks.put(Suits.Club, R.drawable.jackclub);
        jacks.put(Suits.Diamond, R.drawable.jackdiamond);
        jacks.put(Suits.Heart, R.drawable.jackheart);
        jacks.put(Suits.Spade, R.drawable.jackspade);
        ranks.put(Ranks.Jack, jacks);

        Hashtable<Suit, Integer> tens = new Hashtable<Suit, Integer>();
        tens.put(Suits.Club, R.drawable.tenclub);
        tens.put(Suits.Diamond, R.drawable.tendiamond);
        tens.put(Suits.Heart, R.drawable.tenheart);
        tens.put(Suits.Spade, R.drawable.tenspade);
        ranks.put(Ranks.Ten, tens);

        Hashtable<Suit, Integer> nines = new Hashtable<Suit, Integer>();
        nines.put(Suits.Club, R.drawable.nineclub);
        nines.put(Suits.Diamond, R.drawable.ninediamond);
        nines.put(Suits.Heart, R.drawable.nineheart);
        nines.put(Suits.Spade, R.drawable.ninespade);
        ranks.put(Ranks.Nine, nines);

        Hashtable<Suit, Integer> eights = new Hashtable<Suit, Integer>();
        eights.put(Suits.Club, R.drawable.eightclub);
        eights.put(Suits.Diamond, R.drawable.eightdiamond);
        eights.put(Suits.Heart, R.drawable.eightheart);
        eights.put(Suits.Spade, R.drawable.eightspade);
        ranks.put(Ranks.Eight, eights);

        Hashtable<Suit, Integer> sevens = new Hashtable<Suit, Integer>();
        sevens.put(Suits.Club, R.drawable.sevenclub);
        sevens.put(Suits.Diamond, R.drawable.sevendiamond);
        sevens.put(Suits.Heart, R.drawable.sevenheart);
        sevens.put(Suits.Spade, R.drawable.sevenspade);
        ranks.put(Ranks.Seven, sevens);

        couples.put(Suits.Club, R.drawable.belotclub);
        couples.put(Suits.Diamond, R.drawable.belotdiamond);
        couples.put(Suits.Heart, R.drawable.belotheart);
        couples.put(Suits.Spade, R.drawable.belotspade);
    }

    /**
     * The method return associated image to the card.
     *
     * @param card which image is retrieve.
     * @return Image the card's image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public Bitmap getCardImage(final Card card) {
        if (card != null) {
            Hashtable<Suit, Integer> hashtable = ranks.get(card.getRank());
            if (hashtable != null) {
                Integer id = hashtable.get(card.getSuit());
                if (id != null) {
                    return ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
                }
            }
        }
        return null;
    }

    /**
     * The method return associated image to the card.
     *
     * @param rank which image is retrieve.
     * @param suit suit
     * @return Image the card's image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public Bitmap getCardImage(final Rank rank, final Suit suit) {
        Hashtable<Suit, Integer> hashtable = ranks.get(rank);
        if (hashtable != null) {
            Integer id = hashtable.get(suit);
            if (id != null) {
                return ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
            }
        }
        return null;
    }

    /**
     * Returns suit's image.
     *
     * @param suit which image is retrieved.
     * @return Image suit's image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public Bitmap getSuitImage(final Suit suit) {
        Integer id = suits.get(suit);
        if (id != null) {
            return ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
        }
        return null;
    }

    /**
     * Returns couples image from provided Suits.
     *
     * @param suit provided Suits.
     * @return Image couples image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public Bitmap getCoupleImage(final Suit suit) {
        Integer id = couples.get(suit);
        if (id != null) {
            return ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
        }
        return null;
    }

    /**
     * Returns the image of card desk.
     *
     * @return desk image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public Bitmap getCardBackImageSmall() {
        return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.card_back_small)).getBitmap();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public NinePatchDrawable getBubbleLeft() {
        return (NinePatchDrawable) context.getResources().getDrawable(R.drawable.bubble_left);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public NinePatchDrawable getBubbleRight() {
        return (NinePatchDrawable) context.getResources().getDrawable(R.drawable.bubble_right);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public GradientDrawable getMainBKG() {
        return ((GradientDrawable) context.getResources().getDrawable(R.drawable.main_bkg));
    }
}

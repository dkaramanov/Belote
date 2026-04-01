/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package com.karamanov.beloteGame.gui.screen.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.graphics.PictureDecorator;
import com.karamanov.beloteGame.text.TextDecorator;
import com.karamanov.framework.graphics.Color;
import com.karamanov.framework.graphics.ImageUtil;
import com.karamanov.framework.graphics.Rectangle;

import java.util.Objects;

import belote.bean.pack.card.Card;
import belote.bean.pack.card.rank.Ranks;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.Suits;
import belote.bean.pack.sequence.SequenceType;
import belote.bean.pack.square.Square;

/**
 * BasePainter class.
 *
 * @author Dimitar Karamanov
 */
public abstract class BasePainter {

    /**
     * Text decorator of game beans object (Suit, Rank, Announce ...)
     */
    protected final TextDecorator textDecorator;

    /**
     * Text decorator of game beans object (Suit, Rank, Announce ...)
     */
    protected final PictureDecorator pictureDecorator;

    protected final Context context;

    protected final int dip1;

    protected final int dip3;
    protected final int dip2;
    protected final int dip4;

    protected final int dip5;
    protected final int dip6;

    protected final int dip10;

    protected final float dipF12;
    protected final float dipF14;

    /**
     * mSmooth
     */
    private final Paint mSmooth;

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    protected BasePainter(Context context) {
        this.context = context;

        dip1 = Belote.fromPixelToDip(context, 1);
        dip2 = Belote.fromPixelToDip(context, 2);
        dip3 = Belote.fromPixelToDip(context, 2);
        dip4 = Belote.fromPixelToDip(context, 4);
        dip5 = Belote.fromPixelToDip(context, 5);
        dip6 = Belote.fromPixelToDip(context, 6);
        dip10 = Belote.fromPixelToDip(context, 10);

        dipF12 = Belote.fromPixelToDipF(context, 12);
        dipF14 = Belote.fromPixelToDipF(context, 14);

        pictureDecorator = new PictureDecorator(context);
        textDecorator = new TextDecorator(context);

        mSmooth = new Paint(Paint.FILTER_BITMAP_FLAG);
        mSmooth.setAntiAlias(true);
        mSmooth.setDither(true);
    }

    public final int getCardWidth(Canvas canvas) {
        return Objects.requireNonNull(pictureDecorator.getCardImage(Ranks.Ace, Suits.Spade)).getScaledWidth(canvas);
    }

    public final int getCardHeight(Canvas canvas) {
        return Objects.requireNonNull(pictureDecorator.getCardImage(Ranks.Ace, Suits.Spade)).getScaledHeight(canvas);
    }

    public final int getDeskWidth(Canvas canvas) {
        return pictureDecorator.getCardBackImageSmall().getScaledWidth(canvas);
    }

    public final int getDeskHeight(Canvas canvas) {
        return pictureDecorator.getCardBackImageSmall().getScaledHeight(canvas);
    }

    public final Context getContext() {
        return context;
    }

    /**
     * Draws desk image.
     *
     * @param canvas for drawing
     * @param x      position.
     * @param y      position.
     */
    protected void drawCardBackImage(final Canvas canvas, final int x, final int y) {
        Bitmap b = pictureDecorator.getCardBackImageSmall();
        canvas.drawBitmap(b, x, y, mSmooth);
    }

    protected void drawRotatedCardBackImage(final Canvas canvas, final int x, final int y) {
        Bitmap b = pictureDecorator.getCardBackImageSmall();
        canvas.save();
        try {
            canvas.rotate(90);
            canvas.drawBitmap(b, y, -x, mSmooth);
        } finally {
            canvas.restore();
        }
    }

    /**
     * Draw card to the canvas.
     *
     * @param canvas for drawing
     * @param card   which image is retrieve.
     * @param x      - x coordinate.
     * @param y      - y coordinate.
     */
    public final void drawCard(final Canvas canvas, final Card card, final int x, final int y) {
        Bitmap bitmap = pictureDecorator.getCardImage(card);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, mSmooth);
        }
    }

    /**
     * Draw card darkened to the canvas.
     *
     * @param canvas for drawing
     * @param card   which image is retrieve.
     * @param x      - x coordinate.
     * @param y      - y coordinate.
     */
    public final void drawDarkenedCard(final Canvas canvas, final Card card, final int x, final int y) {
        Bitmap picture = pictureDecorator.getCardImage(card);
        if (picture != null) {
            Bitmap b = ImageUtil.transformToDarkenedImage(picture);
            canvas.drawBitmap(b, x, y, mSmooth);
            b.recycle();
        }
    }

    /**
     * Draw card mixed with color to the canvas.
     *
     * @param canvas for drawing
     * @param card   which image is retrieve.
     * @param x      - x coordinate.
     * @param y      - y coordinate. param mixedColor used to transform the image.
     */
    public final void drawMixedColorCard(final Canvas canvas, final Card card, final int x, final int y, final Color mixedColor) {
        Bitmap picture = pictureDecorator.getCardImage(card);
        if (picture != null) {
            final Rectangle rec = new Rectangle(0, 0, picture.getWidth(), picture.getHeight());
            Bitmap b = ImageUtil.transformToMixedColorImage(picture, mixedColor, rec);
            canvas.drawBitmap(b, x, y, mSmooth);
            b.recycle();
        }
    }

    /**
     * Returns equal cards image.
     *
     * @param equalCards which picture is retrieved.
     * @return Image equal cards image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public final Bitmap getEqualCardsImage(final Square equalCards) {
        if (equalCards.getRank().equals(Ranks.Jack)) {
            return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.equal200)).getBitmap();
        }

        if (equalCards.getRank().equals(Ranks.Nine)) {
            return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.equal150)).getBitmap();
        }

        return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.equal100)).getBitmap();
    }

    /**
     * Returns Image sequence's type image.
     *
     * @param sequenceType which image is retrieved.
     * @return Image sequence's type image.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public final Bitmap getSequenceTypeImage(final SequenceType sequenceType) {
        if (sequenceType.equals(SequenceType.Quint)) {
            return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.sequence100)).getBitmap();
        }

        if (sequenceType.equals(SequenceType.Quarte)) {
            return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.sequence50)).getBitmap();
        }

        return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.sequence20)).getBitmap();
    }

    /**
     * Returns couple image for provided suit.
     *
     * @param suit instance.
     * @return Image instance.
     */
    public final Bitmap getCoupleImage(final Suit suit) {
        return pictureDecorator.getCoupleImage(suit);
    }

    /**
     * Returns suit's image.
     *
     * @param suit which image is retrieved.
     * @return Image suit's image.
     */
    public final Bitmap getSuitImage(final Suit suit) {
        return pictureDecorator.getSuitImage(suit);
    }
}
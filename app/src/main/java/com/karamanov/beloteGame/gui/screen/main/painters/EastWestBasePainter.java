package com.karamanov.beloteGame.gui.screen.main.painters;

import android.content.Context;
import android.graphics.Canvas;

import com.karamanov.beloteGame.Belote;
import com.karamanov.framework.graphics.Rectangle;

import belote.bean.pack.card.Card;
import belote.logic.BeloteFacade;
import belote.logic.HumanBeloteFacade;

public abstract class EastWestBasePainter extends PlayerPainter {

    /**
     * Players with ID(1,3) cards overlapped (vertical).
     */
    protected final int EastWestCardsOverlapped;

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    protected EastWestBasePainter(Context context) {
        super(context);
        EastWestCardsOverlapped = Belote.fromPixelToDip(context, 18);
    }


    /**
     * Returns first card y position.
     *
     * @param game BelotGame instance.
     * @return position.
     */
    protected int getFirstVerticalCardPos(Canvas canvas, BeloteFacade game) {
        final int nCards = getCardsCount(game);
        return (canvas.getHeight() - nCards * getDeskWidth(canvas) + (nCards - 1) * EastWestCardsOverlapped) / 2;
    }

    protected final void drowPlayerCard(HumanBeloteFacade game, Canvas canvas, Card card, Rectangle rect) {
        drawRotatedCardBackImage(canvas, rect.x + rect.width, rect.y);
    }

}

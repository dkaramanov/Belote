package com.karamanov.beloteGame.gui.screen.main.painters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.karamanov.beloteGame.Belote;
import com.karamanov.framework.graphics.Rectangle;

import belote.bean.pack.card.Card;
import belote.bean.player.Player;
import belote.logic.BeloteFacade;
import belote.logic.HumanBeloteFacade;

public final class NorthPainter extends PlayerPainter {

    /**
     * Player with ID(0) cards overlapped
     */
    private final int NorthCardsOverlapped;

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    NorthPainter(Context context) {
        super(context);
        NorthCardsOverlapped = Belote.fromPixelToDip(context, 18);
    }

    @Override
    public void drawPlayerName(Canvas canvas, HumanBeloteFacade beloteFacade, Player player) {
        Rectangle rec = getPlayerCardRectangle(canvas, beloteFacade, 0, player);

        Paint paint = getPlayerPaint();
        boolean active = player.equals(beloteFacade.getTrickAttackPlayer());

        int y = rec.y - dip2;
        drawHorizontalPlayerName(canvas, paint, player, y, active);
    }

    @Override
    public Rectangle getPlayerCardRectangle(Canvas canvas, HumanBeloteFacade game, int index, Player player) {
        int x = getPlayer0FirstCardX(canvas, game) + index * (getDeskWidth(canvas) - NorthCardsOverlapped);
        return new Rectangle(x, GAME_FONT_HEIGHT, getDeskWidth(canvas) - NorthCardsOverlapped, getDeskHeight(canvas));
    }

    @Override
    public Rectangle getPlayerPlayedCardRectangle(Canvas canvas, HumanBeloteFacade game, Player player) {
        Rectangle rect = getPlayerCardRectangle(canvas, game, 0, player);

        int x = (canvas.getWidth() - getCardWidth(canvas)) / 2;
        int y = rect.y + rect.height + dip4;

        return new Rectangle(x, y, getCardWidth(canvas), getCardHeight(canvas));
    }

    @Override
    public Point getPlayerAnnounceLeftUpperCorner(Canvas canvas, HumanBeloteFacade game, Player player, int width, int height) {
        Rectangle rect = getPlayerCardRectangle(canvas, game, 0, player);
        int x = (canvas.getWidth() - width) / 2;
        int y = rect.y + rect.height + dip10 + dip1;
        return new Point(x, y);
    }

    @Override
    protected void drowPlayerCard(HumanBeloteFacade game, Canvas canvas, Card card, Rectangle rect) {
        drawCardBackImage(canvas, rect.x, rect.y);
    }

    private int getPlayer0FirstCardX(Canvas canvas, BeloteFacade game) {
        final int nCards = getCardsCount(game);
        return (canvas.getWidth() - nCards * getDeskWidth(canvas) + (nCards - 1) * NorthCardsOverlapped) / 2;
    }
}

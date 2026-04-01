package com.karamanov.beloteGame.gui.screen.main.painters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.text.PlayerNameDecorator;
import com.karamanov.framework.graphics.Rectangle;

import belote.bean.pack.card.Card;
import belote.bean.player.Player;
import belote.logic.BeloteFacade;
import belote.logic.HumanBeloteFacade;

public final class SouthPainter extends PlayerPainter {

    /**
     * Player with ID(2) cards overlapped constant.
     */
    private int SouthCardsOverlapped;

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    SouthPainter(Context context) {
        super(context);
        SouthCardsOverlapped = Belote.fromPixelToDip(context, -7);
    }

    @Override
    public void drawPlayerName(Canvas canvas, HumanBeloteFacade beloteFacade, Player player) {
        Rectangle rec = getPlayerCardRectangle(canvas, beloteFacade, 0, player);

        Paint paint = getPlayerPaint();
        boolean active = player.equals(beloteFacade.getTrickAttackPlayer());

        Rect bounds = new Rect();
        PlayerNameDecorator playerDecorator = new PlayerNameDecorator(player);
        String name = playerDecorator.decorate(context).toUpperCase();
        paint.getTextBounds(name, 0, name.length(), bounds);
        int y = rec.height + rec.y + bounds.height() + dip2;
        drawHorizontalPlayerName(canvas, paint, player, y, active);
    }

    @Override
    public Rectangle getPlayerCardRectangle(Canvas canvas, HumanBeloteFacade game, int index, Player player) {
        int x = getPlayer2FirstCardX(canvas, game) + index * (getCardWidth(canvas) - SouthCardsOverlapped);
        int y = canvas.getHeight() - GAME_FONT_HEIGHT - getCardHeight(canvas);
        if (index == player.getCards().getSize() - 1) {
            return new Rectangle(x, y, getCardWidth(canvas), getCardHeight(canvas));
        } else {
            return new Rectangle(x, y, getCardWidth(canvas) - SouthCardsOverlapped, getCardHeight(canvas));
        }
    }

    @Override
    public Rectangle getPlayerPlayedCardRectangle(Canvas canvas, HumanBeloteFacade game, Player player) {
        Rectangle rect = getPlayerCardRectangle(canvas, game, 0, player);
        int dip4 = Belote.fromPixelToDip(context, 4);

        int x = (canvas.getWidth() - getCardWidth(canvas)) / 2;
        int y = rect.y - dip4 - getCardHeight(canvas);

        return new Rectangle(x, y, getCardWidth(canvas), getCardHeight(canvas));
    }

    @Override
    public Point getPlayerAnnounceLeftUpperCorner(Canvas canvas, HumanBeloteFacade game, Player player, int width, int height) {
        Rectangle rect = getPlayerCardRectangle(canvas, game, 0, player);
        int x = (canvas.getWidth() - width) / 2;
        int y = rect.y - Belote.fromPixelToDip(context, 10) - height;
        return new Point(x, y);
    }

    @Override
    protected void drowPlayerCard(HumanBeloteFacade game, Canvas canvas, Card card, Rectangle rect) {
        if (card.equals(game.getHumanPlayer().getSelectedCard())) {
            drawDarkenedCard(canvas, card, rect.x, rect.y);
        } else {
            drawCard(canvas, card, rect.x, rect.y);
        }
    }

    /**
     * Returns first card X position.
     *
     * @param game BelotGame instance.
     * @return int position.
     */
    private int getPlayer2FirstCardX(Canvas canvas, BeloteFacade game) {
        final int nCards = getCardsCount(game);
        int dip2 = Belote.fromPixelToDip(context, 2);

        int avaiableWidth = canvas.getWidth() - dip2 - 2 * GAME_FONT_HEIGHT;
        if (canvas.getWidth() > canvas.getHeight()) {
            avaiableWidth = avaiableWidth - dip2 - 2 * getDeskHeight(canvas); // Think
            // more
            // !
        }

        final int cardWidth = getCardWidth(canvas);
        if (nCards * cardWidth > (avaiableWidth)) {
            double d = (double) (nCards * cardWidth - avaiableWidth) / (nCards - 1);
            SouthCardsOverlapped = (int) Math.ceil(d);
        } else {
            SouthCardsOverlapped = 0;
        }

        return (canvas.getWidth() - nCards * cardWidth + (nCards - 1) * SouthCardsOverlapped) / 2 + 1;
    }
}

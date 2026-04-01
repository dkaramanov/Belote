package com.karamanov.beloteGame.gui.screen.main.dealer;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.screen.main.BeloteView;
import com.karamanov.beloteGame.gui.screen.main.message.MessageData;
import com.karamanov.beloteGame.gui.screen.main.message.MessageScreen;
import com.karamanov.beloteGame.gui.screen.main.painters.BelotePainter;
import com.karamanov.framework.BooleanFlag;
import com.karamanov.framework.MessageActivity;
import com.karamanov.framework.graphics.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import belote.bean.announce.Announce;
import belote.bean.announce.suit.AnnounceSuits;
import belote.bean.pack.card.Card;
import belote.bean.pack.sequence.Sequence;
import belote.bean.pack.sequence.SequenceType;
import belote.bean.pack.square.Square;
import belote.bean.player.Player;
import belote.logic.HumanBeloteFacade;

/**
 * BaseDealer class.
 *
 * @author Dimitar Karamanov
 */
abstract class BaseDealer {

    /**
     * Handler to GUI thread.
     */
    protected final Handler handler;

    /**
     * Standard card delay on painting (effect).
     */
    public static final int STANDARD_CARD_DELAY = 20;

    /**
     * Belote painter. (All drawing functionality is in it).
     */
    public final BelotePainter belotPainter;

    /**
     * Delay constant
     */
    protected final static int PLAY_DELAY = 200;

    protected final MessageActivity context;

    protected final BeloteView belotPanel;

    protected final HumanBeloteFacade beloteFacade;

    private MessageScreen messageScreen;

    private final int actionBarHeight;

    protected BaseDealer(MessageActivity context, BeloteView belotPanel) {
        this.context = context;
        this.belotPanel = belotPanel;
        this.beloteFacade = Belote.getBeloteFacade(context);

        belotPainter = new BelotePainter(context);
        handler = new Handler();

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            actionBarHeight = 0;
        }
    }

    /**
     * Checks key click.
     *
     * @param keyCode pressed key code.
     */
    public abstract void checkKeyPressed(int keyCode);

    /**
     * Checks pointer click.
     *
     * @param x position.
     * @param y position.
     */
    public abstract void checkPointerPressed(float x, float y);

    /**
     * Invalidate game on belote panel.
     */
    public final void invalidateGame() {
        invalidateGame(0);
    }

    /**
     * Invalidate game on belote panel.
     */
    public final void invalidateGame(int delay) {
        Canvas canvas = belotPanel.getBufferedCanvas();
        if (canvas != null) {
            belotPainter.drawGame(canvas, beloteFacade, belotPanel, delay);
            belotPanel.refresh();
        }
    }

    /**
     * Creates a message list for provided player.
     *
     * @param player Player
     * @param card   Card
     * @return List of messages
     */
    protected final List<MessageData> getMessageList(final Player player, final Card card) {
        ArrayList<MessageData> result = new ArrayList<MessageData>();

        if (player.equals(beloteFacade.getTrickCouplePlayer())) {
            result.add(new MessageData(belotPainter.getCoupleImage(card.getSuit()), context.getString(R.string.Belote)));
        }
        // Add equals and sequences messages on first round and when the game is
        // not NT.
        Announce announce = beloteFacade.getOpenContractAnnounce();
        if (announce != null && !announce.getAnnounceSuit().equals(AnnounceSuits.NotTrump) && beloteFacade.isTrickListEmpty()) {
            for (final Square square : player.getCards().getSquaresList().list()) {
                result.add(new MessageData(belotPainter.getEqualCardsImage(square), context.getString(R.string.FourCards)));
            }

            for (final Sequence sequence : player.getCards().getSequencesList().list()) {
                result.add(new MessageData(belotPainter.getSequenceTypeImage(sequence.getType()), getSequenceString(sequence.getType())));
            }
        }

        return result;
    }

    /**
     * Gets sequence text by type.
     * l
     *
     * @param type SequenceType.
     * @return sequence text.
     */
    private String getSequenceString(SequenceType type) {
        if (SequenceType.Tierce.equals(type)) {
            return context.getString(R.string.Tierce);
        }

        if (SequenceType.Quarte.equals(type)) {
            return context.getString(R.string.Quarte);
        }

        if (SequenceType.Quint.equals(type)) {
            return context.getString(R.string.Quint);
        }
        return context.getString(R.string.Sequence);
    }

    /**
     * Displays a message.
     *
     * @param player   which call the message function.
     * @param messages played by player.
     */
    protected final void displayMessage(final Player player, final List<MessageData> messages) {
        final BooleanFlag flag = new BooleanFlag();
        handler.post(new Runnable() {
            public void run() {
                messageScreen = new MessageScreen(context, player, messages, flag);
                positionMessageScreen(messageScreen, player);
                messageScreen.show();
            }
        });

        while (flag.getValue()) {
            invalidateGame();
            sleep(PLAY_DELAY);
        }
    }

    /**
     * Sleeps for provided millisecond.
     *
     * @param ms provided millisecond.
     */
    protected final void sleep(final long ms) {
        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                // D.N.
            }
        }
    }


    public final int getUpperCardY() {
        Canvas canvas = belotPanel.getBufferedCanvas();
        int dip4 = Belote.fromPixelToDip(context, 4);
        if (canvas != null) {
            Rectangle rect = belotPainter.getPlayerCardRectangle(canvas, beloteFacade, 0, beloteFacade.getHumanPlayer().getPartner());
            return rect.y + dip4;
        }

        return 0;
    }

    /**
     * Place message screen on position related to provided player.
     *
     * @param messageScreen view
     * @param player        message announcer
     */
    private void positionMessageScreen(MessageScreen messageScreen, Player player) {
        Canvas canvas = belotPanel.getBufferedCanvas();

        if (canvas != null) {
            Rectangle rect = belotPainter.getPlayerCardRectangle(canvas, beloteFacade, 0, player);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(messageScreen.getWindow()).getAttributes());

            switch (player.getID()) {
                case 0:
                    lp.gravity |= Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                    lp.y = rect.y + rect.height + belotPanel.getTop() + actionBarHeight;
                    break;

                case 1:
                    lp.gravity |= Gravity.START | Gravity.CENTER_VERTICAL;
                    lp.x = rect.x + rect.width;
                    lp.y = actionBarHeight / 2;
                    break;

                case 2:
                    lp.gravity |= Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                    lp.y = belotPanel.getHeight() - rect.y;
                    break;

                case 3:
                    lp.gravity |= Gravity.END | Gravity.CENTER_VERTICAL;
                    lp.x = belotPanel.getWidth() - rect.x;
                    lp.y = actionBarHeight / 2;
                    break;
            }
            messageScreen.getWindow().setAttributes(lp);
        }
    }

    /**
     * New announce deal.
     */
    protected final void newAnnounceDealRound() {
        if (beloteFacade.getContractAnnounce() != null) {
            beloteFacade.processTrickData();
        }
        beloteFacade.setNextDealAttackPlayer();
        beloteFacade.newGame();
        beloteFacade.getHumanPlayer().setSelectedCard(null);

        if (context.getWindow() != null && context.getWindow().isActive()) {
            invalidateGame(STANDARD_CARD_DELAY);
        } else {
            invalidateGame();
        }
    }

    /**
     * On surface change (reposition message screen if is visible)
     */
    public final void onSurfaceChanged() {
        if (messageScreen != null && messageScreen.isShowing()) {
            positionMessageScreen(messageScreen, messageScreen.getPlayer());
        }
    }

    /**
     * Called when end game activity is closed.
     */
    public final void onCloseEndGame() {
        //save game log
        //BeloteLog.saveGameInfo(beloteFacade.getGame(), context);

        sleep(PLAY_DELAY);
        newAnnounceDealRound();
    }
}

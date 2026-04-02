package com.karamanov.beloteGame.gui.screen.main.message;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.karamanov.beloteGame.R;
import com.karamanov.framework.BooleanFlag;

import java.util.List;
import java.util.Objects;

import belote.bean.player.Player;

public class MessageScreen extends Dialog {

    private final BooleanFlag flag;

    private final Player player;

    public MessageScreen(Context context, Player player, List<MessageData> messages, BooleanFlag flag) {
        super(context);

        this.player = player;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawableResource(R.drawable.message_shape);

        this.flag = flag;
        MessagePanel messagePanel = new MessagePanel(context, player, messages);
        setContentView(messagePanel);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public final Player getPlayer() {
        return player;
    }

    protected void onStop() {
        flag.setFalse();
    }

    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        dismiss();
        return true;
    }

    /**
     * Invoked when the navigational action is selected.
     *
     */
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dismiss();
        return true;
    }
}

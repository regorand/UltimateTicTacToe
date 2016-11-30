package janluca.de.ultimatetictactoe;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;

import janluca.de.ultimatetictactoe.game.FieldState;

public class TicTacToeField extends Button {

    private FieldState fieldState;

    public TicTacToeField(Context context) {
        super(context);
    }

    public TicTacToeField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TicTacToeField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TicTacToeField(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

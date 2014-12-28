package it.polimi.game.state;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import it.polimi.activities.ChoosePlayerActivity;
import it.polimi.activities.MenuActivity;
import it.polimi.core.Assets;
import it.polimi.framework.util.Painter;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;

public class ScoreState extends State {

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.background, 0, 0);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD, 100);
        g.drawString("The winner is:", 240, 350);
        g.setFont(Typeface.DEFAULT_BOLD, 140);
        g.drawString(Game.getInstance().getGh().getMatchResult().getWinner().getName(), 740, 520);
        g.setFont(Typeface.DEFAULT_BOLD, 100);
        g.drawString("Touch the screen.", 440, 700);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            Game.getInstance().getGameActivity().finish();
            Log.v("GameMainActivity->scoreState", "game finished -> destroy gameMainActivity");
        }
        return true;
    }
}
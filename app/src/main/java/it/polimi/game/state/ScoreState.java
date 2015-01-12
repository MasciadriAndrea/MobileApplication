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
        g.setColor(Color.rgb(170,205,255));//background
        g.fillRect(0,0,1920,1200);
        g.drawImage(Assets.win,0,20);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD, 100);
        String winner=Game.getInstance().getGh().getMatchResult().getWinner().getName();
        if(!winner.equals("TIE"))
        g.drawString("The winner is:", 540, 650);
        g.setFont(Typeface.DEFAULT_BOLD, 140);
        g.drawString(winner, 1040, 820);
        g.setFont(Typeface.DEFAULT_BOLD, 100);
        g.drawString("Touch the screen.", 740, 1000);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            Game.getInstance().getGameActivity().finish();
        }
        return true;
    }
}
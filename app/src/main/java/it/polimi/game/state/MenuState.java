package it.polimi.game.state;

import android.content.Intent;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;

import it.polimi.activities.exampleActivity;
import it.polimi.core.GameMainActivity;
import it.polimi.framework.util.Painter;
import it.polimi.core.Assets;
import it.polimi.framework.util.UIButton;


public class MenuState extends State {
    private UIButton playButton, statisticButton, settingButton;


    @Override
	public void init() {
        playButton = new UIButton(232, 30, 568, 140, Assets.playUp,
                Assets.playDown);
        statisticButton = new UIButton(232, 170, 568, 280, Assets.statisticUp,
                Assets.statisticDown);
        settingButton= new UIButton(232, 310, 568, 420, Assets.settingsUp,
                Assets.settingsDown);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Painter g) {
        g.drawImage(Assets.background, 0, 0);
        playButton.render(g);
        statisticButton.render(g);
        settingButton.render(g);
	}

	@Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            statisticButton.onTouchDown(scaledX, scaledY);
            settingButton.onTouchDown(scaledX, scaledY);
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                setCurrentState(new PlayState());
            } else if (settingButton.isPressed(scaledX, scaledY)) {
                settingButton.cancel();
                //Intent i=new Intent(exampleActivity.class);
                //startActivity(i);
            }else if (statisticButton.isPressed(scaledX, scaledY)) {
                statisticButton.cancel();
                //setCurrentState(new ScoreState());
            } else {
                playButton.cancel();
                settingButton.cancel();
                statisticButton.cancel();
            }
        }
        return true;
    }
}
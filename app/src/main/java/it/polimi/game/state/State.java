package it.polimi.game.state;

import android.app.Activity;
import android.view.MotionEvent;
import it.polimi.framework.util.Painter;
import it.polimi.core.GameMainActivity;

public abstract class State {
	public void setCurrentState(State newState) {
		GameMainActivity.sGame.setCurrentState(newState);
	}

	public abstract void init();

	public abstract void update(float delta);

	public abstract void render(Painter g);

	public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);
}
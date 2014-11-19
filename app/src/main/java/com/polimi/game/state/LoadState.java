package com.polimi.game.state;

import android.view.MotionEvent;
import com.polimi.framework.util.Painter;
import com.polimi.core.Assets;

public class LoadState extends State {
	@Override
	public void init() {

        Assets.load();
	}

	@Override
	public void update(float delta) {
		setCurrentState(new MenuState());
	}

	@Override
	public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		return false;
	}
}
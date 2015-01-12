package it.polimi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;

import com.polimi.core.R;

import it.polimi.game.model.Game;

public class RulesActivity extends Activity {
    private ViewFlipper viewFlipper;
    private float lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rules);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
    }


    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();
                // Handling left to right screen swap.
                if (lastX < currentX) {
                    // If there aren't any other children, just break.
                    if (viewFlipper.getDisplayedChild() == 0)
                    break;
                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                    // Display next screen.
                    viewFlipper.showNext();
                }
                // Handling right to left screen swap.
                if (lastX > currentX) {
                    // If there is a child (to the left), kust break.
                    if (viewFlipper.getDisplayedChild() == 1)
                    break;
                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }

}

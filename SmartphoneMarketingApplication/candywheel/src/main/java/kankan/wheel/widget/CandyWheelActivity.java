package kankan.wheel.widget;

import kankan.wheel.R;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CandyWheelActivity extends Activity {
    // Scrolling flag
    private boolean scrolling = false;
    private Button buttonSpin;
    private boolean isVisibility = true;
    private TextView textViewScore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cities_layout);

        textViewScore = (TextView) findViewById(R.id.score);

        final WheelView casinoFirst = (WheelView) findViewById(R.id.casino_first);
        casinoFirst.setVisibleItems(3);
        casinoFirst.setViewAdapter(new CandyAdapter(this, 3));


        final WheelView casinoSecond = (WheelView) findViewById(R.id.casino_second);
        casinoSecond.setVisibleItems(3);
        casinoSecond.setViewAdapter(new CandyAdapter(this, 3));

        final WheelView casinoThird = (WheelView) findViewById(R.id.casino_third);
        casinoThird.setVisibleItems(3);
        casinoThird.setViewAdapter(new CandyAdapter(this, 3));
        casinoThird.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                isVisibility = true;
                animateButton();
                if (casinoFirst.getCurrentItem() == casinoSecond.getCurrentItem()
                        && casinoFirst.getCurrentItem() == wheel.getCurrentItem()) {
                    int score = Integer.parseInt(textViewScore.getText().toString());
                    score += 20;
                    textViewScore.setText(String.valueOf(score));
                    final Dialog dialog = new Dialog(CandyWheelActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_win);
                    dialog.show();
                }
            }
        });
        buttonSpin = (Button) findViewById(R.id.spin);
        buttonSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVisibility = false;
                casinoFirst.scroll(200, 2000);
                casinoSecond.scroll(400, 4000);
                casinoThird.scroll(600, 6000);
            }
        });
        animateButton();
    }

    private void animateButton() {
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = 2 * 1000;
        myAnim.setDuration((long) animationDuration);
        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2f, 20);
        myAnim.setInterpolator(interpolator);
        buttonSpin.startAnimation(myAnim);
        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (isVisibility) {
                    animateButton();
                }
            }
        });
    }

    /**
     * Adapter for countries
     */
    private class CandyAdapter extends AbstractWheelTextAdapter {

        // Countries flags
        private int flagsFirst[];

        private int flagsSecond[] = new int[]{
                R.drawable.orange, R.drawable.yellow, R.drawable.bomb, R.drawable.ch_ball_springkles, R.drawable.ch_oval,
                R.drawable.ch_square_stripes};

        private int flagsThird[] = new int[]{
                R.drawable.ch_ball_springkles, R.drawable.ch_square_stripes,
                R.drawable.orange, R.drawable.bomb, R.drawable.ch_oval, R.drawable.yellow};

        /**
         * Constructor
         */
        protected CandyAdapter(Context context, int position) {
            super(context, R.layout.country_layout, NO_RESOURCE);
            switch (position) {
                case 1:
                    flagsFirst = flagsSecond;
                    break;
                case 2:
                    flagsFirst = flagsThird;
                    break;
                default:
                    flagsFirst = new int[]{
                            R.drawable.yellow, R.drawable.ch_ball_springkles, R.drawable.bomb, R.drawable.ch_oval, R.drawable.ch_square_stripes,
                            R.drawable.orange};
                    break;
            }
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            ImageView img = (ImageView) view.findViewById(R.id.flag);
            img.setImageResource(flagsFirst[index]);
            return view;
        }

        @Override
        public int getItemsCount() {
            return flagsFirst.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return String.valueOf(new Random().nextInt(100));
        }
    }
}

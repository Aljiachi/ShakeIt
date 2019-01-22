package net.aljiachi.shakeit;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;

public class BackgroundSwitcher {

    private OnMoveEndCallback onMoveEndCallback;
    private OnMoveStartCallback onMoveStartCallback;
    private OnMoveUpdateCallback onMoveUpdateCallback;

    private int[] colors;
    private int REPEAT_MOD = 0;
    private int REPEAT_COUNT = -1;
    private int Duration=0, Dealy=100;

    private View view;

    public BackgroundSwitcher setRepeatCount(int count){
        this.REPEAT_COUNT = count;
        return this;
    }

    public BackgroundSwitcher setRepeatMod(int REPEAT_MOD) {
        this.REPEAT_MOD = REPEAT_MOD;
        return this;
    }

    public BackgroundSwitcher setDuration(int duration) {
        Duration = duration;
        return this;
    }

    public BackgroundSwitcher setDealy(int dealy) {
        Dealy = dealy;
        return this;
    }

    public BackgroundSwitcher onStart(OnMoveStartCallback callback){
        onMoveStartCallback = callback;
        return this;
    }

    public BackgroundSwitcher onUpdate(OnMoveUpdateCallback callback){
        onMoveUpdateCallback = callback;
        return this;
    }

    public BackgroundSwitcher onEnd(OnMoveEndCallback callback){
        onMoveEndCallback = callback;
        return this;
    }

    public BackgroundSwitcher fromTo(int from, int to){
        colors = new int[]{
                from,
                to
        };
        return this;
    }

    public BackgroundSwitcher setColors(int[] colors) {
        this.colors = colors;
        return this;
    }

    private void start(){

        if(colors==null) return;

        Duration = Duration * colors.length;

        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(colors);
        anim.setEvaluator(new ArgbEvaluator());

        if (REPEAT_MOD == ShakeIt.RESTART){
            anim.setRepeatMode(ValueAnimator.RESTART);
            anim.setRepeatCount(REPEAT_COUNT);
        }else if (REPEAT_MOD == ShakeIt.REVERSE){
            anim.setRepeatMode(ValueAnimator.REVERSE);
            anim.setRepeatCount(REPEAT_COUNT);
        }

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(onMoveStartCallback!=null) onMoveStartCallback.onStart(view);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(onMoveEndCallback!=null) onMoveEndCallback.onEnd(view);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if(onMoveUpdateCallback!=null) onMoveUpdateCallback.onUpdate(valueAnimator);
                }
            });
        }
        anim.setStartDelay(Dealy);
        anim.setDuration(Duration);
        anim.start();
    }
    public void on(View view){
        this.view = view;
        start();
    }

}

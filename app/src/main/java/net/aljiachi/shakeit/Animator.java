package net.aljiachi.shakeit;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewPropertyAnimator;

public class Animator {

    private Animate animation;

    private int Duration=0, Dealy=0;

    private View view;

    private OnMoveEndCallback onMoveEndCallback;
    private OnMoveStartCallback onMoveStartCallback;
    private OnMoveUpdateCallback onMoveUpdateCallback;

    private int Y_CHANGE = 50;
    private int X_CHANGE = 50;

    public Animator setStyle(int style) {
        switch (style){
            case ShakeIt.NICE :
                Y_CHANGE = 50;
                X_CHANGE = 50;
                break;
            case ShakeIt.QUICK :
                Y_CHANGE = 10;
                X_CHANGE = 10;
                break;
            case ShakeIt.TOUGH :
                Y_CHANGE = 150;
                X_CHANGE = 150;
                break;
        }
        return this;
    }

    public Animator onStart(OnMoveStartCallback callback){
        onMoveStartCallback = callback;
        return this;
    }

    public Animator onUpdate(OnMoveUpdateCallback callback){
        onMoveUpdateCallback = callback;
        return this;
    }

    public Animator onEnd(OnMoveEndCallback callback){
        onMoveEndCallback = callback;
        return this;
    }

    public Animator setDealy(int dealy) {
        Dealy = dealy;
        return this;
    }

    Animator(Animate animate){
        animation = animate;
    }

    public Animator setDuration(int duration) {
        Duration = duration;
        return this;
    }

    private ViewPropertyAnimator prepare(){
        if(animation == Animate.FadeInBottom){
            view.setAlpha(0);
            return view.animate().alpha(1).translationY(-Y_CHANGE).setDuration(Duration).setStartDelay(Dealy);
        }else if (animation == Animate.FadeIn){
            view.setAlpha(0);
            return view.animate().alpha(1).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeInTop){
            view.setAlpha(0);
            if(view.getTranslationY() == 0) view.setTranslationY(Y_CHANGE);
            return view.animate().alpha(1).translationY(0).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeInLeft){
            view.setAlpha(0);
            if(view.getTranslationX() == 0) view.setTranslationX(-X_CHANGE);
            return view.animate().alpha(1).translationX(0).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeInRight){
            view.setAlpha(0);
            if(view.getTranslationX() == 0) view.setTranslationX(X_CHANGE);
            return view.animate().alpha(1).translationX(0).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeOutBottom){
            return view.animate().alpha(0).translationY(Y_CHANGE).setDuration(Duration).setStartDelay(Dealy);
        }else if (animation == Animate.FadeOut){
            return view.animate().alpha(0).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeOutTop){
            return view.animate().alpha(0).translationY(-Y_CHANGE).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeOutLeft){
            view.setTranslationX(-X_CHANGE);
            return view.animate().alpha(0).translationX(0).setDuration(Duration).setStartDelay(Dealy);
        }else if(animation == Animate.FadeOutRight){
            view.setTranslationX(X_CHANGE);
            return view.animate().alpha(0).translationX(0).setDuration(Duration).setStartDelay(Dealy);
        }
        return null;
    }

    private void start(){
        if(view==null) return;

        ViewPropertyAnimator viewPropertyAnimator = prepare();

        if(viewPropertyAnimator==null) return;

        viewPropertyAnimator.setListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {
                if(onMoveStartCallback!=null) onMoveStartCallback.onStart(view);
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                if(onMoveEndCallback!=null) onMoveEndCallback.onEnd(view);
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            viewPropertyAnimator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                   if(onMoveUpdateCallback!=null) onMoveUpdateCallback.onUpdate(valueAnimator);
                }
            });
        }
        viewPropertyAnimator.start();
    }

    public void on(View view){
        this.view = view;
        start();
    }
}

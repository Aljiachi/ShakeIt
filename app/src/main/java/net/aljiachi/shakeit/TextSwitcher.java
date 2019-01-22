package net.aljiachi.shakeit;

import android.view.View;
import android.widget.TextView;

public class TextSwitcher {

    private String[] Texts;
    private OnMoveEndCallback onMoveEndCallback;

    private int Duration=0, Dealy=100;
    private TextView view;
    private int currentIndex = 0;
    private int style=0;

    public TextSwitcher setStyle(int style){
        this.style = style;
        return this;
    }

    public TextSwitcher setDuration(int duration) {
        Duration = duration;
        return this;
    }

    public TextSwitcher setDealy(int dealy) {
        Dealy = dealy;
        return this;
    }

    public void on(TextView view){
        this.view = view;
        updateText();
    }

    public TextSwitcher onEnd(OnMoveEndCallback callback){
        onMoveEndCallback = callback;
        return this;
    }

    private void updateText(){
        view.setText(Texts[currentIndex]);

        ShakeIt.play(Animate.FadeInTop).setDealy(Duration/2).setStyle(style).setDuration(Duration).onEnd(new OnMoveEndCallback() {
            @Override
            public void onEnd(final View view) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(currentIndex==Texts.length-1) return;

                        ShakeIt.play(Animate.FadeOutBottom).setStyle(style).setDuration(Duration/2).onEnd(new OnMoveEndCallback() {
                            @Override
                            public void onEnd(View view) {
                                currentIndex++;
                                if(currentIndex<Texts.length) {
                                    updateText();
                                }
                            }
                        }).on(view);
                    }
                }, Dealy);
            }
        }).on(view);

        // Switching Ends
        if(currentIndex==Texts.length-1) {
            if(onMoveEndCallback!=null) onMoveEndCallback.onEnd(view);
        }

    }

    TextSwitcher(String[] strings){
        Texts = strings;
    }

}

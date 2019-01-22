package aljiachi.net.shakeit;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.aljiachi.shakeit.Animate;
import net.aljiachi.shakeit.OnMoveEndCallback;
import net.aljiachi.shakeit.OnMoveStartCallback;
import net.aljiachi.shakeit.ShakeIt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.label);

        int[] colors = new int[]{
                Color.parseColor("#ef5a5a"),
                Color.parseColor("#35d0ba"),
                Color.parseColor("#503bff"),
                Color.parseColor("#64a36f"),
                Color.parseColor("#f60c86"),
                Color.parseColor("#90f2ff"),
                Color.parseColor("#fff200")
        };

        //hakeIt.backgroundSwitcher().setDuration(1500).fromTo(Color.parseColor("#ef5a5a") , Color.parseColor("#35d0ba")).on(textView);
        ShakeIt.backgroundSwitcher().setDuration(1500).setRepeatMod(ShakeIt.REVERSE).setRepeatCount(ShakeIt.INFINITE).setColors(colors).on(textView);

        ShakeIt.play(Animate.FadeInRight).setDuration(300).setDealy(350)
                .onStart(new OnMoveStartCallback() {
                    @Override
                    public void onStart(View view) {
                        Log.e("change", "onStart");
                    }
                })
                .onEnd(new OnMoveEndCallback() {
                    @Override
                    public void onEnd(View view) {
                        RunSwitcher((TextView) view);
                    }
                })
                .on(textView);
    }

    private void RunSwitcher(TextView textView){
        String[] labels = new String[] {
          "Jack",
          "Lucas",
          "Suso",
          "Conti",
          "Gigo",
          "Patrick"
        };
        ShakeIt.textSwitcher(labels).setDuration(750).setStyle(ShakeIt.QUICK).setDealy(2500).on(textView);
    }

}

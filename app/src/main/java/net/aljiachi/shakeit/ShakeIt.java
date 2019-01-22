package net.aljiachi.shakeit;

public class ShakeIt {

    public final static int NICE = 0;
    public final static int QUICK = 1;
    public final static int TOUGH = 2;

    public final static int RESTART = 1;
    public final static int REVERSE = 2;

    public final static int INFINITE = -1;

    public static Animator play(Animate animate){
        return new Animator(animate);
    }

    public static TextSwitcher textSwitcher(String[] strings){
        return new TextSwitcher(strings);
    }

    public static BackgroundSwitcher backgroundSwitcher(){
        return new BackgroundSwitcher();
    }

}

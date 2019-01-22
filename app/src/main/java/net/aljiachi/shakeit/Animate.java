package net.aljiachi.shakeit;

public enum Animate {

    FadeIn(1),
    FadeInRight(2),
    FadeInLeft(3),
    FadeInTop(4),
    FadeInBottom(5),
    FadeOut(6),
    FadeOutTop(7),
    FadeOutBottom(7),
    FadeOutRight(7),
    FadeOutLeft(7);

    int animteionInde;
    Animate(int index){
        this.animteionInde = index;
    }
}
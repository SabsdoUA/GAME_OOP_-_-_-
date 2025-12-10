package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer
{
    private boolean electricity;

    public Computer(){
        setAnimation(new Animation("sprites/computer.png",80,48,0.2f));
    }

    public int add(int value1,int value2){
        if (!electricity) return 0;
       return value1 + value2;
    }
    public float add(float value1,float value2){
        if (!electricity) return 0;
        return value1 + value2;
    }

    public int sub(int value1,int value2){
        if (!electricity) return 0;
        return value1 - value2;
    }
    public float sub(float value1,float value2){
        if (!electricity) return 0;
        return value1 - value2;
    }

    @Override
    public void setPowered(boolean powered) {
        electricity = powered;
        if (!electricity)
            getAnimation().pause();
        else
            getAnimation().play();
    }
}

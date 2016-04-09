package hillbillies.model;

public abstract class MovableWorldObject {

    public abstract void setLocation(double locationX, double locationY, double locationZ);

    public abstract void setLocation(VLocation location);

    public abstract void setLocation(double[] location);


    public abstract VLocation getLocation();

    public abstract void register(VLocation location);
    
    public abstract void unregister();
    
    public abstract World getWorld();

    public abstract void activityFinished();
    
    public abstract int getWeight();

    public abstract void advanceTime(double dt);

    
}

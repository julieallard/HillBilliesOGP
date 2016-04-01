package hillbillies.model;

public abstract class MovableWorldObject {

    public abstract void setLocation(double locationX,double locationY,double locationZ);

    public abstract void setLocation(VLocation location);

    public abstract VLocation getLocation();

    public abstract World getWorld();

    public abstract void activityFinished();
}

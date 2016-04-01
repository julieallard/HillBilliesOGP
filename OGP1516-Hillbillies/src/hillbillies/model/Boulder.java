package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Random;

import static hillbillies.model.Unit.isValidlocation;

public class Boulder extends MovableWorldObject {
    /**
     * Initialize this new Boulder with given x, y and z coordinates.
     *
     * @param  x
     *         The x coordinate for this new Boulder.
     * @param  y
     *         The y coordinate for this new Boulder.
     * @param  z
     *         The z coordinate for this new Boulder.
     * @post   The x coordinate of this new Boulder is equal to the given x coordinate.
     * @post   The y coordinate of this new Boulder is equal to the given y coordinate.
     * @post   The z coordinate of this new Boulder is equal to the given z coordinate.
     * @throws UnitIllegalLocation
     *         This new Boulder cannot have the given location as its location.
     */
    public Boulder(double x, double y, double z, World world) throws UnitIllegalLocation {
        this.setLocation(x, y, z);
        Random random = new Random();
        this.weight = 10 + random.nextInt(41);
        this.World = world;
    }

    /**
     * Return the location of this Boulder.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return this.location;
    }

    private VLocation location;
    private final int weight;
    
    @Override
    @Raw
    public void setLocation(double x, double y, double z) {
        this.location = new VLocation(x, y, z, this);
    }
    
    @Override
    @Raw
    public void setLocation(VLocation location) throws UnitIllegalLocation {
        if (!isValidlocation(location)) throw new UnitIllegalLocation();
        this.location = location;
        this.register(location);
    }

    /**
     * Return the weight of this Boulder.
     */
    @Basic
    @Raw
    public int getWeight() {
        return weight;
    }

    @Override
    public void unregister() {
    	this.getWorld().getWorldMap().remove(this.getLocation());
    }

    @Override
    public void register(VLocation location) {
        this.getWorld().getWorldMap().put(location, this);
    }//todo check java's bullshit

    public void advanceTime(double dt) {
    	this.Activity.advanceActivityTime(dt);
    }

    /**
     * Return the Activity of this Boulder.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.Activity;
    }

    /**
     * Check whether the given Activity is a valid Activity for
     * any Boulder.
     *
     * @param  Activity
     *         The Activity to check.
     * @return
     *       | result == true if fall or no activity
     */
    public static boolean isValidActivity(IActivity Activity) {
        return ((Activity.getId() == 0) || (Activity.getId() == 6));
    }

    /**
     * Set the Activity of this Boulder to the given Activity.
     *
     * @param  Activity
     *         The new Activity for this Boulder.
     * @post   The Activity of this new Boulder is equal to
     *         the given Activity.
     *       | new.getActivity() == Activity
     * @throws IllegalArgumentException
     *         The given Activity is not a valid Activity for any
     *         Boulder.
     *       | ! isValidActivity(getActivity())
     */
    @Raw
    public void setActivity(IActivity Activity)
            throws IllegalArgumentException {
        if (! isValidActivity(Activity))
            throw new IllegalArgumentException();
        this.Activity = Activity;
    }

    /**
     * Variable registering the Activity of this Boulder.
     */
    private IActivity Activity;

    public static boolean isValidlocation(VLocation location) {
        return VLocation.isValidLocation(location);
    }

    /**
     * Return the World of this Boulder.
     */
    @Basic
    @Raw
    @Immutable
    @Override
    public World getWorld() {
        return this.World;
    }

    /**
     * Check whether this Boulder can have the given World as its World.
     *
     * @param  world
     *         The World to check.
     * @return
     *       | result ==true
     */
    @Raw
    public boolean canHaveAsWorld(World world) {
        return true;
    }

    /**
     * Variable registering the World of this Boulder.
     */
    private final World World;

    @Override
    public void activityFinished() {
        this.setActivity(new NoActivity());
        return;
    }

}





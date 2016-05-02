package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.IllegalLocation;

public abstract class MovableWorldObject {

    
    /**
     * Set the location of this Object to the given x, y and z coordinate.
     *
     * @param  locationX
     * 		   The x coordinate for this Object.
     * @param  locationY
     * 		   The y coordinate for this Object.
     * @param  locationZ
     * 		   The z coordinate for this Object.
     * @post   The x coordinate of the location of this new Object is equal to the given x coordinate.
     * 		 | new.getLocation() == location
     * @post   The y coordinate of the location of this new Object is equal to the given y coordinate.
     * 		 | new.getLocation() == location
     * @post   The z coordinate of the location of this new Object is equal to the given z coordinate.
     * 		 | new.getLocation() == location
     * @throws IllegalLocation
     * 		   The given location is not a valid location for any Object.
     *       | ! isValidLocation(getLocation())
     */
    public void setLocation(double locationX, double locationY, double locationZ){
        VLocation location = new VLocation(locationX, locationY, locationZ, this);
        this.setLocation(location);
    }
    /**
     * Set the location of this Unit to the given location.
     *
     * @param  location
     * 		   The new location for this Unit.
     * @post   The location of this new Unit is equal to the given location.
     * 		 | new.getLocation() == location
     * @throws IllegalLocation
     * 		   The given location is not a valid location for any Unit.
     *       | ! isValidLocation(getLocation())
     */
    @Raw
    public void setLocation(VLocation location) throws IllegalLocation {
        if ((! isValidLocation(location)) || location.occupant == this)
            throw new IllegalLocation();
        this.location = location;
        this.register(location);
    }

    /**
     * Register the given location and this Log in the world's world map.
     *
     * @param  location
     * 		   The location to register.
     * @post   The given location and this Log are added in the world's world map.
     */
    public void register(VLocation location) {
        this.getWorld().getWorldMap().put(location, this);
    }

    /**
     * Unregister this Log and its location from the world's world map.
     *
     * @post   This Log and its location are removed from the world's world map.
     */
    public void unregister() {
        this.getWorld().getWorldMap().remove(this.getLocation());
    }

    /**
     * Variable registering the location of this Unit.
     */
    private VLocation location;


    /**
     * Set the location of this Object to the x, y and z coordinate supplied by the given array.
     *
     * @param  array
     * 		   The array supplying the x, y and z coordinate for this Object.
     * @post   The x coordinate of the location of this new Object is equal to the x coordinate supplied by the given array.
     * @post   The y coordinate of the location of this new Object is equal to the y coordinate supplied by the given array.
     * @post   The z coordinate of the location of this new Object is equal to the z coordinate supplied by the given array.
     * @throws IllegalLocation
     * 		   The given location is not a valid location for any Object.
     */

    public void setLocation(double[] array) {
        VLocation location = new VLocation(array[0], array[1], array[2], this);
        this.setLocation(location);
    }

    /**
     * Check whether the given location is a valid location for any Object.
     *
     * @param  location
     * 		   The location to check.
     * @return True if and only if this Object can have the given location as its location in this Object's world.
     */

    public boolean isValidLocation(VLocation location) {
        return VLocation.isValidLocation(location);
    }

    public abstract void activityFinished();
    
    public abstract int getWeight();

    public abstract void advanceTime(double dt);
    /**
     * Variable registering the activity of this Unit.
     */
    private IActivity activity;

    /**
     * Return the activity of this Object.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.activity;
    }

    /**
     * Return the location of this Unit.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return this.location;
    }

    /**
     * Set the activity of this Object to the given activity.
     *
     * @param  activity
     * 		   The new activity for this Object.
     * @throws IllegalArgumentException
     * 		   The given activity is not a valid activity for any Object.
     *       | ! isValidActivity(getActivity())
     * @post   The activity of this new Object is equal to the given activity.
     * 		 | new.getActivity() == activity
     */
    @Raw
    public void setActivity(IActivity activity) throws IllegalArgumentException {
        if (! isValidActivity(activity))
            throw new IllegalArgumentException("this is not a valid Activity");
        this.activity = activity;
    }

    abstract boolean isValidActivity(IActivity activity);

    boolean canHaveAsWorld(World world){
        return true;
    }

    /**
     * Return the world of this Log.
     */
    @Basic
    @Raw
    @Immutable
    public World getWorld() {
        return this.world;
    }

    /**
     * Variable registering the world of this Log.
     */
    private World world;
    /**
     * Set the world of this Log to the given world.
     *
     * @param  world
     *         The new world for this Log.
     * @post   The world of this new Log is equal to the given world.
     * @throws IllegalArgumentException
     *         The given world is not a valid world for any Log.
     */
    @Raw
    void setWorld(World world) throws IllegalArgumentException {
        if (! canHaveAsWorld(world))
            throw new IllegalArgumentException();
        this.world = world;
    }
}

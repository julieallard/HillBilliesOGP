package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.IActivity;
import hillbillies.model.exceptions.IllegalLocation;
import hillbillies.model.exceptions.SyntaxError;

/**
 * A class of movable world objects.
 * 		Movable world objects can be units, boulders or logs.
 * 
 * @version 2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public abstract class MovableWorldObject {

	/*Variables*/
	
    /**
     * Variable registering the world of this movable world object.
     */
    private World world;
	
    /**
     * Variable registering the location of this movable world object.
     */
    private VLocation location;
	
    /**
     * Variable registering the activity of this movable world object.
     */
    private IActivity activity;

    /* Methods */
    
    /**
     * Update this movable world object's position and activity status according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     */
    public abstract void advanceTime(double dt) throws SyntaxError;
    
    /**
     * Return the weight of this movable world object.
     */
    public abstract int getWeight();

    /**
     * Return the world of this movable world object.
     */
    @Basic
    @Raw
    @Immutable
    public World getWorld() {
        return this.world;
    }

    /**
     * Check whether this movable world object can have the given world as its world.
     * 
     * @param	world
     * 			The world to check.
     */
    boolean canHaveAsWorld(World world){
        return true;
    }
    
    /**
     * Set the world of this movable world object to the given world.
     *
     * @param  world
     *         The new world for this movable world object.
     * @post   The world of this new movable world object is equal to the given world.
     * @throws IllegalArgumentException
     *         The given world is not a valid world for any movable world object.
     */
    @Raw
    void setWorld(World world) throws IllegalArgumentException {
        if (! canHaveAsWorld(world))
            throw new IllegalArgumentException();
        this.world = world;
    }
    
    /**
     * Return the location of this movable world object.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return this.location;
    }
	
    /**
     * Set the location of this movable world object to the given x, y and z coordinate.
     *
     * @param	x
     *			The x coordinate for this movable world object.
     * @param	y
     *			The y coordinate for this movable world object.
     * @param	z
     *			The z coordinate for this movable world object.
     * @effect	The location of this movable world object is set to the location with given x, y and z coordinate.
     */
	@Raw
    public void setLocation(double x, double y, double z) {
        VLocation location = new VLocation(x, y, z, this);
        this.setLocation(location);
    }
	
    /**
     * Set the location of this movable world object to the x, y and z coordinate supplied by the given array.
     *
     * @param	array
     *			The array supplying the x, y and z coordinate for this movable world object.
     * @effect	The location of this movable world object is set to the location with the x, y and z coordinate supplied by the given array.
     */
	@Raw
    public void setLocation(double[] array) {
        VLocation location = new VLocation(array[0], array[1], array[2], this);
        this.setLocation(location);
    }
	
    /**
     * Set the location of this movable world object to the given location.
     *
     * @param  location
     * 		   The new location for this movable world object.
     * @post   The location of this new movable world object is equal to the given location.
     * @throws IllegalLocation
     * 		   The given location is not a valid location for any movable world object.
     */
    @Raw
    public void setLocation(VLocation location) throws IllegalLocation {
        if ((! location.isValidLocation()) || location.getOccupant() == this)
            throw new IllegalLocation();
        this.location = location;
        this.register(location);
    }

    /**
     * Register the given location and this movable world object in the world's world map.
     *
     * @param	location
     *			The location to register.
     * @post	The given location and this movable world object are added in the world's world map.
     */
    public void register(VLocation location) {
        this.getWorld().getWorldMap().put(location, this);
    }

    /**
     * Unregister this movable world object and its location from the world's world map.
     *
     * @post	This movable world object and its location are removed from the world's world map.
     */
    public void unregister() {
        this.getWorld().getWorldMap().remove(this.getLocation());
    }
    
    /**
     * Return the activity of this movable world object.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.activity;
    }

    /**
     * Check whether the given activity is a valid activity for the movable world object.
     *
     * @param	activity
     * 			The activity to check.
     */
    abstract boolean isValidActivity(IActivity activity);
    
    /**
     * Set the activity of this movable world object to the given activity.
     *
     * @param	activity
     *			The new activity for this movable world object.
     * @post	The activity of this new movable world object is equal to the given activity.
     * @throws	IllegalArgumentException
     *			The given activity is not a valid activity for any movable world object.
     */
    @Raw
    public void setActivity(IActivity activity) throws IllegalArgumentException {
        if (! isValidActivity(activity))
            throw new IllegalArgumentException("Invalid activity");
        this.activity = activity;
    }
    
    /**
     * Let this movable world object finish its current activity.
     */
    public abstract void activityFinished();

}
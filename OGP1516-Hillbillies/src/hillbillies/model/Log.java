package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Random;

public class Log extends MovableWorldObject {
	/**
	 * Initialize this new Log with given x, y and z coordinate and given world.
     *
     * @param  x
     *         The x coordinate for this new Log.
     * @param  y
     *         The y coordinate for this new Log.
     * @param  z
     *         The z coordinate for this new Log.
     * @param  world
     * 		   The world for this new Log.
	 * @effect The x coordinate of this new Log is set to the given x coordinate.
	 * @effect The y coordinate of this new Log is set to the given y coordinate.
	 * @effect The z coordinate of this new Log is set to the given z coordinate.       
     * @post   The world of this new Log is equal to the given world.
     * @throws UnitIllegalLocation
     *         The given location is not a valid location for any Log.
     */
    public Log(double x, double y, double z, World world) throws UnitIllegalLocation {
        this.setLocation(x, y, z);
        this.world = world;
        Random random = new Random();
        this.weight = 10 + random.nextInt(41);
    }

    /**
     * Variable registering the location of this Log.
     */
    private VLocation location;
    
    /**
     * Variable registering the world of this Log.
     */
    private World world;

    /**
     * Variable registering the weight of this Log.
     */
    final int weight;
    
    /**
     * Variable registering the activity of this Log.
     */
    private IActivity activity;    
    
    /**
     * Return the location of this Log.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return location;
    }
  
    /**
     * Check whether the given location is a valid location for any Log.
     *
     * @param  location
     * 		   The location to check.
     * @return True if and only if this Log can have the given location as its location in this Log's world.
     */
    public static boolean isValidLocation(VLocation location) {
        return VLocation.isValidLocation(location);
    }    
    
    /**
     * Set the location of this Log to the given x, y and z coordinate.
     *
     * @param  x
     * 		   The x coordinate for this Log.
     * @param  y
     * 		   The y coordinate for this Log.
     * @param  z
     * 		   The z coordinate for this Log.
     * @post   The x coordinate of the location of this new Log is equal to the given x coordinate.
     * @post   The y coordinate of the location of this new Log is equal to the given y coordinate.
     * @post   The z coordinate of the location of this new Log is equal to the given z coordinate.
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any Log.
     */
    @Override
    @Raw
    public void setLocation(double x, double y, double z) throws UnitIllegalLocation {
        VLocation location = new VLocation(x, y, z, this);
        this.setLocation(location);
    }
    
    /**
     * Set the location of this Log to the x, y and z coordinate supplied by the given array.
     *
     * @param  array
     * 		   The array supplying the x, y and z coordinate for this Log.
     * @post   The x coordinate of the location of this new Log is equal to the x coordinate supplied by the given array.
     * @post   The y coordinate of the location of this new Log is equal to the y coordinate supplied by the given array.
     * @post   The z coordinate of the location of this new Log is equal to the z coordinate supplied by the given array.
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any Log.
     */
    @Override
    public void setLocation(double[] array) {
        VLocation location = new VLocation(array[0], array[1], array[2], this);
        this.setLocation(location);
    }
    
    /**
     * Set the location of this Log to the given location.
     *
     * @param  location
     * 		   The new location for this Log.
     * @post   The location of this new Log is equal to the given location.
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any Log.
     */
    @Override
    @Raw
    public void setLocation(VLocation location) throws UnitIllegalLocation {
        if ((! isValidLocation(location)) || location.occupant == this)
        	throw new UnitIllegalLocation("This is an invalid location for this Log.");
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
    @Override
    public void register(VLocation location) {
    	this.getWorld().getWorldMap().put(location, this);
    }
    
    /**
     * Unregister this Log and its location from the world's world map.
     * 
     * @post   This Log and its location are removed from the world's world map.
     */
	@Override
    public void unregister() {
		this.getWorld().getWorldMap().remove(this.getLocation());
    }

    /**
     * Return the world of this Log.
     */
    @Basic
    @Raw
    @Immutable
    @Override
    public World getWorld() {
      return world;
    }

    /**
     * Check whether this Log can have the given world as its world.
     *
     * @param  world
     *         The world to check.
     * @return Always true, as a world may contain an unlimited number of Logs.
     */
    @Raw
    public boolean canHaveAsWorld(World world) {
      return true;
    }
    
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
    public void setWorld(World world) throws IllegalArgumentException {
		if (! canHaveAsWorld(world))
			throw new IllegalArgumentException();
		this.world = world;	
	}    
    
    /**
     * Return the weight of this Log.
     */
    @Basic
    @Raw
    @Override
    public int getWeight() {
        return weight;
    }

    /**
     * No documentation needed.
     */
    @Override
    public void advanceTime(double dt) {
    	this.getActivity().advanceActivityTime(dt);
    }
    
    /**
     * Return the activity of this Log.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return activity;
    }

    /**
     * Check whether the given activity is a valid activity for any Log.
     *
     * @param  activity
     *         The activity to check.
     * @return True if and only if the activity consists of not conducting any activity or falling.
     */
    public static boolean isValidActivity(IActivity activity) {
        return ((activity.getId() == 0) || (activity.getId() == 6));
    }

    /**
     * Set the activity of this Log to the given activity.
     *
     * @param  activity
     *         The new activity for this Log.
     * @post   The activity of this new Log is equal to the given activity.
     * @throws IllegalArgumentException
     *         The given activity is not a valid activity for any Log.
     */
    @Raw
    public void setActivity(IActivity activity) throws IllegalArgumentException {
        if (! isValidActivity(activity))
            throw new IllegalArgumentException("Logs can only fall or conduct no activity at all.");
        this.activity = activity;
    }

    /**
     * Let the Log finish its current activity.
     * 
     * @post   This Log is no longer conducting an activity. 
     */
    @Override
    public void activityFinished() {
        this.setActivity(new NoActivity());
    }

}
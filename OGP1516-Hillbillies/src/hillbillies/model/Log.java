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
	 * @effect The x coordinate of this new Log is set to
	 *         the given x coordinate.
	 * @effect The y coordinate of this new Log is set to
	 *         the given y coordinate.
	 * @effect The z coordinate of this new Log is set to
	 *         the given z coordinate.       
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

    private VLocation location;
    private final World world;

    p final int weight;    
    private IActivity activity;    
    
    /**
     * Return the location of this Log.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return this.location;
    }
  
    /**
     * Check whether the given location is a valid location for
     * any Log.
     *
     * @param  location
     * 		   The location to check.
     * @return 
     */
    public static boolean isValidLocation(VLocation location) {
        return VLocation.isValidLocation(location);
    }    
    
    @Override
    @Raw
    public void setLocation(double x, double y, double z) throws UnitIllegalLocation {
        VLocation location = new VLocation(x, y, z, this);
        this.setLocation(location);
    }
    
    /**
     * Set the location of this Log to the given location.
     *
     * @param  location
     * 		   The new location for this Log.
     * @post   The location of this new Log is equal to
     * 		   the given location.
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any
     *         Log.
     */
  
    @Override
    @Raw
    public void setLocation(VLocation location) throws UnitIllegalLocation {
        if (! isValidLocation(location)) throw new UnitIllegalLocation();
        this.location = location;
        this.register(location);
    }

    @Override
    public void register(VLocation location) {
    	this.getWorld().getWorldMap().put(location, this);
    }

    /**
     * Return the world of this Log.
     */
    @Basic
    @Raw
    @Immutable
    @Override
    public World getWorld() {
      return this.world;
    }

    /**
     * Check whether this Log can have the given world as its world.
     *
     * @param  world
     *         The world to check.
     * @return Always true.
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
     * @post   The world of this new Log is equal to
     *         the given world.
     * @throws IllegalArgumentException
     *         The given world is not a valid world for any
     *         Log.
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

	@Override
    public void unregister() {
		this.getWorld().getWorldMap().remove(this.getLocation());
    }

    /**
     * No documentation needed.
     */
    public void advanceTime(double dt) {
    	this.getActivity().advanceActivityTime(dt);
    }
    
    /**
     * Return the activity of this Log.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.activity;
    }

    /**
     * Check whether the given activity is a valid activity for
     * any Log.
     *
     * @param  activity
     *         The activity to check.
     * @return True if and only if the activity consists of not conducting
     * 		   any activity or falling.
     */
    public static boolean isValidActivity(IActivity activity) {
        return ((activity.getId() == 0) || (activity.getId() == 6));
    }

    /**
     * Set the activity of this Log to the given activity.
     *
     * @param  activity
     *         The new activity for this Log.
     * @post   The activity of this new Log is equal to
     *         the given activity.
     * @throws IllegalArgumentException
     *         The given activity is not a valid activity for any
     *         Log.
     */
    @Raw
    public void setActivity(IActivity activity) throws IllegalArgumentException {
        if (! isValidActivity(activity))
            throw new IllegalArgumentException("This is not a valid activity.");
        this.activity = activity;
    }

    @Override
    public void activityFinished() {
        this.setActivity(new NoActivity());
        return;
    }

}




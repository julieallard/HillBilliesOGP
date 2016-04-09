package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Random;

public class Boulder extends MovableWorldObject {
    /**
     * Initialize this new Boulder with given x, y and z coordinate and given world.
     *
     * @param  x
     *         The x coordinate for this new Boulder.
     * @param  y
     *         The y coordinate for this new Boulder.
     * @param  z
     *         The z coordinate for this new Boulder.
     * @param  world
     * 		   The world for this new Boulder.
	 * @effect The x coordinate of this new Boulder is set to
	 *         the given x coordinate.
	 * @effect The y coordinate of this new Boulder is set to
	 *         the given y coordinate.
	 * @effect The z coordinate of this new Boulder is set to
	 *         the given z coordinate.       
     * @post   The world of this new Boulder is equal to the given world.
     * @throws UnitIllegalLocation
     *         The given location is not a valid location for any Boulder.
     */
    public Boulder(double x, double y, double z, World world) throws UnitIllegalLocation {
        this.setLocation(x, y, z);
        this.world = world;
        Random random = new Random();
        this.weight = 10 + random.nextInt(41);
    }

    private VLocation location;
    private World world;

    private final int weight;    
    private IActivity activity;    
    
    /**
     * Return the location of this Boulder.
     */
    @Basic
    @Raw
    public VLocation getLocation() {
        return this.location;
    }
  
    /**
     * Check whether the given location is a valid location for
     * any Boulder.
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
     * Set the location of this Boulder to the given location.
     *
     * @param  location
     * 		   The new location for this Boulder.
     * @post   The location of this new Boulder is equal to
     * 		   the given location.
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location for any
     *         Boulder.
     */
   
    @Override
    @Raw
    public void setLocation(VLocation location) throws UnitIllegalLocation {
        if ((!isValidLocation(location))||location.occupant == this) throw new UnitIllegalLocation("Illegal location was set to Boulder");
        this.location = location;
        this.register(location);
    }

    @Override
    public void setLocation(double[] array) {
        VLocation location = new VLocation(array[0], array[1], array[2], this);
        this.setLocation(location);
    }


    @Override
    public void register(VLocation location) {
    	this.getWorld().getWorldMap().put(location, this);
    }
 
    /**
     * Return the world of this Boulder.
     */
    @Basic
    @Raw
    @Immutable
    @Override
    public World getWorld() {
      return this.world;
    }

    /**
     * Check whether this Boulder can have the given world as its world.
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
     * Set the world of this Boulder to the given world.
     * 
     * @param  world
     *         The new world for this Boulder.
     * @post   The world of this new Boulder is equal to
     *         the given world.
     * @throws IllegalArgumentException
     *         The given world is not a valid world for any
     *         Boulder.
     */
    @Raw
    public void setWorld(World world) throws IllegalArgumentException {
		if (! canHaveAsWorld(world))
			throw new IllegalArgumentException();
		this.world = world;	
	}    
    
    /**
     * Return the weight of this Boulder.
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
    @Override
    public void advanceTime(double dt) {
    	this.getActivity().advanceActivityTime(dt);
    }

    /**
     * Return the activity of this Boulder.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.activity;
    }

    /**
     * Check whether the given activity is a valid activity for
     * any Boulder.
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
     * Set the activity of this Boulder to the given activity.
     *
     * @param  activity
     *         The new activity for this Boulder.
     * @post   The activity of this new Boulder is equal to
     *         the given activity.
     * @throws IllegalArgumentException
     *         The given activity is not a valid activity for any
     *         Boulder.
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





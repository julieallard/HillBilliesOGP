package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Random;

public class Log extends MovableWorldObject {
	/**
	 * Initialize this new Log with given x, y and z coordinates.
	 *
	 * @param  x
	 * 		   The x coordinate for this new Log.
	 * @param  y
	 * 		   The y coordinate for this new Log.
	 * @param  z
	 * 		   The z coordinate for this new Log.
	 * @post   The x coordinate of this new Log is equal to the given x coordinate.
	 * @post   The y coordinate of this new Log is equal to the given y coordinate.
	 * @post   The z coordinate of this new Log is equal to the given z coordinate.
	 * @throws UnitIllegalLocation
	 * 		   This new Log cannot have the given location as its location.
	 */
	public Log(double x, double y, double z, World world) throws UnitIllegalLocation {
		this.setLocation(x, y, z);
        Random random = new Random();
        this.weight = 10 + random.nextInt(41);
		this.World = world;
	}

	/**
	 * Return the location of this Log.
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
	 * Return the weight of this Log.
	 */
	@Basic
	@Raw
	public int getWeight() {
		return weight;
	}

	@Override
    public void unregister() {
    	WorldMap.remove(this.getLocation());
    }
    
	@Override
    public void register(VLocation location) {
    	WorldMap.put(location, this);
    }

    public void advanceTime(double dt) {
    	this.Activity.advanceActivityTime(dt);
    }
    
    /**
     * Return the Activity of this Log.
     */
    @Basic
    @Raw
    public IActivity getActivity() {
        return this.Activity;
    }

    /**
     * Check whether the given Activity is a valid Activity for
     * any Log.
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
     * Set the Activity of this Log to the given Activity.
     *
     * @param  Activity
     *         The new Activity for this Log.
     * @post   The Activity of this new Log is equal to
     *         the given Activity.
     *       | new.getActivity() == Activity
     * @throws IllegalArgumentException
     *         The given Activity is not a valid Activity for any
     *         Log.
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
     * Variable registering the Activity of this Log.
     */
    private IActivity Activity;

    public static boolean isValidlocation(VLocation location) {
        return VLocation.isValidLocation(location);
    }

	/**
	 * Return the World of this Log.
	 */
	@Basic
	@Raw
	@Immutable
	@Override
	public World getWorld() {
		return this.World;
	}

	/**
	 * Check whether this Log can have the given world as its World.
	 *
	 * @param  World
	 *         The World to check.
	 * @return
	 *       | result ==true
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		return true;
	}

	/**
	 * Variable registering the World of this Log.
	 */
	private final World World;

	@Override
	public void activityFinished() {
		this.setActivity(new NoActivity());
		return;
	}

}




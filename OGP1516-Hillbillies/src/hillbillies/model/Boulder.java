package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
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
     * @post   The x coordinate of this new Boulder is equal to the given
     *         x coordinate.
     *       | new.getX() == x
     * @post   The y coordinate of this new Boulder is equal to the given
     *         y coordinate.
     *       | new.getY() == y
     * @post   The z coordinate of this new Boulder is equal to the given
     *         z coordinate.
     *       | new.getZ() == z
     * @throws UnitIllegalLocation
     *         This new Boulder cannot have the given location as its location.
     *       | ! canHaveAsLocation(this.getLocation())
     */
    public Boulder(double x, double y, double z) throws UnitIllegalLocation {
        this.setLocation(x, y, z);
        Random random = new Random();
        this.weight = 10 + random.nextInt(41);
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
    }


    /**
     * Return the weight of this Boulder.
     */
    @Basic
    @Raw
    public int getWeight() {
        return weight;
    }


    public void unregister() {
        //TODO remove from world hashmap when starting to carry the boulder
    }

    public void register() {
        //TODO add back to world hashmap after dropping the boulder
    }

    public void advanceTime(double dt) {this.Activity.advanceActivityTime(dt);}


    /** TO BE ADDED TO CLASS HEADING
     * @invar  The Activity of each Boulder must be a valid Activity for any
     *         Boulder.
     *       | isValidActivity(getActivity())
     */


    /**
     * Initialize this new Boulder with given Activity.
     *
     * @param  Activity
     *         The Activity for this new Boulder.
     * @effect The Activity of this new Boulder is set to
     *         the given Activity.
     *       | this.setActivity(Activity)
     */

    /**
     * Return the Activity of this Boulder.
     */
    @Basic @Raw
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
}

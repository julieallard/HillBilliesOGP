package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.IActivity;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.IllegalLocation;

import java.util.Random;

abstract class InanimateMovableWorldObject extends MovableWorldObject {


    /**
     * Initialize this new Object with given x, y and z coordinate and given world.
     *
     * @param  x
     *         The x coordinate for this new Object.
     * @param  y
     *         The y coordinate for this new Object.
     * @param  z
     *         The z coordinate for this new Object.
     * @param  world
     * 		   The world for this new Object.
     * @effect The x coordinate of this new Object is set to the given x coordinate.
     * @effect The y coordinate of this new Object is set to the given y coordinate.
     * @effect The z coordinate of this new Object is set to the given z coordinate.
     * @post   The world of this new Object is equal to the given world.
     * @throws IllegalLocation
     *         The given location is not a valid location for any Object.
     */
    InanimateMovableWorldObject(double x, double y, double z, World world) throws IllegalLocation {
        this.setLocation(x, y, z);
        setWorld(world);
        Random random = new Random();
        this.weight = 10 + random.nextInt(41);
    }

    /**
     * Variable registering the weight of this Object.
     */
    private final int weight;

    /**
     * Return the weight of this Object.
     */
    @Basic
    @Raw
    @Override
    public int getWeight() {
        return this.weight;
    }

    /**
     * No documentation needed.
     */
    @Override
    public void advanceTime(double dt) {
        this.getActivity().advanceActivityTime(dt);
    }
    /**
     * Check whether the given activity is a valid activity for any Object.
     *
     * @param  activity
     *         The activity to check.
     * @return True if and only if the activity consists of not conducting any activity or falling.
     */
    @Override
    public boolean isValidActivity(IActivity activity) {
        return ((activity.getId() == 0) || (activity.getId() == 6));
    }

    /**
     * Let the Object finish its current activity.
     *
     * @post   This Object is no longer conducting an activity.
     */
    @Override
    public void activityFinished() {
        this.setActivity(new NoActivity());
    }

}

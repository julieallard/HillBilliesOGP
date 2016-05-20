package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.IActivity;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.IllegalLocation;

import java.util.Random;

/**
 * A class of ananimate movable world objects.
 * 		Inanimate movable world objects can be boulders or logs.
 * 
 * @invar	The activity of each inanimate movable world object must be a valid activity for any inanimate movable world object.
 * 		  |	isValidActivity(getActivity())
 * 
 * @version	2.9.05 technical beta
 * @author	Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
abstract class InanimateMovableWorldObject extends MovableWorldObject {

    /**
     * Initialize this new inanimate movable world object with given x, y and z coordinate and given world.
     *
     * @param	x
     *			The x coordinate for this new inanimate movable world object.
     * @param	y
     *			The y coordinate for this new inanimate movable world object.
     * @param	z
     *			The z coordinate for this new inanimate movable world object.
     * @param	world
     *			The world for this new inanimate movable world object.
     * @effect	The location of this new inanimate movable world object is set to the given x, y and z coordinate.
     * @effect	The world of this new inanimate movable world object is set to the given world.
     * @post	The weight of this new inanimate movable world object is equal to a random weight.
     */
    InanimateMovableWorldObject(double x, double y, double z, World world) throws IllegalLocation {
        this.setLocation(x, y, z);
        this.setWorld(world);
        /*
      Object holding the random generator used during the random cration of the inanimate movable world object.
     */
        Random random = new Random();
        this.weight = random.nextInt(41) + 10;
    }

    /* Variables */
    
    /**
     * Variable registering the weight of this inanimate movable world object.
     */
    private final int weight;
    
    /* Methods */
    
    /**
     * Update this inanimate movable world object's position and activity status according to the given amount of time advanced.
     * 
     * @param	dt
     * 			The amount of time to advance.
     */
    @Override
    public void advanceTime(double dt) {
        this.getActivity().advanceActivityTime(dt);
    }
    
    /**
     * Return the weight of this inanimate movable world object.
     */
    @Basic
    @Raw
    @Override
    public int getWeight() {
        return this.weight;
    }
    
    /**
     * Check whether the given activity is a valid activity for any inanimate movable world object.
     *
     * @param	activity
     *			The activity to check.
     * @return	True if and only if the activity consists of not conducting any activity or falling.
     */
    @Override
    public boolean isValidActivity(IActivity activity) {
        return ((activity.getId() == 0) || (activity.getId() == 6));
    }

    /**
     * Let the inanimate movable world object finish its current activity.
     *
     * @effect	This inanimate movable world object is no longer conducting an activity.
     */
    @Override
    public void activityFinished() {
        this.setActivity(new NoActivity());
    }

}

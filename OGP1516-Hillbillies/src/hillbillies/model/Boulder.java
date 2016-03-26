package hillbillies.model;

import java.util.Random;

public class Boulder {
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
	this.setWeight();
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

public void setLocation(double x, double y, double z) {
	this.location = new VLocation(x, y, z, this);
}

/**
 * Return the weight of this Boulder.
 */
@Basic
@Raw
public int getWeight() {
	return weight;
}

public void setWeight() {
	Random random = new Random();
	int w = 10 + random.nextInt(41);
	this.weight = w;
}

public void unregister() {
	//TODO remove from world hashmap when starting to carry the boulder
}

public void register() {
	//TODO add back to world hashmap after dropping the boulder
}

public void advanceTime() {
	
}

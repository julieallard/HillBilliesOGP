package hillbillies.model;

/** 
 * @invar  Each Boulder can have its weight as weight.
 *       | canHaveAsPropertyName_Java(this.getWeight())
 */
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
	if (! canHaveAsLocation(x))
		throw new UnitIllegalLocation();
	this.setLocation(x, y, z);
	this.setWeight();
}

/**
 * Return the location of this Boulder.
 */
@Basic @Raw @Immutable
public int getLocation() {
	return this.location;
}

/**
 * Check whether this Boulder can have the given location as its location.
 *  
 * @param  x
 *         The weight to check.
 * @return 
 *       | result == 
*/
@Raw
public boolean canHaveAsLocation(int x) {
	return false;
}

/**
 * Variable registering the weight of this Boulder.
 */
private VLocation location;
	private void setLocation(double x, double y, double z) {
		this.location = new VLocation(x, y, z, this);
	}

	
}

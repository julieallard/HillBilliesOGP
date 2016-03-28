package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Random;

public class Log extends MovableWorldObject {
	/**
	 * Initialize this new Log with given x, y and z coordinates.
	 *
	 * @param x The x coordinate for this new Log.
	 * @param y The y coordinate for this new Log.
	 * @param z The z coordinate for this new Log.
	 * @throws UnitIllegalLocation This new Log cannot have the given location as its location.
	 *                             | ! canHaveAsLocation(this.getLocation())
	 * @post The x coordinate of this new Log is equal to the given
	 * x coordinate.
	 * | new.getX() == x
	 * @post The y coordinate of this new Log is equal to the given
	 * y coordinate.
	 * | new.getY() == y
	 * @post The z coordinate of this new Log is equal to the given
	 * z coordinate.
	 * | new.getZ() == z
	 */
	public Log(double x, double y, double z) throws UnitIllegalLocation {
		this.setLocation(x, y, z);
        Random random = new Random();
        int w = 10 + random.nextInt(41);
        this.weight = w;
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

	public void setLocation(double x, double y, double z) {
		this.location = new VLocation(x, y, z, this);
	}

	/**
	 * Return the weight of this Log.
	 */
	@Basic
	@Raw
	public int getWeight() {
		return weight;
	}

	public void unregister() {
		//TODO remove from world hashmap when starting to carry the Log
	}

	public void register() {
		//TODO add back to world hashmap after dropping the Log
	}

	public void advanceTime() {

    }
}
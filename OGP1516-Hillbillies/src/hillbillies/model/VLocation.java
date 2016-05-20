package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.exceptions.IllegalLocation;

/**
 * A class of vector locations involving an x, y and z coordinate and an occupant.
 * 
 * @version	2.9.05 technical beta
 * @author	Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class VLocation {
    
    /**
     * Initialize this new VLocation with given x, y and z coordinate and given occupant.
     * 
     * @param	x
     *			The x coordinate for this new vector location.     
     * @param	y
     *			The y coordinate for this new vector location.
     * @param	z
     *			The z coordinate for this new vector location.
     * @post	The occupant of this new vector location is equal to the given occupant.
     * @post	The x coordinate of this new vector location is equal to the given x coordinate.
     * @post	The y coordinate of this new vector location is equal to the given y coordinate.
     * @post	The z coordinate of this new vector location is equal to the given z coordinate.
     * @throws	IllegalLocation
     *			This new vector location cannot have the given x, y or z coordinate as its x, y or zcoordinate.
     */
    public VLocation(double x, double y, double z, MovableWorldObject occupant) throws IllegalLocation {
        this.occupant = occupant;
        if (!canHaveAsCoordinates(x, y, z))
            throw new IllegalLocation();
        this.XLocation = x;
        this.YLocation = y;
        this.ZLocation = z;
    }
    
    /* Variables */
    
    /**
     * Variable registering the occupant of this vector location.
     */
    public final MovableWorldObject occupant;
    
    /**
     * Variable registering the x coordinate of this vector location.
     */
    private final double XLocation;
    
    /**
     * Variable registering the y coordinate of this vector location.
     */
    private final double YLocation;
    
    /**
     * Variable registering the z coordinate of this vector location.
     */
    private final double ZLocation;
 
    /* Methods */
    
    /**
     * Return the occupant of this vector location.
     */
    public MovableWorldObject getOccupant() {
    	return this.occupant;
    }
    
    /**
     * Return the x coordinate of this vector location.
     */
    @Basic
    @Raw
    @Immutable
    public double getXLocation() {
        return this.XLocation;
    }
    
    /**
     * Return the y coordinate of this vector location.
     */
    @Basic
    @Raw
    @Immutable
    public double getYLocation() {
      return this.YLocation;
    }

    /**
     * Return the z coordinate of this vector location.
     */
    @Basic
    @Raw
    @Immutable
    public double getZLocation() {
        return this.ZLocation;
    }

    /**
     * Check whether this vector location can have the given x, y and z coordinates as its x, y and z coordinates.
     *
     * @param	x
     *			The x coordinate to check.
     * @param	y
     * 			The y coordinate to check.
     * @param	z
     * 			The z coordinate to check.
     * @return	True if and only if the x, y and z coordinates are positive and within the borders of this vector location's occupant's world.
     */
    @Raw
    public boolean canHaveAsCoordinates(double x, double y, double z) {
        return (x >= 0 && y >= 0 && z >= 0
        		&& x <= this.occupant.getWorld().xSideSize
        		&& y <= this.occupant.getWorld().ySideSize
        		&& z <= this.occupant.getWorld().zSideSize);
    }

    /**
     * Return an array with the x, y and z coordinate of this vector location.
     */
    public double[] getArray() {
        double[] array = new double[3];
        array[0] = this.getXLocation();
        array[1] = this.getYLocation();
        array[2] = this.getZLocation();
        return array;
    }

    /**
     * Return an array with the x, y and z coordinate of the cube that this vector location is located in.
     */
    public int[] getCubeLocation() {
        int[] array = new int[3];
        array[0] = (int) getXLocation();
        array[1] = (int) getYLocation();
        array[2] = (int) getZLocation();
        return array;
    }
    
    /**
     * Check whether the given location is a valid location for its occupant in the occupant's world.
     *
     * @param	location
     *			The location to check.
     * @return	True if and only if the occupant of the given location can have the given location as its location in the occupant's world.
     */
    public static boolean isValidLocation(VLocation location) {
        return location.getOccupant().getWorld().canHaveAsCubeLocation(location.getCubeLocation(), location.getOccupant());
    }

}
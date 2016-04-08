package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.exceptions.UnitIllegalLocation;
public class VLocation {
    
    /**
     * Initialize this new VLocation with given YLocation.
     * 
     * @param  XLocation
     *         The XLocation for this new VLocation.     
     * @param  YLocation
     *         The YLocation for this new VLocation.
     * @param  ZLocation
     *         The ZLocation for this new VLocation.
     * @post   The XLocation of this new VLocation is equal to the given
     *         XLocation.
     *       | new.getXLocation() == XLocation
     * @post   The YLocation of this new VLocation is equal to the given
     *         YLocation.
     *       | new.getYLocation() == YLocation
     * @post   The ZLocation of this new VLocation is equal to the given
     *         ZLocation.
     *       | new.getZLocation() == ZLocation
     * @throws UnitIllegalLocation
     *         This new VLocation cannot have the given XLocation as its XLocation.
     *       | ! canHaveAsXLocation(this.getXLocation())
     * @throws UnitIllegalLocation
     *         This new VLocation cannot have the given YLocation as its YLocation.
     *       | ! canHaveAsYLocation(this.getYLocation())
     * @throws UnitIllegalLocation
     *         This new VLocation cannot have the given ZLocation as its ZLocation.
     *       | ! canHaveAsZLocation(this.getZLocation())
     */
    public VLocation(double XLocation, double YLocation, double ZLocation, MovableWorldObject occupant) throws UnitIllegalLocation {
        if (! canHaveAsOccupant(occupant))
        	throw new IllegalArgumentException("IllegalOccupantAssignedToLocation");
        this.occupant = occupant;
    	if (! canHaveAsXLocation(XLocation))
            throw new UnitIllegalLocation();
        this.XLocation = XLocation;
    	if (! canHaveAsYLocation(YLocation))
    		throw new UnitIllegalLocation();
        this.YLocation = YLocation;        
        if (! canHaveAsZLocation(ZLocation))
            throw new UnitIllegalLocation();
        this.ZLocation = ZLocation;
    }
    
    public VLocation(double YLocation, double XLocation,double ZLocation) throws UnitIllegalLocation {
        if (! canHaveAsXLocation(XLocation))
            throw new UnitIllegalLocation();
        this.XLocation = XLocation;
    	if (! canHaveAsYLocation(YLocation))
    		throw new UnitIllegalLocation();
        this.YLocation = YLocation;        
        if (! canHaveAsZLocation(ZLocation))
            throw new UnitIllegalLocation();
        this.ZLocation = ZLocation;
    }
    
    private MovableWorldObject occupant;
    private final double XLocation;
    private final double YLocation;
    private final double ZLocation;
    
    /**
     * Check whether this VLocation can have the given object as its occupant.
     *  
     * @param  object
     *         The object to check.
     * @return True if and only if the object is an instance of the class
     * 		   MovableWorldObject or of the class CubeWorldObject.
     *       | result == (object instanceof MovableWorldObject
     *       		|| object instanceof CubeWorldObject)
     */
    public boolean canHaveAsOccupant(Object object) {
        return (object instanceof MovableWorldObject);
    }
 
    /**
     * Return the x coordinate of this VLocation.
     */
    @Basic
    @Raw
    @Immutable
    public double getXLocation() {
        return this.XLocation;
    }

    /**
     * Check whether this VLocation can have the given XLocation as its XLocation.
     *
     * @param  XLocation
     *         The x coordinate to check.
     * @return True if and only if the x coordinate is between 0 and 50.
     *       | result == (Xlocation >= 0
     *       		&& XLocation <= 50)
     */
    @Raw
    public boolean canHaveAsXLocation(double XLocation) {
        return (XLocation >= 0 && XLocation <= this.occupant.getWorld().sideSize);
    }
    
    /**
     * Return the y coordinate of this VLocation.
     */
    @Basic
    @Raw
    @Immutable
    public double getYLocation() {
      return this.YLocation;
    }
    
    /**
     * Check whether this VLocation can have the given YLocation as its YLocation.
     *  
     * @param  YLocation
     *         The y coordinate to check.
     * @return True if and only if the y coordinate is between 0 and size of the sides of this world.
     *       | result == (Ylocation >=0
     *       		&& YLocation <= this.occupant.getWorld().sideSize)
     */
    @Raw
    public boolean canHaveAsYLocation(double YLocation) {
        return (YLocation >= 0 && YLocation <= this.occupant.getWorld().sideSize);
    }

    /**
     * Return the z coordinate of this VLocation.
     */
    @Basic
    @Raw
    @Immutable
    public double getZLocation() {
        return this.ZLocation;
    }

    /**
     * Check whether this VLocation can have the given ZLocation as its ZLocation.
     *
     * @param  ZLocation
     *         The z coordinate to check.
     * @returnTrue if and only if the z coordinate is between 0 and 50.
     *       | result == (Zlocation >= 0
     *       		&& ZLocation <= 50)
     */
    @Raw
    public boolean canHaveAsZLocation(double ZLocation) {
        return (ZLocation >= 0 && ZLocation <= this.occupant.getWorld().sideSize);
    }

    /**
     * Return an array with the x, y and z coordinate of this VLocation.
     */
    public double[] getArray() {
        double[] array = new double[3];
        array[0] = this.getXLocation();
        array[1] = this.getYLocation();
        array[2] = this.getZLocation();
        return array;
    }
    
    /**
     * Return the straight distance between location 1 and location 2.
     * 
     * @param  loc1
     * 		   The firstlocation.
     * @param  loc2
     * 		   The second location. 
     * @return The straight distance between location 1 and location 2.
     */
    public static double getStraightDist(VLocation loc1, VLocation loc2) {
        double deltX2 = Math.pow(loc2.getXLocation() - loc1.getXLocation(), 2);
        double deltY2 = Math.pow(loc2.getYLocation() - loc1.getYLocation(), 2);
        double deltZ2 = Math.pow(loc2.getZLocation() - loc1.getZLocation(), 2);
        return Math.sqrt(deltX2 + deltY2 + deltZ2);
    }

    /**
     * Return an array with the x, y and z coordinate of cube that VLocation is
     * located in.
     */
    public int[] getCubeLocation(){
        int[] array = new int[3];
        array[0] = (int) getXLocation();
        array[1] = (int) getYLocation();
        array[2] = (int) getZLocation();
        return array;
    }
    
    public static boolean isValidLocation(VLocation location){
        return true; // TODO: 26/03/16 implement 
    }

}



package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.exceptions.UnitIllegalLocation;

public class VLocation {

    /** TO BE ADDED TO CLASS HEADING
     * @invar  Each VLocation can have its YLocation as YLocation.
     *       | canHaveAsYLocation(this.getYLocation())
     */
    
    /**
     * Initialize this new VLocation with given YLocation.
     * 
     * @param  YLocation
     *         The YLocation for this new VLocation.
     * @post   The YLocation of this new VLocation is equal to the given
     *         YLocation.
     *       | new.getYLocation() == YLocation
     * @throws UnitIllegalLocation
     *         This new VLocation cannot have the given YLocation as its YLocation.
     *       | ! canHaveAsYLocation(this.getYLocation())
     * @param  XLocation
     *         The XLocation for this new VLocation.
     * @post   The XLocation of this new VLocation is equal to the given
     *         XLocation.
     *       | new.getXLocation() == XLocation
     * @throws UnitIllegalLocation
     *         This new VLocation cannot have the given XLocation as its XLocation.
     *       | ! canHaveAsXLocation(this.getXLocation())
     * @param  ZLocation
     *         The ZLocation for this new VLocation.
     * @post   The ZLocation of this new VLocation is equal to the given
     *         ZLocation.
     *       | new.getZLocation() == ZLocation
     * @throws UnitIllegalLocation
     *         This new VLocation cannot have the given ZLocation as its ZLocation.
     *       | ! canHaveAsZLocation(this.getZLocation())
     */
    public VLocation(double YLocation, double XLocation,double ZLocation,Object occupant) throws UnitIllegalLocation {
      if (! canHaveAsYLocation(YLocation)){
          throw new UnitIllegalLocation();
        }
        this.YLocation = YLocation;
        if (! canHaveAsXLocation(XLocation)){
            throw new UnitIllegalLocation();}
        this.XLocation = XLocation;

        if (! canHaveAsZLocation(ZLocation)){
            throw new UnitIllegalLocation();
        }
        this.ZLocation = ZLocation;
        if (!canHaveAsOccupant(occupant)) throw new IllegalArgumentException("IllegalOccupantAssignedToLocation");
        this.occupant=occupant;
    }
    
    public VLocation(double YLocation, double XLocation,double ZLocation) throws UnitIllegalLocation {
        if (! canHaveAsYLocation(YLocation)){
            throw new UnitIllegalLocation();
          }
          this.YLocation = YLocation;
          if (! canHaveAsXLocation(XLocation)){
              throw new UnitIllegalLocation();}
          this.XLocation = XLocation;

          if (! canHaveAsZLocation(ZLocation)){
              throw new UnitIllegalLocation();
          }
          this.ZLocation = ZLocation;
      }
    
    private Object occupant;

    public boolean canHaveAsOccupant(Object object){
        return (object instanceof MovableWorldObject||object instanceof CubeWorldObject);
    }
    /**
     * Return the YLocation of this VLocation.
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
     *         The YLocation to check.
     * @return 
     *       | result == (Ylocation>=0 &&YLocation<=50
     */
    @Raw
    public boolean canHaveAsYLocation(double YLocation) {
        return (YLocation>=0&&YLocation<=50);
    }
    
    /**
     * Variable registering the YLocation of this VLocation.
     */
    private final double YLocation;

    /**
     * Return the XLocation of this VLocation.
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
     *         The XLocation to check.
     * @return
     *       | result == (Xlocation>=0 &&XLocation<=50
     */
    @Raw
    public boolean canHaveAsXLocation(double XLocation) {
        return (XLocation>=0&&XLocation<=50);
    }

    /**
     * Variable registering the XLocation of this VLocation.
     */
    private final double XLocation;

    /**
     * Return the ZLocation of this VLocation.
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
     *         The ZLocation to check.
     * @return
     *       | result == (Zlocation>=0 &&ZLocation<=50
     */
    @Raw
    public boolean canHaveAsZLocation(double ZLocation) {
        return (ZLocation>=0&&ZLocation<=50);
    }

    /**
     * Variable registering the ZLocation of this VLocation.
     */
    private final double ZLocation;


    public double[] getArray(){
        double[] array=new double[3];
        array[0]=this.getXLocation();
        array[1]=this.getYLocation();
        array[2]=this.getZLocation();
        return array;

    }
    public static double getStraightDist(VLocation loc1,VLocation loc2){
        double deltX2 = Math.pow(loc2.getXLocation()-loc1.getXLocation(),2);
        double deltY2 = Math.pow(loc2.getYLocation()-loc1.getYLocation(),2);
        double deltZ2 = Math.pow(loc2.getZLocation()-loc1.getZLocation(),2);
        return Math.sqrt(deltX2+deltY2+deltZ2);


    }

    public int[] getCubeLocation(){
        int[] array=new int[3];
        array[0]=(int) getXLocation();
        array[1]=(int) getYLocation();
        array[3]=(int) getZLocation();
        return array;
    }
    public static boolean isValidLocation(VLocation location){
        return true; // TODO: 26/03/16 implement 
    }


}



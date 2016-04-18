package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.activities.NoActivity;
import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.Random;

/**
 * A class of Boulders involving an x, y and z coordinate and a world.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Boulder extends InanimateMovableWorldObject {
	
    /**
     * Initialize this new Boulder with given x, y and z coordinate and given world.
     *
     * @param  x
     *         The x coordinate for this new Boulder.
     * @param  y
     *         The y coordinate for this new Boulder.
     * @param  z
     *         The z coordinate for this new Boulder.
     * @param  world
     * 		   The world for this new Boulder.
	 * @effect The x coordinate of this new Boulder is set to the given x coordinate.
	 * @effect The y coordinate of this new Boulder is set to the given y coordinate.
	 * @effect The z coordinate of this new Boulder is set to the given z coordinate.       
     * @post   The world of this new Boulder is equal to the given world.
     * @throws UnitIllegalLocation
     *         The given location is not a valid location for any Boulder.
     */
    public Boulder(double x, double y, double z, World world) throws UnitIllegalLocation {
        super(x, y, z, world);
    }
}
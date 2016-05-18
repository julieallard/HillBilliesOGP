package hillbillies.model;

import hillbillies.model.exceptions.IllegalLocation;

/**
 * A class of boulders.
 * 
 * @version	2.9.05 technical beta
 * @author	Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Boulder extends InanimateMovableWorldObject {
	
    /**
     * Initialize this new boulder with given x, y and z coordinate and given world.
     *
     * @param	x
     *			The x coordinate for this new boulder.
     * @param	y
     *			The y coordinate for this new boulder.
     * @param	z
     *			The z coordinate for this new boulder.
     * @param	world
     *			The world for this new boulder.
     * @effect	This new boulder is initialized as an inanimate movable world object with given x, y and z coordinate as its x, y and z coordinate, respectively,
     * 			and with the given world as its world.
     */
	public Boulder(double x, double y, double z, World world) throws IllegalLocation {
        super(x, y, z, world);
    }
	
}
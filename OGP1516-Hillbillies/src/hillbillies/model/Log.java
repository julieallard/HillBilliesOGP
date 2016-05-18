package hillbillies.model;

import hillbillies.model.exceptions.IllegalLocation;

/**
 * A class of logs.
 * 
 * @version	2.9.05 technical beta
 * @author	Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Log extends InanimateMovableWorldObject {
	
    /**
     * Initialize this new log with given x, y and z coordinate and given world.
     *
     * @param	x
     *			The x coordinate for this new log.
     * @param	y
     *			The y coordinate for this new log.
     * @param	z
     *			The z coordinate for this new log.
     * @param	world
     *			The world for this new log.
     * @effect	This new log is initialized as an inanimate movable world object with given x, y and z coordinate as its x, y and z coordinate, respectively,
     * 			and with the given world as its world.
     */
    public Log(double x, double y, double z, World world) throws IllegalLocation {
        super(x, y, z, world);
    }
    
}
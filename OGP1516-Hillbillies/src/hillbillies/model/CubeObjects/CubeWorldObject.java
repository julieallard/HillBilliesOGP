package hillbillies.model.CubeObjects;

/**
 * A class of cubes in the game world.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public abstract class CubeWorldObject {

	/**
	 * Return whether this cube world object is passable.
	 */
    public abstract boolean isPassable();
    
    
    /**
     * Return whether this cube world object is destructible.
     */
    public abstract boolean isDestructible();
    
    /**
     * Return whether this cube world object can support an object.
     */
    public abstract boolean willSupport();
    
}

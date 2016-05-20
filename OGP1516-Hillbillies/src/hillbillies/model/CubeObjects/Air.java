package hillbillies.model.CubeObjects;

/**
 * A class of air cubes.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Air extends CubeWorldObject {

	/**
	 * Return whether this air cube is passable.
	 * 
	 * @return	Always true.
	 */
    @Override
    public boolean isPassable() {
        return true;
    }
    
    /**
     * Return whether this air cube is destructible.
     * 
     * @return	Always false.
     */
    @Override
    public boolean isDestructible() {
        return false;
    }
    
    /**
     * Return whether this air cube can support an object.
     * 
     * @return	Always false.
     */
    @Override
    public boolean willSupport() {
        return false;
    }
    
}

package hillbillies.model.CubeObjects;

/**
 * A class of rock cubes.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Rock extends CubeWorldObject {

	/**
	 * Return whether this rock cube is passable.
	 * 
	 * @return	Always false.
	 */
    @Override
    public boolean isPassable() {
        return false;
    }

    /**
     * Return whether this rock cube is destructible.
     * 
     * @return	Always true.
     */
    @Override
    public boolean isDestructible() {
        return true;
    }

    /**
     * Return whether this rock cube can support an object.
     * 
     * @return	Always true.
     */
    @Override
    public boolean willSupport() {
        return true;
    }

}
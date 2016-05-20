package hillbillies.model.CubeObjects;

/**
 * A class of workshop cubes.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Workshop extends CubeWorldObject {
	
	/**
	 * Return whether this workshop cube is passable.
	 * 
	 * @return	Always true.
	 */
    @Override
    public boolean isPassable() {
        return true;
    }
    
    /**
     * Return whether this workshop cube is destructible.
     * 
     * @return	Always false.
     */
    @Override
    public boolean isDestructible() {
        return false;
    }
    
    /**
     * Return whether this workshop cube can support an object.
     * 
     * @return	Always false.
     */
    @Override
    public boolean willSupport() {
        return false;
    }
    
}

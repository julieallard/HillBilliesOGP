package hillbillies.model.CubeObjects;

/**
 * A class of Cubes in the game world.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public abstract class CubeWorldObject {

    public abstract boolean isPassable();
    
    public abstract boolean isDestructible();
    
    public abstract boolean willSupport();
    
}

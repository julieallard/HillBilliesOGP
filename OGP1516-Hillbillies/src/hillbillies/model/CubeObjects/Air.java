package hillbillies.model.CubeObjects;

/**
 * A class of Air cubes.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Air extends CubeWorldObject {

    @Override
    public boolean isPassable() {
        return true;
    }
    
    @Override
    public boolean isDestructible() {
        return false;
    }
    
    @Override
    public boolean willSupport() {
        return false;
    }
    
}

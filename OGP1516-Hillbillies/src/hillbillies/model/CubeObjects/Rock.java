package hillbillies.model.CubeObjects;

/**
 * A class of Rock cubes.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class Rock extends CubeWorldObject {

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean isDestructible() {
        return true;
    }

    @Override
    public boolean willSupport() {
        return true;
    }

}
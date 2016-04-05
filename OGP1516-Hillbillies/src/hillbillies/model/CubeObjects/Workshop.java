package hillbillies.model.CubeObjects;

public class Workshop extends CubeWorldObject {
	
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

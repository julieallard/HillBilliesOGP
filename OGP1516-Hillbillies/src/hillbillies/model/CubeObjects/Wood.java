package hillbillies.model.CubeObjects;


public class Wood extends CubeWorldObject {



        @Override
        public boolean isPassable(){
            return false;
        }
        @Override
        public boolean isDestructible(){
            return true;
        }
        @Override
        public boolean willSupport(){
            return true;
        }


}

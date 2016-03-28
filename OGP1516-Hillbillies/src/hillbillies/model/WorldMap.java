package hillbillies.model;

import java.util.*;

public class WorldMap<Key extends VLocation ,Value extends MovableWorldObject>extends HashMap<VLocation,MovableWorldObject> {
    public List<MovableWorldObject> getAllInCube(int[] cubeLocation){
        Set<VLocation> keySet=this.keySet();
        List<MovableWorldObject> objectSet=new ArrayList<>();
        for (VLocation loc :
                keySet) {
            if (!Arrays.equals(loc.getCubeLocation(),cubeLocation)) continue;
            objectSet.add(this.get(loc));
        }
        return objectSet;
    }

    public List<Log> getAllLogsInCube(int[] cubeLocation){
        List<MovableWorldObject> totList =getAllInCube(cubeLocation);
        List<Log> logList=new ArrayList<>();
        for (MovableWorldObject object: totList) {
            if(object instanceof Log){
                logList.add((Log)object);
            }

        }
        return logList;

    }
    public List<Boulder> getAllBouldersInCube(int[] cubeLocation){
        List<MovableWorldObject> totList =getAllInCube(cubeLocation);
        List<Boulder> boulderList=new ArrayList<>();
        for (MovableWorldObject object: totList) {
            if(object instanceof Boulder){
                boulderList.add((Boulder)object);
            }

        }
        return boulderList;

    }
    public List<Unit> getAllUnitsInCube(int[] cubeLocation){
        List<MovableWorldObject> totList =getAllInCube(cubeLocation);
        List<Unit> unitList=new ArrayList<>();
        for (MovableWorldObject object: totList) {
            if(object instanceof Unit){
                unitList.add((Unit)object);
            }

        }
        return unitList;

    }

}

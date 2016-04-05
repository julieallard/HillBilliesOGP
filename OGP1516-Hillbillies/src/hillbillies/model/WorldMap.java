package hillbillies.model;

import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.*;

public class WorldMap<Key extends VLocation, Value extends MovableWorldObject> extends HashMap<VLocation, MovableWorldObject> {
	
	/**
	 * Return all the objects in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the objects to check.
	 * @return A list with all the objects in the cube with given location.
	 */
    public List<MovableWorldObject> getAllInCube(int[] cubeLocation) {
        Set<VLocation> keySet = this.keySet();
        List<MovableWorldObject> objectSet = new ArrayList<>();
        for (VLocation loc: keySet) {
            if (! Arrays.equals(loc.getCubeLocation(), cubeLocation))
            	continue;
            objectSet.add(this.get(loc));
        }
        return objectSet;
    }

	/**
	 * Return all the Logs in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Logs to check.
	 * @return A list with all the Logs in the cube with given location.
	 */
    public List<Log> getAllLogsInCube(int[] cubeLocation) {
        List<MovableWorldObject> totList = getAllInCube(cubeLocation);
        List<Log> logList = new ArrayList<>();
        for (MovableWorldObject object: totList) {
            if (object instanceof Log) {
                logList.add((Log) object);
            }
        }
        return logList;
    }
    
	/**
	 * Return all the Boulders in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Boulders to check.
	 * @return A list with all the Boulders in the cube with given location.
	 */
    public List<Boulder> getAllBouldersInCube(int[] cubeLocation){
        List<MovableWorldObject> totList = getAllInCube(cubeLocation);
        List<Boulder> boulderList = new ArrayList<>();
        for (MovableWorldObject object: totList) {
            if (object instanceof Boulder) {
                boulderList.add((Boulder) object);
            }
        }
        return boulderList;
    }
    
	/**
	 * Return all the Units in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Units to check.
	 * @return A list with all the Units in the cube with given location.
	 */ 
    public List<Unit> getAllUnitsInCube(int[] cubeLocation){
        List<MovableWorldObject> totList = getAllInCube(cubeLocation);
        List<Unit> unitList = new ArrayList<>();
        for (MovableWorldObject object: totList) {
            if (object instanceof Unit) {
                unitList.add((Unit) object);
            }
        }
        return unitList;
    }

    /**
     * Move the given object to given new location in this WorldMap.
     * 
     * @param  object
     * 		   The object to move.
     * @param  newLoc
     * 		   The new location of the object.
     * @throws IllegalArgumentException
     * 		   The given object is not present in the Worldmap.
     * @throws UnitIllegalLocation
     * 		   The given location is the key of another, wrong object in the WorldMap.
     */
    public void moveObject(MovableWorldObject object, VLocation newLoc) throws UnitIllegalLocation, IllegalArgumentException {
        MovableWorldObject removed = this.remove(object.getLocation());
        if (removed == null)
        	throw new IllegalArgumentException("This object did not exist in the WorldMap.");
        if (! object.equals(removed))
        	throw new UnitIllegalLocation("The wrong object was removed, the assignment of locations is wrong.");
        object.setLocation(newLoc);
    }
}

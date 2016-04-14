package hillbillies.model;

import hillbillies.model.exceptions.UnitIllegalLocation;

import java.util.*;

/**
 * A class of WorldMaps.
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
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
	 * Return a set collecting all the units in this WorlMap.
	 */
    public Set<Unit> getAllUnits(){
        Collection<MovableWorldObject> totColl = this.values();
        Set<Unit> unitset = new HashSet<>();
        for (MovableWorldObject object: totColl) {
            if (object instanceof Unit)
            	unitset.add((Unit) object);
        }
        return unitset;
    }
  
	/**
	 * Return a set collecting all the boulders in this WorlMap.
	 */
    public Set<Boulder> getAllBoulders() {
        Collection<MovableWorldObject> totColl = this.values();
        Set<Boulder> boulderSet = new HashSet<>();
        for (MovableWorldObject object: totColl) {
            if (object instanceof Boulder)
            	boulderSet.add((Boulder) object);
        }
        return boulderSet;
    }
    
	/**
	 * Return a set collecting all the logs in this WorlMap.
	 */
    public Set<Log> getAllLogs(){
        Collection<MovableWorldObject> totColl = this.values();
        Set<Log> logSet = new HashSet<>();
        for (MovableWorldObject object: totColl) {
            if (object instanceof Log)
            	logSet.add((Log) object);
        }
        return logSet;
    }

}

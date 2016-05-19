package hillbillies.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class of world maps.
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class WorldMap<K extends VLocation, V extends MovableWorldObject> extends HashMap<VLocation, MovableWorldObject> {
	
	/**
	 * Return all the objects in the cube with given location.
	 * 
	 * @param	cubeLocation
	 *			The location of the cube with the objects to check.
	 * @return	A list with all the objects in the cube with given location.
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
	 * Return all the logs in the cube with given location.
	 * 
	 * @param	cubeLocation
	 *			The location of the cube with the logs to check.
	 * @return	A list with all the logs in the cube with given location.
	 */
    public List<Log> getAllLogsInCube(int[] cubeLocation) {
        List<MovableWorldObject> totList = getAllInCube(cubeLocation);
        List<MovableWorldObject> logList = totList.stream().filter(object -> (object instanceof Log)).collect(Collectors.toList());
        return (List) logList;
    }
   
	/**
	 * Return all the boulders in the cube with given location.
	 * 
	 * @param	cubeLocation
	 *			The location of the cube with the boulders to check.
	 * @return	A list with all the boulders in the cube with given location.
	 */
    public List<Boulder> getAllBouldersInCube(int[] cubeLocation){
        List<MovableWorldObject> totList = getAllInCube(cubeLocation);
        List<MovableWorldObject> boulderList = totList.stream().filter(object -> (object instanceof Boulder)).collect(Collectors.toList());
        return (List) boulderList;
    }
    
	/**
	 * Return all the units in the cube with given location.
	 * 
	 * @param	cubeLocation
	 *			The location of the cube with the units to check.
	 * @return	A list with all the units in the cube with given location.
	 */ 
    public List<Unit> getAllUnitsInCube(int[] cubeLocation){
        List<MovableWorldObject> totList = getAllInCube(cubeLocation);
        List<MovableWorldObject> unitList = totList.stream().filter(object -> (object instanceof Unit)).collect(Collectors.toList());
        return (List) unitList;
    }

	/**
	 * Return a set collecting all the units in this world map.
	 */
    public Set<Unit> getAllUnits(){
        Collection<MovableWorldObject> totColl = this.values();
        Set<MovableWorldObject> unitSet = totColl.stream().filter(object -> (object instanceof Unit)).collect(Collectors.toSet());
        return (Set) unitSet;
    }
  
	/**
	 * Return a set collecting all the boulders in this world map.
	 */
    public Set<Boulder> getAllBoulders() {
        Collection<MovableWorldObject> totColl = this.values();
        Set<MovableWorldObject> boulderSet = totColl.stream().filter(object -> (object instanceof Boulder)).collect(Collectors.toSet());
        return (Set) boulderSet;
    }
    
	/**
	 * Return a set collecting all the logs in this world map.
	 */
    public Set<Log> getAllLogs() {
        Collection<MovableWorldObject> totColl = this.values();
        Set<MovableWorldObject> logSet = totColl.stream().filter(object -> (object instanceof Log)).collect(Collectors.toSet());
        return (Set) logSet;
    }

}
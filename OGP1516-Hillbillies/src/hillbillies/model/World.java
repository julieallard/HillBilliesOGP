package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.CubeObjects.Air;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.CubeObjects.Rock;
import hillbillies.model.CubeObjects.Wood;
import hillbillies.model.exceptions.UnitIllegalLocation;
import hillbillies.model.CubeObjects.Workshop;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @invar  The CubeWorld of each World must be a valid CubeWorld for any
 *         World.
 *       | isValidCubeWorld(getCubeWorld())
 */
public class World {
    /**
     * Initialize this new World with given CubeWorld.
     *
     * @param  CubeWorld
     * 		   The CubeWorld for this new World.
     * @effect The CubeWorld of this new World is set to
     * 		   the given CubeWorld.
     */

    public World(int[][][] CubeWorld) throws UnitIllegalLocation, IllegalArgumentException {
        this.setCubeWorld(CubeWorld);
        this.setWorldMap(new WorldMap<>());
        this.sideSize = CubeWorld.length;
    }



    private CubeWorldObject[][][] CubeWorld;
    private WorldMap<VLocation,MovableWorldObject> WorldMap;
    
	/**
	 * Set collecting references to factions belonging to this world.
	 * 
	 * @invar The set of factions is effective.
	 * @invar Each faction in the FactionSet references this world as the world to
	 * 		  which it is attached. 
	 */
	private Set<Faction> FactionSet;
	
	/**
	 * Set collecting references to Units belonging to this world.
	 * 
	 * @invar The set of Units is effective.
	 * @invar Each Unit in the TotalUnitSet references this world as the world to
	 * 		  which it is attached. 
	 */
	private Set<Unit> TotalUnitSet; 
	
    public final int sideSize;
    
    /**
     * Return the CubeWorld of this World.
     * 		The CubeWorld represents the three dimensional extent of this World.
     */
    @Basic
    @Raw
    public CubeWorldObject[][][] getCubeWorld() {
        return this.CubeWorld;
    }

    /**
     * Check whether the given CubeWorld is a valid CubeWorld for
     * any World.
     *
     * @param  CubeWorld
     * 		   The CubeWorld to check.
     * @return True if and only if the CubeWorld is cubical, in other words
     * 		   if all sub arrays have the same size.
     */
    public boolean isValidCubeWorld(int[][][] CubeWorld) {
        for (int[][] YZLevel: CubeWorld) {
            if (YZLevel.length != sideSize) {
                return false;
            }
            for (int[] ZLine: YZLevel) {
                if (ZLine.length != sideSize) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Set the CubeWorld of this World to the given CubeWorld.
     *
     * @param  CubeWorld
     * 		   The new CubeWorld for this World.
     * @post   The CubeWorld of this new World is equal to
     * 		   the given CubeWorld.
     * @throws UnitIllegalLocation
     * 		   The given CubeWorld is not a valid CubeWorld for any
     *		   World.
     */
    @Raw
    public void setCubeWorld(int[][][] CubeWorld) throws UnitIllegalLocation, IllegalArgumentException {
        if (! isValidCubeWorld(CubeWorld))
            throw new UnitIllegalLocation();
        CubeWorldObject[][][] CubeWorldFinal = new CubeWorldObject[sideSize][sideSize][sideSize];
        for (int x = 0; x < sideSize; x++) {
            for (int y = 0; y < sideSize; y++) {
                for (int z = 0; z < sideSize; z++) {
                    switch (CubeWorld[x][y][z]) {
                        case 0:		CubeWorldFinal[x][y][z] = new Air();
                                    break;
                        case 1:		CubeWorldFinal[x][y][z] = new Rock();
                            		break;
                        case 2:		CubeWorldFinal[x][y][z] = new Wood();
                                    break;
                        case 3:		CubeWorldFinal[x][y][z] = new Workshop();
                                    break;
                        default:	CubeWorldFinal[x][y][z] = new Air();
                                    break;
                    }
                }
            }
        }
        this.CubeWorld = CubeWorldFinal;
    }

    /**
     * Destroy the cube at given location in this world.
     *
     * @param  location
     * 		   The location of the cube to destroy.
     * @post   The cube at given location is destroyed and becomes air.
     * @throws UnitIllegalLocation
     * 		   The given location is not a valid location.
     * @throws IllegalArgumentException
     * 		   The cube at given location is not destructible.
     */    
    public void destroyCube(int[] location) throws IllegalArgumentException, UnitIllegalLocation {
        if (location.length != 3)
            throw new UnitIllegalLocation();
        CubeWorldObject[][][] world = this.getCubeWorld();
        CubeWorldObject cube = world[location[0]][location[1]][location[2]];
        if (! cube.isDestructible())
        	throw new IllegalArgumentException("This cube is not destructible.");
        this.CubeWorld[location[0]][location[1]][location[2]] = new Air();
        replace(cube, location);
    }

    private void replace(CubeWorldObject cube, int[] location) {
        if (Math.random() <= 0.25) {
            Rock rock = new Rock();
            if (cube.getClass() == rock.getClass()) {
                new Boulder(location[0], location[1], location[2], this);
            } else {
                new Log(location[0], location[1], location[2], this);
            }
        }
    }

    /**
     * Return the WorldMap of this World.
     */
    @Basic @Raw
    public WorldMap<VLocation, MovableWorldObject> getWorldMap() {
      return this.WorldMap;
    }
    
    /**
     * Check whether the given WorldMap is a valid WorldMap for
     * any World.
     *  
     * @param  WorldMap
     *         The WorldMap to check.
     * @return Always true.
    */
    public static boolean isValidWorldMap(WorldMap<VLocation, MovableWorldObject> WorldMap) {
    	return true;
    }
    
    /**
     * Set the WorldMap of this World to the given WorldMap.
     * 
     * @param  WorldMap
     *         The new WorldMap for this World.
     * @post   The WorldMap of this new World is equal to
     *         the given WorldMap.
     * @throws IllegalArgumentException
     *         The given WorldMap is not a valid Worldmap for any
     *         world.
     */
    @Raw
    public void setWorldMap(WorldMap<VLocation, MovableWorldObject> WorldMap) throws IllegalArgumentException {
    	if (! isValidWorldMap(WorldMap))
    		throw new IllegalArgumentException();
    	this.WorldMap = WorldMap;
    }
    
	/**
	 * Return all the Units in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the objects to check.
	 * @return A list with all the Units in the cube with given location.
	 */
    public List<Unit> getUnitsAt(int[] cubeLocation) {
        return this.getWorldMap().getAllUnitsInCube(cubeLocation);
    }
    
	/**
	 * Return all the Logs in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Logs to check.
	 * @return A list with all the Logs in the cube with given location.
	 */
    public List<Log> getLogsAt(int[] cubeLocation) {
        return this.getWorldMap().getAllLogsInCube(cubeLocation);
    }
    
	/**
	 * Return all the Boulders in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Boulders to check.
	 * @return A list with all the Boulders in the cube with given location.
	 */
    public List<Boulder> getBouldersAt(int[] cubeLocation) {
        return this.getWorldMap().getAllBouldersInCube(cubeLocation);
    }

    /**
     * Check whether 
     * 
     * @param  cubeLoc
     * 		   The location of the cube to check.
     * @param  object
     * 		   The object
     * @return
     */
    public boolean canHaveAsCubeLocation(int[] cubeLoc, MovableWorldObject object) {
        if (cubeLoc.length!=3) return false;
        if (! CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].isPassable())
        	return false;
        if (CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].willSupport())
        	return true;
        if (! (object instanceof Unit))
        	return false;
        int xLoc = cubeLoc[0];
        int yLoc = cubeLoc[1];
        int zLoc = cubeLoc[2];
        if (zLoc == 0) 
        	return true;
        for (int x = xLoc-1; x < xLoc+2; x++) {
            for (int y = yLoc-1; y < yLoc+2; y++) {
                for (int z = zLoc-1; z < zLoc+2; z++) {
                    if (x < 0 || y < 0 || z < 0)
                        return true;
                    if (x == sideSize || y == sideSize || z == sideSize)
                    	return true;
                    if (CubeWorld[x][y][z].willSupport())
                    	return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Check whether the given location contains a cube that will stop the fall
     * of any object.
     * 
     * @param  cubeLoc
     *         The location of the cube the object is passing while falling.
     * @return True if and only if the terrain feature of the cube at the given
     * 		   location will support any object.
     */
    public boolean willBreakFall(int[] cubeLoc) {
        return (CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].willSupport());
    }
	
	/**
	 * Add the given faction to this world.
	 * 
	 * @param  faction
	 * 		   The faction to add.
	 * @post   The given faction is added to this world.
	 */
    public void addFaction(Faction faction) throws IllegalArgumentException {
		if (! faction.canHaveAsWorld(this))
			throw new IllegalArgumentException("This world has already reached its max amount of factions.");
        this.FactionSet.add(faction);
	}

	/**
	 * Remove the given faction from this world.
	 * 
	 * @param  faction
	 * 		   The faction to remove.
	 * @post   The given faction is removed from this world.
	 */
	public void removeFaction(Faction faction) {
		FactionSet.remove(faction);
	}

	/**
	 * Return the number of factions in this world.
	 */
	public int getNumberOfFactions() {
		return FactionSet.size();
	}
	
	/**
	 * Return the faction containing the smallest number of Units.
	 */
	public Faction getSmallestFaction() {
		Faction smallestFaction = (Faction) FactionSet.toArray()[0];
		for (Faction faction: FactionSet) {
			if (faction.getUnitSet().size() < smallestFaction.getUnitSet().size()) {
				smallestFaction = faction;
			}
		}
		return smallestFaction;
	}

	/**
	 * Add the given unit to this world.
	 * 
	 * @param  unit
	 * 		   The unit to add.
	 * @post   The given unit is added to this world.
	 */
	public void addUnit(Unit unit) throws IllegalArgumentException {
		if (! unit.canHaveAsWorld(this)) 
			throw new IllegalArgumentException("This world has already reached its max amount of Units.");
        this.TotalUnitSet.add(unit);
	}

	/**
	 * Remove the given unit from this world.
	 * 
	 * @param  unit
	 * 		   The unit to remove.
	 * @post   The given unit is removed from this world.
	 */
	public  void removeUnit(Unit unit) {
		TotalUnitSet.remove(unit);
	}	

	/**
	 * Return the number of Units of this world.
	 */
	public int getNumberOfUnits() {
		return TotalUnitSet.size();
	}

	/**
	 * Check whether this world can add extra Units.
	 */
    public boolean canHaveExtraUnits(){
        return (TotalUnitSet.size() < 100);
    }


    public static boolean isValidTimeDuration(double dt){
        return (dt>0&&dt<0.2);
    }

    public void advanceTime(double dt){

    }

    public int getCubeAt(int[] loc){
        CubeWorldObject cube;

        try {cube=this.CubeWorld[loc[0]][loc[1]][loc[2]];}
        catch (IndexOutOfBoundsException exception){
            throw new IllegalArgumentException("An illegal cubelocation was inspected getCubeAt");
        }

        if (cube instanceof Air) return 0;
        if (cube instanceof Rock) return 1;
        if (cube instanceof Wood) return 2;
        if (cube instanceof Workshop) return 3;
        throw new IllegalArgumentException();
    }



    //TODO cave in

}

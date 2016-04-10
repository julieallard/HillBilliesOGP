package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.CubeObjects.Air;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.CubeObjects.Rock;
import hillbillies.model.CubeObjects.Wood;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.exceptions.UnitIllegalLocation;
import hillbillies.model.CubeObjects.Workshop;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import java.util.*;

/**
 * A class of Worlds involving a cube world and a listener of world changes.
 * 
 * @invar  The CubeWorld of each World must be a valid CubeWorld for any World. 
 *       | isValidCubeWorld(getCubeWorld())
 * 
 * @version 0.9 alpha
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class World {
    /**
     * Initialize this new World with given CubeWorld.
     *
     * @param  CubeWorld
     * 		   The cube world with an allocation of the geological features for this new World.
     * @effect The cube world of this new World is set to the given cube world.
     */

    public World(int[][][] CubeWorld, TerrainChangeListener changeListener) throws UnitIllegalLocation, IllegalArgumentException {
        this.setWorldMap(new WorldMap<>());
        this.sideSize = CubeWorld.length;
        this.borderConnect = new ConnectedToBorder(sideSize, sideSize, sideSize);
        this.caveInlist = new ArrayList<>();
        this.setCubeWorld(CubeWorld);
        this.changeListener = changeListener;
        this.FactionSet = new HashSet<>();
        this.TotalUnitSet = new HashSet<>();
    }
    
    /**
     * Variable registering the changes of terrains of this World.
     */
    private final TerrainChangeListener changeListener;
    
    /**
     * Variable registering the solid and non-solid state of neigboring cubes (of neighboring cubes etc.) of this World.
     */
    public final ConnectedToBorder borderConnect;
    
    /**
     * Variable registering the cubes of this World that are not supported anymore and which will cave in.
     */
    private final ArrayList<int[]> caveInlist;
    
    /**
     * Variable registering the cube world with an allocation of the geological features and the size of this World.
     */
    private CubeWorldObject[][][] CubeWorld;
    
    /**
     * Variable registering a world map with the objects and their location in this World,
     * with the location as the key and the object as the value of this HashMap.
     */
    private WorldMap<VLocation, MovableWorldObject> WorldMap;
    
	/**
	 * Set collecting references to factions belonging to this world.
	 * 
	 * @invar The set of factions is effective.
	 * @invar Each element in the set of factions references a faction that is an acceptable faction for this World.
	 * @invar Each faction in the FactionSet references this world as the world to which it is attached. 
	 */
	public Set<Faction> FactionSet;
	
	/**
	 * Set collecting references to Units belonging to this world.
	 * 
	 * @invar The set of Units is effective.
	 * @invar Each element in the set of Units references a unit that is an acceptable unit for this World.
	 * @invar Each Unit in the TotalUnitSet references this world as the world to which it is attached. 
	 */
	public Set<Unit> TotalUnitSet;
	
	/**
	 * Variable registering the length of the sides of this World.
	 */
    public int sideSize;
    
    /**
     * Return the cube world of this World.
     */
    @Basic
    @Raw
    public CubeWorldObject[][][] getCubeWorld() {
        return this.CubeWorld;
    }

    /**
     * Check whether the given cube world is a valid cube world for any World.
     *
     * @param  CubeWorld
     * 		   The cube world to check.
     * @return True if and only if the cube world is cubical, in other words if all sub arrays have the same length.
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
     * Set the cube world of this World to the given cube world.
     *
     * @param  CubeWorld
     * 		   The new cube world for this World.
     * @post   The cube world of this new World is equal to the given cube world.
     * @throws UnitIllegalLocation
     * 		   The given cube world is not a valid cube world for any World.
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
                                    caveInlist.addAll(borderConnect.changeSolidToPassable(x,y,z));
                                    break;
                        case 1:		CubeWorldFinal[x][y][z] = new Rock();
                            		break;
                        case 2:		CubeWorldFinal[x][y][z] = new Wood();
                                    break;
                        case 3:		CubeWorldFinal[x][y][z] = new Workshop();
                                    caveInlist.addAll(borderConnect.changeSolidToPassable(x,y,z));
                                    break;
                        default:	CubeWorldFinal[x][y][z] = new Air();
                                    caveInlist.addAll(borderConnect.changeSolidToPassable(x,y,z));
                                    break;
                    }
                }
            }
        }
        this.CubeWorld = CubeWorldFinal;
    }

    /**
     * Destroy the cube at given location in this World.
     *
     * @param  location
     * 		   The location of the cube to destroy.
     * @post   The geological feature or the cube at given location is air.
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
        	return;
        this.changeListener.notifyTerrainChanged(location[0], location[1], location[2]);
        caveInlist.addAll(borderConnect.changeSolidToPassable(location[0], location[1], location[2]));
        this.CubeWorld[location[0]][location[1]][location[2]] = new Air();
        replace(cube, location);
    }

    /**
     * Replace the given cube at given location by a boulder, a log or nothing.
     * 
     * @param  cube
     * 		   The cube caving in to replace.
     * @param  location
     * 		   The location of the cube to replace.
     * @effect With a propbability of 25%, if the cube was of wood, a Log is created in the middle of the cube
     * 		   and if the cube was of rock, a Boulder is created in the middle of the cube.
     */
    private void replace(CubeWorldObject cube, int[] location) {
        if (Math.random() <= 0.25) {
            Rock rock = new Rock();
            if (cube.getClass() == rock.getClass()) {
                new Boulder((double) location[0] + 0.5, (double) location[1] + 0.5, (double) location[2] + 0.5, this);
            } else {
                new Log((double) location[0] + 0.5, (double) location[1] + 0.5, (double) location[2] + 0.5, this);
            }
        }
    }

    /**
     * Return the world map of this World.
     */
    @Basic @Raw
    public WorldMap<VLocation, MovableWorldObject> getWorldMap() {
      return this.WorldMap;
    }
    
    /**
     * Check whether the given world map is a valid world map for any World.
     *  
     * @param  WorldMap
     *         The world map to check.
     * @return Always true.
    */
    public static boolean isValidWorldMap(WorldMap<VLocation, MovableWorldObject> WorldMap) {
    	return true;
    }
    
    /**
     * Set the world map of this World to the given world map.
     * 
     * @param  WorldMap
     *         The new world map for this World.
     * @post   The world map of this new World is equal to the given world map.
     * @throws IllegalArgumentException
     *         The given world map is not a valid world map for any world.
     */
    @Raw
    public void setWorldMap(WorldMap<VLocation, MovableWorldObject> WorldMap) throws IllegalArgumentException {
    	if (! isValidWorldMap(WorldMap))
    		throw new IllegalArgumentException();
    	this.WorldMap = WorldMap;
    }
    
	/**
	 * Return a list collecting all the Units in the cube at given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the objects to check.
	 */
    public List<Unit> getUnitsAt(int[] cubeLocation) {
        return this.getWorldMap().getAllUnitsInCube(cubeLocation);
    }
    
	/**
	 * Return a list collecting all the Logs in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Logs to check.
	 */
    public List<Log> getLogsAt(int[] cubeLocation) {
        return this.getWorldMap().getAllLogsInCube(cubeLocation);
    }
    
	/**
	 * Return a list collecting all the Boulders in the cube with given location.
	 * 
	 * @param  cubeLocation
	 * 		   The location of the cube with the Boulders to check.
	 */
    public List<Boulder> getBouldersAt(int[] cubeLocation) {
        return this.getWorldMap().getAllBouldersInCube(cubeLocation);
    }

    /**
     * Check whether the given object can have the given cube location as its cube location.
     * 
     * @param  cubeLoc
     * 		   The location of the cube to check.
     * @param  object
     * 		   The object whose location to check.
     * @return True if and only if the cube location is a three dimensional array, if the cube is passable
     * 		   and if the cube underneath has zero as its z coordinate or can support objects. If the object is a Unit,
     * 		   this method will return true, except the before mentioned conditions, if and only if the x, y and z
     * 		   coordinate is at the border of the game world
     * 		   
     */
    public boolean canHaveAsCubeLocation(int[] cubeLoc, MovableWorldObject object) {
        if (cubeLoc.length != 3)
        	return false;
        if (! CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].isPassable())
        	return false;
        int zLoc = cubeLoc[2];
        if (zLoc == 0)
            return true;
        if (CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2] - 1].willSupport())
        	return true;
        if (! (object instanceof Unit))
        	return false;
        int xLoc = cubeLoc[0];
        int yLoc = cubeLoc[1];
        if (xLoc == 0 || xLoc == sideSize - 1)
        	return true;
        if (yLoc == 0 || yLoc == sideSize - 1)
        	return true;
        if (zLoc == sideSize - 1)
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
    void addFaction(Faction faction) throws IllegalArgumentException {
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
    void removeFaction(Faction faction) {
		FactionSet.remove(faction);
	}

	/**
	 * Return the number of factions in this world.
	 */
    int getNumberOfFactions() {
		return FactionSet.size();
	}
	
	/**
	 * Return the faction containing the smallest number of Units.
	 */
    Faction getSmallestFaction() {
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
        unit.register(unit.getLocation());
	}

	/**
	 * Remove the given unit from this world.
	 * 
	 * @param  unit
	 * 		   The unit to remove.
	 * @post   The given unit is removed from this world.
	 */
    void removeUnit(Unit unit) {
		TotalUnitSet.remove(unit);
	}	

	/**
	 * Return the number of Units of this world.
	 */
    int getNumberOfUnits() {
		return TotalUnitSet.size();
	}

	/**
	 * Check whether this world can add extra Units.
	 */
    public boolean canHaveExtraUnits(){
        return (TotalUnitSet.size() < 100);
    }

    /**Check whether the time duration is valid.
     * 
     * @param  dt
     * 		   The time duration to check.
     * @return True if and only if the time duration is greater or equal with zero
     * 		   and lower than 0.2.
     */
     private static boolean isValidTimeDuration(double dt) {
        return (!(dt<0) && dt < 0.2);
    }

    /**
     * Invoke the advanceTime methods of all game objects that are part of the World
     * with parameter dt.
     * 
     * @param  dt
     * 		   The time duration.
     */
    public void advanceTime(double dt){
        if(!isValidTimeDuration(dt)) throw new IllegalTimeException();
        caveIn();
        //World validity check
        assert this.TotalUnitSet.equals(this.getWorldMap().getAllUnits());
        Collection<MovableWorldObject> totColl=this.getWorldMap().values();
        for (MovableWorldObject object :
                totColl) {
            object.advanceTime(dt);
        }
    }

    public int getCubeAt(int[] loc) {
        CubeWorldObject cube;
        try {
        	cube = this.CubeWorld[loc[0]][loc[1]][loc[2]];
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("An illegal cubelocation was inspected getCubeAt");
        }
        if (cube instanceof Air)
        	return 0;
        if (cube instanceof Rock)
        	return 1;
        if (cube instanceof Wood)
        	return 2;
        if (cube instanceof Workshop)
        	return 3;
        throw new IllegalArgumentException();
    }

    /**
     * Let the cubes that need to cave in cave in.
     * 
     * @effect The cubes of the list containing the location of the cubes about to cave in are destroyed.
     */
    private void caveIn() {
        for (int[] loc: caveInlist) {
            destroyCube(loc);
        }
        caveInlist.clear();
    }

    /**
     * Set the geological feature of the cube at the location with given x, y and z coordinate to the given feature.
     * 
     * @param  x
     * 		   The x coordinate of the cube to set the geological feature of.
     * @param  y
     * 		   The y coordinate of the cube to set the geological feature of.
     * @param  z
     * 		   The z coordinate of the cube to set the geological feature of.
     * @param  value
     * 		   The number referring to the geological feature.
     */
    public void setCubeType(int x, int y, int z, int value) {
        CubeWorldObject cubeObject = new Air();
        switch (value) {
            case 3:	
                cubeObject = new Workshop();
                if (getCubeAt(new int[]{x, y, z}) == 1 || getCubeAt(new int[]{x, y, z}) == 2)
                    this.borderConnect.changeSolidToPassable(x, y, z);
                break;
            default:
            case 0:
                cubeObject = new Air();
                if (getCubeAt(new int[]{x, y, z}) == 1 || getCubeAt(new int[]{x, y, z}) == 2)
                    this.borderConnect.changeSolidToPassable(x, y, z);
                break;
            case 1:
                if (getCubeAt(new int[]{x, y, z}) == 0 || getCubeAt(new int[]{x, y, z}) == 3)
                    throw new IllegalArgumentException("Facade just tried to make a non-Solid cube Solid");
                cubeObject = new Rock();
                break;
            case 2:
                if (getCubeAt(new int[]{x, y, z}) == 0 || getCubeAt(new int[]{x, y, z}) == 3)
                    throw new IllegalArgumentException("Facade just tried to make a non-Solid cube Solid");
                cubeObject = new Rock();
                break;
        }
        changeListener.notifyTerrainChanged(x, y, z);
        CubeWorld[x][y][z] = cubeObject;
    }
    
}

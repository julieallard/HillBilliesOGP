package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.CubeObjects.*;
import hillbillies.model.exceptions.IllegalLocation;
import hillbillies.model.exceptions.IllegalTimeException;
import hillbillies.model.exceptions.SyntaxError;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.Util;

import java.util.*;

/**
 * A class of worlds involving a cube world and a listener of world changes.
 * 
 * @invar  The CubeWorld of each World must be a valid CubeWorld for any World. 
 *       | isValidCubeWorld(getCubeWorld())
 * 
 * @version	2.9.05 technical beta
 * @author  Arthur Decloedt - Bachelor in de Informatica
 * 			Julie Allard - Bachelor Handelsingenieur in de beleidsinformatica  
 * 			https://github.com/julieallard/HillBilliesOGP.git
 */
public class World {

    /**
     * Initialize this new world with given CubeWorld.
     *
     * @param  CubeWorld
     * 		   The cube world with an allocation of the geological features for this new World.
     * @effect The cube world of this new world is set to the given cube world.
     */
    public World(int[][][] CubeWorld, TerrainChangeListener changeListener) throws IllegalLocation, IllegalArgumentException {
        this.setWorldMap(new WorldMap<>());
        this.setxSideSize(CubeWorld[0].length);
        this.setySideSize(CubeWorld[1].length);
        this.setzSideSize(CubeWorld[2].length);
        this.borderConnect = new ConnectedToBorder(getxSideSize(), getySideSize(), getzSideSize());
        this.setCubeWorld(CubeWorld);
        this.changeListener = changeListener;
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
    private final ArrayList<int[]> caveInlist = new ArrayList<>();
    
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
 	 * @invar	The set of factions is effective.
 	 * @invar	Each element in the set of factions references a faction that is an acceptable faction for this world.
 	 * @invar	Each faction in the FactionSet references this world as the world to which it is attached.
	 */
	public Set<Faction> FactionSet = new HashSet<>();
	
	/**
	 * Set collecting references to units belonging to this world.
 	 * 
 	 * @invar	The set of units is effective.
 	 * @invar	Each element in the set of units references a unit that is an acceptable unit for this world.
 	 * @invar	Each unit in the UnitSet references this world as the world to which it is attached.
	 */
	public Set<Unit> TotalUnitSet = new HashSet<>();
	
	private int xSideSize;
    
	private int ySideSize;
    
	private int zSideSize;
    
	/**
	 * Constant reflecting the maximum amount of active factions in a world.
	 * 
	 * @return	The maximum amount of active factions of all worlds is 5.
	 *		  |	result == 5
	 */
    static final int MAX_FACTIONS = 5;
	
	/**
	 * Constant reflecting the maximum amount of units in a world.
	 * 
	 * @return	The maximum amount of units of all worlds is 100.
	 *		  |	result == 100
	 */
	 static final int MAX_UNITS = 100;
    
	/**
	 * Return the maximum amount of active factions in this world.
	 */
	 int getMaxNbFactions() {
		return World.MAX_FACTIONS;
	}
	
	/**
	 * Return the maximum amount of units in this world.
	 */
	 int getMaxNbUnits() {
		return World.MAX_UNITS;
	}
	
    /**
     * Return the cube world of this World.
     */
    @Basic
    @Raw
     CubeWorldObject[][][] getCubeWorld() {
        return this.CubeWorld;
    }

    /**
     * Check whether the given cube world is a valid cube world for any World.
     *
     * @param  CubeWorld
     * 		   The cube world to check.
     * @return True if and only if the cube world is cubical, in other words if all sub arrays have the same length.
     */
     boolean isValidCubeWorld(int[][][] CubeWorld) {
        for (int[][] YZLevel: CubeWorld) {
            if (YZLevel.length != getySideSize()) {
                return false;
            }
            for (int[] ZLine: YZLevel) {
                if (ZLine.length != getzSideSize()) {
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
     * @throws IllegalLocation
     * 		   The given cube world is not a valid cube world for any World.
     */
    @Raw
     void setCubeWorld(int[][][] CubeWorld) throws IllegalLocation, IllegalArgumentException {
        if (! isValidCubeWorld(CubeWorld))
            throw new IllegalLocation();
        CubeWorldObject[][][] CubeWorldFinal = new CubeWorldObject[getxSideSize()][getySideSize()][getzSideSize()];
        for (int x = 0; x < getxSideSize(); x++) {
            for (int y = 0; y < getySideSize(); y++) {
                for (int z = 0; z < getzSideSize(); z++) {
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
     * @throws IllegalLocation
     * 		   The given location is not a valid location.
     * @throws IllegalArgumentException
     * 		   The cube at given location is not destructible.
     */    
    public void destroyCube(int[] location) throws IllegalArgumentException, IllegalLocation {
        if (location.length != 3)
            throw new IllegalLocation();
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
     * @effect With a probability of 25%, if the cube was of wood, a Log is created in the middle of the cube
     * 		   and if the cube was of rock, a Boulder is created in the middle of the cube.
     */
    private void replace(CubeWorldObject cube, int[] location) {
        if (Math.random() <= 0.25) {
            if (cube.getClass() == Rock.class) {
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
     static boolean isValidWorldMap(WorldMap<VLocation, MovableWorldObject> WorldMap) {
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
     void setWorldMap(WorldMap<VLocation, MovableWorldObject> WorldMap) throws IllegalArgumentException {
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
     * 		   coordinate is at the border of the game world.	   
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
        if (xLoc == 0 || xLoc == getxSideSize() - 1)
        	return true;
        if (yLoc == 0 || yLoc == getySideSize() - 1)
        	return true;
        if (zLoc == getzSideSize() - 1)
        	return true;
        for (int x = xLoc-1; x < xLoc+2; x++) {
            for (int y = yLoc-1; y < yLoc+2; y++) {
                for (int z = zLoc-1; z < zLoc+2; z++) {
                    if (x < 0 || y < 0 || z < 0)
                        return true;
                    if (x == getxSideSize() || y == getySideSize() || z == getzSideSize())
                    	return true;
                    if (CubeWorld[x][y][z].willSupport())
                    	return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Check whether the given location contains a cube that will stop the fall of any object.
     * 
     * @param  cubeLoc
     *         The location of the cube the object is passing while falling.
     * @return True if and only if the terrain feature of the underlying cube at the given location will support any object.
     */
    public boolean willBreakFall(int[] cubeLoc) {
        return (CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2] - 1].willSupport());
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
    int getNbFactions() {
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
    int getNbUnits() {
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
        return (Util.fuzzyGreaterThanOrEqualTo(dt,0)&&Util.fuzzyLessThanOrEqualTo(dt,0.2));

    }

    /**
     * No documentation required.
     */
    public void advanceTime(double dt) throws SyntaxError {
        if(! isValidTimeDuration(dt)) throw new IllegalTimeException("thrown by advanceTime from World time duration was not valid");
        caveIn();
        //World validity check
        assert this.TotalUnitSet.equals(this.getWorldMap().getAllUnits());
        Collection<MovableWorldObject> totColl = this.getWorldMap().values();
        for (MovableWorldObject object: totColl) {
            object.advanceTime(dt);
        }
    }

    /**
     * Return the geological feature of the cube at the given location.
     * 
     * @param  loc
     * 		   The location of the cube to check.
     * @return The geological feature of the cube.
     */
    public int getCubeIDAt(int[] loc) {
        CubeWorldObject cube;
        try {
        	cube = this.CubeWorld[loc[0]][loc[1]][loc[2]];
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("An illegal cubelocation was inspected getCubeIDAt");
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

    public CubeWorldObject getCubeAt(int[] loc){
        CubeWorldObject cube;
        try {
            cube = this.CubeWorld[loc[0]][loc[1]][loc[2]];
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("An illegal cubelocation was inspected getCubeIDAt");
        }
        return cube;
    }

    /**
     * Let the cubes that need to cave in cave in.
     * 
     * @effect The cubes of the list containing the location of the cubes about to cave in are destroyed.
     */
    private void caveIn() {
        caveInlist.forEach(this::destroyCube);
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
        CubeWorldObject cubeObject;
        switch (value) {
            case 3:	
                cubeObject = new Workshop();
                if (getCubeIDAt(new int[]{x, y, z}) == 1 || getCubeIDAt(new int[]{x, y, z}) == 2)
                    this.borderConnect.changeSolidToPassable(x, y, z);
                break;
            default:
            case 0:
                cubeObject = new Air();
                if (getCubeIDAt(new int[]{x, y, z}) == 1 || getCubeIDAt(new int[]{x, y, z}) == 2)
                    this.borderConnect.changeSolidToPassable(x, y, z);
                break;
            case 1:
                if (getCubeIDAt(new int[]{x, y, z}) == 0 || getCubeIDAt(new int[]{x, y, z}) == 3)
                    throw new IllegalArgumentException("Facade just tried to make a non-Solid cube Solid");
                cubeObject = new Rock();
                break;
            case 2:
                if (getCubeIDAt(new int[]{x, y, z}) == 0 || getCubeIDAt(new int[]{x, y, z}) == 3)
                    throw new IllegalArgumentException("Facade just tried to make a non-Solid cube Solid");
                cubeObject = new Rock();
                break;
        }
        changeListener.notifyTerrainChanged(x, y, z);
        CubeWorld[x][y][z] = cubeObject;
    }

    /**
     * Variable registering the length of x side of this world.
     */
    public int getxSideSize() {
        return xSideSize;
    }

    void setxSideSize(int xSideSize) {
        this.xSideSize = xSideSize;
    }

    /**
     * Variable registering the length of y side of this world.
     */
    public int getySideSize() {
        return ySideSize;
    }

    void setySideSize(int ySideSize) {
        this.ySideSize = ySideSize;
    }

    /**
     * Variable registering the length of z side of this world.
     */
    public int getzSideSize() {
        return zSideSize;
    }

    void setzSideSize(int zSideSize) {
        this.zSideSize = zSideSize;
    }
}

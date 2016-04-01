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

public class World {


    /** TO BE ADDED TO CLASS HEADING
     * @invar The CubeWorld of each World must be a valid CubeWorld for any
     *         World.
     *       | isValidCubeWorld(getCubeWorld())
     */


    /**
     * Initialize this new World with given CubeWorld.
     *
     * @param CubeWorld The CubeWorld for this new World.
     * @effect The CubeWorld of this new World is set to
     * the given CubeWorld.
     * | this.setCubeWorld(CubeWorld)
     * Initialize this new World with given movableWorldObjectsHashmap.
     */

    public World(int[][][] CubeWorld)
            throws UnitIllegalLocation, IllegalArgumentException {
        this.setCubeWorld(CubeWorld);
        this.WorldMap=new WorldMap<>();
    }

    /**
     * Return the CubeWorld of this World.
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
     * @param CubeWorld The CubeWorld to check.
     * @return | result ==
     */

    // TODO: 22/03/16 isvalidinitcubeworld
    public boolean isValidCubeWorld(int[][][] CubeWorld) {
        //world must be cubical so its Array must not be jagged(all sub arrays must have the same size)
        int length = CubeWorld.length;
        this.sideSize=length;
        for (int[][] yzVlak :
                CubeWorld) {
            if (yzVlak.length != length) {
                return false;
            }
            for (int[] zLijn :
                    yzVlak) {
                if (zLijn.length != length) {
                    return false;
                }
            }
        }
        return true;
    }
    private int sideSize;

    /**
     * Set the CubeWorld of this World to the given CubeWorld.
     *
     * @param CubeWorld The new CubeWorld for this World.
     * @throws UnitIllegalLocation The given CubeWorld is not a valid CubeWorld for any
     *                             World.
     *                             | ! isValidCubeWorld(getCubeWorld())
     * @post The CubeWorld of this new World is equal to
     * the given CubeWorld.
     * | new.getCubeWorld() == CubeWorld
     */
    @Raw
    public void setCubeWorld(int[][][] CubeWorld)
            throws UnitIllegalLocation, IllegalArgumentException {
        if (!isValidCubeWorld(CubeWorld)) {
            throw new UnitIllegalLocation();
        }

        int worldSide = CubeWorld.length;
        CubeWorldObject[][][] CubeWorldFinal = new CubeWorldObject[worldSide][worldSide][worldSide];

        for (int x = 0; x < worldSide; x++) {
            for (int y = 0; y < worldSide; y++) {
                for (int z = 0; z < worldSide; z++) {
                    switch (CubeWorld[x][y][z]) {
                        case 0:
                            CubeWorldFinal[x][y][z] = new Air();
                            break;
                        case 1:
                            CubeWorldFinal[x][y][z] = new Rock();
                            break;
                        case 2:
                            CubeWorldFinal[x][y][z] = new Wood();
                            break;
                        case 3:
                            CubeWorldFinal[x][y][z] = new Workshop();
                            break;
                        default:
                            throw new IllegalArgumentException("illegal terrain feature supplied");
                    }
                }
            }
        }
    }

    /**
     * Variable registering the CubeWorld of this World.
     */
    private CubeWorldObject[][][] CubeWorld;


    public void destroyCube(int[] location) throws IllegalArgumentException, UnitIllegalLocation {
        if (location.length != 3) {
            throw new UnitIllegalLocation();
        }
        CubeWorldObject[][][] world = this.getCubeWorld();
        CubeWorldObject object = world[location[0]][location[1]][location[2]];
        if (object.isDestructible()) {
            this.CubeWorld[location[0]][location[1]][location[2]] = new Air();
            replace(object, location);

        } else {
            throw new IllegalArgumentException("tried to destroy an non destructible cube");
        }

    }

    private void replace(CubeWorldObject cube, int[] location) {
        if (Math.random() < 0.75) {
            Rock rock = new Rock();
            if (cube.getClass() == rock.getClass()) {
                new Boulder(location[0],location[1],location[2],this);

            } else {
                new Log(location[0],location[1],location[2],this);
            }
        }
    }


    /** TO BE ADDED TO CLASS HEADING
     * @invar  The Worldmap of each World must be a valid Worldmap for any
     *         World.
     *       | isValidWorldMap(getWorldMap())
     */


    /**
     * Initialize this new World with given Worldmap.
     *
     * @param  WorldMap
     *         The Worldmap for this new World.
     * @effect The Worldmap of this new World is set to
     *         the given Worldmap.
     *       | this.setWorldMap(WorldMap)
     */


    /**
     * Return the Worldmap of this World.
     */
    @Basic @Raw
    public WorldMap getWorldMap() {
      return this.WorldMap;
    }


    /**
     * Variable registering the Worldmap of this World.
     */
    private final WorldMap<VLocation,MovableWorldObject> WorldMap;

    public List<Unit> getUnitsAt(int[] cubeLocation){
        return this.WorldMap.getAllUnitsInCube(cubeLocation);
    }
    public List<Log> getLogsAt(int[] cubeLocation){
        return this.WorldMap.getAllLogsInCube(cubeLocation);
    }
    public List<Boulder> getBouldersAt(int[] cubeLocation){
        return this.WorldMap.getAllBouldersInCube(cubeLocation);
    }

    public boolean canHaveAsCubeLocation(int[] cubeLoc,MovableWorldObject object){
        if(!CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].isPassable()) return false;
        if (CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].willSupport()) return true;
        if (!(object instanceof Unit)) return false;

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    if(x==0||y==0||z==0) continue;
                    if (CubeWorld[x][y][z].willSupport()) return true;
                }
            }
        }
        return false;
    }
    public boolean willBreakFall(int[] cubeLoc){
        return (CubeWorld[cubeLoc[0]][cubeLoc[1]][cubeLoc[2]].willSupport());

    }

	/**
	 * Set collecting references to Factions belonging to this world.
	 * 
	 * @invar The set of Factions is effective.
	 * 		| FactionSet != null
	 * @invar Each Faction in the FactionSet references this world as the world to
	 * 		  which it is attached.
	 * 		| for each Faction in FactionSet:
	 * 		|	(FactionSet.getWorld() == this) || 
	 */
	private  Set<Faction> FactionSet;
	
	public void addFaction(Faction faction) throws IllegalArgumentException {
		if (!faction.canHaveAsWorld()) {
			throw new IllegalArgumentException("World already contains its max no of factions");}
        else {
            this.FactionSet.add(faction);
		}
	}
	
	public  void removeFaction(Faction faction) {
		FactionSet.remove(faction);
	}
	
	public  int getNumberOfFactions() {
		return FactionSet.size();
	}
	
	public  Faction getSmallestFaction() {
		Faction smallestFaction=(Faction) FactionSet.toArray()[0];
		for (Faction faction: FactionSet) {
			if (faction.getUnitSet().size() < smallestFaction.getUnitSet().size()) {
				smallestFaction = faction;
			}}
		return smallestFaction;
		}

	
}

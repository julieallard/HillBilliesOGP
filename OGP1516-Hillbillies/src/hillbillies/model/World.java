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
     *
     * @param  MovableWorldObjectHashmap
     *         The movableWorldObjectsHashmap for this new World.
     * @effect The movableWorldObjectsHashmap of this new World is set to
     *         the given movableWorldObjectsHashmap.
     *       | this.setMovableWorldObjectHashmap(MovableWorldObjectHashmap)
     */
    public World(int[][][] CubeWorld)
            throws UnitIllegalLocation, IllegalArgumentException {
        this.setCubeWorld(CubeWorld);
        HashMap<VLocation,MovableWorldObject> newWorld=new HashMap<VLocation,MovableWorldObject>();
        this.MovableWorldObjectHashmap=newWorld;

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
    public static boolean isValidCubeWorld(int[][][] CubeWorld) {
        //world must be cubical so its Array must not be jagged(all sub arrays must have the same size)
        int lenght = CubeWorld.length;
        for (int[][] yzVlak :
                CubeWorld) {
            if (yzVlak.length != lenght) {
                return false;
            }
            for (int[] zLijn :
                    yzVlak) {
                if (zLijn.length != lenght) {
                    return false;
                }
            }

        }
        return true;
    }

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
                createNewBoulder(location);

            } else {
                createNewLog(location);
            }
        }
    }

    /**
     * Return the movableWorldObjectsHashmap of this World.
     */
    @Basic @Raw
    public HashMap<VLocation,MovableWorldObject> getMovableWorldObjectHashmap() {
      return this.MovableWorldObjectHashmap;
    }

    private HashMap<VLocation,MovableWorldObject> MovableWorldObjectHashmap;

    public void addNewMovableWorldObject(MovableWorldObject object,VLocation location){
        this.MovableWorldObjectHashmap.put(location,object);
    }

    public void changeMovableObjectLocation(MovableWorldObject object, VLocation newlocation){
        VLocation oldLocation=object.getLocation();
        this.MovableWorldObjectHashmap.remove(oldLocation,object);
        this.addNewMovableWorldObject(object,newlocation);
    }




}

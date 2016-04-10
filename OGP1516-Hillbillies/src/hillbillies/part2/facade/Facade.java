package hillbillies.part2.facade;


import hillbillies.model.*;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;
import java.util.Set;


public class Facade extends hillbillies.part1.facade.Facade implements hillbillies.part2.facade.IFacade {

    /* WORLD */

    /**
     * Create a new world of the given size and with the given terrain. To keep
     * the GUI display up to date, the method in the given listener must be
     * called whenever the terrain type of a cube in the world changes.
     *
     * @param terrainTypes
     *            A three-dimensional array (structured as [x][y][z]) with the
     *            types of the terrain, encoded as integers. The terrain always
     *            has the shape of a box (i.e., the array terrainTypes[0] has
     *            the same length as terrainTypes[1] etc.). The integer types
     *            are as follows:
     *            <ul>
     *            <li>0: air</li>
     *            <li>1: rock</li>
     *            <li>2: tree</li>
     *            <li>3: workshop</li>
     *            </ul>
     * @param modelListener
     *            An object with a single method,
     *            {@link TerrainChangeListener#notifyTerrainChanged(int, int, int)}
     *            . This method must be called by your implementation whenever
     *            the terrain type of a cube changes (e.g., as a consequence of
     *            cave-ins), so that the GUI will correctly update the display.
     *            The coordinate of the changed cube must be given in the form
     *            of the parameters x, y and z. You do not need to call this
     *            method during the construction of your world.
     * @return
     * @throws ModelException
     */
    @Override
    public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException{
        try {
            return new World(terrainTypes,modelListener);
        }
        catch (Exception error) {throw new ModelException(error);}
    }

    /**
     * Return the number of cubes in the world in the x-direction.
     *
     * @param world
     *            The world for which to retrieve the number of cubes.
     * @return The number of cubes in the x-direction.
     * @throws ModelException
     */

    @Override
    public int getNbCubesX(World world) throws ModelException{

        try{ return world.sideSize;
        }
        catch (Exception error) {throw new ModelException(error);}

    }

    /**
     * Return the number of cubes in the world in the y-direction.
     *
     * @param world
     *            The world for which to retrieve the number of cubes.
     * @return The number of cubes in the y-direction.
     * @throws ModelException
     */
    @Override
    public int getNbCubesY(World world) throws ModelException{

        try{ return world.sideSize;
        }
        catch (Exception error) {throw new ModelException(error);}


    }

    /**
     * Return the number of cubes in the world in the z-direction.
     *
     * @param world
     *            The world for which to retrieve the number of cubes.
     * @return The number of cubes in the z-direction.
     * @throws ModelException
     */
    @Override
    public int getNbCubesZ(World world) throws ModelException{

        try{ return world.sideSize;
        }
        catch (Exception error) {throw new ModelException(error);}

    }

    /**
     * Advance the state of the given world by the given time period.
     *
     * @param world
     *            The world for which to advance the time
     * @param dt
     *            The time period, in seconds, by which to advance the world's
     *            state.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     *
     * @note This method replaces the {@link #advanceTime(Unit, double)} method
     *       from part 1.
     */
    @Override
    public void advanceTime(World world, double dt) throws ModelException{
        try{world.advanceTime(dt);}
        catch (Exception error) {throw new ModelException(error);}
    }

    /**
     * Return the terrain type of the cube at the given coordinates.
     *
     * @param world
     *            The world from which to retrieve the type.
     * @param x
     *            The x-coordinate of the cube
     * @param y
     *            The y-coordinate of the cube
     * @param z
     *            The z-coordinate of the cube
     * @return The terrain type of the given cube, encoded as an integer
     *         according to the values in
     *         {@link #createWorld(int[][][], TerrainChangeListener)}.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public int getCubeType(World world, int x, int y, int z) throws ModelException{

        try{return world.getCubeAt(new int[]{x,y,z});}
        catch (Exception error) {throw new ModelException(error);}
    }

    /**
     * Set the terrain type of the cube at the given coordinates the given
     * value.
     *
     * @param world
     *            The world in which to set the type.
     * @param x
     *            The x-coordinate of the cube
     * @param y
     *            The y-coordinate of the cube
     * @param z
     *            The z-coordinate of the cube
     * @param value
     *            The new value of the terrain type of the cube, encoded as an
     *            integer according to the values in
     *            {@link #createWorld(int[][][], TerrainChangeListener)}.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */

    @Override
    public void setCubeType(World world, int x, int y, int z, int value) throws ModelException{

    }

    /**
     * Return whether the cube at the given coordinates is solid and connected
     * to the border of the world.
     *
     * @param world
     *            The world to which the cube belongs
     * @param x
     *            The x-coordinate of the cube
     * @param y
     *            The y-coordinate of the cube
     * @param z
     *            The z-coordinate of the cube
     * @return true if the given cube is solid and connected to the border of
     *         the world; false otherwise.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     *
     */
    @Override
    public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException{

        try{
            return world.borderConnect.isSolidConnectedToBorder(x,y,z);
            }
        catch (Exception error) {throw new ModelException(error);}

    }

	/* UNITS */

    /**
     * Spawn a new unit in the world, according to the rules in the assignment
     * (section 1.1.2).
     *
     * @param world
     *            The world in which to spawn a new unit
     *
     * @param enableDefaultBehavior
     *            Whether the unit should act according to the default behaviour
     *            or not.
     *
     * @return The newly spawned unit.
     *
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException{

        try{
            return new Unit(world,enableDefaultBehavior);
            }
        catch (Exception error) {throw new ModelException(error);}


    }

    /**
     * Adds the given unit to the given world.
     *
     * @param unit
     * @param world
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public void addUnit(Unit unit, World world) throws ModelException{

    try{
        world.addUnit(unit);
    
        }
    catch (Exception error) {throw new ModelException(error);}
    
    }
    

    /**
     * Return all units that are currently part of the world.
     *
     * @param world
     *            The world from which to retrieve units
     * @return A set containing all units from the world.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Unit> getUnits(World world) throws ModelException{

        try{
            return world.getWorldMap().getAllUnits();
    
        }
        catch (Exception error) {throw new ModelException(error);}
    
    }

    /**
     * Return whether the given unit is currently carrying a log.
     *
     * @param unit
     *            The unit
     * @return true if the unit is currently carrying a log; false otherwise
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isCarryingLog(Unit unit) throws ModelException{

        try{
            return (unit.isCarrying()&&unit.getCarriedObject() instanceof Log);
        }
        catch (Exception error) {throw new ModelException(error);}
    
    }

    /**
     * Return whether the given unit is currently carrying a boulder.
     *
     * @param unit
     *            The unit
     * @return true if the unit is currently carrying a boulder; false otherwise
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isCarryingBoulder(Unit unit) throws ModelException{
        try{
            return (unit.isCarrying()&&unit.getCarriedObject() instanceof Boulder);
        }
        catch (Exception error) {throw new ModelException(error);}

    }

    /**
     * Return whether the given unit is currently alive.
     *
     * @param unit
     *            The unit
     * @return true if the unit is currently alive; false if it's dead
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isAlive(Unit unit) throws ModelException{

        try{
            return unit.getWorld().TotalUnitSet.contains(unit);
            }
        catch (Exception error) {throw new ModelException(error);}


    }

    /**
     * Return the current number of experience points of the given unit.
     *
     * @param unit
     * @return The number of experience points.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public int getExperiencePoints(Unit unit) throws ModelException{
        
        try{
            return unit.getXP();
            }
        catch (Exception error) {throw new ModelException(error);}
        
        
    }

    /**
     * Make the given unit start working on the given target cube.
     *
     * @param unit
     *            The unit that should start working.
     * @param x
     *            The x-coordinate of the target cube
     * @param y
     *            The y-coordinate of the target cube
     * @param z
     *            The z-coordinate of the target cube
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     *
     * @note This method replaces the {@link #work(Unit)} method from part 1.
     */
    @Override
    public void workAt(Unit unit, int x, int y, int z) throws ModelException{
        
        try{
            unit.work(new int[]{x,y,z});
            }
        catch (Exception error) {throw new ModelException(error);}
        
    }

    /**
     * This method is no longer necessary, and is replaced by the
     * {@link #workAt(Unit, int,int,int)} method.
     */
    @Override
    @Deprecated
    public void work(Unit unit) throws ModelException {
        throw new NoSuchMethodError("This method no longer needs to be supported");
    }

    /**
     * This method is no longer necessary, and is replaced by the
     * {@link #advanceTime(World, double)} method.
     */
    @Override
    @Deprecated
    public void advanceTime(Unit unit, double dt) throws ModelException {
        throw new NoSuchMethodError("This method no longer needs to be supported");
    }

	/* FACTIONS */
    /**
     * Return the current faction of the given unit.
     *
     * @param unit
     *            The unit of which to retrieve the faction.
     * @return The current faction of the unit.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Faction getFaction(Unit unit) throws ModelException{
        
        try{
            return unit.getFaction();
            }
        catch (Exception error) {throw new ModelException(error);}
        
        
    }

    /**
     * Return the units that are part of the given faction.
     *
     * @param faction
     *            The faction of which to retrieve the members.
     * @return The set of units that belong to the faction.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException{

    try{
           return faction.getUnitSet();
        }
    catch (Exception error) {throw new ModelException(error);}
    
        }

    /**
     * Return all the active factions of the given world.
     *
     * @param
     * @return A set of all active (i.e., non-empty) factions in the world.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Faction> getActiveFactions(World world) throws ModelException{
        
        try{
            return world.FactionSet;
            }
        catch (Exception error) {throw new ModelException(error);}
    }

	/* BOULDERS */

    /**
     * Get the precise coordinates of the given boulder.
     *
     * @param boulder
     *            The boulder for which to return the position.
     * @return The coordinate of the center of the boulder, as an array with 3
     *         doubles {x, y, z}.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public double[] getPosition(Boulder boulder) throws ModelException{
        
        try{
            return boulder.getLocation().getArray();
            }
        catch (Exception error) {throw new ModelException(error);}
        
    }

    /**
     * Return all boulders that are part of the given world.
     *
     * @param world
     *            The world from which to retrieve the boulders.
     * @return A set containing all boulders present in the given world (i.e.,
     *         not picked up, consumed, destroyed, ...).
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Boulder> getBoulders(World world) throws ModelException{
        
        try{
            return world.getWorldMap().getAllBoulders();
            }
        catch (Exception error) {throw new ModelException(error);}
    }

	/* LOGS */

    /**
     * Get the precise coordinate of the given log.
     *
     * @param log
     *            The log for which to return the position.
     * @return The coordinate of the center of the log, as an array with 3
     *         doubles {x, y, z}.
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public double[] getPosition(Log log) throws ModelException{
        try{
            return log.getLocation().getArray();
        }
        catch (Exception error) {throw new ModelException(error);}



    }

    /**
     * Return all logs that are part of the given world.
     *
     * @param world
     *            The world from which to retrieve the logs.
     * @return A set containing all logs present in the given world (i.e., not
     *         picked up, consumed, destroyed, ...).
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Set<Log> getLogs(World world) throws ModelException{

        try{
            return world.getWorldMap().getAllLogs();
        }
        catch (Exception error) {throw new ModelException(error);}

    }

}

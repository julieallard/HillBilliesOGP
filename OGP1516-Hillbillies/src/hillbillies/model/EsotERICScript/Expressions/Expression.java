package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.CubeObjects.Workshop;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.exceptions.SyntaxError;

import java.util.*;

public abstract class Expression {

    public abstract Object value(Unit executor) throws SyntaxError;

    public Task task;

    public Unit executor;

    private boolean isValidCube(int[] loc, int requestType) throws IllegalArgumentException {
    	switch (requestType) {
    		case 0: 	return ! this.task.world.getLogsAt(loc).isEmpty();
    		case 1: 	return ! this.task.world.getBouldersAt(loc).isEmpty();
    		case 2: 	return this.task.world.getCubeAt(loc) instanceof Workshop;
    		case 3:		return ! this.task.world.getUnitsAt(loc).isEmpty();
    		case 4:		boolean friendPresent = false;
    					List<Unit> unitList1 = this.task.world.getUnitsAt(loc);
    					for (Unit unit: unitList1)
    						if (executor.getFaction() == unit.getFaction())
    							friendPresent = true;
    					return friendPresent;
    		case 5:		boolean enemyPresent = false;
						List<Unit> unitList2 = this.task.world.getUnitsAt(loc);
						for (Unit unit: unitList2)
							if (executor.getFaction() != unit.getFaction())
								enemyPresent = true;
						return enemyPresent;    			
    		default: 	throw new IllegalArgumentException("Illegal requested type");
    	}
    }
    
    //TODO check if deftig
    protected int[] scanWorld(int[] initialLoc, String target) throws SyntaxError {
        int requestType;
        switch (target) {
            case "Log": 		requestType = 0;
                				break;
            case "Boulder": 	requestType = 1;
                				break;
            case "Workshop": 	requestType = 2;
                				break;
            case "Any":         requestType = 3;
                                break;
            case "Friend":      requestType = 4;
                                break;
            case "Enemy":     	requestType = 5;
                                break;
            default:			throw new SyntaxError("Illegal scan request");
        }
        int[] curLoc = initialLoc.clone();
        Queue<int[]> toBeInspected = new LinkedList<>();
        Set<int[]> inspected = new HashSet<>();
        inspected.add(curLoc);
        while (true) {
            toBeInspected.addAll(getAllNeighbours(curLoc, inspected));
            curLoc = toBeInspected.poll();
            if (curLoc == null)
            	throw new SyntaxError("No " + target + " object was found.");
            inspected.add(curLoc);
            if (isValidCube(curLoc, requestType))
            	break;
        }
        return curLoc;
    }

    protected boolean withinConfines(World world, int[] loc) {
        return (loc [0] < world.getxSideSize() && loc[1] < world.getySideSize() && loc[2] < world.getzSideSize() && loc[0] > 0 && loc[1] > 0 && loc[2] > 0);
    }

    protected Collection<int[]> getAllNeighbours(int[] centralCube, Set<int[]> inspected) {
        World world = this.task.world;
        Collection<int[]> neighbourSet = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            for (int j = -1; j < 2 ; j++) {
                if (j == 0)
                	continue;
                int[] surroundingCube = centralCube.clone();
                surroundingCube[i] += j;
                if (this.withinConfines(world, surroundingCube) && ! inspected.contains(surroundingCube))
                    neighbourSet.add(surroundingCube);
            }
        }
        return neighbourSet;
    }
}

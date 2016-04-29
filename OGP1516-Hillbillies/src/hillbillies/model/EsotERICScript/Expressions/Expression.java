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

    private boolean isValidCube(int[] loc, int request) {
    	switch (request) {
    		case 0: 	return ! this.task.world.getLogsAt(loc).isEmpty();
    		case 1: 	return ! this.task.world.getBouldersAt(loc).isEmpty();
    		case 2: 	return this.task.world.getCubeAt(loc) instanceof Workshop;
    		default: 	throw new IllegalArgumentException("illegalen maken dit land kapot ajlkfjeihgirhkahhh");
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
            case "Enemy":     requestType = 5;
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
            	throw new SyntaxError("No valid location was found");
            inspected.add(curLoc);
            if (isValidCube(curLoc, requestType))
            	break;
        }
        return curLoc;
    }

    protected boolean withinConfines(World world, int[] loc) {
        return (loc [0] < world.sideSize && loc[1] < world.sideSize && loc[2] < world.sideSize && loc[0] > 0 && loc[1] > 0 && loc[2] > 0);
    }

    protected Collection<int[]> getAllNeighbours(int[] cubeLoc, Set<int[]> inspected) {
        World world = this.task.world;
        Collection<int[]> collLoc = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            for (int j = -1; j <1 ; j++) {
                if (j == 0)
                	continue;
                int[] curloc = cubeLoc.clone();
                curloc[i] += j;
                if (this.withinConfines(world, curloc) && !inspected.contains(curloc))
                    collLoc.add(curloc);
            }
        }
        return collLoc;
    }
}

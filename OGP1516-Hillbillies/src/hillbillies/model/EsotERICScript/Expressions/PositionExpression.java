package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.EsotERICScript.Expression;
import hillbillies.model.World;
import hillbillies.model.exceptions.SyntaxError;

import java.util.*;

public class PositionExpression extends Expression {

    @Override
    public int[] value() throws SyntaxError {
        return this.innerExpression.getValue();
    }

    public PosPartExpression innerExpression;

    public abstract class PosPartExpression extends PartExpression {

        @Override
        public abstract int[] getValue() throws SyntaxError;

        Expression arg1;

    }

    // (x, y, z)
    public class PosConstantPartExpression extends PosPartExpression {

        public PosConstantPartExpression(int[] position) throws SyntaxError {
            if (position.length != 3 && ! withinConfines(task.world,position))
            	throw new SyntaxError("Illegal position supplied");
            this.value = position;
        }
        
        private int[] value;
        
        @Override
        public int[] getValue() throws SyntaxError {
            return value;
        }
    }

    // here
    public class PosLocationOfPartExpression extends PosPartExpression{

        public PosLocationOfPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }

        UnitExpression arg1;

        @Override
        public int[] getValue() throws SyntaxError {
            return arg1.value().getLocation().getCubeLocation();
        }

    }

    // log
    public class PosLogPartExpression extends PosPartExpression{

        public PosLogPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }

        public PosLogPartExpression() {
        }

        @Override
        public int[] getValue() throws SyntaxError {
            return null;
        }

    }
    
    // boulder
    public class PosBoulderPartExpression extends PosPartExpression{

        public PosBoulderPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }

        public PosBoulderPartExpression() {
        }

        @Override
        public int[] getValue() throws SyntaxError {
            return null;
        }

    }

    private static boolean isvalidCube(int[] loc, int request) {
        //TODO: world is valid cube
        return true;
    }

    private int[] scanWorld(int[] initialLoc, String target) throws SyntaxError {
        int requestType;
        switch (target) {
            case "Log": 		requestType = 0;
                				break;
            case "Boulder": 	requestType = 1;
                				break;
            case "Workshop": 	requestType = 2;
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
            if (isvalidCube(curLoc, requestType))
            	break;
        }
        return curLoc;
    }
    
    private boolean withinConfines(World world, int[] loc) {
        return (loc [0] < world.sideSize && loc[1] < world.sideSize && loc[2] < world.sideSize && loc[0] > 0 && loc[1] > 0 && loc[2] > 0);
    }

    private Collection<int[]> getAllNeighbours(int[] cubeLoc, Set<int[]> inspected) {
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
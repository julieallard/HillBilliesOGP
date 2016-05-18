package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class PositionExpression extends Expression {

    @Override
    public int[] value(Unit executor) throws SyntaxError {
        this.executor = executor;
        return this.innerExpression.getValue();
    }

    public PosPartExpression innerExpression;

    public abstract class PosPartExpression extends PartExpression {

        @Override
        public abstract int[] getValue() throws SyntaxError;

    }

    // here
    public class PosHerePartExpression extends PosPartExpression {
    	
        public PosHerePartExpression() {
        }
        
        @Override
        public int[] getValue() throws SyntaxError {
            return executor.getLocation().getCubeLocation();
        }
        
    }
    
    // log
    public class PosLogPartExpression extends PosPartExpression {
    	
        public PosLogPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }
        
        private UnitExpression arg1;
        
        @Override
        public int[] getValue() throws SyntaxError {
            return scanWorld(this.arg1.value(executor).getLocation().getCubeLocation(), "Log");
        }
    
    }
    
    // boulder
    public class PosBoulderPartExpression extends PosPartExpression {

        public PosBoulderPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }

        private UnitExpression arg1;
        
        @Override
        public int[] getValue() throws SyntaxError {
            return scanWorld(this.arg1.value(executor).getLocation().getCubeLocation(), "Boulder");
        }

    }

    // workshop
    public class PosWorkshopPartExpression extends PosPartExpression {

        public PosWorkshopPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }

        private UnitExpression arg1;
        
        @Override
        public int[] getValue() throws SyntaxError {
            return scanWorld(this.arg1.value(executor).getLocation().getCubeLocation(), "Workshop");
        }

    }
    
    // (x, y, z)
    public class PosConstantPartExpression extends PosPartExpression {

        public PosConstantPartExpression(int[] position) throws SyntaxError {
            if (position.length != 3 && ! withinConfines(task.world, position))
            	throw new SyntaxError("Illegal position supplied");
            this.value = position;
        }
        
        private int[] value;
        
        @Override
        public int[] getValue() throws SyntaxError {
            return value;
        }
    }

    // next_to e
    public class NextToPosPartExpression extends PosPartExpression {

        public NextToPosPartExpression(PositionExpression position) {
            this.arg1 = position;
        }
        
        private PositionExpression arg1;

        @Override
        public int[] getValue() throws SyntaxError {
            Set<int[]> emptySet = (Set<int[]>) Collections.EMPTY_SET; //EMPTY SET moet weg???
            Collection<int[]> neighbourSet = getAllNeighbours(arg1.value(executor), emptySet);
            for (int[] loc: neighbourSet)
                if (executor.getWorld().canHaveAsCubeLocation(loc, executor))
                	return loc;           
            throw new SyntaxError("no legal location was found");
        }
    }
    
    // position_of e
    public class PosLocationOfPartExpression extends PosPartExpression {

        public PosLocationOfPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }
        
        UnitExpression arg1;
        
        @Override
        public int[] getValue() throws SyntaxError {
            return arg1.value(executor).getLocation().getCubeLocation();
        }

    }


    public class PositionReadPartExpression extends PosHerePartExpression {

        public void setKeynMap(String key, Map<String, int[]> map) {
            this.key = key;
            this.usedMap = map;
        }

        private String key;

        private Map<String, int[]> usedMap;

        @Override
        public int[] getValue() throws SyntaxError {
            return this.usedMap.get(key);
        }
    }
    
    // TODO: 29/04/16 selected
//    public class PosSelectedPartExpression extends PosPartExpression {
//
//        public PosSelectedPartExpression(PositionExpression position) {
//            this.arg1 = position;
//        }
//        
//        PositionExpression arg1;
//        
//        @Override
//        public int[] getValue() throws SyntaxError {
//            return arg1.value(executor);
//        }
//
//    }
    
}
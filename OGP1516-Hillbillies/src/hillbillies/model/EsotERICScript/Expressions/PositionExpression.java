package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PositionExpression extends Expression {

    @Override
    public int[] value(Unit executor) throws SyntaxError {
        this.executor = executor;
        return this.getInnerExpression().getValue();
    }

    private PosPartExpression innerExpression;

    public PosPartExpression getInnerExpression() {
        return innerExpression;
    }

    public void setInnerExpression(PosPartExpression innerExpression) {
        this.innerExpression = innerExpression;
    }

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
            Set<int[]> emptySet = new HashSet<>(); //EMPTY SET moet weg???
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

    public class PosReadPartExpression extends PosPartExpression {

        public PosReadPartExpression(String key) {
            this.key = key;
        }

        private String key;

        @Override
        public int[] getValue() throws SyntaxError {
            return task.positionGlobalMap.get(key);
        }
    }

    public class PosSelectedPartExpression extends PosPartExpression {

        public PosSelectedPartExpression() {
            this.arg = task.getSelected();
        }
        int[] arg;
        @Override
        public int[] getValue() throws SyntaxError {
            return arg;
        }
    }


    public static Expression newHereExpression(){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosHerePartExpression());
        return expression;
    }

    public static Expression newPosLogExpression(UnitExpression unit){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosLogPartExpression(unit));
        return expression;
    }

    public static Expression newPosBoulderExpression(UnitExpression unit){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosBoulderPartExpression(unit));
        return expression;
    }

    public static Expression newPosWorkshopExpression(UnitExpression unit){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosWorkshopPartExpression(unit));
        return expression;
    }

    public static Expression newPosConstantExpression(int[] location){
        PositionExpression expression=new PositionExpression();
        try {
            expression.setInnerExpression(expression.new PosConstantPartExpression(location));
        } catch (SyntaxError syntaxError) {
            throw new IllegalArgumentException(syntaxError);
        }
        return expression;
    }

    public static Expression newNextToExpression(PositionExpression pos){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new NextToPosPartExpression(pos));
        return expression;
    }

    public static Expression newPosOfExpression(UnitExpression unit){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosLocationOfPartExpression(unit));
        return expression;
    }

    public static Expression newPosReadExpression(String key){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosReadPartExpression(key));
        return expression;
    }

    public static Expression newPosSelectedExpression(){
        PositionExpression expression=new PositionExpression();
        expression.setInnerExpression(expression.new PosSelectedPartExpression());
        return expression;
    }
}

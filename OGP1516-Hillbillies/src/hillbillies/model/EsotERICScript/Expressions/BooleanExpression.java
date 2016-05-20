package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class BooleanExpression extends Expression {
	
    @Override
    public Boolean value(Unit executor) throws SyntaxError {
        this.executor = executor;
        return this.getInnerExpression().getValue();
    }
    
    private BooleanPartExpression innerExpression;

    public BooleanPartExpression getInnerExpression() {
        return innerExpression;
    }

    public void setInnerExpression(BooleanPartExpression innerExpression) {
        this.innerExpression = innerExpression;
    }

    public abstract class BooleanPartExpression extends PartExpression {
    	
    	@Override
        public abstract Boolean getValue() throws SyntaxError;
        
    }

    // true
    // false
    public class BooleanConstantPartExpression extends BooleanPartExpression {
    	
        public BooleanConstantPartExpression(boolean flag) {
            this.value = flag;
        }
        
        private boolean value;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return value;
        }
    
    }

    // ! e
    public class BooleanNotPartExpression extends BooleanPartExpression {
    	
        public BooleanNotPartExpression(BooleanExpression arg) {
            this.arg1 = arg;
        }
        
        private BooleanExpression arg1;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return ! arg1.value(executor);
        }
        
    }

    // e || e
    public class BooleanOrPartExpression extends BooleanPartExpression {
    	
        public BooleanOrPartExpression(BooleanExpression arg1, BooleanExpression arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        private BooleanExpression arg1;
        private BooleanExpression arg2;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return ( arg1.value(executor)) || ((boolean) arg2.value(executor));
        }
    }

    // e && e
    public class BooleanAndPartExpression extends BooleanPartExpression {
    	
        public BooleanAndPartExpression(BooleanExpression arg1, BooleanExpression arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        private BooleanExpression arg1;
        private BooleanExpression arg2;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return ( arg1.value(executor)) && (arg2.value(executor));
        }
        
    }
    
    // is_passable e
    // => is_solid will be implemented as NOT(is_passable)
    public class BooleanIsPassablePartExpression extends BooleanPartExpression {
    	
        public BooleanIsPassablePartExpression(PositionExpression arg) {
            this.arg1 = arg;
        }
        
        private PositionExpression arg1;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return task.world.getCubeAt(arg1.value(executor)).isPassable();
        }
        
    }
    
    // is_friend e
    // => is_enemy will be implemented as NOT(is_friend)
    public class BooleanIsFriendPartExpression extends BooleanPartExpression {
    	
        public BooleanIsFriendPartExpression(UnitExpression arg1, UnitExpression arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }
        
        private UnitExpression arg1;
        private UnitExpression arg2;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return arg1.value(executor).getFaction().equals(arg2.value(executor).getFaction());
        }
        
    }
    
    // is_alive e
    public class BooleanIsAlivePartExpression extends BooleanPartExpression {

        public BooleanIsAlivePartExpression(UnitExpression arg) {
            this.arg1 = arg;
        }
        
        private UnitExpression arg1;

        @Override
        public Boolean getValue() throws SyntaxError {
            return arg1.value(executor).isTerminated();
        }
        
    }
    
    // carries_item e
    public class BooleanIscarryingPartExpression extends BooleanPartExpression {

        public BooleanIscarryingPartExpression(UnitExpression arg) {
            this.arg1 = arg;
        }
        
        private UnitExpression arg1;

        @Override
        public Boolean getValue() throws SyntaxError {
            return arg1.value(executor).isCarrying();
        }
        
    }
    
    // read and assign in statement
    public class BooleanReadPartExpression extends BooleanPartExpression {

        public BooleanReadPartExpression(String key) {
            this.key = key;
        }

        private String key;

        @Override
        public Boolean getValue() throws SyntaxError {
            return task.booleanGlobalMap.get(key);
        }
    }

    public static Expression newBooleanConstantExpression(boolean flag) {
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanConstantPartExpression(flag));
        return expression;

    }

    public static Expression newNotExpression(BooleanExpression arg) {
        BooleanExpression expression=new BooleanExpression();

        expression.setInnerExpression(expression.new BooleanNotPartExpression(arg));
        return expression;
    }

    public static Expression newAndExpression(BooleanExpression arg1,BooleanExpression arg2) {
        BooleanExpression expression = new BooleanExpression();

        expression.setInnerExpression(expression.new BooleanAndPartExpression(arg1, arg2));
        return expression;
    }

    public static Expression newOrExpression(BooleanExpression arg1,BooleanExpression arg2) {
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanOrPartExpression(arg1, arg2));
        return expression;
    }

    public static Expression newIsPassableExpression(PositionExpression pos) {
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanIsPassablePartExpression(pos));
        return expression;
    }

    public static Expression newIsSolidExpression(PositionExpression pos) {

        return newNotExpression((BooleanExpression) newIsPassableExpression(pos));
    }
    public static Expression newIsFriendExpression(UnitExpression unit){
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanIsFriendPartExpression((UnitExpression) UnitExpression.newThisUnitExpression(),unit));
        return expression;
    }

    public static Expression newIsAliveExpression(UnitExpression unit){
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanIsAlivePartExpression(unit));
        return expression;
    }

    public static Expression newIsEnemyExpression(UnitExpression unit){
        return newNotExpression((BooleanExpression)newIsFriendExpression((unit)));

    }

    public static Expression newIsCarryingExpression(UnitExpression unit){
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanIscarryingPartExpression(unit));
        return expression;
    }

    public static Expression newReadExpression(String key){
        BooleanExpression expression=new BooleanExpression();
        expression.setInnerExpression(expression.new BooleanReadPartExpression(key));
        return expression;
    }
}
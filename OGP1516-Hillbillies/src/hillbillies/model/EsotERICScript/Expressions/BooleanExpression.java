package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class BooleanExpression extends Expression {
	
    @Override
    public Boolean value(Unit executor) throws SyntaxError {
        this.executor=executor;
        return this.innerExpression.getValue();
    }
    
    public BooleanPartExpression innerExpression;

    public abstract class BooleanPartExpression extends PartExpression {
    	
    	@Override
        public abstract Boolean getValue() throws SyntaxError;
    	
    	Expression arg1;
        Expression arg2;
        
    }

    // true
    // false
    // "(" e ")"
    public class BooleanConstantPartExpression extends BooleanPartExpression {
    	
        public BooleanConstantPartExpression(boolean flag) throws SyntaxError {
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
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return ! ((BooleanExpression) arg1).value(executor);
        }
        
    }

    // e || e
    public class BooleanOrPartExpression extends BooleanPartExpression {
    	
        public BooleanOrPartExpression(BooleanExpression arg1, BooleanExpression arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Override
        public Boolean getValue() throws SyntaxError {
            return ((boolean) arg1.value(executor)) || ((boolean) arg2.value(executor));
        }
    }

    // e && e
    public class BooleanAndPartExpression extends BooleanPartExpression {
    	
        public BooleanAndPartExpression(BooleanExpression arg1, BooleanExpression arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Override
        public Boolean getValue() throws SyntaxError {
            return ((boolean) arg1.value(executor)) && ((boolean) arg2.value(executor));
        }
        
    }

    // carries_item e
    public class BooleanIscarryingPartExpression extends BooleanPartExpression {

        public BooleanIscarryingPartExpression(UnitExpression arg1) {
            this.arg1 = arg1;
        }
        
        private UnitExpression arg1;

        @Override
        public Boolean getValue() throws SyntaxError {
            return arg1.value(executor).isCarrying();
        }
        
    }

    // is_alive e
    public class BooleanIsAlivePartExpression extends BooleanPartExpression {

        public BooleanIsAlivePartExpression(UnitExpression arg1) {
            this.arg1 = arg1;
        }
        
        private UnitExpression arg1;

        @Override
        public Boolean getValue() throws SyntaxError {
            return arg1.value(executor).isAlive();
        }
    }
    
    // is_friend e
    // => is_enemy will be implemented as NOT(is_friend)
    public class BooleanIsFriendPartExpression extends BooleanPartExpression {
    	
        public BooleanIsFriendPartExpression(UnitExpression arg1, UnitExpression arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }
        
        UnitExpression arg1;
        UnitExpression arg2;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return arg1.value(executor).getFaction().equals(arg2.value(executor).getFaction());
        }
    }
    
    // is_passable e
    // => is_solid will be implemented as NOT(is_passable)
    public class BooleanIsPassablePartExpression extends BooleanPartExpression {
    	
        public BooleanIsPassablePartExpression(PositionExpression arg1) {
            this.arg1 = arg1;
        }
        
        private PositionExpression arg1;
        
        @Override
        public Boolean getValue() throws SyntaxError {
            return task.world.getCubeAt(arg1.value(executor)).isPassable();
        }
        
    }
    
}
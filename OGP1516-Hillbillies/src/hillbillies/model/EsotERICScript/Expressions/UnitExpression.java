package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class UnitExpression extends Expression {
	
    @Override
    public Unit value() throws SyntaxError {
        return this.innerExpression.getValue();
    }
    
    public UnitPartExpression innerExpression;

    public abstract class UnitPartExpression extends PartExpression {
    	
    	@Override
        public abstract Unit getValue() throws SyntaxError;
    	
    	Expression arg1;
        Expression arg2;
        
    }
    
    //this
    public class UnitThisPartExpression extends UnitPartExpression {
    	
        public UnitThisPartExpression(Unit unit) throws SyntaxError {
            this.value = unit;
        }
        
        private Unit value;
        
        @Override
        public Unit getValue() throws SyntaxError {
            return value;
        }
        
    }
    
    //friend
    
    //enemy
    
    //any
    
}

package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.EsotERICScript.Expressions.Expression;
import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.exceptions.SyntaxError;

public class ActionStatement extends Statement {

	public ActionStatement(Task task) {
		super(task);
	}

	public abstract class ActionPartStatement extends PartStatement {
		
		@Override
	    public abstract void execute(Unit unit, double dt) throws SyntaxError;
		
	}

    // moveTo e
    public class MoveToPartStatement extends ActionPartStatement {

        public MoveToPartStatement(PositionExpression destination) {
            this.argExpr1 = destination;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(Unit unit, double dt) throws SyntaxError {
            if (! beingExcecuted)
            	unit.moveTo(argExpr1.value(executor));
            //TODO: keep information about the completion of this statement
        }
    }
    
    // work e
    public class WorkPartStatement extends ActionPartStatement {

        public WorkPartStatement(PositionExpression position) {
            this.argExpr1 = position;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(Unit unit, double dt) throws SyntaxError {
            unit.work(argExpr1.value(executor));
            //TODO: keep information about the completion of this statement
        }
        
    }
    
    // follow e
    // TODO: bc Arthur likes to procrastinate
    public class FollowPartStatement extends ActionPartStatement {

        public FollowPartStatement(UnitExpression unitToFollow) {
            this.argExpr1 = unitToFollow;
        }
        
        UnitExpression argExpr1;
        
        @Override
        public void execute(Unit unit, double dt) throws SyntaxError {
            //unit.moveTo(argExpr1.value());
            // TODO: keep information about the completion of this statement
        }
        
    }
    
    // attack e
    public class AttackPartStatement extends ActionPartStatement {
    	
        public AttackPartStatement(UnitExpression defender) {
            this.argExpr1 = defender;
        }
        
        UnitExpression argExpr1;
        
        @Override
        public void execute(Unit unit, double dt) throws SyntaxError {
            unit.attack(argExpr1.value(executor));
            //TODO: keep information about the completion of this statement
        }
        
    }
	
}

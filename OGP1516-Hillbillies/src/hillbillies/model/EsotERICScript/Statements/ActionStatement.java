package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.ProgramExecutor;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class ActionStatement extends Statement {

	public ActionStatement(Task task) {
		super(task);
	}

	public abstract class ActionPartStatement extends PartStatement {
		
		@Override
	    public abstract void execute(ProgramExecutor executor) throws SyntaxError;

	}

    // moveTo e
    public class MoveToPartStatement extends ActionPartStatement {

        public MoveToPartStatement(PositionExpression destination) {
            this.argExpr1 = destination;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit=executor.getExecutingUnit();
            if (! beingExcecuted)
            	unit.moveTo(argExpr1.value(ActionStatement.this.executingUnit));
            //TODO: keep information about the completion of this statement
        }
        
        boolean singular() {
        	return false;
        }
        
    }
    
    // work e
    public class WorkPartStatement extends ActionPartStatement {

        public WorkPartStatement(PositionExpression position) {
            this.argExpr1 = position;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit=executor.getExecutingUnit();
            unit.work(argExpr1.value(ActionStatement.this.executingUnit));
            //TODO: keep information about the completion of this statement
        }
        
        boolean singular() {
        	return false;
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
        public void execute(ProgramExecutor executor) throws SyntaxError {
            //unit.moveTo(argExpr1.value());
            // TODO: keep information about the completion of this statement
        }
        
        boolean singular() {
        	return false;
        }
        
    }
    
    // attack e
    public class AttackPartStatement extends ActionPartStatement {
    	
        public AttackPartStatement(UnitExpression defender) {
            this.argExpr1 = defender;
        }
        
        UnitExpression argExpr1;
        
        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit=executor.getExecutingUnit();
            unit.attack(argExpr1.value(ActionStatement.this.executingUnit));
            //TODO: keep information about the completion of this statement
        }
        
        boolean singular() {
        	return false;
        }
        
    }
	
}

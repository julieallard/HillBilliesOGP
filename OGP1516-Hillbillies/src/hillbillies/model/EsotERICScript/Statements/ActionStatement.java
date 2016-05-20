package hillbillies.model.EsotERICScript.Statements;


import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.EsotERICScript.ProgramExecutor;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.Collection;
import java.util.Collections;

public class ActionStatement extends Statement {

	public ActionStatement() {
		super();
	}

	public abstract class ActionPartStatement extends PartStatement {
        @Override
        public Collection<Statement> probe() {
            return Collections.EMPTY_SET;
        }

        boolean activityStarted;

        @Override
        public void refresh() {
            super.refresh();
            this.activityStarted = false;
        }

        @Override
        public Statement getNext() throws SyntaxError {
            return getEncapsulatingStatement();
        }
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
            if (! activityStarted) {
                unit.moveTo(argExpr1.value(ActionStatement.this.executingUnit));
                this.activityStarted=true;
            }
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
    public class AttackPartStatement extends ActionPartStatement {

        public AttackPartStatement(UnitExpression defender) {
            this.argExpr1 = defender;
        }

        UnitExpression argExpr1;

        @Override
        public void execute(ProgramExecutor executor) throws SyntaxError {
            Unit unit = executor.getExecutingUnit();
            unit.attack(argExpr1.value(ActionStatement.this.executingUnit));
        }

        boolean singular() {
            return false;
        }
    }
}

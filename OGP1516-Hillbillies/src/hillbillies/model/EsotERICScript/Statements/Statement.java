package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class Statement {
    private boolean beingExcecuted;

    private boolean executed;

    public Unit executor;

    public Statement(Object ... args) {

        beingExcecuted=false;
        executed=false;
    }

    public void Execute(Unit unit,double dt) throws SyntaxError{
        this.executor=unit;
        this.partStatement.execute(unit,dt);
    }

    public void finishExecuting(){
        this.beingExcecuted=false;
        this.executed=true;
        //Execute next?
    }

    public void reExecutePrepare(){
        this.beingExcecuted=false;
        this.executed=false;
    }

    private PartStatement partStatement;

    //moveTo
    class MoveToPartStatement extends PartStatement {

        public MoveToPartStatement(PositionExpression argument) {
            this.argExpr1 = argument;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(Unit unit,double dt) throws SyntaxError {
            if (!beingExcecuted) unit.moveTo(argExpr1.value(executor));
            //TODO: keep information about the completion of this statement
        }
        
    }
    
    //work
    class WorkPartStatement extends PartStatement {

        public WorkPartStatement(PositionExpression argument) {
            this.argExpr1 = argument;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(Unit unit,double dt) throws SyntaxError {
            unit.work(argExpr1.value(executor));
            //TODO: keep information about the completion of this statement
        }
        
    }
    
    // follow
    // TODO: bc Arthur likes to procrastinate
    class FollowPartStatement extends PartStatement {

        public FollowPartStatement(UnitExpression argument) {
            this.argExpr1 = argument;
        }
        
        UnitExpression argExpr1;
        
        @Override
        public void execute(Unit unit,double dt) throws SyntaxError {
            //unit.moveTo(argExpr1.value());
            // TODO: keep information about the completion of this statement
        }
        
    }
    // attack
    class AttackPartStatement extends PartStatement {
        public AttackPartStatement(UnitExpression argument) {
            this.argExpr1 = argument;
        }
        UnitExpression argExpr1;
        @Override
        public void execute(Unit unit,double dt) throws SyntaxError {
            unit.attack(argExpr1.value(executor));
            //TODO: keep information about the completion of this statement
        }
    }
}

package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.EsotERICScript.Statements.PartStatement;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public  class Statement {

    public Statement(Object ... args) {

    }

    public void Execute(Unit unit) throws SyntaxError{
        this.partStatement.execute(unit);
    }

    private PartStatement partStatement;

    //moveTo
    class MoveToPartStatement extends PartStatement {

        public MoveToPartStatement(PositionExpression argument) {
            this.argExpr1 = argument;
        }
        
        PositionExpression argExpr1;
        
        @Override
        public void execute(Unit unit) throws SyntaxError {
            unit.moveTo(argExpr1.value());
            this.setExecuted(true);
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
        public void execute(Unit unit) throws SyntaxError {
            unit.work(argExpr1.value());
            this.setExecuted(true);
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
        public void execute(Unit unit) throws SyntaxError {
            //unit.moveTo(argExpr1.value());
            this.setExecuted(true);
            //TODO: keep information about the completion of this statement
        }
        
    }
    
    // attack
    class AttackPartStatement extends PartStatement {

        public AttackPartStatement(UnitExpression argument) {
            this.argExpr1 = argument;
        }
        
        UnitExpression argExpr1;
        
        @Override
        public void execute(Unit unit) throws SyntaxError {
            unit.attack(argExpr1.value());
            this.setExecuted(true);
            //TODO: keep information about the completion of this statement
        }
        
    }

}

package hillbillies.model.EsotERICScript.Statements;

import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

public class Statement {
    private boolean beingExcecuted;
    private boolean executed;
    public Task task;
    public Unit executor;

    public Statement(Task task) {
        this.task=task;
        this.beingExcecuted=false;
        this.executed=false;

    }
    public void execute(Unit unit,double dt) throws SyntaxError{
        this.executor=unit;
        this.partStatement.execute(unit,dt);
    }

    public void finishExecuting(){
        this.beingExcecuted=false;
        this.executed=true;
        //TODO Execute next?
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
    class IfPartStatement extends PartStatement {

        private final Statement ifPart;
        private final BooleanExpression condition;
        private final Statement elsePart;

        public IfPartStatement(BooleanExpression condition, Statement ifpart, Statement elsePart){
            this.condition=condition;
            this.ifPart=ifpart;
            this.elsePart=elsePart;
        }
        private Boolean flag;
        private Boolean conditionEvaluated;

        @Override
        public void execute(Unit unit, double dt) throws SyntaxError {
            if (!conditionEvaluated) {
                flag = condition.value(unit);
                conditionEvaluated = true;
            }
            if (flag) ifPart.execute(unit, dt);
            else elsePart.execute(unit,dt);

        }
    }


    /**
     * Static methods for creating new statements
     */
    public static Statement newWorkAt(PositionExpression loc,Task task){
        Statement statement =new Statement(task);
        statement.partStatement=statement.new WorkPartStatement(loc);
        return statement;
    }
    public static Statement newAttack(UnitExpression unit,Task task){
        Statement statement =new Statement(task);
        statement.partStatement=statement.new AttackPartStatement(unit);
        return statement;
    }
    public static Statement newFollow(UnitExpression unit,Task task){
        Statement statement =new Statement(task);
        statement.partStatement = statement.new FollowPartStatement(unit);
        return statement;
    }
    public static Statement newMoveTo(PositionExpression pos,Task task){
        Statement statement =new Statement(task);
        statement.partStatement = statement.new MoveToPartStatement(pos);
        return statement;
    }

}

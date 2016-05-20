package hillbillies.model;

import hillbillies.model.EsotERICScript.Expressions.BooleanExpression;
import hillbillies.model.EsotERICScript.Expressions.Expression;
import hillbillies.model.EsotERICScript.Expressions.PositionExpression;
import hillbillies.model.EsotERICScript.Expressions.UnitExpression;
import hillbillies.model.EsotERICScript.Statements.Statement;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;

import java.util.ArrayList;
import java.util.List;


public class TaskFactory implements ITaskFactory<Expression,Statement,Task>  {


    /**
     * Create a list of tasks from the given arguments.
     *
     * @param name          The name of the task
     * @param priority      The initial priority of the task
     * @param activity      The activity of the task. Most likely this is a sequence
     *                      statement.
     * @param selectedCubes A list of cube coordinates (each represented as an array {x,
     *                      y, z}) that were selected by the player in the GUI.
     * @return A list of new task instances. One task instance should be created
     * for each selectedCube coordinate. If selectedCubes is empty and
     * the 'selected' expression does not occur in the activity, a list
     * with exactly one Task instance should be returned.
     */
    @Override
    public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
        List<Task> taskList=new ArrayList<>();
        for (int[] loc: selectedCubes){
            Task task=new Task(name, priority);
            task.setRootStatement(activity);
            if (!task.isLegaltask()) {
                throw new IllegalArgumentException("IllegalTaskSupplied");
            }
            taskList.add(task);
        }
        return taskList;
    }

    /**
     * Create a statement that represents the assignment of a variable.
     *
     * @param variableName   The name of the assigned variable
     * @param value
     * @param sourceLocation
     */
    @Override
    public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
        return Statement.newAssignstatement(variableName,value);
    }

    /**
     * Create a statement that represents a while loop.
     *
     * @param condition      The condition of the while loop
     * @param body           The body of the loop (most likely this is a sequence
     * @param sourceLocation
     */
    @Override
    public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
        if(!(condition instanceof BooleanExpression))return null;
        return Statement.newWhileStatement((BooleanExpression) condition,body);
    }

    /**
     * Create an if-then-else statement.
     *
     * @param condition      The condition of the if statement
     * @param ifBody         * The body of the if-part, which must be executed when the
     *                       condition evaluates to true
     * @param elseBody       The body of the else-part, which must be executed when the
     *                       condition evaluates to false. Can be null if no else clause is
     * @param sourceLocation
     */
    @Override
    public Statement createIf(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
        if(!(condition instanceof BooleanExpression))return null;
        return Statement.newIfStatement((BooleanExpression) condition,ifBody,elseBody);
    }

    /**
     * Create a break statement that immediately terminates the enclosing loop.
     *
     * @param sourceLocation
     * @note Students working alone may return null.
     */
    @Override
    public Statement createBreak(SourceLocation sourceLocation) {
        return Statement.newBreakStatement();
    }

    /**
     * Create a print statement that prints the value obtained by evaluating the
     * given expression.
     *
     * @param value          The expression to evaluate and print
     * @param sourceLocation
     */

    //Todo (print?????)
    @Override
    public Statement createPrint(Expression value, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create a sequence of statements.
     *
     * @param statements     The statements that must be executed in the given order.
     * @param sourceLocation
     */
    @Override
    public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
        return Statement.newSequenceStatement(statements);
    }

    /**
     * Create a moveTo statement
     *
     * @param position       The position to which to move
     * @param sourceLocation
     */
    @Override
    public Statement createMoveTo(Expression position, SourceLocation sourceLocation) {
        if(!(position instanceof PositionExpression))return null;
        return Statement.newMovetoStatement((PositionExpression)position);
    }

    /**
     * Create a work statement
     *
     * @param position       The position on which to work
     * @param sourceLocation
     */
    @Override
    public Statement createWork(Expression position, SourceLocation sourceLocation) {
        if(!(position instanceof PositionExpression))return null;
        return Statement.newWorkStatement((PositionExpression)position);
    }

    /**
     * Create a follow statement
     *
     * @param unit           The unit to follow
     * @param sourceLocation
     */
    @Override
    public Statement createFollow(Expression unit, SourceLocation sourceLocation) {
        if(!(unit instanceof UnitExpression))return null;
        return Statement.newFollowStatement((UnitExpression)unit);
    }

    /**
     * Create an attack statement
     *
     * @param unit           The unit to attack
     * @param sourceLocation
     */
    @Override
    public Statement createAttack(Expression unit, SourceLocation sourceLocation) {
        if(!(unit instanceof UnitExpression))return null;
        return Statement.newAttackStatement((UnitExpression)unit);
    }

    /**
     * Create an expression that evaluates to the current value of the given
     * variable.
     *
     * @param variableName   The name of the variable to read.
     * @param sourceLocation
     */
    @Override
    public Expression createReadVariable(String variableName, SourceLocation sourceLocation) {

    }

    /**
     * Create an expression that evaluates to true when the given position
     * evaluates to a solid position; false otherwise.
     *
     * @param position       The position expression
     * @param sourceLocation
     */
    @Override
    public Expression createIsSolid(Expression position, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when the given position
     * evaluates to a passable position; false otherwise.
     *
     * @param position       The position expression
     * @param sourceLocation
     */
    @Override
    public Expression createIsPassable(Expression position, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit of the same faction; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation
     */
    @Override
    public Expression createIsFriend(Expression unit, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit of another faction; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation
     */
    @Override
    public Expression createIsEnemy(Expression unit, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit that is alive; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation
     */
    @Override
    public Expression createIsAlive(Expression unit, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when the given unit evaluates
     * to a unit that carries an item; false otherwise.
     *
     * @param unit           The unit expression
     * @param sourceLocation
     */
    @Override
    public Expression createCarriesItem(Expression unit, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when the given expression
     * evaluates to false, and vice versa.
     *
     * @param expression
     * @param sourceLocation
     */
    @Override
    public Expression createNot(Expression expression, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true when both the left and right
     * expression evaluate to true; false otherwise.
     *
     * @param left
     * @param right
     * @param sourceLocation
     * @note short-circuit: the right expression does not need to be evaluated
     * when the left expression evaluates to false.
     */
    @Override
    public Expression createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to false only when the left and right
     * expression evaluate to false; true otherwise.
     *
     * @param left
     * @param right
     * @param sourceLocation
     * @note short-circuit: the right expression does not need to be evaluated
     * when the left expression evaluates to true.
     */
    @Override
    public Expression createOr(Expression left, Expression right, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the current position of the unit
     * that is executing the task.
     *
     * @param sourceLocation
     */
    @Override
    public Expression createHerePosition(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the position of a log.
     *
     * @param sourceLocation
     * @note for groups of two students, this needs to be the log closest to the
     * unit that is executing the task.
     */
    @Override
    public Expression createLogPosition(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the position of a boulder.
     *
     * @param sourceLocation
     * @note for groups of two students, this needs to be the boulder closest to
     * the unit that is executing the task.
     */
    @Override
    public Expression createBoulderPosition(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the position of a workshop.
     *
     * @param sourceLocation
     * @note for groups of two students, this needs to be the workshop closest
     * to the unit that is executing the task.
     */
    @Override
    public Expression createWorkshopPosition(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the position selected by the user
     * in the GUI.
     *
     * @param sourceLocation
     * @note Students working alone may return null.
     */
    @Override
    public Expression createSelectedPosition(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to a position next to the given
     * position.
     *
     * @param position
     * @param sourceLocation
     */
    @Override
    public Expression createNextToPosition(Expression position, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the position of the given unit.
     *
     * @param unit
     * @param sourceLocation
     */
    @Override
    public Expression createPositionOf(Expression unit, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to a static position with a given
     * coordinate.
     *
     * @param x
     * @param y
     * @param z
     * @param sourceLocation
     */
    @Override
    public Expression createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to the unit that is currently
     * executing the task.
     *
     * @param sourceLocation
     */
    @Override
    public Expression createThis(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to a unit that is part of the same
     * faction as the unit currently executing the task.
     *
     * @param sourceLocation
     */
    @Override
    public Expression createFriend(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to a unit that is not part of the
     * same faction as the unit currently executing the task.
     *
     * @param sourceLocation
     */
    @Override
    public Expression createEnemy(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to any unit (other than this).
     *
     * @param sourceLocation
     */
    @Override
    public Expression createAny(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to true.
     *
     * @param sourceLocation
     */
    @Override
    public Expression createTrue(SourceLocation sourceLocation) {
        return null;
    }

    /**
     * Create an expression that evaluates to false.
     *
     * @param sourceLocation
     */
    @Override
    public Expression createFalse(SourceLocation sourceLocation) {
        return null;
    }
}

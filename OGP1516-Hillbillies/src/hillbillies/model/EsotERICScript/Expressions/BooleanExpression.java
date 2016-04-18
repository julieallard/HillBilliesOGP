package hillbillies.model.EsotERICScript.Expressions;
import hillbillies.model.EsotERICScript.Expression;
import hillbillies.model.exceptions.SyntaxError;


public class BooleanExpression extends Expression {
    @Override
    public Boolean value() throws SyntaxError {
        Object value =this.innerExpression.getValue();
        try{
            return (Boolean) false;
        }catch (ClassCastException exception){
            throw new SyntaxError("the expression should have returned a Boolean, but didn't ");
        }
    }
    private  PartExpression innerExpression;
    private  Expression arg1;
    private  Expression arg2;

    public abstract class BooleanPartExpression extends PartExpression{

    }

    public class BooleanConstantPartExpression extends BooleanPartExpression{
        public BooleanConstantPartExpression(boolean flag) throws SyntaxError {
            this.value=flag;
        }
        @Override
        public Boolean getValue() throws SyntaxError {
            return value;
        }
    }
    public class BooleanNotPartExpression extends PartExpression{

        @Override
        public Object getValue() throws SyntaxError {
            return null;
        }
    }
}

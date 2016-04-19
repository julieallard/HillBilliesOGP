package hillbillies.model.EsotERICScript.Expressions;
import hillbillies.model.EsotERICScript.Expression;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;


public class BooleanExpression extends Expression {
    @Override
    public Boolean value() throws SyntaxError {
        Object value =this.innerExpression.getValue();
        try{
            return (Boolean) value;
        }catch (ClassCastException exception){
            throw new SyntaxError("the expression should have returned a Boolean, but didn't ");
        }
    }
    private  PartExpression innerExpression;
    private  Expression arg1;
    private  Expression arg2;

    public abstract class BooleanPartExpression extends PartExpression{

        @Override
        public abstract Boolean getValue() throws SyntaxError;

        public Expression arg1;
        public Expression arg2;
    }

    public class BooleanConstantPartExpression extends BooleanPartExpression{
        public BooleanConstantPartExpression(boolean flag) throws SyntaxError {
            this.value=flag;
        }
        private boolean value;
        @Override
        public Boolean getValue() throws SyntaxError {
            return value;
        }
    }
    public class BooleanNotPartExpression extends BooleanPartExpression{
        public BooleanNotPartExpression(BooleanExpression arg) {
            this.arg1=arg;
        }
        @Override
        public Boolean getValue() throws SyntaxError {
            return !(((BooleanExpression)arg1).value());
        }
    }

    public class BooleanOrPartExpression extends BooleanPartExpression{
        public BooleanOrPartExpression(BooleanExpression arg1, BooleanExpression arg2) {
            this.arg1=arg1;
            this.arg2=arg2;
        }

        @Override
        public Boolean getValue() throws SyntaxError {
            return ((boolean)arg1.value())||((boolean)arg2.value());
        }
    }

    public class BooleanAndPartExpression extends BooleanPartExpression{
        public BooleanAndPartExpression(BooleanExpression arg1, BooleanExpression arg2) {
            this.arg1=arg1;
            this.arg2=arg2;
        }

        @Override
        public Boolean getValue() throws SyntaxError {
            return ((boolean)arg1.value())&&((boolean)arg2.value());
        }
    }

    public class BooleanIscarryingPartExpression extends BooleanPartExpression{

        @Override
        public Boolean getValue() throws SyntaxError {
            return (Unfit) arg1.value()
        }
    }
}

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
            throw new Sy
        }
    }
    private  PartExpression innerExpression;
    private  Expression arg1;
    private  Expression arg2;


    public class BooleanConstantPartExpression extends PartExpression{
        public BooleanConstantPartExpression(boolean flag) throws SyntaxError {


        }

        @Override
        public Boolean getValue() throws SyntaxError {
            return null;
        }
    }
}

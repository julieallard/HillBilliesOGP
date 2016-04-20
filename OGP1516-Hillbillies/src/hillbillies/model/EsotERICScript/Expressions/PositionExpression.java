package hillbillies.model.EsotERICScript.Expressions;


import hillbillies.model.EsotERICScript.Expression;
import hillbillies.model.exceptions.SyntaxError;

public class PositionExpression extends Expression {
    @Override
    public int[] value() throws SyntaxError {
        return innerExpression.getValue();
    }
    public  PosPartExpression innerExpression;

    public abstract class PosPartExpression extends PartExpression {

        @Override
        public abstract int[] getValue() throws SyntaxError;

        Expression arg1;
    }
    //Here expression will be implemented using this class
    public class LocationOfPosPartExpression extends PosPartExpression{

        public LocationOfPosPartExpression(UnitExpression unit) {
            this.arg1=unit;
        }
        UnitExpression arg1;
        @Override
        public int[] getValue() throws SyntaxError {
            return arg1.value().getLocation().getCubeLocation();
        }
    }

    public class LogPosPartExpression extends PosPartExpression{
        public LogPosPartExpression(UnitExpression unit) {
            this.arg1=unit;
        }
        public LogPosPartExpression() {
        }

        @Override
        public int[] getValue() throws SyntaxError {
            return null;
        }
    }


    private static boolean isvalidCube(int[] loc, int request){
        //Todo implement
        return true;

    }

    private int[] scanWorld(int[] initialLoc,String target) throws SyntaxError{
        int requestType;
        switch (target){
            case "Log": requestType=0;
                break;
            case "Boulder": requestType=1;
                break;
            case "Workshop": requestType=2;
                break;
            default:throw new SyntaxError("illegal scan request");
        }
        int[] curLoc=initialLoc.clone();
        while (true){
            if (isvalidCube(curLoc,requestType)) break;

        }
        return null;
    }




}



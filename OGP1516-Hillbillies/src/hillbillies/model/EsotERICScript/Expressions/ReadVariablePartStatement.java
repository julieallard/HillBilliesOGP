package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.exceptions.SyntaxError;

import java.util.Map;

/**
 * Created by arthurdecloedt on 9/05/16.
 */
public abstract class ReadVariablePartStatement extends PartExpression {



    //TODO Checking for existence should be done before creation;
    public void setKey(String key) throws SyntaxError {
        this.key = key;

    }
    public void setUsedMap(Map<String, ?> usedMap) {
        this.usedMap = usedMap;
    }

    protected Map<String,?> usedMap;
    protected String key;
    @Override
    public Object getValue() throws SyntaxError {
        return usedMap.get(key);
    }
}

package hillbillies.model.EsotERICScript.Expressions;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.SyntaxError;

import java.util.List;
import java.util.Map;

public class UnitExpression extends Expression {
	
    @Override
    public Unit value(Unit executor) throws SyntaxError {
        this.executor = executor;
        return this.innerExpression.getValue();
    }
    
    public UnitPartExpression innerExpression;

    public abstract class UnitPartExpression extends PartExpression {
    	
    	@Override
        public abstract Unit getValue() throws SyntaxError;
        
    }
    
    // this
    public class UnitThisPartExpression extends UnitPartExpression {
    	
        public UnitThisPartExpression() throws SyntaxError {
        }
        
        @Override
        public Unit getValue() throws SyntaxError {
            return executor;
        }
        
    }
    
    // friend
    public class UnitFriendPartExpression extends UnitPartExpression {
    	
        public UnitFriendPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }
        
        UnitExpression arg1;
        
        @Override
        public Unit getValue() throws SyntaxError {
            Unit arg = arg1.value(executor);
            Faction faction = arg.getFaction();
            int[] loc = scanWorld(arg.getLocation().getCubeLocation(), "Friend");
            List<Unit> UnitList = task.world.getUnitsAt(loc);
            for (Unit unit: UnitList)
                if (unit.getFaction() == faction)
                	return unit;
            throw new SyntaxError("No friend Units found");
        }
        
    }
    
    // enemy
    public class UnitEnemyPartExpression extends UnitPartExpression {
    	
        public UnitEnemyPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }
        
        UnitExpression arg1;
        
        @Override
        public Unit getValue() throws SyntaxError {
            Unit arg = arg1.value(executor);
            Faction faction = arg.getFaction();
            int[] loc = scanWorld(arg.getLocation().getCubeLocation(), "Enemy");
            List<Unit> UnitList = task.world.getUnitsAt(loc);
            for (Unit unit: UnitList)
                if (unit.getFaction() != faction)
                	return unit;
            throw new RuntimeException("No enemy Units found");
        }
        
    }
    
    // any
    public class UnitAnyPartExpression extends UnitPartExpression {
    	
        public UnitAnyPartExpression(UnitExpression unit) {
            this.arg1 = unit;
        }
        
        UnitExpression arg1;
        
        @Override
        public Unit getValue() throws SyntaxError {
            Unit arg = arg1.value(executor);
            int[] loc = scanWorld(arg.getLocation().getCubeLocation(), "Any");
            return task.world.getUnitsAt(loc).get(0);
        }
        
    }

    public class UnitReadPartExpression extends UnitPartExpression {

        public void setKeynMap(String key, Map<String, Unit> map) throws SyntaxError {
            this.key = key;
            this.usedMap = map;
        }

        private String key;

        private Map<String, Unit> usedMap;

        @Override
        public Unit getValue() throws SyntaxError {
            return this.usedMap.get(key);
        }
    }
    
}
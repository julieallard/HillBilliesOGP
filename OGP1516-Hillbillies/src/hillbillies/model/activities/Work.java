package hillbillies.model.activities;

import hillbillies.model.CubeObjects.Air;
import hillbillies.model.CubeObjects.CubeWorldObject;
import hillbillies.model.IActivity;
import hillbillies.model.MovableWorldObject;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;
import ogp.framework.util.Util;

public class Work implements IActivity {

    public Work(Unit unit, VLocation targetLocation){
        this.simpleTimeLeft=((double) 500)/unit.getStrength();
        this.unit=unit;
        this.targetLocation=targetLocation;
    }
    private Unit unit;
    private double simpleTimeLeft;

	@Override
	public void advanceActivityTime(double dt) {
        if (Util.fuzzyGreaterThanOrEqualTo(dt,this.returnSimpleTimeLeft())) {
            this.simpleTimeLeft=0;
            conductWork();}
        else {
            this.simpleTimeLeft-=dt;


        }

	}

	@Override
	public boolean hasSimpleTimeLeft() {
		return true;
	}

	@Override
	public double returnSimpleTimeLeft() throws IllegalArgumentException {
		return this.simpleTimeLeft;
	}

	@Override
	public boolean canBeInterruptedBy(IActivity activity) {
		return true;
	}



    private final VLocation targetLocation;










}

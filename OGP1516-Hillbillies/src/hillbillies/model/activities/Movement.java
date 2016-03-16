package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.Unit;

public class Movement implements IActivity {
    @Override
    public void createNewActivity(Unit unit) {

    }

    @Override
    public void advanceActivityTime() {

    }

    @Override
    public boolean hasSimpleTimeLeft() {
        return false;
    }

    @Override
    public double returnSimpleTimeLeft() {
        return 0;
    }

    @Override
    public boolean canBeInterruptedBy(Object activity) {
        return true;
    }

    @Override
    public void interrupt() {

    }




}



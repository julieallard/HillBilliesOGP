package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.Unit;

public class Movement implements IActivity {


    @Override
    public void advanceActivityTime(double dt) {

    }

    @Override
    public boolean hasSimpleTimeLeft() {
        return false;
    }

    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        throw new IllegalArgumentException("Movement Has no simple TimeLeft attribute");
    }

    @Override
    public boolean canBeInterruptedBy(String activity) {
        return true;
    }

    @Override
    public void interrupt() {

    }




}



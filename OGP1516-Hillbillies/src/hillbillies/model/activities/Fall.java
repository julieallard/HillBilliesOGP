package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.MovableWorldObject;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;

/**
 * the Id's of the activities are the following:
 * 0: noActivity
 * 1: attack
 * 2: defend
 * 3: movement
 * 4: working
 * 5: resting
 * 6: falling
 */
public class Fall implements IActivity {

    public Fall(MovableWorldObject object){
        this.object=object;

    }
    @Override
    public void advanceActivityTime(double dt) {

        advanceFallLocation(dt);

    }

    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        return 0;
    }

    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return false;
    }

    @Override
    public int getId() {
        return 6;
    }
    private MovableWorldObject object;
    private double distanceFallen;

    private void advanceFallLocation(double dt){
        VLocation oldLoc=this.object.getLocation();
        //todo Implementeren van op zijn bek gaan

    }
}


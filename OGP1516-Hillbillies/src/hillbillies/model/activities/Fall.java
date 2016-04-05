package hillbillies.model.activities;

import hillbillies.model.IActivity;
import hillbillies.model.MovableWorldObject;
import hillbillies.model.Unit;
import hillbillies.model.VLocation;

/**
 * The Id's of the activities are the following:
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
        this.object = object;
        this.damagetobedone = 0;
    }
    
    private MovableWorldObject object;
    private double damagetobedone;
    private double distanceFallen;

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

    private void advanceFallLocation(double dt) {
        VLocation oldLoc = this.object.getLocation();
        double newZ = oldLoc.getCubeLocation()[2] - 3*dt;
        if (this.object.getWorld().willBreakFall(oldLoc.getCubeLocation()) && newZ <= oldLoc.getCubeLocation()[2] + 0.5) {
            this.object.setLocation(oldLoc.getXLocation(), oldLoc.getYLocation(), oldLoc.getCubeLocation()[2] + 0.5);
            this.damagetobedone += oldLoc.getZLocation() - this.object.getLocation().getZLocation();
            if (this.object instanceof Unit) {
                ((Unit) object).dealDamage(this.damagetobedone);
            this.object.activityFinished();
            return;
            }
        }
        VLocation newPossibleLoc = new VLocation(oldLoc.getXLocation(), oldLoc.getYLocation(), oldLoc.getZLocation() - 3*dt, this.object);
        if (! this.object.getWorld().willBreakFall(newPossibleLoc.getCubeLocation())) {
            this.object.setLocation(newPossibleLoc);
            this.damagetobedone = 3*dt*10;
            return;
        } else if (newPossibleLoc.getZLocation() - Math.floor(newPossibleLoc.getZLocation()) <= 0.5)
            this.object.setLocation(newPossibleLoc.getXLocation(), newPossibleLoc.getYLocation(), Math.floor(newPossibleLoc.getZLocation()) + 0.5);
        this.damagetobedone += oldLoc.getZLocation() - this.object.getLocation().getZLocation();
        if (this.object instanceof Unit) {
            ((Unit) object).dealDamage(this.damagetobedone);
        }
        this.object.activityFinished();
        return;
    }
    
}
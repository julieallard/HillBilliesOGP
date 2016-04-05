package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.UnitIllegalLocation;

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

public class Movement implements IActivity {
	
    public Movement(Unit unit, int[] destination) throws UnitIllegalLocation {
        this.unit = unit;
        if (! canHaveAsDestination(destination))
            throw new UnitIllegalLocation();
        this.destination = destination;
    }
    
    private Unit unit;
    private final int[] destination;	
	
    @Override
    public void advanceActivityTime(double dt) {

    }

    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        throw new IllegalArgumentException("A movement does not have a SimpleTimeLeft attribute.");
    }

    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return true;
    }

    @Override
    public int getId() {
        return 3;
    }

    /**
     * Return the destination of this Movement.
     */
    @Basic
    @Raw @Immutable
    public int[] getDestination() {
      return this.destination;
    }

    /**
     * Check whether this Movement can have the given destination as its destination.
     *
     * @param  destination
     *         The destination to check.
     * @return True if and only if the destination is an array containing
     * 		   3 coordinates and if the Unit can have the given
     * 		   destination as a cube location.
     */
    @Raw
    public boolean canHaveAsDestination(int[] destination) {
        if (destination.length != 3)
        	return false;
        return this.unit.getWorld().canHaveAsCubeLocation(destination, unit);
    }
    
}
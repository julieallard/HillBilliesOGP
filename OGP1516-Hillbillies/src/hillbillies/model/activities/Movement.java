package hillbillies.model.activities;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.IActivity;
import hillbillies.model.Unit;
import hillbillies.model.exceptions.UnitIllegalLocation;

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

public class Movement implements IActivity {


    @Override
    public void advanceActivityTime(double dt) {

    }


    @Override
    public double returnSimpleTimeLeft() throws IllegalArgumentException {
        throw new IllegalArgumentException("Movement Has no simple TimeLeft attribute");
    }

    @Override
    public boolean canBeInterruptedBy(IActivity activity) {
        return true;
    }


    @Override
    public int getId() {
        return 3;
    }

    public Movement(Unit unit,int[] destination){
        this.unit=unit;
        if (! canHaveAsDestination(destination))
            throw new UnitIllegalLocation();
        this.destination = destination;

    }

    private Unit unit;


    /**
     * Return the Destination of this Movement.
     */
    @Basic
    @Raw @Immutable
    public int[] getDestination() {
      return this.destination;
    }

    /**
     * Check whether this Movement can have the given Destination as its Destination.
     *
     * @param  destination
     *         The Destination to check.
     * @return
     *       | result ==
     */
    @Raw
    public boolean canHaveAsDestination(int[] destination) {
        if (destination.length!=3) return false;
        return this.unit.getWorld().canHaveAsCubeLocation(destination,unit);
    }

    /**
     * Variable registering the Destination of this Movement.
     */
    private final int[] destination;


}



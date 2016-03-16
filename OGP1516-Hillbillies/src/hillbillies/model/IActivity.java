package hillbillies.model;


import java.util.Objects;

/**
 * Interface for the different Activities
 */

public interface IActivity {

    void createNewActivity();

    void getState(Object activity);

    boolean hasSimpleTimeLeft(Object activity);

    double getTimeLeft(Object activity);





}

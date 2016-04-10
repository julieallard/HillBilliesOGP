package hillbillies.model;

import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Assert.*;


public class UnitTest {
    private World world;
    private Unit unit1;
    private Unit unit2;
    private Unit unit3;

    @Before
    public void setUp() throws Exception {
        int[][][] cubeWorld=new int[10][10][10];
        TerrainChangeListener terrainChangeListener=new DefaultTerrainChangeListener();
        this.world=new World(cubeWorld,terrainChangeListener);
        this.unit1=new Unit("Billy Bob",3,3,3,75,75,75,75,false,world);
        this.unit2=new Unit("Cletus",3,3,3,75,75,75,75,false,world);
        this.unit3=new Unit("John Favreau",3,3,3,75,75,75,75,false,world);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(unit1.getName(),"Billy Bob");
        assertEquals(unit2.getName(),"Cletus");
    }
    @Test
    public void setName() throws Exception {
        unit3.setName("Chef");
        assertEquals(unit3.getName(),"Chef");
    }
    @Test
    public void getLocation() throws Exception {
        double[] arr=new double[]{3,3,3};
        assertArrayEquals(unit1.getLocation().getArray(),arr,0.005);
    }
    @Test
    public void setLocation() throws Exception {
        unit1.setLocation(1,2,3);
        double[] arr=new double[]{1,2,3};
        assertArrayEquals(unit1.getLocation().getArray(),arr,0.005);
    }
    @Test
    public void setLocation1() throws Exception {
        VLocation loc=new VLocation(4,4,4,unit2);
        unit2.setLocation(loc);
        assertFalse(!loc.equals(unit2.getLocation()));
        assertEquals(unit1.getWorld().getWorldMap().get(loc),unit2);
    }

    @Test
    public void setLocation2() throws Exception {
        double[] arr=new double[]{1,2,3};
        assertArrayEquals(unit1.getLocation().getArray(),arr,0.005);
        unit1.setLocation(arr);
    }

    @Test
    public void register() throws Exception {
        unit1.unregister();
        assertFalse(world.getWorldMap().getAllUnits().contains(unit1));
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(unit1.getWeight(),75);

    }

    @Test
    public void setWeight() throws Exception {
        unit1.setWeight(100);
        assertEquals(unit1.getWeight(),100);

    }

    @Test
    public void getStrength() throws Exception {
        assertEquals(unit1.getStrength(),75);
    }

    @Test
    public void setStrength() throws Exception {
        unit1.setStrength(100);
        assertEquals(unit1.getStrength(),100);
    }

    @Test
    public void getAgility() throws Exception {
        assertEquals(unit1.getAgility(),75);
    }

    @Test
    public void setAgility() throws Exception {
        unit1.setAgility(100);
        assertEquals(unit1.getAgility(),100);

    }

    @Test
    public void getToughness() throws Exception{
        assertEquals(unit1.getToughness(),75);
        }

    @Test
    public void setToughness() throws Exception {
        unit1.setToughness(100);
        assertEquals(unit1.getToughness(),100);

    }

    @Test
    public void isDefaultBehaviorEnabled() throws Exception {
        assertEquals(unit1.isDefaultBehaviorEnabled(),false);
    }

    @Test
    public void setDefaultBehavior() throws Exception {
        unit1.setDefaultBehavior(true);
        assertEquals(unit1.isDefaultBehaviorEnabled(),true);

    }

    @Test
    public void getWorld() throws Exception {
        assertEquals(unit1.getWorld(),world);

    }

    @Test
    public void setWorld() throws Exception {

    }

    @Test
    public void getMaxPoints() throws Exception {

    }

    @Test
    public void isValidPoints() throws Exception {

    }

    @Test
    public void getCurrentHitPoints() throws Exception {

    }

    @Test
    public void setCurrentHitPoints() throws Exception {

    }

    @Test
    public void getCurrentStaminaPoints() throws Exception {

    }

    @Test
    public void setCurrentStaminaPoints() throws Exception {

    }

    @Test
    public void getOrientation() throws Exception {

    }

    @Test
    public void isValidOrientation() throws Exception {

    }

    @Test
    public void setOrientation() throws Exception {

    }

    @Test
    public void getActivity() throws Exception {

    }

    @Test
    public void isValidActivity() throws Exception {

    }

    @Test
    public void setActivity() throws Exception {

    }

    @Test
    public void isCarrying() throws Exception {

    }

    @Test
    public void setCarrying() throws Exception {

    }

    @Test
    public void getCarriedObject() throws Exception {

    }

    @Test
    public void carry() throws Exception {

    }

    @Test
    public void drop() throws Exception {

    }

    @Test
    public void isSprinting() throws Exception {

    }

    @Test
    public void setSprinting() throws Exception {

    }

    @Test
    public void advanceTime() throws Exception {

    }

    @Test
    public void behaveDefault() throws Exception {

    }

    @Test
    public void activityFinished() throws Exception {

    }

    @Test
    public void work() throws Exception {

    }

    @Test
    public void attack() throws Exception {

    }

    @Test
    public void getFaction() throws Exception {

    }

    @Test
    public void canHaveAsFaction() throws Exception {

    }

    @Test
    public void setFaction() throws Exception {

    }

    @Test
    public void unregister() throws Exception {

    }

    @Test
    public void dealDamage() throws Exception {

    }

    @Test
    public void getXP() throws Exception {

    }

    @Test
    public void addXP() throws Exception {

    }

    @Test
    public void moveTo() throws Exception {

    }

    @Test
    public void moveToAdjacent() throws Exception {

    }

    @Test
    public void rest() throws Exception {

    }
}
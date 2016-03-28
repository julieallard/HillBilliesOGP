package hillbillies.modeltest;



import hillbillies.model.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;


public class WorldMapTest {




/*    public void Setup(){
        WorldMap<VLocation,MovableWorldObject> worldMap=new WorldMap<>();
        Unit unit1 = new Unit("unit1", new double[]{1.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit1.getLocation(),unit1);
        Unit unit2 = new Unit("unit1", new double[]{1.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit2.getLocation(),unit2);
        Unit unit3 = new Unit("unit1", new double[]{1.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit3.getLocation(),unit3);
        Unit unit4 = new Unit("unit1", new double[]{1.5,1.6,1.5},100,50,50,50,false);
        worldMap.put(unit4.getLocation(),unit4);
        Unit unit5 = new Unit("unit1", new double[]{3.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit5.getLocation(),unit5);

        Log log1=new Log(1.2,1.5,1.9);
        worldMap.put(log1.getLocation(),log1);
        Log log2=new Log(1.4,1.1,1.999999999);
        worldMap.put(log2.getLocation(),log2);
        Log log3=new Log(1,1.5,1.9);
        worldMap.put(log3.getLocation(),log3);
        Log log4=new Log(1.22,1.45,7.9);
        worldMap.put(log4.getLocation(),log4);

        Boulder Boulder1=new Boulder(1.2,1.5,1.9);
        worldMap.put(Boulder1.getLocation(),Boulder1);
        Boulder Boulder2=new Boulder(1.4,1.1,1.999999999);
        worldMap.put(Boulder2.getLocation(),Boulder2);
        Boulder Boulder3=new Boulder(1,1.5,1.9);
        worldMap.put(Boulder3.getLocation(),Boulder3);
        Boulder Boulder4=new Boulder(1.22,1.45,7.9);
        worldMap.put(Boulder4.getLocation(),Boulder4);
        Unit[] unit111expected =new Unit[]{unit1,unit2,unit3,unit4};
        List<Unit> unitlist111expected= Arrays.asList(unit111expected);
        Boulder[] Boulder111expected =new Boulder[]{Boulder1,Boulder2,Boulder3};
        Log[] Log111expected =new Log[]{log1,log2,log3};
        MovableWorldObject[] allexpected=new MovableWorldObject[]{unit1,unit2,unit3,unit4,Boulder1,Boulder2,Boulder3,log1,log2,log3};
        List<Boulder> Boulderlist111expected= Arrays.asList(Boulder111expected);
        List<Log> Loglist111expected= Arrays.asList(Log111expected);


    }*/

    @Test
    public void getAllInCube() throws Exception {
        WorldMap<VLocation,MovableWorldObject> worldMap=new WorldMap<>();
        Unit unit1 = new Unit("unit1", new double[]{1.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit1.getLocation(),unit1);
        Unit unit2 = new Unit("unit1", new double[]{1.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit2.getLocation(),unit2);
        Unit unit3 = new Unit("unit1", new double[]{1.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit3.getLocation(),unit3);
        Unit unit4 = new Unit("unit1", new double[]{1.5,1.6,1.5},100,50,50,50,false);
        worldMap.put(unit4.getLocation(),unit4);
        Unit unit5 = new Unit("unit1", new double[]{3.2,1.3,1.5},100,50,50,50,false);
        worldMap.put(unit5.getLocation(),unit5);

        Log log1=new Log(1.2,1.5,1.9);
        worldMap.put(log1.getLocation(),log1);
        Log log2=new Log(1.4,1.1,1.999999999);
        worldMap.put(log2.getLocation(),log2);
        Log log3=new Log(1,1.5,1.9);
        worldMap.put(log3.getLocation(),log3);
        Log log4=new Log(1.22,1.45,7.9);
        worldMap.put(log4.getLocation(),log4);

        Boulder Boulder1=new Boulder(1.2,1.5,1.9);
        worldMap.put(Boulder1.getLocation(),Boulder1);
        Boulder Boulder2=new Boulder(1.4,1.1,1.999999999);
        worldMap.put(Boulder2.getLocation(),Boulder2);
        Boulder Boulder3=new Boulder(1,1.5,1.9);
        worldMap.put(Boulder3.getLocation(),Boulder3);
        Boulder Boulder4=new Boulder(1.22,1.45,7.9);
        worldMap.put(Boulder4.getLocation(),Boulder4);
        Unit[] unit111expected =new Unit[]{unit1,unit2,unit3,unit4};
        List<Unit> unitlist111expected= Arrays.asList(unit111expected);
        MovableWorldObject[] allexpected=new MovableWorldObject[]{unit1,unit2,unit3,unit4,Boulder1,Boulder2,Boulder3,log1,log2,log3};
        List<MovableWorldObject>listallexpected=Arrays.asList(allexpected);
        List<MovableWorldObject>  result=worldMap.getAllInCube( new int[]{1,1,1});
        assertTrue("has all necessary",result.containsAll(listallexpected));
        assertTrue("length",result.size()==listallexpected.size());


    }

    @org.junit.Test
    public void getAllLogsInCube() throws Exception {

    }

    @org.junit.Test
    public void getAllBouldersInCube() throws Exception {

    }

    @org.junit.Test
    public void getAllUnitsInCube() throws Exception {

    }
}
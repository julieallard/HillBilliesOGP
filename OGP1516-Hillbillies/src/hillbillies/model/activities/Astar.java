package hillbillies.model.activities;


import hillbillies.model.Unit;
import hillbillies.model.UnitIllegalLocation;
import hillbillies.model.VLocation;

import java.util.*;

public class Astar {

    protected VLocation[] FindPath(Unit unit, VLocation startcube, VLocation destination) throws RuntimeException {

        //nodes already discovered and evaluated
        Set<VLocation> closedSet= Collections.emptySet();
        //nodes already discovered but yet to be evaluated
        Set<VLocation> openSet= Collections.emptySet();

        openSet.add(startcube);
        //the node this node can be most efficiently be reached from
        Map<VLocation,VLocation> cameFrom=new Map<VLocation,VLocation>(){};
        // The cost of getting to that node from the start
        Map<VLocation,Double> gScore=new Map<VLocation, Double>() {};
        // distance from start to start is 0
        gScore.put(startcube,(double) 0);

        // A cost estimate for reaching the destination from the start through this node
        // partly known (start => node) partly heuristic (node=>destination)
        Map<VLocation,Double> FScore=new Map<VLocation, Double>() {};
        FScore.put(startcube,costEstimatefrom(startcube,destination));


        while (!openSet.isEmpty()){
            VLocation current= startcube;
            double fscore=Double.MAX_VALUE;
            //search the discovered neighbour that is probably the closest to the destination
            for (VLocation member:
                 openSet) {
                double currentFScore=FScore.getOrDefault(member,Double.MAX_VALUE);
                if (currentFScore<=fscore){
                    current=member;
                    fscore=currentFScore;
                    }

            }
            //if the destination is reached the shortest path is reconstructed
            if (current==destination){
                return reconstructPath(cameFrom,destination);
            }
            openSet.remove(current);
            closedSet.add(current);
            //alle buren worden ontdekt en er worden schattingen gemaakt van de afstand tot de bestemming
            for (VLocation buur :
                    getAllNeighbours(current)) {
                if (closedSet.contains(current)){
                    continue;
                }
                double tentativeGScore=gScore.get(current)+VLocation.getDistanceBetween(current,buur);
                if (!openSet.contains(buur)){
                    openSet.add(buur);
                }
                else if (tentativeGScore>=gScore.get(buur)){
                    //a better path has already been found to this node
                    continue;

                }
                //this is the best path found to this node yet
                cameFrom.put(buur,current);
                gScore.put(buur,tentativeGScore);
                FScore.put(buur,tentativeGScore+costEstimatefrom(buur,destination));


            }
        }
        throw new RuntimeException("A* has failed to find a shortest path");

    }

    private double costEstimatefrom(VLocation node,VLocation destination){
        return VLocation.getDistancebetween(node,destination);
    }
    private VLocation[] reconstructPath(Map<VLocation,VLocation> cameFrom,VLocation current){
        List<VLocation> path= new ArrayList<VLocation>();
        path.add(current);

        while (cameFrom.containsKey(current)){
            current=cameFrom.get(current);
            path.add(current);
        }
        VLocation[] path2=new VLocation[path.size()];
        return path.toArray(path2);
    }
}
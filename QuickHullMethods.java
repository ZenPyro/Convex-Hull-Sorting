package JavaLearningConvexHull;

import java.util.*;
import java.awt.Point;

//This class will hold all of our convex hull static methods
public class QuickHullMethods {
    //First we will make a static helper method to find the extrema for the line `P1P2`
    //It will return an array with the `Point` `min` at the index `[0]` and `Point` `max` ->
    //-> at the index `[1]`
    public static Point[] findExtrema(ArrayList<Point> list){
        Iterator<Point> iter = list.iterator();
        //Making the starting `min` and `max` points something that will always be changed ->
        //-> on first iteration
        Point min = new Point(51,0);
        Point max = new Point(-1, 0);

        while(iter.hasNext() == true){
            Point curPoint = iter.next();

            //This will always run only the first time
            if(curPoint.getX() < min.getX() && curPoint.getX() > max.getX()){
                min = curPoint;
                max = curPoint;
            }
            else if(curPoint.getX() < min.getX()){
                min = curPoint;
            }
            else if(curPoint.getX() > max.getX()){
                max = curPoint;
            }

            //System.out.println(curPoint);
        }

        System.out.println();

        // System.out.println("Min: " + min);
        // System.out.println("Max: " + max);
        list.remove(min);
        list.remove(max);
        Point[] extrema = {min,max};
        return extrema;
    }

    //This static method finds the furthest point from the two extrema and add it to the ->
    //-> the convex hull
    //NOTE: A -> B is right side list (`sR`) and B -> A is left side list (`sL`)
    public static ArrayList<Point> findFurthest(ArrayList<Point> finalConvexHull, ArrayList<Point> sList, Point min, Point max, Boolean isAboveOrBelow){
        if(sList.size() == 0){//Base case for recursion: If the `ArrayList` `list` is empty ->
            //-> then `return null`
            return null;
        }
        else{//Iterate through the `ArrayList` of points `sList` and calculate which point ->
            //-> is the furthest by adding the distance from the min to the point, to the ->
            //-> distance from the point to the max
            Point C = new Point();
            for(int i = 0; i < sList.size(); i++){
                if(i == 0){
                    C = sList.get(i);
                }
                //IMPORTANT NOTE: We are not using the "sqrt" in the "distance formula" ->
                //-> because the solution is monotonically increasing, so with or without ->
                //-> the "sqrt" we will get the furthest point from `min`/`max` and ->
                //-> removing the `sqrt` releases the load on the machine from having ->
                //-> to calculate the `sqrt` everytime
                //NOTE: My distance formula was having a problem so now I am going to comment ->
                //-> it out and try the built in static method `distanceSq(i,i,i,i)` ->
                //-> in the `Point` class (I have no doubt it is because I messed up the ->
                //-> calculation somewhere inside that MESS of parenthesis)
                //else if((Math.sqrt(Math.pow(sList.get(i).getX()-min.getX(), 2)+Math.pow(sList.get(i).getY()-min.getY(), 2))+Math.sqrt(Math.pow(max.getX()-sList.get(i).getX(), 2)+Math.pow(max.getY()-sList.get(i).getY(), 2))) > Math.sqrt(Math.pow(C.getX()-min.getX(), 2)+Math.pow(C.getY()-min.getY(), 2))+Math.sqrt(Math.pow(max.getX()-C.getX(), 2)+Math.pow(max.getY()-C.getY(), 2))){
                else if((Point.distance(min.getX(), min.getY(), sList.get(i).getX(), sList.get(i).getY()) + Point.distance(sList.get(i).getX(), sList.get(i).getY(), max.getX(), max.getY())) > (Point.distance(min.getX(), min.getY(), C.getX(), C.getY()) + Point.distance(C.getX(), C.getY(), max.getX(), max.getY()))){
                    C = sList.get(i);
                }
            }
            //Add the furthest point `C` from `min`/`max` to the convex hull points ->
            //-> `ArrayList` `finalConvexHull` between the `min` and `max`
            //The additional points added to the `finalConvexHull` `ArrayList` come into ->
            //-> play for painting the points on the canvas (look at `JavaFXQuickHullMain` ->
            //-> for a better understanding of why)
            if(isAboveOrBelow == true){
                finalConvexHull.add(finalConvexHull.indexOf(min)+1, C);
                finalConvexHull.add(finalConvexHull.indexOf(C), new Point(-1,-1));//Means above
            }
            else{
                finalConvexHull.add(finalConvexHull.indexOf(min)+1, C);
                finalConvexHull.add(finalConvexHull.indexOf(C), new Point(-2,-2));//Means below
            }
            sList.remove(C);
            // System.out.println("C is: " + C);
            // System.out.println("Min is: " + min);
            // System.out.println("Max is: " + max);

            //We are not going to have a `s0` just an `s1` and a `s2` that we will add ->
            //-> points to

            //Slopes from `min` to `C` and `C` to `max` so we do not have to calculate them ->
            //-> everytime
            double minCSlope = (C.getY()-min.getY())/(C.getX()-min.getX());
            double maxCSlope = (max.getY()-C.getY())/(max.getX()-C.getX());
            
            //1ArrayList1s to store the `s1` (`sL`) and `s2` (`sR`)
            ArrayList<Point> sL = new ArrayList<Point>();
            ArrayList<Point> sR = new ArrayList<Point>();
            
            for(int i = 0; i < sList.size(); i++){
                //IMPORTANT NOTE FOR BELOW: If point `C` (the furthest point) is above or ->
                //-> below (left or right) the slope from `min` to `max` then the way ->
                //-> to determine where all other points fall from the triangle made ->
                //-> between `min`, `max`, and `C` is different which is why below we ->
                //-> have an if statement to determine whether the point `C` currently ->
                //-> being used is above or below (left or right) the slope from `min` to ->
                //-> `max` and the condition in the if statement just checks whether the ->
                //-> the slope from `min` to `C` is negative or positive
                //NOTE: Have to do it a new way because I forgot that `minCSlope` is ->
                //-> calculated with `C` and not `max` meaning a point above the slope ->
                //-> from `min` to `max` could be determined as below when it should be ->
                //-> determined as above
                //if(minCSlope > 0){
                //NEW CONDITION USED BELOW:
                //I really did not want to resend `max` and `min` everytime and re-calculate ->
                //-> the `min` to `max` slope everytime so to get around this I just used ->
                //-> a boolean in the initial and recursive calls that marks it as an ->
                //-> above (`true`) or below (`below`) `findFurthest()` function call
                if(isAboveOrBelow == true){
                //If the slope from `min` to a point is greater than `min` to `C` then we ->
                //-> know that this unknown point is on the left side of the line from ->
                //-> `min` to `C`
                //IMPORTANT NOTE: This does not mean that all other points will be on ->
                //-> the right side of the line from `C` to `max` because they could fall ->
                //-> in the middle of the triangle instead (we will need another check ->
                //-> to determine if a point is on the right side of the line from `C` to `max`)
                    if((sList.get(i).getY()-min.getY())/(sList.get(i).getX()-min.getX()) > minCSlope){
                        sL.add(sList.get(i));
                        // System.out.println("LEFT SIDE OF TRI (S1): " + sList.get(i));
                    }
                //If the slope from some point to `max` is less than the slope from `C` to ->
                //-> `max` and it already failed the check of having a greater slope than ->
                //-> `min` to `C` then we know that the point is on the right side of the ->
                //-> line from `C` to `max`
                    else if((max.getY()-sList.get(i).getY())/(max.getX()-sList.get(i).getX()) < maxCSlope){
                        sR.add(sList.get(i));
                        // System.out.println("RIGHT SIDE OF TRI (S2): " + sList.get(i));
                    }
                    else{
                        // System.out.println("[S0 Detected] " + sList.get(i));
                    }
                }
                else{
                    if((sList.get(i).getY()-min.getY())/(sList.get(i).getX()-min.getX()) < minCSlope){
                        sL.add(sList.get(i));
                        //System.out.println("LEFT SIDE OF TRI (S1): " + sList.get(i));
                    }
                    else if((max.getY()-sList.get(i).getY())/(max.getX()-sList.get(i).getX()) > maxCSlope){
                        sR.add(sList.get(i));
                        //System.out.println("RIGHT SIDE OF TRI (S2): " + sList.get(i));
                    }
                    else{
                        //System.out.println("[S0 Detected] " + sList.get(i));
                    }
                }
            }

            //Recursively call the static function `findFurthest()` with the newly determined ->
            //-> points and `C` as one of the extrema for the differing sides (`sL` or `sR`)
            findFurthest(finalConvexHull, sL, min, C, isAboveOrBelow);
            findFurthest(finalConvexHull, sR, C, max, isAboveOrBelow);
        }
        return null;
    }

    //Static method for actually assembling all of the `Point` points that make the ->
    //-> convex hull and putting them into an `ArrayList` to be returned
    public static ArrayList<Point> convexHullAssembler(ArrayList<Point> list){
        ArrayList<Point> finalConvexHull = new ArrayList<Point>(2);//This `ArrayList` instance (reference/pointer variable) ->
        //-> will hold all the `Point` points at the very end that make up the convex hull

        Point[] extrema = findExtrema(list);
        Point min = extrema[0];
        Point max = extrema[1];
        finalConvexHull.add(min);
        finalConvexHull.add(max);

        ArrayList<Point> sL = new ArrayList<Point>();//Left extrema line
        ArrayList<Point> sR = new ArrayList<Point>();//Right extrema line

        //Check if the slope from `min` to `max` is positive, negative, or no slope because ->
        //-> it changes the conditions for determining if a point is on the left or right side
        if(min.getY() < max.getY()){//Positive slope from `min` to `max`

            //First: we must find what the slope between `min` and `max` is to be used later for ->
            //-> which side of the slope a point is on
            //NOTE: `MMS` stands for "MinMaxSlope"
            double MMS = ((max.getY()-min.getY())/(max.getX()-min.getX()));

            for(int i = 0; i < list.size(); i++){
                // //BELOW: is if the slope from `min` to `max` is positive
                // //If a point's slope to the `min` is >= 1 it will ALWAYS be on the left side
                // //NOTE: a point that has a higher `Y` value than the `max` `Y` value will always ->
                // //-> have a negative slope to `max` meaning it must be on its left side
                // if(((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) >= 1)){
                //     sL.add(list.get(i));
                //     System.out.println("LEFT SIDE: " + list.get(i));
                // }
                // //*If a point's slope to the `min` is < 1 there are two options:*
                // //1.) The point's slope to the `max` is also < 1 in which the point is on the ->
                // //-> left side
                // else if(((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) < 1) && (max.getY()-list.get(i).getY())/(max.getX()-list.get(i).getX()) < 1){
                //     sL.add(list.get(i));
                //     System.out.println("LEFT SIDE: " + list.get(i));
                // }
                // //2.) The point's slope to the `max` is NOT also < 1 in which the point is on ->
                // //-> the right side
                // else{
                //     sR.add(list.get(i));
                //     System.out.println("RIGHT SIDE: " + list.get(i));
                // }
                
                //Above is the old and worse way of finding which side the point is on ->
                //-> (but I put so much work into that first method I didnt have the heart ->
                //-> to delete it so now it will be eternally memorialized in commented out ->
                //-> form)

                //The point's slope to `min` is > `MMS` meaning it will ALWAYS be on the ->
                //-> left side
                if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) > MMS){
                    sL.add(list.get(i));
                    //System.out.println("LEFT SIDE: " + list.get(i));
                }
                //The point's slope to `min` is <= `MMS` meaning it will ALWAYS be on the ->
                //-> right side
                //NOTE: The `=` in the inequality `<=` means that if the point's slope to ->
                //-> the `min` is the same as the slope from `min` to `max` then it will ->
                //-> arbitrarily be put on the right side
                //NOTE: I could just make the `else if()` statement below just an `else` ->
                //-> statement but I wanted to be able to show how the other condition is ->
                //-> found
                else if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) <= MMS){
                    sR.add(list.get(i));
                    //System.out.println("RIGHT SIDE: " + list.get(i));
                }
            }
        }
        else if(min.getY() > max.getY()){//Negative slope from `min` to `max`
        //NOTE: The left side is below and the right side is above

            //First: we must find what the slope between `min` and `max` is to be used later for ->
            //-> which side of the slope a point is on
            //NOTE: `MMS` stands for "MinMaxSlope"
            double MMS = ((max.getY()-min.getY())/(max.getX()-min.getX()));

            for(int i = 0; i < list.size(); i++){
                // //If the point's slope to `min` is >= 0 then it will ALWAYS be on the right ->
                // //-> side 
                // if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) >= 0){
                //     sR.add(list.get(i));
                //     System.out.println("RIGHT SIDE: " + list.get(i));
                // }
                // //*If the point's slope to `min` is < 0 there are two options:*
                // //1.) The point's slope to `max` is < -1 in which the point is on the right ->
                // //-> side
                // else if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) < 0 && ((max.getY()-list.get(i).getY())/(max.getX()-list.get(i).getX()) <= -1)){
                //     sR.add(list.get(i));
                //     System.out.println("RIGHT SIDE: " + list.get(i));
                // }
                // //2.) The point's slope to `max` is > -1 in which the point is on the left ->
                // //-> side
                // else{
                //     sL.add(list.get(i));
                //     System.out.println("LEFT SIDE: " + list.get(i));
                // }

                // Above is the old and worse way of finding which side the point is on ->
                // -> (but I put so much work into that first method I didnt have the heart ->
                // -> to delete it so now it will be eternally memorialized in commented out ->
                // -> form)
                
                //The point's slope to the `min` is < `MMS` meaning the point will ALWAYS ->
                //-> be on the left side
                if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) < MMS){
                    sR.add(list.get(i));
                    //System.out.println("(RIGHT) LEFT SIDE: " + list.get(i));
                }
                //The point's slope to the `min` is >= `MMS` meaning the point will ALWAYS ->
                //-> be on the right side
                //NOTE: The `=` in the inequality `>=` means that if the point's slope to ->
                //-> the `min` is the same as the slope from `min` to `max` then it will ->
                //-> arbitrarily be put on the right side
                //NOTE: I could just make the `else if()` statement below just an `else` ->
                //-> statement but I wanted to be able to show how the other condition is ->
                //-> found
                else if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) >= MMS){
                    sL.add(list.get(i));
                    //System.out.println("(LEFT) RIGHT SIDE: " + list.get(i));
                }
            }
        }
        else if(min.getY() == max.getY()){//No slope from `min` to `max`
            for(int i = 0; i < list.size(); i++){
                //The point's slope to the `min` is positive in which the point is on the ->
                //-> left side (above)
                if((list.get(i).getY()-min.getY())/(list.get(i).getX()-min.getX()) >= 0){
                    sL.add(list.get(i));
                    //System.out.println("LEFT SIDE (above): " + list.get(i));
                }
                //The point's slope to the `min` is negative in which the point is on the ->
                //-> right side (below)
                else{
                    sR.add(list.get(i));
                    //System.out.println("RIGHT SIDE (below): " + list.get(i));
                }
            }
        }

        findFurthest(finalConvexHull, sL, min, max, true);
        findFurthest(finalConvexHull, sR, min, max, false);
        return finalConvexHull;
    }
}

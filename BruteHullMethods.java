package JavaLearningConvexHull;

import java.util.ArrayList;
import java.awt.Point;

public class BruteHullMethods {
    public static ArrayList<Point> convexHullAssembler(ArrayList<Point> list){
        ArrayList<Point> finalConvexHull = new ArrayList<Point>();

        //We need an intial `for-loop` to loop through the whole list
        for(int i = 0; i < list.size(); i++){
            //This next `for-loop` then compares each index with all other indicies
            for(int j = i+1; j < list.size(); j++){
                //Initialize two ArrayLists to determine if all points are on the same side ->
                //-> of the slope from `i` -> `j` later
                ArrayList<Point> sL = new ArrayList<Point>();
                ArrayList<Point> sR = new ArrayList<Point>();
                //The `for-loop` below is to check this slope (line) from `i` -> `j` ->
                //-> with all other points `c`
                for(int c = 0; c < list.size(); c++){
                    //I will go into much more depth about the if statements below later ->
                    //-> (when I finish looking into the derivation of determinents) but ->
                    //-> essentially it uses a cross product (determinent of a 2D matrix) ->
                    //-> to determine if a point is on one side or the other of a line ->
                    //-> in my case if the cross product is greater than `0` it is on the ->
                    //-> left side and put in the `sL` `ArrayList` but if the cross product is ->
                    //-> less than `0` it is on the right side and put in the `sR` ->
                    //-> `ArrayList` (I do understand that giving the lists names like ->
                    //-> left and right can be confusing since they could "switch" sides ->
                    //-> in some cases but I found it most useful to name them this way)
                    //IMPORTANT NOTE: You may be wondering: What if the cross product is ->
                    //-> excatly `0`? Well if it is exactly `0` then the three points (`i`, ->
                    //-> `j`, and `c`) are colinear meaning they all fall on the same line ->
                    //-> which means `c` has no bearing on determining if `i` -> `j` make ->
                    //-> up the convex hull (in fact it may make up part of the convex hull ->
                    //->itself)
                    //NOTE: The cross product gives you the area of the triangle formed ->
                    //-> by the three points (`i`, `j`, and `c`) but normally you would ->
                    //-> take the absolute value of it (because area is always positive) ->
                    //-> we do not do this since it being negative or positive actaully ->
                    //-> tells us what side of the line `i` -> `j` the point `c` is on ->
                    //-> A very neat way to find out what side it falls on indeed
                    if((list.get(j).getX()-list.get(i).getX())*(list.get(c).getY()-list.get(i).getY())-(list.get(j).getY()-list.get(i).getY())*(list.get(c).getX()-list.get(i).getX()) > 0){
                        sL.add(list.get(c));
                        // System.out.println("LEFT SIDE: " + list.get(c));
                    }
                    else if((list.get(j).getX()-list.get(i).getX())*(list.get(c).getY()-list.get(i).getY())-(list.get(j).getY()-list.get(i).getY())*(list.get(c).getX()-list.get(i).getX()) < 0){
                        sR.add(list.get(c));
                        // System.out.println("RIGHT SIDE: " + list.get(c));
                    }
                }
                //`if-statements` below only allow for the points at `i` and `j` to be ->
                //-> added to the convex hull if all points `c` fall on one side of its ->
                //-> slope
                //NOTE: I could probably merged the `if` and `else if` with an either-or ->
                //-> operator (||) but I did not want it to possibly fail with my small ->
                //-> amount of time left to finish `Brute-Force ConvexHull`
                if(sL.isEmpty() == true && sR.isEmpty() == false){
                    finalConvexHull.add(list.get(i));
                    finalConvexHull.add(list.get(j));
                    // System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                }
                else if(sL.isEmpty() == false && sR.isEmpty() == true){
                    finalConvexHull.add(list.get(i));
                    finalConvexHull.add(list.get(j));
                    // System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                }
                else{
                    // System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                }
            }
        }
        return finalConvexHull;
    }
}

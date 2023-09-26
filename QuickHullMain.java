package JavaLearningConvexHull;

import java.util.*;
import java.awt.Point;
// import java.util.Random;

public class QuickHullMain{
    public static void main(String args[]){//NOTE: Just a quick reminder that the array ->
    //-> type symbol `[]` can either be next to `String` or after the instance ->
    //-> (reference/pointer variable) `args` (although the brackets identify the array type ->
    //-> and should appear with the type designation `String`)

        //Constructing a new `Random` instance (instance/pointer variable) `rand`
        // Random rand = new Random();

        //NOTE: the static method `Math.floor()` returns the value rounded down which is why ->
        //-> we must include the `+1` in `(50-10+1)` because the static method ->
        //-> `Math.random()` returns a double between 0.0 and < 1.0 so at max we get ->
        //-> 0.9999... which means 0.9999... * 51 would give a double something like ->
        //-> 50.9999 but since the static method `Math.floor()` returns the rounded down ->
        //-> value, we would get 50 instead of something like 51 (which I originally thought ->
        //-> because I initially thought `Math.floor()` rounded up)
        int numPoints = (int)Math.floor(Math.random()*(20-10+1)+10);
        System.out.println(numPoints);

        //Im going to use an `ArrayList` datastructure as my list type because is is much ->
        //-> faster than `LinkedList` when it comes to accessing with a specific index ->
        //-> (althrough `ArrayList` is slower at insertion/deletion, we wont be doing much ->
        //-> inserting/deleting so that is perfectly fine)
        //(I will look into making my own `ArrayList` class later but I want to dive into ->
        //-> the convex hull problem first)
        //NOTE: I honestly could just use an array for this part since the `ArrayList`'s size ->
        //-> does not change but I wanted to toy around with `ArrayList`s for practice
        ArrayList<Point> list = new ArrayList<Point>(numPoints);

        for(int i = 0; i < numPoints; i++){
            Point curPoint = new Point((int)Math.floor(Math.random()*(50-0+1)+0), (int)Math.floor(Math.random()*(50-0+1)+0));
            list.add(curPoint);
        }

        // ArrayList<Point> list = new ArrayList<Point>();
        
        // Point point1 = new Point(6,19);
        // Point point2 = new Point(16,43);
        // Point point3 = new Point(32,7);
        // Point point4 = new Point(15,3);
        // Point point5 = new Point(11,42);
        // Point point6 = new Point(11,38);
        // Point point7 = new Point(5,39);
        // Point point8 = new Point(29,39);
        // Point point9 = new Point(31,36);
        // Point point10 = new Point(44,15);
        // Point point11 = new Point(24,9);
        // Point point12 = new Point(1,8);
        // Point point13 = new Point(43,27);
        // Point point14 = new Point(0,31);
        // Point point15 = new Point(48,7);

        // list.add(point1);
        // list.add(point2);
        // list.add(point3);
        // list.add(point4);
        // list.add(point5);
        // list.add(point6);
        // list.add(point7);
        // list.add(point8);
        // list.add(point9);
        // list.add(point10);
        // list.add(point11);
        // list.add(point12);
        // list.add(point13);
        // list.add(point14);
        // list.add(point15);

        //ConvexHullMethods.findExtrema(list);
        ArrayList<Point> finalConvexHull = QuickHullMethods.convexHullAssembler(list);

        System.out.println("Points that make up the Convex-Hull:");
        for(int i = 0; i < finalConvexHull.size(); i++){
            System.out.print(" " + finalConvexHull.get(i));
        }

    }
}
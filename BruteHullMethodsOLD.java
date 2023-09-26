package JavaLearningConvexHull;

import java.util.ArrayList;
import java.awt.Point;

public class BruteHullMethodsOLD {
    public static ArrayList<Point> convexHullAssembler(ArrayList<Point> list){
        ArrayList<Point> finalConvexHull = new ArrayList<Point>();

        for(int i = 0; i < list.size(); i++){
            for(int j = i+1; j < list.size(); j++){
                double curSlope = 0;
                //Point `i` to Point `j`
                if(list.get(i).getX() < list.get(j).getX()){
                    curSlope = (list.get(j).getY()-list.get(i).getY())/(list.get(j).getX()-list.get(i).getX());
                    //Positive slope from `i` to `j`
                    if(curSlope > 0){
                        ArrayList<Point> sL = new ArrayList<Point>();
                        ArrayList<Point> sR = new ArrayList<Point>();
                        // System.out.println("THIS IS THE CURSLOPE: " + curSlope);
                        for(int c = 0; c < list.size(); c++){
                            //Check if `c` is on the right of `i`
                            // if(list.get(i).getX() < list.get(c).getX()){
                            //     if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) > curSlope){
                            //         sL.add(list.get(c));
                            //         System.out.println("LEFT SIDE: " + list.get(c));
                            //     }
                            //     else if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) <= curSlope){
                            //         sR.add(list.get(c));
                            //         System.out.println("RIGHT SIDE: " + list.get(c));
                            //     }
                            // }
                            // //Check if `c` is on the left of `i`
                            // else if(list.get(i).getX() > list.get(c).getX()){
                            //     if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) > curSlope){
                            //         System.out.println(list.get(i).getY()-list.get(c).getY()/(list.get(i).getX()-list.get(c).getX()));
                            //         sL.add(list.get(c));
                            //         System.out.println("LEFT SIDE: " + list.get(c));
                            //     }
                            //     else if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) <= curSlope){
                            //         System.out.println(list.get(i).getY()-list.get(c).getY()/(list.get(i).getX()-list.get(c).getX()));
                            //         sR.add(list.get(c));
                            //         System.out.println("RIGHT SIDE: " + list.get(c));
                            //     }
                            // }
                            if(list.get(c).getX() < list.get(i).getX()){
                                if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) > curSlope){
                                    sL.add(list.get(c));
                                    System.out.println("(ORANGE) LEFT SIDE: " + list.get(c));
                                }
                                else if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) < curSlope && (list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) > 0){
                                    sR.add(list.get(c));
                                    System.out.println("(BLACK) RIGHT SIDE: " + list.get(c));
                                }
                                else if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) < curSlope && (list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) < 0){
                                    sL.add(list.get(c));
                                    System.out.println("(RED) LEFT SIDE: " + list.get(c));
                                }
                            }
                            else if(list.get(c).getX() > list.get(i).getX()){
                                if(list.get(c).getX() > list.get(j).getX()){
                                    if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) > curSlope){
                                        sL.add(list.get(c));
                                        System.out.println("(BLUE) LEFT SIDE: " + list.get(c));
                                    }
                                    else if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) < curSlope && (list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) > 0){
                                        sR.add(list.get(c));
                                        System.out.println("(PURPLE) RIGHT SIDE: " + list.get(c));
                                    }
                                    else if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) < curSlope && (list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) < 0){
                                        sR.add(list.get(c));
                                        System.out.println("(PINK) RIGHT SIDE: " + list.get(c));
                                    }
                                }
                                else if(list.get(c).getX() < list.get(j).getX()){
                                    if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) < curSlope){
                                        sR.add(list.get(c));
                                        System.out.println("(GREY) RIGHT SIDE: " + list.get(c));
                                    }
                                    else if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) > curSlope){
                                        sL.add(list.get(c));
                                        System.out.println("(YELLOW) LEFT SIDE: " + list.get(c));
                                    }
                                }
                            }
                        }
                        if(sL.isEmpty() == true && sR.isEmpty() == false){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else if(sL.isEmpty() == false && sR.isEmpty() == true){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else{
                            System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                        }
                    }
                    //Negative slope from `i` to `j`
                    else if(curSlope < 0){
                        ArrayList<Point> sL = new ArrayList<Point>();
                        ArrayList<Point> sR = new ArrayList<Point>();
                        for(int c = 0; c < list.size(); c++){
                            //Check if `c` is on the right of `i`
                            if(list.get(i).getX() < list.get(c).getX()){
                                if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) < curSlope){
                                    sR.add(list.get(c));
                                    System.out.println("(RIGHT) LEFT SIDE: " + list.get(c));
                                }
                                else if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) >= curSlope){
                                    sL.add(list.get(c));
                                    System.out.println("(LEFT) RIGHT SIDE: " + list.get(c));
                                }
                            }
                            //Check if `c` is on the left of `i`
                            else if(list.get(i).getX() > list.get(c).getX())
                            {
                                if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) < curSlope){
                                    sR.add(list.get(c));
                                    System.out.println("(RIGHT) LEFT SIDE: " + list.get(c));
                                }
                                else if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) >= curSlope){
                                    sL.add(list.get(c));
                                    System.out.println("(LEFT) RIGHT SIDE: " + list.get(c));
                                }
                            }
                        }
                        if(sL.isEmpty() == true && sR.isEmpty() == false){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else if(sL.isEmpty() == false && sR.isEmpty() == true){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else{
                            System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                        }
                    }
                    else if(curSlope == 0){
                        ArrayList<Point> sL = new ArrayList<Point>();
                        ArrayList<Point> sR = new ArrayList<Point>();
                        for(int c = 0; c < list.size(); c++){
                            //Check if `c` is on the left or right of `i`
                            if(list.get(i).getX() < list.get(c).getX()){
                                if((list.get(c).getY()-list.get(i).getY())/(list.get(c).getX()-list.get(i).getX()) >= 0){
                                    sL.add(list.get(c));
                                    System.out.println("LEFT SIDE (above): " + list.get(c));
                                }
                                else{
                                    sR.add(list.get(c));
                                    System.out.println("RIGHT SIDE (below): " + list.get(c));
                                }
                            }
                            //Check if `c` is on the left or right of `i`
                            if(list.get(i).getX() > list.get(c).getX()){
                                if((list.get(i).getY()-list.get(c).getY())/(list.get(i).getX()-list.get(c).getX()) >= 0){
                                    sL.add(list.get(c));
                                    System.out.println("LEFT SIDE (above): " + list.get(c));
                                }
                                else{
                                    sR.add(list.get(c));
                                    System.out.println("RIGHT SIDE (below): " + list.get(c));
                                }
                            }
                        }
                        if(sL.isEmpty() == true && sR.isEmpty() == false){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else if(sL.isEmpty() == false && sR.isEmpty() == true){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else{
                            System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                        }
                    }
                }

                //Point `j` to Point `i`
                else if(list.get(i).getX() > list.get(j).getX()){
                    curSlope = (list.get(i).getY()-list.get(j).getY())/(list.get(i).getX()-list.get(j).getX());
                    //Positive slope from `j` to `i`
                    if(curSlope > 0){
                        ArrayList<Point> sL = new ArrayList<Point>();
                        ArrayList<Point> sR = new ArrayList<Point>();
                        for(int c = 0; c < list.size(); c++){
                            //Check if `c` is on the right of `j`
                            // if(list.get(j).getX() < list.get(c).getX()){
                            //     if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) > curSlope){
                            //         sL.add(list.get(c));
                            //         System.out.println("LEFT SIDE: " + list.get(c));
                            //     }
                            //     else if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) <= curSlope){
                            //         sR.add(list.get(c));
                            //         System.out.println("RIGHT SIDE: " + list.get(c));
                            //     }
                            // }
                            // //Check if `c` is on the left of `j`
                            // else if(list.get(j).getX() > list.get(c).getX()){
                            //     if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) > curSlope){
                            //         sL.add(list.get(c));
                            //         System.out.println("LEFT SIDE: " + list.get(c));
                            //     }
                            //     else if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) <= curSlope){
                            //         sR.add(list.get(c));
                            //         System.out.println("RIGHT SIDE: " + list.get(c));
                            //     }
                            // }
                            if(list.get(c).getX() < list.get(j).getX()){
                                if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) > curSlope){
                                    sL.add(list.get(c));
                                    System.out.println("(ORANGE) LEFT SIDE: " + list.get(c));
                                }
                                else if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) < curSlope && (list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) > 0){
                                    sR.add(list.get(c));
                                    System.out.println("(BLACK) RIGHT SIDE: " + list.get(c));
                                }
                                else if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) < curSlope && (list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) < 0){
                                    sL.add(list.get(c));
                                    System.out.println("(RED) LEFT SIDE: " + list.get(c));
                                }
                            }
                            else if(list.get(c).getX() > list.get(j).getX()){
                                if(list.get(c).getX() > list.get(i).getX()){
                                    if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) > curSlope){
                                        sL.add(list.get(c));
                                        System.out.println("(BLUE) LEFT SIDE: " + list.get(c));
                                    }
                                    else if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) < curSlope && (list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) > 0){
                                        sR.add(list.get(c));
                                        System.out.println("(PURPLE) RIGHT SIDE: " + list.get(c));
                                    }
                                    else if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) < curSlope && (list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) < 0){
                                        sR.add(list.get(c));
                                        System.out.println("(PINK) RIGHT SIDE: " + list.get(c));
                                    }
                                }
                                else if(list.get(c).getX() < list.get(i).getX()){
                                    if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) < curSlope){
                                        sR.add(list.get(c));
                                        System.out.println("(GREY) RIGHT SIDE: " + list.get(c));
                                    }
                                    else if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) > curSlope){
                                        sL.add(list.get(c));
                                        System.out.println("(YELLOW) LEFT SIDE: " + list.get(c));
                                    }
                                }
                            }
                        }
                        if(sL.isEmpty() == true && sR.isEmpty() == false){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else if(sL.isEmpty() == false && sR.isEmpty() == true){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else{
                            System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                        }
                    }
                    //Negative slope from `j` to `i`
                    else if(curSlope < 0){
                        ArrayList<Point> sL = new ArrayList<Point>();
                        ArrayList<Point> sR = new ArrayList<Point>();
                        for(int c = 0; c < list.size(); c++){
                            //Check if `c` is on the right of `j`
                            if(list.get(j).getX() < list.get(c).getX()){
                                if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) < curSlope){
                                    sR.add(list.get(c));
                                    System.out.println("(RIGHT) LEFT SIDE: " + list.get(c));
                                }
                                else if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) >= curSlope){
                                    sL.add(list.get(c));
                                    System.out.println("(LEFT) RIGHT SIDE: " + list.get(c));
                                }
                            }
                            //Check if `c` is on the left of `j`
                            else if(list.get(j).getX() > list.get(c).getX())
                            {
                                if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) < curSlope){
                                    sR.add(list.get(c));
                                    System.out.println("(RIGHT) LEFT SIDE: " + list.get(c));
                                }
                                else if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) >= curSlope){
                                    sL.add(list.get(c));
                                    System.out.println("(LEFT) RIGHT SIDE: " + list.get(c));
                                }
                            }
                        }
                        if(sL.isEmpty() == true && sR.isEmpty() == false){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else if(sL.isEmpty() == false && sR.isEmpty() == true){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else{
                            System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                        }
                    }
                    else if(curSlope == 0){
                        ArrayList<Point> sL = new ArrayList<Point>();
                        ArrayList<Point> sR = new ArrayList<Point>();
                        for(int c = 0; c < list.size(); c++){
                             //Check if `c` is on the left or right of `j`
                             if(list.get(j).getX() < list.get(c).getX()){
                                if((list.get(c).getY()-list.get(j).getY())/(list.get(c).getX()-list.get(j).getX()) >= 0){
                                    sL.add(list.get(c));
                                    System.out.println("LEFT SIDE (above): " + list.get(c));
                                }
                                else{
                                    sR.add(list.get(c));
                                    System.out.println("RIGHT SIDE (below): " + list.get(c));
                                }
                            }
                            //Check if `c` is on the left or right of `j`
                            if(list.get(j).getX() > list.get(c).getX()){
                                if((list.get(j).getY()-list.get(c).getY())/(list.get(j).getX()-list.get(c).getX()) >= 0){
                                    sL.add(list.get(c));
                                    System.out.println("LEFT SIDE (above): " + list.get(c));
                                }
                                else{
                                    sR.add(list.get(c));
                                    System.out.println("RIGHT SIDE (below): " + list.get(c));
                                }
                            }
                        }
                        if(sL.isEmpty() == true && sR.isEmpty() == false){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else if(sL.isEmpty() == false && sR.isEmpty() == true){
                            finalConvexHull.add(list.get(i));
                            finalConvexHull.add(list.get(j));
                            System.out.println("[SUCCSESS] LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS PART OF THE CONVEX-HULL");
                        }
                        else{
                            System.out.println("LINE SEGMENT MADE BY: " + list.get(i) + " AND " + list.get(j) + " IS NOT PART OF THE CONVEX-HULL");
                        }
                    }
                }
            }
        }
        return finalConvexHull;
    }
}

package JavaLearningConvexHull;

import java.util.ArrayList;
// import java.awt.Point;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JavaFXQuickHullMain extends JPanel{
    public static void main(String[] args){//NOTE: Just a quick reminder that the array ->
        //-> type symbol `[]` can either be next to `String` or after the instance ->
        //-> (reference/pointer variable) `args` (although the brackets identify the array type ->
        //-> and should appear with the type designation `String`)
        JFrame frame = new JFrame("Convex Hull");
        frame.getContentPane().add(new JavaFXQuickHullMain());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLocationRelativeTo(null);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2));
        g2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2D.translate(500, 100);

        //Constructing a new `Random` instance (instance/pointer variable) `rand`
        // Random rand = new Random();

        //NOTE: the static method `Math.floor()` returns the value rounded down which is why ->
        //-> we must include the `+1` in `(50-10+1)` because the static method ->
        //-> `Math.random()` returns a double between 0.0 and < 1.0 so at max we get ->
        //-> 0.9999... which means 0.9999... * 51 would give a double something like ->
        //-> 50.9999 but since the static method `Math.floor()` returns the rounded down ->
        //-> value, we would get 50 instead of something like 51 (which I originally thought ->
        //-> because I initially thought `Math.floor()` rounded up)
        int numPoints = (int)Math.floor(Math.random()*(10000-10+1)+10);
        System.out.println(numPoints);

        // //Im going to use an `ArrayList` datastructure as my list type because is is much ->
        // //-> faster than `LinkedList` when it comes to accessing with a specific index ->
        // //-> (althrough `ArrayList` is slower at insertion/deletion, we wont be doing much ->
        // //-> inserting/deleting so that is perfectly fine)
        // //(I will look into making my own `ArrayList` class later but I want to dive into ->
        // //-> the convex hull problem first)
        // //NOTE: I honestly could just use an array for this part since the `ArrayList`'s size ->
        // //-> does not change but I wanted to toy around with `ArrayList`s for practice
        ArrayList<Point> list = new ArrayList<Point>(numPoints);

        for(int i = 0; i < numPoints; i++){
            Point curPoint = new Point((int)Math.floor(Math.random()*(10000-0+1)+0), (int)Math.floor(Math.random()*(10000-0+1)+0));
            list.add(curPoint);
        }

        //IMPORTANT NOTE: If you would like to use my hard-coded points make sure you ->
        //-> comment out lines 43 -> 59 and change the division by `25` to multiply by ->
        //-> `50` on lines 118 -> 165 or else the convex hull will be unbearably small
        //AMAZING NOTE: Its so intriguing to actually be able to see the Quick-Hull ->
        //-> algorithm actually make the program run so so SO much faster than ->
        //-> Brute-Force Hull (its very gratifying to see why Quick-Hull is a necessity ->
        //-> even when just painting lines on a canvas)
        //-> For example running `10000` points on Brute-Force Hull would take ages but ->
        //-> `100000` points does not even make Quick-Hull break a sweat (could possibly ->
        //-> even run faster if I commented out all the output lines)
        //CURIOUS NOTE: Its curious to see that the more points I add, the convex hull seems ->
        //-> to become more rectangular

        // ArrayList<Point> list = new ArrayList<Point>();
    
        //     Point point1 = new Point(6,19);
        //     Point point2 = new Point(16,43);
        //     Point point3 = new Point(32,7);
        //     Point point4 = new Point(15,3);
        //     Point point5 = new Point(11,42);
        //     Point point6 = new Point(11,38);
        //     Point point7 = new Point(5,39);
        //     Point point8 = new Point(29,39);
        //     Point point9 = new Point(31,36);
        //     Point point10 = new Point(44,15);
        //     Point point11 = new Point(24,9);
        //     Point point12 = new Point(1,8);
        //     Point point13 = new Point(43,27);
        //     Point point14 = new Point(0,31);
        //     Point point15 = new Point(48,7);
    
        //     list.add(point1);
        //     list.add(point2);
        //     list.add(point3);
        //     list.add(point4);
        //     list.add(point5);
        //     list.add(point6);
        //     list.add(point7);
        //     list.add(point8);
        //     list.add(point9);
        //     list.add(point10);
        //     list.add(point11);
        //     list.add(point12);
        //     list.add(point13);
        //     list.add(point14);
        //     list.add(point15);
    
        // ConvexHullMethods.findExtrema(list);
    
        ArrayList<Point> finalConvexHull = QuickHullMethods.convexHullAssembler(list);

        System.out.println("Points that make up the Convex-Hull:");
        for(int i = 0; i < finalConvexHull.size(); i++){
            System.out.print(" " + finalConvexHull.get(i));
        }

        for(int i = 0; i < list.size(); i++){
            g2D.drawOval((int)list.get(i).getX()/25, (int)list.get(i).getY()/25, 4, 4);
        }

        //I ran into a wall when trying to paint the lines between the convex hull for ->
        //-> Quick-Hull since the `finalConvexHull` was filled only with points not the ->
        //-> psuedo-set of points like in Brute-Force hull so to combat this I set up ->
        //-> some intricate `if-statements` to jump between points and paint lines to the ->
        //-> correct corresponding point depending on if it was above or below the slope ->
        //-> from `min` to `max` and if it was the first or last point to be explored on ->
        //-> their respective sides
        Point aboveSlope = new Point(-1,-1);
        Point belowSlope = new Point(-2,-2);
        for(int i = 0; i < finalConvexHull.size();){
            //Runs for connecting the `min` to the first point in `finalConvexHull` `ArrayList`
            if(i == 0){
                //NOTE: `Graphics.drawLine(i,i,i,i)` is easier to understand with the arguments ->
                //-> shown as `Graphics.drawLine(x1,y1,x2,y2)`
                g2D.drawLine((int)finalConvexHull.get(i).getX()/25, (int)finalConvexHull.get(i).getY()/25, (int)finalConvexHull.get(i+2).getX()/25, (int)finalConvexHull.get(i+2).getY()/25);
                i++;
            }
            //Runs if the point lies below the slope from `min` to `max`
            else if(finalConvexHull.get(i).equals(belowSlope)){
                //Runs if this is the first to second to last point explored below ->
                //-> the slope from `min` to `max`
                if(finalConvexHull.get(i+2).equals(belowSlope)){
                    g2D.drawLine((int)finalConvexHull.get(i+1).getX()/25, (int)finalConvexHull.get(i+1).getY()/25, (int)finalConvexHull.get(i+3).getX()/25, (int)finalConvexHull.get(i+3).getY()/25);
                    i = i+2;
                }
                //Runs if this is the last point to be explored below the slope from ->
                //-> `min` to `max` (paints a line from `min` to the point)
                else{
                    g2D.drawLine((int)finalConvexHull.get(i+1).getX()/25, (int)finalConvexHull.get(i+1).getY()/25, (int)finalConvexHull.get(finalConvexHull.size()-1).getX()/25, (int)finalConvexHull.get(finalConvexHull.size()-1).getY()/25);
                    g2D.drawLine((int)finalConvexHull.get(0).getX()/25, (int)finalConvexHull.get(0).getY()/25, (int)finalConvexHull.get(i+3).getX()/25, (int)finalConvexHull.get(i+3).getY()/25);
                    i = i+2;
                }
            }
            //Runs if the point lies above the slope from `min` to `max`
            else if(finalConvexHull.get(i).equals(aboveSlope)){
                //Runs if this is the first to second to last point explored above the ->
                //-> slope from `min` to `max`
                if(finalConvexHull.get(i+2).equals(aboveSlope)){
                    g2D.drawLine((int)finalConvexHull.get(i+1).getX()/25, (int)finalConvexHull.get(i+1).getY()/25, (int)finalConvexHull.get(i+3).getX()/25, (int)finalConvexHull.get(i+3).getY()/25);
                    i = i+2;
                }
                //Runs if this is the last point to be explored above the slope from ->
                //-> `min` to `max` (makes the point paint a line to the `max`)
                else{
                    g2D.drawLine((int)finalConvexHull.get(i+1).getX()/25, (int)finalConvexHull.get(i+1).getY()/25, (int)finalConvexHull.get(i+2).getX()/25, (int)finalConvexHull.get(i+2).getY()/25);
                    i = i+3;
                }
            }

        }
         //The translation below is to set the orgin of the canvas back to the top left ->
        //-> corner because if I did not do this the canvas would forever be translated
        g2D.translate(-500, -100);
    }   
}
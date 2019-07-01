/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {
    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
//        throw new RuntimeException("implement me!");
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double degree = (sides - 2)*180/(double)sides;
        return degree;
//    	throw new RuntimeException("implement me!");
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int sides = (int) (360/(180 - angle));
        if(360%(180 - angle) != 0)//如果不能整除，加1为正多边形边数
		{
        	sides++;
		}
        return sides;
//    	throw new RuntimeException("implement me!");
    }
    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    //假设从右上角出发
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	int i;
    	double indegree = (sides - 2)*180/sides;
    	double outdegree = 180 - indegree;
    	for(i=0;i<sides;i++)
    	{
    		turtle.turn(outdegree);
    		turtle.forward(sideLength);
    	}
//        throw new RuntimeException("implement me!");
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	double angle,X1,Y1;
		X1 = targetX - currentX;
		Y1 = targetY - currentY;
    	if(targetY == currentY)//角度为90
    	{
    		angle = 90;
    	}
    	else {
    		 angle = Math.atan2(X1,Y1) * 180.0/Math.PI;
		}
    	if(angle - currentBearing < 0)
    	{
    		angle += 360;
    	}
        return (angle - currentBearing);
//    	throw new RuntimeException("implement me!");
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	Integer tempx,tempy,i;
    	double current_angle = 0;
    	List<Double> angle = new ArrayList<>();
    	if(xCoords.size() <2 || yCoords.size() <2)//如果长度为0或1返回0集合
    	{
    		angle.add((double) 0);
    		return angle;
    	}
    	//数组的第一个点为起点
    	tempx = xCoords.get(0);
    	tempy = yCoords.get(0);
    	//获取长度为size - 1的angle数组
    	for(i=1;i<xCoords.size();i++)
    	{
    		current_angle = calculateBearingToPoint(current_angle,tempx,tempy,xCoords.get(i),yCoords.get(i));
    		tempx = xCoords.get(i);
        	tempy = yCoords.get(i);
    		angle.add(current_angle);
    	}
    	return angle;
    }
	/**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	Set<Point> hull = new HashSet<Point>();
    	if(points.size() < 1)
    	{
    		return hull;
    	}
    	//寻找最左点
    	int l = 0,n = points.size(); 
    	double min_angle= 360.0,temp;
    	List<Point> result = new ArrayList<>(points);
    	Point[] objx=new Point[result.size()];
		for(int i=0;i<result.size();i++)
		{
			objx[i]=result.get(i);
		}
        for (int i = 1; i < points.size(); i++) 
            if (objx[i].x< objx[l].x) 
                l = i; 
        //从最左侧点开始，顺时针方向继续移动，直到再次到达起点
        int p = l; 
        do
    	{ 
    		// 把当前点添加到hull中
    		hull.add(objx[p]); 
			for (int i = 0; i < n; i++) {
				// 判断是否在hull中
				if (!hull.contains(objx[i])) {
					temp = calculateBearingToPoint(0.0, (int) objx[p].x, (int) objx[p].y, (int) objx[i].x,
							(int) objx[i].y);
					//求更顺时针的点，即从起点到达目标点偏离度数最小
					if (temp < min_angle) {
						min_angle = temp;
						p = i;
					}
				}
			}
    	} while (p != l);  // 当没有至最左点，继续循环
    	//返回结果
    	return hull;
//        throw new RuntimeException("implement me!");
}
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    //画一个等边三角形
    public static void drawPersonalArt(Turtle turtle) {
//        throw new RuntimeException("implement me!");
    	int i,sideLength = 50,sides = 6;
    	double indegree = (sides - 2)*180/sides;//120
    	double outdegree = 180 - indegree;//60
    	PenColor c = PenColor.RED;
    	turtle.color(c);
    	for(i=0;i<sides;i++)
    	{
    		turtle.turn(outdegree);
    		turtle.forward(sideLength);
    	}
    	turtle.turn(outdegree);
    	for(i=0;i<sides;i++)
    	{
    		turtle.turn(outdegree);
    		turtle.forward(sideLength);
    	}
    	turtle.turn(180 + outdegree);
    	for(i=0;i<sides;i++)
    	{
    		turtle.turn(outdegree);
    		turtle.forward(sideLength);
    	}
    	turtle.turn(indegree + 90);//210
    	turtle.forward((int)(sideLength*1.732));
		turtle.turn(360 - outdegree/2);//330
		turtle.forward(sideLength);
		turtle.turn(outdegree-indegree);//60
		turtle.forward(sideLength);
		turtle.turn(360 - outdegree);//300
		turtle.forward(sideLength);
		turtle.turn(outdegree-indegree);//60
		turtle.forward(sideLength);
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
//        drawSquare(turtle, 40);
//        drawRegularPolygon(turtle,6,100);
        // draw the window
        drawPersonalArt(turtle);
        turtle.draw();
    }

}

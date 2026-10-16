package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Pose;

public class Rectangle {
    Pose p1;
    Pose p2;
    Pose p3;
    Pose p4;
    public Rectangle(Pose p1, Pose p2, Pose p3, Pose p4){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }
    /**
     * gets the lowest X value of the rectangle
     * @return double X
     */
    public double getLowestX(){
        return Math.min(Math.min(p1.x(),p2.x()), Math.min(p3.x(), p4.x()));
    }
    /**
     * gets the highest X value of the rectangle
     * @return double X
     */
    public double getHighestX(){
        return Math.max(Math.max(p1.x(),p2.x()), Math.max(p3.x(), p4.x()));

    }
    /**
     * gets the lowest Y value of the rectangle
     * @return double Y
     */
    public double getLowestY(){
        return Math.min(Math.min(p1.y(),p2.y()), Math.min(p3.y(), p4.y()));

    }

    /**
     * gets the highest Y value of the rectangle
     * @return double Y
     */
    public double getHighestY(){
        return Math.max(Math.max(p1.y(),p2.y()), Math.max(p3.y(), p4.y()));
    }

    /**
     * checks if a given point is within a rectangle
     * @param point the point to check
     * @return is a boolean if the point is within the rectangle
     */
    public boolean pointIsWithinRectangle(Pose point){
        return point.x() > getLowestX() && point.x() < getHighestX() &&
                point.y() > getLowestY() && point.y() < getHighestY();
    }
    
}

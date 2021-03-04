/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.effect.Reflection;

/**
 *
 * @author a3yko
 */
public class asa368AdrianViz implements Visualizer {

     private final String name = "asa368AdrianViz";
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minEllipseRadius = 10.0;  // 10.0
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private final Double startHue = 260.0;
    
    private Circle[] circles;
    private Reflection reflect;
    
    
    public asa368AdrianViz() {
    }
     @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        reflect = new Reflection();
        reflect.setFraction(.81);
        this.numOfBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
 
        circles = new Circle[numBands];
      
        for (int i = 0; i < numBands; i++) {
            Circle circle = new Circle();
            circle.setCenterX(bandWidth / 2 + bandWidth * i);
            circle.setCenterY(height / 2);
            circle.setScaleX(bandWidth / 20);
            circle.setRadius(minEllipseRadius);
            circle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            circle.setEffect(reflect);
            vizPane.getChildren().add(circle);
            circles[i] = circle;
        }

    }
    
    @Override
    public void end() {
         if (circles != null) {
             for (Circle circle : circles) {
                 vizPane.getChildren().remove(circle);
             }
            circles = null;
        } 
    }
    
    @Override
    public void draw(double timestamp, double lenght, float[] magnitudes, float[] phases) {
        if (circles == null) {
            return;
        }
        Integer num = min(circles.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) { 
            circles[i].setRadius( ((69 + magnitudes[i])/120) * halfBandHeight + minEllipseRadius);
            circles[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
        }
    }
}

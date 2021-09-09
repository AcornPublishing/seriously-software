package eis.chapter9.generic.plot;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Dimension;

/**
 * A panel which plots a PFunction.
 *
 * @author Marco Faella
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PlotPanel extends JPanel implements ActionListener {

    /**
     * Create a panel that can display the provided function.
     * 
     * @param f
     *            the function to plot
     */
    public PlotPanel(ObservableFunction f) {
        f.addActionListener(this);
        this.f = PFunctionDecorators.scaleRange(f, xMin, xMax);

        Border b = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        TitledBorder tb = 
                BorderFactory.createTitledBorder(b, 
                        "Graph of the function",
                        TitledBorder.LEFT, TitledBorder.TOP);
        tb.setTitleColor(Color.black);
        tb.setTitleFont(Main.standardFont);
        setBorder(tb);
        setPreferredSize(new Dimension(300, 200));
        repaint();
    }

    private ParametricFunction f;
    private final static int nPoint = 101;
    /* the range that will be plotted */
    private final static double xMin = -5, xMax = 5, yMin = -5, yMax = 5;
    /* these are fields (rather than local variables)
     * to avoid the overhead of multiple allocations
     */
    private int[] x = new int[nPoint];
    private int[] y = new int[nPoint];

    /**
     * Respond to a change in the observed function model
     */
    @Override
    public void actionPerformed(ActionEvent _event) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint the background
        
        final int xpadding = 6, ypadding = 8;
        final int width = getWidth() - 2*xpadding,
                  height = getHeight() - 2*ypadding;
        
        Shape oldClip = g.getClip();
        g.clipRect(xpadding, ypadding, width, height);
        
        // Axes
        g.drawLine(0, ypadding + height/2, width + xpadding, ypadding + height/2);
        g.drawLine(xpadding + width/2, 0, xpadding + width/2, height + ypadding);
        
        // compute screen coordinates
        for (int i=0; i < nPoint; i++) {
            double t = ((double) i) / (nPoint - 1);
            double val = f.eval(t);
            x[i] = xpadding + (int) ((i / (double)(nPoint-1)) * width);
            y[i] = ypadding + (int) (height * (1- (val-yMin) / (double) (yMax - yMin)) );
        }
        Color oldColor = g.getColor();
        g.setColor(Color.RED);
        g.drawPolyline(x, y, nPoint);
        g.setColor(oldColor);
        g.setClip(oldClip);
    }
}

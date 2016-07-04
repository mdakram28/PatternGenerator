package pattern;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.io.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Pattern extends JFrame {
    JButton run = new JButton("RUN") , si = new JButton("  +  ") , sd = new JButton("  -  "), ran = new JButton("ANYTHING"), res = new JButton("RESET"), p_but = new JButton("PAUSE") , set_but = new JButton("SET");
    JTextField in_R = new JTextField(5) , in_r = new JTextField(5) , in_r2 = new JTextField(5) , len = new JTextField("10000",5);
    int i=0 , temp_speed = 5 , thread_length = 10000 , acc = 1 , max_speed = 100 , min_speed = 10;
    JSlider s_tR = new JSlider() , s_tr = new JSlider() , s_tr2 = new JSlider() , speed_bar = new JSlider() , scale = new JSlider();
    JCheckBox smooth = new JCheckBox();
    double tR=300 , tr=127 , tr2=100 , d=1000.0 , speed=10;
    double offset = d/7.0 ;
    static double scale_factor = 1;

    boolean animate = true;
    double temp_tR;
    double temp_tr;
    double temp_tr2;
    public static void main(String args[]){
        new Pattern();
        
    }
    public Pattern()
    {
        final DrawPanel panel = new DrawPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.setMaximumSize(new Dimension(200,GetScreenWorkingHeight()));
        
        panel1.setBorder(new EmptyBorder(new Insets(40, 20, 40, 0)));
        s_tR.setValue(400);
        s_tr.setValue(400);
        s_tr2.setValue(400);
        JPanel p2 = new JPanel(new FlowLayout()) , p3 = new JPanel(new FlowLayout()) , p4 = new JPanel(new FlowLayout()) , p5 = new JPanel(new FlowLayout());
        p2.add(in_R);
        p2.add(s_tR);
        p3.add(in_r);
        p3.add(s_tr);
        p4.add(in_r2);
        p4.add(s_tr2);
        panel1.add(p2);
        panel1.add(p3);
        panel1.add(p4);
        panel1.add(scale);
        scale.setValue(10);
        
        //panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        panel1.add(run);
        
        panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        //panel1.add(si);
        
        //panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        //panel1.add(sd);
        
        panel1.add(speed_bar);
        panel1.add(smooth);
        panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        panel1.add(ran);
        
        panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        panel1.add(res);
        
        //panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        panel1.add(p_but);
        
        panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        p5.add(len);
        panel1.add(p5);
        
        //panel1.add(Box.createRigidArea(new Dimension(0, 50)));
        panel1.add(set_but);
        
        
        add(panel1,BorderLayout.WEST);
        add(panel);
        
        s_tR.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent ce){
              if(!animate)
              {
                tR = s_tR.getValue()*8;
                in_R.setText(tR+"");
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
              }
            }
        });
        
        s_tr.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent ce){
              if(!animate)
              {
                tr = s_tr.getValue()*8;
                in_r.setText(tr+"");
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
              }
            }
        });
        
        s_tr2.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent ce){
              if(!animate)
              {
                tr2 = s_tr2.getValue()*8;
                in_r2.setText(tr2+"");
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
              }
            }
        });
        speed_bar.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent ce){
              speed = speed_bar.getValue()/2;
            }
        });
        scale.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent ce){
              scale_factor = scale.getValue()/5.0;
              len.setText(scale_factor+"");
              
              
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
            }
        });
        run.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) {
              if(!animate)
              {
                tR = Integer.parseInt(in_R.getText());
                tr = Integer.parseInt(in_r.getText());
                tr2 = Integer.parseInt(in_r2.getText());
                
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
              }
            } 
        } );
        set_but.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) { 
                thread_length = Integer.parseInt(len.getText());
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
            } 
        } );
        p_but.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) { 
                if(animate)
                {
                    p_but.setText("PLAY");
                    animate = false;
                }
                else
                {
                    animate = true;
                    p_but.setText("PAUSE");
                }
            } 
        } );
        ran.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) { 
                if(!animate)
                {
                tR = ((int)(Math.random()*40000))/100.0;
                tr = ((int)(Math.random()*40000))/100.0;
                tr2 = ((int)(Math.random()*40000))/100.0;
                
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
                
                in_R.setText(tR+"");
                in_r.setText(tr+"");
                in_r2.setText(tr2+"");
                
                }
            } 
        } );
        si.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) { 
                speed++;
            } 
        } );
        sd.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) { 
                speed--;
            } 
        } );
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(220, 150);
        setVisible(true);
        centre1_x = GetScreenWorkingWidth()/2 -200;
        centre1_y = GetScreenWorkingHeight()/2 ;
        
//        for(i = 0;;i+= d)
//        {
//            int[] p = prepare_graphData(tR , tr , tr2 , i);
//            panel.update(p);
//        }
        
        while(true)
        {
            int tR_prev = (int)tR;
            int tr_prev = (int)tr;
            int tr2_prev = (int)tr2;
            int tR_new = (int)(Math.random()*500);
            int tr_new = (int)(Math.random()*500);
            int tr2_new = (int)(Math.random()*500);
            
            double d1 = (tR_new-tR_prev)/d;
            double d2 = (tr_new-tr_prev)/d;
            double d3 = (tr2_new-tr2_prev)/d;
            double step = 1 ;
            if(smooth.isSelected())speed = min_speed;
            int diff = tR_new - tR_prev ;
            double acc = ((max_speed - min_speed)/offset);
            while(step<=d)
            {
                if(!animate)continue;
                tR =  tR_prev + (d1 * step);
                tr =  tr_prev + (d2 * step);
                tr2 = tr2_prev + (d3 * step);
                
                s_tR.setValue((int)tR/8);
                s_tr.setValue((int)tr/8);
                s_tr2.setValue((int)tr2/8);
                
                in_R.setText(tR+"");
                in_r.setText(tr+"");
                in_r2.setText(tr2+"");
                int[] p = prepareGraphData(tR , tr , tr2 , thread_length );
                panel.update(p);
                
                try {
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                }
                if(smooth.isSelected())
                {
                    if(step<=offset && speed <= max_speed)
                    {
                        speed = min_speed + acc*step ;
                    }
                    else if(step>=(d-offset) && speed >= min_speed)
                    {
                        speed = max_speed - acc*(step - d + offset) ;
                    }
                    speed_bar.disable();
                }
                else
                {
                    speed_bar.enable();
                }
                speed_bar.setValue((int)speed*2);
                
                step += speed/10.0;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    static int centre1_x = 0 , centre1_y = 0 ;
    public static int[] prepareGraphData(double R , double r , double r2 , int max_d ) {
        int d=0  ,i=0;
        double f = scale_factor;
        R = scale_factor*R;
        r = scale_factor*r;
        r2 = scale_factor*r2;
        
        int[] ret = new int[max_d/5];
        for(;d<max_d;d+=10)
        {
            double angle1 = d/R , angle2 = d/r;
            
            int x = (int) (centre1_x + (R-r)*Math.cos(angle1) + r2*Math.cos(angle2));
            int y = (int) (centre1_y - (R-r)*Math.sin(angle1) + r2*Math.sin(angle2));
            
            ret[i] = x;
            i++;
            ret[i] = y;
            i++;
        }
        //ret = convert(coord);
        return ret;
    }
    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }
    public static int[] convert(ArrayList<Integer> a)
    {
        int l = a.size();
        int ret[] = new int[l];
        for(int i = 0 ; i<l ;i++ )
        {
            ret[i] = (int)a.get(i);
        }
        return ret;
    }

    
}
class DrawPanel extends JPanel{

int[] pos = {};
public void update(int[] p)
{
    pos = p;
    repaint();
}
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.red);
        int l =(int) (pos.length/2)-1;
        for(int i = 0;i<l;i++)
        {
            g.drawLine(pos[i*2], pos[i*2+1], pos[i*2+2], pos[i*2+3]);
        }
    }
    
}
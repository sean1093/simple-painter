import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;




public class PainterFrame extends JFrame
{
    private JButton button1,button2,button3;
    private JLabel label,label2,label3;
    private JComboBox combobox;
    private String name[] = {"筆刷","直線","橢圓形","矩形","圓角矩形"};
    private JRadioButton SuperBigButton,BigButton,MidButton,SmallButton;
    private ButtonGroup radioGroup;
	private JCheckBox check;
	private paintpanel panel1;
	private JPanel panel2;
	private Color Backcolor,fontcolor;
	int Index,x1,x2,y1,y2,size=4,lastone=0;
	public BufferedImage buffImage;
	
	
	
	
    
	 public PainterFrame()
     {
    	super("小畫家");
    	setLayout(new BorderLayout());
    	
    	panel1 = new paintpanel();
    	Backcolor=Color.WHITE;
    	fontcolor=Color.BLACK;
        panel2 = new JPanel();
    	panel2.setLayout(new GridLayout( 11,1 ));//Panel2的版面配置
    	
    	label = new JLabel("滑鼠位置");
    	label2 = new JLabel("  [繪圖工具]");
    	label2.setToolTipText("可選擇不同繪圖工具");
    	label3 = new JLabel("  [筆刷大小]");
    	label3.setToolTipText("分為大中小三種筆刷");
    	
    	combobox = new JComboBox(name); 
    	combobox.setMaximumRowCount(3);
    	

    	add(panel1,BorderLayout.CENTER);
        add(panel2,BorderLayout.WEST);
        add(label,BorderLayout.SOUTH);
        
        
        button1 = new JButton("前景色");
        button2 = new JButton("背景色");
        button3 = new JButton("清除畫面");
        
        SuperBigButton = new JRadioButton("巨大",false);
        BigButton = new JRadioButton("大",false);
        MidButton = new JRadioButton("中",false);
        SmallButton= new JRadioButton("小",true);
        
        check = new JCheckBox("填滿"); 
        

        panel2.add(label2);
        panel2.add(combobox);
        panel2.add(label3);
        panel2.add(SuperBigButton);
        panel2.add(BigButton);
        panel2.add(MidButton);
        panel2.add(SmallButton);
        panel2.add(check);
        panel2.add(button1);
        panel2.add(button2);
        panel2.add(button3);
        
        radioGroup = new ButtonGroup();
        radioGroup.add(SuperBigButton);
        radioGroup.add(BigButton);
        radioGroup.add(MidButton);
        radioGroup.add(SmallButton);
        
        
        //下方按鈕事件handler
        ButtonHandler handler = new ButtonHandler();
        button1.addActionListener( handler );
        button2.addActionListener( handler );
        button3.addActionListener( handler );
        
        
        

        ItemHandler JCoboboxhandler = new ItemHandler();
        combobox.addItemListener(JCoboboxhandler);
		
		
		RadioButtonHandler radiohandler = new RadioButtonHandler();
		SuperBigButton.addItemListener(radiohandler);
        BigButton.addItemListener(radiohandler);
        MidButton.addItemListener(radiohandler);
        SmallButton.addItemListener(radiohandler);
		
        

    	
     }
	 
	 
	 private class ButtonHandler implements ActionListener
	 {
		   public void actionPerformed( ActionEvent event)
		   {
			   if (event.getSource() == button1)
	           {fontcolor = JColorChooser.showDialog(PainterFrame.this, "請選擇背景顏色", Backcolor );
               if ( fontcolor == null )
               {fontcolor=Color.BLACK;}}
			   
			   if (event.getSource() == button2)
	           {
				   Backcolor = JColorChooser.showDialog(PainterFrame.this, "請選擇背景顏色", Backcolor );
	               if ( Backcolor == null )
	                   {panel1.setBackground(Color.WHITE);}
	               panel1.setBackground( Backcolor );
	           }
			   
			   if (event.getSource() == button3)
	           {
				   JOptionPane.showMessageDialog(PainterFrame.this,"你點選了：清除畫面");
				   buffImage=new BufferedImage(1500,1500,BufferedImage.TYPE_INT_ARGB_PRE);
				   repaint();
				   }
		   }   
	 
	 }
	 

	 
	 
	 private class ItemHandler implements ItemListener
	 {
		 public void itemStateChanged(ItemEvent event)
		 {
			 if (event.getStateChange() == ItemEvent.SELECTED){
			 Index = combobox.getSelectedIndex() ;
			 System.out.println("aaaa"+Index);
			 }
			 
		 }
	 }
	 
	 
	 private class RadioButtonHandler implements ItemListener
	 {
		 public void itemStateChanged(ItemEvent event) {
				if(event.getSource()==SmallButton){
					size=4;
					label.setText("畫筆大小為:小");
					//last=panel.pointCount;
				}
				if(event.getSource()==MidButton){
					size=8;
					label.setText("畫筆大小為:中");
					//last=panel.pointCount;
				}
				if(event.getSource()==BigButton){
					size=16;
					label.setText("畫筆大小為:大");
					//last=panel.pointCount;
				}
				if(event.getSource()==SuperBigButton){
					size=30;
					label.setText("畫筆大小為:巨大。");
					//last=panel.pointCount;
				}
	   }
	 }

	 
	 public class paintpanel extends JPanel
	 {
	 	    public int pointCount = 0;
	 	    public Point points[] = new Point[10000];
	 	
	 	    public paintpanel()
	 	    {
	 		  
	 	    	buffImage=new BufferedImage(700,700,BufferedImage.TYPE_INT_ARGB_PRE);
				MouseHandler mouseHandler = new MouseHandler();
				addMouseListener(mouseHandler);
				addMouseMotionListener(mouseHandler);

	         }

   
	 private class MouseHandler implements MouseListener, MouseMotionListener 
	  {

	 		  public void mouseClicked(MouseEvent event){}
              public void mouseDragged(MouseEvent event) 
	 	      {
	 		  label.setText( String.format("滑鼠位置：(%d,%d)",event.getX(),event.getY())); 
	 		     
	 		  if(pointCount<points.length)
	 		   {
					points[pointCount]=event.getPoint();
					pointCount++;
					repaint();
				}
	 	      }
	 		  public void mousePressed(MouseEvent event)
	 		  {   
	 			  x1=event.getX();
				  y1=event.getY();
			   }
	 		 public void mouseReleased(MouseEvent event)
	 		  {
	 			  x2=event.getX();
	 			  y2=event.getY();
	 			  repaint();
	 		  }
	 	     public void mouseEntered( MouseEvent event )
			  {
			    label.setText( String.format("滑鼠位置：(%d,%d)",event.getX(),event.getY()));
			    panel1.setBackground(Backcolor);
			  }
			 
			 public void mouseExited( MouseEvent event )
			  {
				 label.setText("滑鼠已經離開畫布");
			 }
			 public void mouseMoved( MouseEvent event )
			 {
				 label.setText( String.format("滑鼠位置：(%d,%d)",event.getX(),event.getY()));
			  } 

	      
	 }


	   
	 public void paintComponent( Graphics g )
     {
        super.paintComponent( g );
        
        
        Graphics gg = buffImage.createGraphics();
		Graphics2D g2d=( Graphics2D )gg;
		g2d.setColor(fontcolor);
        
		//gg.fillRect(0, 0, 500, 500); 
		 g2d.setStroke(new BasicStroke(size));
         System.out.println(Index);
         
		 if(Index==0)
		     {
		       for ( int i = lastone; i < pointCount; i++ )
                  {//gg.fillOval(x1,y1 ,points[i].x,points[i].y);
		    	     g2d.draw(new Ellipse2D.Double(points[i].x,points[i].y,size,size));
        	          lastone=pointCount; }
		     }
		 if(Index==1)
		 {//g2d.draw(new Line2D.Double(x1,y1,x2,y2));
			 g.drawLine(x1, y1,x2, y2);
		 }
         g.drawImage(buffImage,0,0,this);
     }
	 
 }
}

	 



	 


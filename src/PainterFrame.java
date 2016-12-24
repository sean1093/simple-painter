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
    private String name[] = {"����","���u","����","�x��","�ꨤ�x��"};
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
    	super("�p�e�a");
    	setLayout(new BorderLayout());
    	
    	panel1 = new paintpanel();
    	Backcolor=Color.WHITE;
    	fontcolor=Color.BLACK;
        panel2 = new JPanel();
    	panel2.setLayout(new GridLayout( 11,1 ));//Panel2�������t�m
    	
    	label = new JLabel("�ƹ���m");
    	label2 = new JLabel("  [ø�Ϥu��]");
    	label2.setToolTipText("�i��ܤ��Pø�Ϥu��");
    	label3 = new JLabel("  [����j�p]");
    	label3.setToolTipText("�����j���p�T�ص���");
    	
    	combobox = new JComboBox(name); 
    	combobox.setMaximumRowCount(3);
    	

    	add(panel1,BorderLayout.CENTER);
        add(panel2,BorderLayout.WEST);
        add(label,BorderLayout.SOUTH);
        
        
        button1 = new JButton("�e����");
        button2 = new JButton("�I����");
        button3 = new JButton("�M���e��");
        
        SuperBigButton = new JRadioButton("���j",false);
        BigButton = new JRadioButton("�j",false);
        MidButton = new JRadioButton("��",false);
        SmallButton= new JRadioButton("�p",true);
        
        check = new JCheckBox("��"); 
        

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
        
        
        //�U����s�ƥ�handler
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
	           {fontcolor = JColorChooser.showDialog(PainterFrame.this, "�п�ܭI���C��", Backcolor );
               if ( fontcolor == null )
               {fontcolor=Color.BLACK;}}
			   
			   if (event.getSource() == button2)
	           {
				   Backcolor = JColorChooser.showDialog(PainterFrame.this, "�п�ܭI���C��", Backcolor );
	               if ( Backcolor == null )
	                   {panel1.setBackground(Color.WHITE);}
	               panel1.setBackground( Backcolor );
	           }
			   
			   if (event.getSource() == button3)
	           {
				   JOptionPane.showMessageDialog(PainterFrame.this,"�A�I��F�G�M���e��");
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
					label.setText("�e���j�p��:�p");
					//last=panel.pointCount;
				}
				if(event.getSource()==MidButton){
					size=8;
					label.setText("�e���j�p��:��");
					//last=panel.pointCount;
				}
				if(event.getSource()==BigButton){
					size=16;
					label.setText("�e���j�p��:�j");
					//last=panel.pointCount;
				}
				if(event.getSource()==SuperBigButton){
					size=30;
					label.setText("�e���j�p��:���j�C");
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
	 		  label.setText( String.format("�ƹ���m�G(%d,%d)",event.getX(),event.getY())); 
	 		     
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
			    label.setText( String.format("�ƹ���m�G(%d,%d)",event.getX(),event.getY()));
			    panel1.setBackground(Backcolor);
			  }
			 
			 public void mouseExited( MouseEvent event )
			  {
				 label.setText("�ƹ��w�g���}�e��");
			 }
			 public void mouseMoved( MouseEvent event )
			 {
				 label.setText( String.format("�ƹ���m�G(%d,%d)",event.getX(),event.getY()));
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

	 



	 


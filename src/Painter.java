import javax.swing.JFrame;


public class Painter 
{
   public static void main( String args[] )
   {
	   PainterFrame painter = new PainterFrame();
	   painter.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   painter.setSize( 700, 700);
       painter.setVisible(true);
      
   }
}

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;

class FlyweightFactory
{
	private HashMap <Color, Button> flyweightButton = new HashMap<Color, Button>();
	private int sharedCircle = 0;
	private ButtonListener bListener = new ButtonListener();
	
	public Button makeButton(Color clr) 
	{
		Button btn;
	    if (flyweightButton.containsKey(clr)) 
	    {
	    	 //    Return an existing object   [The same Button cannot be added
	         //    multiple times to a container, and, Buttons cannot be cloned.
	         //    So - this is only simulating the sharing that the Flyweight
	         //    pattern provides.]
	         btn = new Button(flyweightButton.get(clr).getLabel() );
	         btn.setBackground(clr);
	         sharedCircle++;
	    } 
	    else 
	    {
	         //    Return a new object
	         btn = new Button();
	         btn.setBackground(clr);
	         flyweightButton.put( clr, btn );
	    }
	    btn.addActionListener(bListener);
	    return btn;
	}
	
	public void report() 
	{
		System.out.print( "new Buttons - " + flyweightButton.size()
		+ ", \"shared\" Buttons - " + sharedCircle + ", " );
		
		for (Iterator<Color> it = flyweightButton.keySet().iterator(); it.hasNext(); )
			System.out.print( it.next() + " " );
		
		System.out.println();
	}  
}

class ButtonListener implements ActionListener 
{
	public void actionPerformed( ActionEvent e) 
	{
		Button btn  = (Button)e.getSource();
	    Component[] btns = btn.getParent().getComponents();
	    int k=0;
	      
	    for (int i = 0; i < btns.length; i++)
	    {
	    	if (btn == btns[i]) break;
	    	k++;
	    }
	    	
	      //     A third party must compute the extrinsic state (x and y)
	      //    (the Button label is intrinsic state)
	    System.out.println( "label-" + e.getActionCommand()
	    + "  x-" + k/10   + "  y-" + k%10 );  // 1. Identify extrinsic state
	}  
}

public class FlyweightSquare 
{
	public static void main( String[] args ) 
	{
		FlyweightFactory ff = new FlyweightFactory();
     	Random rn = new java.util.Random();
     	Frame frame = new Frame( "Flyweight Colorful Square" );
     	
     	frame.addWindowListener(new WindowAdapter() 
     	{       
     		public void windowClosing( WindowEvent e ) 
     		{
     			System.exit( 0 );
     		} 
     	});
     	
     	frame.setLayout( new GridLayout( 10, 10 ) );
     	// 1. Identify shareable and non-shareable state
     	//    shareable - Button label, non-shareable - Button location
     	for (int i=0; i < 10; i++)
     		for (int j=0; j < 10; j++)
     		{
     			Color color;
     			switch(rn.nextInt(7))
     			{
	   			case 1:
	   				color = Color.black;
	   				break;
	   			case 2:
	   				color = Color.blue;
	   				break;
	   			case 3:
	   				color = Color.green;
	   				break;
	   			case 4:
	   				color = Color.cyan;
	   				break;
	   			case 5:
	   				color = Color.gray;
	   				break;
	   			case 6:
	   				color = Color.orange;
	   				break;
	   			default:
	   				color = Color.red;
	   				break;
	   			}
     			frame.add(ff.makeButton(color));
     		}
     	frame.pack();
     	frame.setSize(700, 700);
     	frame.setVisible( true );
     	ff.report();
	}  
}

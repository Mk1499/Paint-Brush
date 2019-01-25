package TaskPkg;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class PaintBrush extends Applet implements ItemListener{

	Choice colorChoice , shapeChoice ; 
	Checkbox filled  , eraser; 
	Color col ; 
	ArrayList <Shape> shapeArr = new ArrayList<> (); 
	Shape currentShape = new Shape() ;  
	Button undo , clr  ; 

	boolean red , green , blue , fill
	      , linShape , rectShape ,  cirShape 
	      , freeDraw , erase , cleared; 
	MLestion ml ; 
	
	
	
	public void init() {
         
		// set Applet Background
		 setBackground(Color.white);
		 //put Fill Check box 
		 filled = new Checkbox("Filled") ; 
		 // put Eraser Checkbox and intialize erase flag 
		 eraser =new Checkbox("Eraser" ) ; 
		 eraser.addItemListener(this) ;
		 erase = false ; 
		 cleared = false ; 
		 
		 //undo Btn 
		 undo = new Button("Undo")  ; 
		 UndoAct Uact = new UndoAct() ; 
		 undo.addActionListener(Uact);
		 this.add(undo)  ; 
		 
		 // clear Button 
		 clr = new Button ("Clear All") ; 
		 ClrAct cla = new ClrAct() ; 
		 clr.addActionListener(cla);
		 this.add(clr) ;
		 
		// Colors Drop Down List 
		colorChoice = new Choice();
		colorChoice.add("Red");
		colorChoice.add("Green");
		colorChoice.add("Blue");
		this.add(colorChoice) ;
		colorChoice.addItemListener(this) ; 
		
		// colors 
		   red = true ; 
		   blue =false;   
		   green = false ; 
		   
		
		//Shapes DropDown 
		shapeChoice = new Choice() ; 
		shapeChoice.add("Line");
		shapeChoice.add("Rect") ; 
		shapeChoice.add("Circle") ; 
		shapeChoice.add("Free Draw") ;
		
		this.add(shapeChoice) ; 
		shapeChoice.addItemListener(this); 
		this.add(filled) ;
		this.add(eraser) ;
		
		
			
		// Shapes 
		linShape = true; 
		rectShape = false ;
		cirShape = false ; 
		freeDraw = false ; 

	   //Action Listener 
	   ml = new MLestion() ; 
	   this.addMouseListener(ml);
	   this.addMouseMotionListener(ml);		
	}
	
	
	// Define Shape Class " Main " 

	class Shape {
	
		String   cString , type ; 
		
		TheLin lin = new TheLin();
		TheCir cir = new TheCir() ; 
		TheRect rect = new TheRect() ; 
		Point p = new Point() ;
		Blank blnk = new Blank() ; 
		
		class Blank {
			int x , y ; 
		}
		
		class Point {
			int x , y ; 
		}
		
		class TheLin{
			int x1 ,y1 , x2, y2 ; 
	    }   
		
		class TheCir {
			int x , y , width , height  ;
			boolean isFilled ; 
	    }
			 
		class TheRect {
			int x , y , width , height ;
          	//String cString , type ; 
          	boolean isFilled ;  	
		}
	}
	
		// Implement Mouse Action Listener 
	
	class MLestion implements MouseListener , MouseMotionListener {

		int cirX  , cirY , x1,y1 ; 
		Shape l , c ,r ; 
		 
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
		x1 = arg0.getX() ; 
		y1 = arg0.getY() ; 
		if (linShape)
		 l = new Shape() ; 
		else if (cirShape)
		 c = new Shape() ; 
		else if (rectShape)
		 r = new Shape() ; 
		}

		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			if(freeDraw) {
			Shape f = new Shape() ; 
			f.type = "point" ; 
			f.p.x = arg0.getX(); 
			f.p.y = arg0.getY() ; 
			   
			if (red)
			  f.cString = "red" ; 
		    else if (blue)
		      f.cString = "blue" ; 
		    else if (green)
			  f.cString = "green" ; 
		
			  shapeArr.add(f) ; 
			   repaint() ; 
			}
			
			else if (erase) {
				Shape e = new Shape() ; 
				e.type = "blank" ; 
				e.blnk.x = arg0.getX(); 
				e.blnk.y = arg0.getY() ; 
			
				shapeArr.add(e) ; 
				   repaint() ;
			}
			
		else if (linShape) {
				   
		           l.type = "line" ; 
                   currentShape.lin.x1 = l.lin.x1 = x1 ; 
                   currentShape.lin.y1 = l.lin.y1 = y1 ;
				   currentShape.lin.x2 = l.lin.x2 = arg0.getX() ;
				   currentShape.lin.y2 = l.lin.y2 = arg0.getY() ; 
				   
				   
				   if (red)
					   l.cString = "red" ; 
				   else if (blue)
					   l.cString = "blue" ; 
				   else if (green)
					   l.cString = "green" ; 
				 
                   repaint() ; 
	        	}
			
		else if (cirShape) {
				
			    c.type = "circle" ; 
				currentShape.cir.isFilled = c.cir.isFilled = filled.getState() ; 
				
				if (x1 < arg0.getX())
					currentShape.cir.x =  c.cir.x = x1 ; 
				else 
					currentShape.cir.x = c.cir.x = arg0.getX() ; 
				
				if (y1 < arg0.getY())
					currentShape.cir.y = c.cir.y = y1 ; 
				else 
					currentShape.cir.y = c.cir.y = arg0.getY() ; 
				
				currentShape.cir.width = c.cir.width = Math.abs(x1 - arg0.getX() ) ;
				currentShape.cir.height = c.cir.height = Math.abs(y1 - arg0.getY() ) ;
				 if (red)
					   c.cString = "red" ; 
				   else if (blue)
					   c.cString = "blue" ; 
				   else if (green)
					   c.cString = "green" ; 
				   
				   repaint() ; 
			}
			
			else if (rectShape) {
				
				 r.type = "rectangle" ; 
				
				currentShape.rect.isFilled = r.rect.isFilled = filled.getState() ; 
				if (x1 < arg0.getX())
					currentShape.rect.x =  r.rect.x = x1 ; 
				else 
					currentShape.rect.x = r.rect.x = arg0.getX() ; 
				
				if (y1 < arg0.getY())
					currentShape.rect.y = r.rect.y = y1 ; 
				else 
					currentShape.rect.y = r.rect.y = arg0.getY() ; 
				
				currentShape.rect.width = r.rect.width = Math.abs(x1 - arg0.getX() ) ;
				currentShape.rect.height = r.rect.height = Math.abs(y1 - arg0.getY() ) ;
				 if (red)
					  r.cString = "red" ; 
				   else if (blue)
					   r.cString = "blue" ; 
				   else if (green)
					   r.cString = "green" ; 
				   
				   repaint() ;
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {}
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (linShape) 
			shapeArr.add(l) ;
			else if(cirShape)
			 shapeArr.add(c) ;
			else if (rectShape)
			 shapeArr.add(r) ; 
			   System.out.println(shapeArr.size());
			    
		}
	}
	
	
	 // undo Action Performed 
    class UndoAct implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (shapeArr.size() >= 1) {
			shapeArr.remove(shapeArr.size()-1) ;
			System.out.println(shapeArr.size());
			cleared = true ;
			repaint() ; 
			}
		}
    }
	
    //clear Vtn Action Performed 
    class ClrAct implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			shapeArr.clear();
			cleared = true ; 
			repaint() ; 
		
		}
    	
    }


	// DropDown Menues Changed 
	
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		 // Test Color 
		if (colorChoice.getSelectedItem() == "Red") {
			red = true ;
			blue = false ; 
			green = false ; 
	  }
		else if (colorChoice.getSelectedItem() == "Blue") {
			red = false ;
			blue = true ; 
			green = false ; 
	}
		else if (colorChoice.getSelectedItem() == "Green") {
			red = false ;
			blue = false ; 
			green = true ; 
	}
		
		 // Test Shape Selected 
		
		if (shapeChoice.getSelectedItem() == "Free Draw" ) {
			linShape = false ;
			rectShape = false ; 
			cirShape = false ; 
			freeDraw = true ; 
			
	  }
		
		else if (shapeChoice.getSelectedItem() == "Line") {
				linShape = true ;
				rectShape = false ; 
				cirShape = false ; 
				freeDraw = false ; 
				erase = false ; 
		  }
			else if (shapeChoice.getSelectedItem() == "Rect") {
				linShape = false ;
				rectShape = true ; 
				cirShape = false ; 
				freeDraw = false ; 
				erase = false ; 
		}
			else if (shapeChoice.getSelectedItem() == "Circle") {
				linShape = false ;
				rectShape = false ; 
				cirShape = true ; 
				freeDraw = false ; 
				erase = false ; 
		}
		
		 if (eraser.getState() == true) {
			 //System.out.println("Checked");
			 erase = true ; 
			 linShape = false ;
			 rectShape = false ; 
			 cirShape = false ; 
			 freeDraw = false ;

		 }
		 else {
			 erase = false ; 
		 }	
	}
	
	
	// Start PAINT 
	public void paint (Graphics g) {
		
		
		g.setColor(Color.RED);
		
		if(blue)
			g.setColor(Color.blue);
		else if (green)
			g.setColor(Color.green);
		
       			
		
		if (linShape && !cleared)
			g.drawLine(currentShape.lin.x1, currentShape.lin.y1, currentShape.lin.x2, currentShape.lin.y2);
		
		else if (cirShape && !cleared) {
			 if (currentShape.cir.isFilled)
				 g.fillOval(currentShape.cir.x, currentShape.cir.y,
						 currentShape.cir.width, currentShape.cir.height);
			   else
			     g.drawOval(currentShape.cir.x, currentShape.cir.y,
			    		 currentShape.cir.width, currentShape.cir.height);
			}
		else if (rectShape && !cleared) {
			if (currentShape.rect.isFilled )
				  g.fillRect(currentShape.rect.x, currentShape.rect.y, 
						  currentShape.rect.width, currentShape.rect.height);
			    else
				  g.drawRect(currentShape.rect.x, currentShape.rect.y, 
						  currentShape.rect.width,currentShape.rect.height);
			
		}
		
		for (int i=0 ; i<shapeArr.size() ; i++) {
			if (shapeArr.get(i).cString == "red" )
				g.setColor(Color.RED);
			
			if (shapeArr.get(i).cString == "blue" )
				g.setColor(Color.BLUE);
			
			if (shapeArr.get(i).cString == "green" )
				g.setColor(Color.GREEN);
			
			switch (shapeArr.get(i).type ) {
			
			  case "line":
				  g.drawLine(shapeArr.get(i).lin.x1, shapeArr.get(i).lin.y1, shapeArr.get(i).lin.x2, shapeArr.get(i).lin.y2);
			      break ; 
			      
			  case "circle":
				   if (shapeArr.get(i).cir.isFilled)
					 g.fillOval(shapeArr.get(i).cir.x, shapeArr.get(i).cir.y, shapeArr.get(i).cir.width, shapeArr.get(i).cir.height);
				   else
				     g.drawOval(shapeArr.get(i).cir.x, shapeArr.get(i).cir.y, shapeArr.get(i).cir.width, shapeArr.get(i).cir.height);
				 break ; 
			      
			  case "rectangle" : 
				    if (shapeArr.get(i).rect.isFilled )
					  g.fillRect(shapeArr.get(i).rect.x, shapeArr.get(i).rect.y, shapeArr.get(i).rect.width, shapeArr.get(i).rect.height);
				    else
					  g.drawRect(shapeArr.get(i).rect.x, shapeArr.get(i).rect.y, shapeArr.get(i).rect.width, shapeArr.get(i).rect.height);
				  break ; 
			      
			  case "point" : 
				      g.fillRect(shapeArr.get(i).p.x, shapeArr.get(i).p.y, 5, 5);
			     break;
                 
			  case "blank":
					  g.setColor(Color.white);
					  g.fillRect(shapeArr.get(i).blnk.x, shapeArr.get(i).blnk.y, 15, 15);
					 
				  }
			
			
			
		}
		
		cleared = false ; 
		
	}

			// End PAINT 

}
	


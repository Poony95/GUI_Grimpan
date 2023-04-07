package grimpan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GrimpanPanel extends JPanel implements MouseListener{

	private int x1=0;
	private int x2=0;
	private int y1=0;
	private int y2=0;
	ArrayList<Graphicinfo> list;
	int drawtype =0;
	boolean isNew;
	Color drawColor = Color.black;
	
	public GrimpanPanel() {
		list = new ArrayList<Graphicinfo>();
		addMouseListener(this);
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for(Graphicinfo info :list) {
			g.setColor(info.getDrawColor());
			
			int x = info.getX1();
			int y = info.getY1();
			int w = info.getX2() - info.getX1();
			int h = info.getY2() - info.getY1();
			
			if(info.getX1() > info.getX2()) {
				w = info.getX1() - info.getX2();
				x = info.getX2();
			}
			if(info.getY1() > info.getX2()) {
				h = info.getY2() - info.getY1();
				y = info.getY2();
			}
			switch(info.getDrawtype()) {
			case 0 : g.drawLine(info.getX1(),info.getY1(), 
					info.getX2(), info.getY2());break;
			case 1 : g.drawRect(x, y, w, h);break;
			case 2 : g.drawOval(x, y, w, h);break;
			}
		}
			
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		list.add(new Graphicinfo(x1, y1, x2, y2, drawtype, drawColor));
		isNew = true;
		repaint();
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}

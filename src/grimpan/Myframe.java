package grimpan;

import java.awt.Color;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Myframe extends JFrame implements ActionListener{

	GrimpanPanel pan;
	JColorChooser jcc;
	JFileChooser jfc;
	String fname = "c:/temp/my.pan";
	File file;
	
	public Myframe () {
		setTitle("그림판");
		pan = new GrimpanPanel();
		add(pan);
		
		jcc = new JColorChooser(Color.black);
		jfc = new JFileChooser("c:/temp");
		
		// 메뉴바 만들기
		JMenuBar jmb = new JMenuBar();
		
		// 파일 메뉴바만들기
		JMenu file = new JMenu("파일");
		
		JMenuItem item = new JMenuItem("새파일");
		JMenuItem open = new JMenuItem("열기");
		JMenuItem save = new JMenuItem("저장");
		JMenuItem exit = new JMenuItem("종료");
		
		item.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		
		file.add(item);
		file.add(open);
		file.add(save);
		file.add(exit);
		
		jmb.add(file);
		
		// 그리기도구 메뉴 만들기
		
		JMenu draw = new JMenu("그리기 도구");
		
		JMenuItem line = new JMenuItem("선");
		JMenuItem rect = new JMenuItem("사각형");
		JMenuItem oval = new JMenuItem("원");
		
		line.addActionListener(this);
		rect.addActionListener(this);
		oval.addActionListener(this);
		
		draw.add(line);
		draw.add(rect);
		draw.add(oval);
		
		jmb.add(draw);
		
		
		// 색상 메뉴 만들기
		
		JMenu color = new JMenu("그리기 색상");
		
		JMenuItem red = new JMenuItem("빨강");
		JMenuItem blue = new JMenuItem("파랑");
		JMenuItem green = new JMenuItem("초록");
		JMenuItem other = new JMenuItem("다른색상...");
		
		red.addActionListener(this);
		blue.addActionListener(this);
		green.addActionListener(this);
		other.addActionListener(this);
		
		color.add(red);
		color.add(blue);
		color.add(green);
		color.add(other);
		
		jmb.add(color);
		
		setJMenuBar(jmb);  // 메뉴바를 프레임에 붙이는 메소드
		
		setSize(600,500);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	} // 생성자 끝
	
	//열기, 저장 메소드
	public void openFile() {
		try {
			int re = jfc.showOpenDialog(this);
			if(re == 0) {
				file = jfc.getSelectedFile();
				ObjectInputStream ois = 
				new ObjectInputStream(new FileInputStream(file));
				pan.list = (ArrayList<Graphicinfo>)ois.readObject();
				pan.repaint();
			}
		}catch (Exception e2) {
			System.out.println("예외발생:"+e2.getMessage());
		}
	}
	
	public void saveFile() {
		try {
			int re=9;
			if(file == null) {
				re = jfc.showSaveDialog(this);
			}
			if(re != 1 ) {	
				if(re == 0) {
					file = jfc.getSelectedFile();	//열기파일
					}
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(pan.list);
					oos.close();
					JOptionPane.showMessageDialog(this, "파일에 저장하였습니다.");
					pan.isNew = false;
				}
		} catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Myframe();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch (cmd) {
			case "선" : pan.drawtype=0; break;
			case "사각형":pan.drawtype=1;break;
			case "원":pan.drawtype=2;break;
			}
			
		switch (cmd) {
			case "빨강" : pan.drawColor=Color.red; break;
			case "초록":pan.drawColor=Color.green;break;
			case "파랑":pan.drawColor=Color.blue;break;
			case "다른색상...":pan.drawColor = jcc.showDialog(this,"색상선택", Color.black);break;
			}
		
		switch (cmd) {
		case "새파일":
			if(pan.isNew == true) {
				int re = JOptionPane.showConfirmDialog(this, "저장하시겠습니까?");
				if(re == 2) {
					return;
				}
				if(re == 0) {
					saveFile();
				}
			}
			pan.list.clear();
			pan.repaint();
			break;
			
		case "열기":
			if(pan.isNew == true) {
				int re =  JOptionPane.showConfirmDialog(this, "변경된 내용을 저장하시겠습니까?");
				//예:0,아니오:1,취소:2
				if(re == 2) {
					return;
				}
				if(re == 0) {
					saveFile();
				}
			}
			openFile();
			break;
			
		case "저장":saveFile();break;
			
		case "종료":
			System.exit(0);
			break;
	}
	}

}

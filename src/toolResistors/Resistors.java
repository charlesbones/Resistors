/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * Carlos Rodriguez bones.charles@gmail.com wrote this file. As long as you retain 
 * this notice you can do whatever you want with this stuff. If we meet some day, 
 * and you think this stuff is worth it, you can buy me a beer in return.
 * ----------------------------------------------------------------------------
 *
 * (C) 2013
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		Carlos Rodriguez - Charles Bones
 * @modified	13/11/2015
 * @version		##version##
 */

 package toolResistors;
 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import processing.app.*;
import processing.app.tools.*;
 
 public class Resistors implements Tool, ActionListener {
	 
	public JComboBox<String> firstList, secondList, multiplierList, qualityList;
	public String firstLabel="2";
	public String secondLabel="2";
	public String multiplierLabel="10";
	public String qualityLabel="10";
	public JLabel resultString=new JLabel();
	public int totalResult;
	public float totalResultF;
	DecimalFormat d1 = new DecimalFormat("#.#");
	JLabel punta1,punta2 ,uno, dos, tres, cuatro;
	
	public String getMenuTitle() {
		return "Resistors";
	}
 
	public void init(Editor base) {
	}
 
	public void run() {		
		//-----------
		//JPanel
		//-----------
		JPanel gui=new JPanel();
		JPanel result=new JPanel();
		JPanel resistor=new JPanel();
		//-----------
		//ComboBox
		//-----------
		//ComboBox content
		String[] firstValue = { "0-Black", "1-Brown", "2-Red", "3-Orange", "4-Yellow", "5-Green", "6-Blue", "7-Purple", "8-Grey", "9-White" };
	    String[] secondValue = { "0-Black", "1-Brown", "2-Red", "3-Orange", "4-Yellow", "5-Green", "6-Blue", "7-Purple", "8-Grey", "9-White" };
	    String[] multiplierValue = { "0.01-Silver", "0.1-Gold", "1-Black", "10-Brown", "100-Red", "1000-Orange", "10000-Yellow", "100000-Green", "1000000-Blue", "10000000-Purple" };
	    String[] qualityValue = { "+-20%-None", "+-1%-Brown", "+-2%-Red", "+-5%-Gold", "+-10%-Silver"};
	    //ComboBox 
	    firstList = new JComboBox<String>(firstValue);
	    secondList = new JComboBox<String>(secondValue);
	    multiplierList = new JComboBox<String>(multiplierValue);
	    qualityList = new JComboBox<String>(qualityValue);
	    //Initial ComboBox state
	    firstList.setSelectedIndex(2);
	    secondList.setSelectedIndex(2);
	    multiplierList.setSelectedIndex(3);
	    qualityList.setSelectedIndex(4);
	    //ComboBox Listener
	    firstList.addActionListener(this);
	    secondList.addActionListener(this);
	    multiplierList.addActionListener(this);
	    qualityList.addActionListener(this);
	    
	    //-----------
	    //Labels
	    //-----------
	    //Stripes Name
	    JLabel firstLabel=new JLabel("First");
	    JLabel secondLabel=new JLabel("Second");
	    JLabel multiplierLabel=new JLabel("Multiplier");
	    JLabel qualityLabel=new JLabel("Quality");
	    firstLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
	    secondLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
	    multiplierLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
	    qualityLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
	    //-----------
	    //Layout ComboBox and Labels
	    //-----------
	    gui.setLayout(new GridLayout(4,1,0,0));
	    gui.setOpaque(false);
	    //First
	    gui.add(firstLabel);
	    gui.add(firstList);	
	    //Second
        gui.add(secondLabel);
        gui.add(secondList);
        //Multiplier
	    gui.add(multiplierLabel);
	    gui.add(multiplierList);
	    //Quality
	    gui.add(qualityLabel);
	    gui.add(qualityList);
	    gui.setPreferredSize(new Dimension(200,120));
	    
	    //-----------
	    //Result
	    //-----------
	    updateValue();
	    //result.setBounds(100, 10, 200, 20);
	    result.setOpaque(false);
	    result.add(resultString,BorderLayout.CENTER);
	    resultString.setFont(new Font("Verdana", Font.PLAIN, 27));
	    result.setPreferredSize(new Dimension(200,40));
	    
	    //-----------
	    //Resistor Graphic
	    //-----------
	    uno=new JLabel();
	    dos=new JLabel();
	    tres=new JLabel();
	    cuatro=new JLabel();
	    resistor.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
	    resistor.setOpaque(false);
	    //Initial state
	    updateLabel(uno,"2-Red");
	    updateLabel(dos,"-2-Red");
	    updateLabel(tres,"10-Brown");
	    updateLabel(cuatro,"+-10%-Silver");
	    //add
	    resistor.add(uno);
	    resistor.add(dos);
	    resistor.add(tres);
	    resistor.add(cuatro);
	    resistor.setPreferredSize(new Dimension(200,100));
	    
	    //-----------
	    //Background Panel Image
	    //-----------
		JBackgroundPanel bgPanel = new JBackgroundPanel();
		 
		bgPanel.setLayout(new BoxLayout(bgPanel,BoxLayout.Y_AXIS));
		bgPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		// add some elements...
		bgPanel.add(gui);
		//position result
		//LINUX 15 - WINDOWS 42
		bgPanel.add(Box.createRigidArea(new Dimension(0,15)));
		bgPanel.add(result);
		//bgPanel.add(Box.createRigidArea(new Dimension(0,4)));
		bgPanel.add(resistor);
		
		
		// create the window
		JFrame f = new JFrame("Resistors");
		//canvas size
		//LINUX 238 - WINDOWS 258
		f.setSize(210,238);
		f.setLocation(160, 200);
        f.setResizable(false);
        // show the window
     	f.setVisible(true);
		// add the panel with the background image
		f.add(bgPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComboBox cb = (JComboBox)e.getSource();
		if(cb==firstList){
			String first = (String)cb.getSelectedItem();
			updateLabel(uno,first);
			String[] splitedString=first.split("-");
			firstLabel=splitedString[0];
		}
		if(cb==secondList){
			String second = (String)cb.getSelectedItem();
			updateLabel(dos,"-"+second);
			String[] splitedString=second.split("-");
			secondLabel=splitedString[0];
		}
		if(cb==multiplierList){
			String multiplier = (String)cb.getSelectedItem();
			updateLabel(tres,multiplier);
			String[] splitedString=multiplier.split("-");
			multiplierLabel=splitedString[0];
		}
		if(cb==qualityList){
			String quality = (String)cb.getSelectedItem();
			updateLabel(cuatro,quality);
			String[] splitedString=quality.split("%-");
			qualityLabel=splitedString[0];
		}
		updateValue();
	} 
	//Compute the value for the label
	protected void updateValue() {
		String indexString=firstLabel+secondLabel;
		int multiplier=Integer.parseInt(multiplierLabel)%10000000;
		if(multiplier==100){
			totalResultF=(float) ((Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel))/1000.0);
			resultString.setText(totalResultF+"K"+"\u2126 "+qualityLabel+"%");
		}else if(multiplier==1000){
			totalResult=(int)(Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel))/1000;
			resultString.setText(d1.format(totalResult)+"K"+"\u2126 "+qualityLabel+"%");
		}else if(multiplier==10000){
			totalResult=(int)(Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel))/1000;
			resultString.setText(d1.format(totalResult)+"K"+"\u2126 "+qualityLabel+"%");
		}else if(multiplier==100000){
			totalResultF=(float) ((Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel))/1000000.0);
			resultString.setText(totalResultF+"M"+"\u2126 "+qualityLabel+"%");
		}else if(multiplier==1000000){
			totalResult=(int)(Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel))/1000000;
			resultString.setText(d1.format(totalResult)+"M"+"\u2126 "+qualityLabel+"%");
		}else if(multiplier==0){
			totalResult=(int)(Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel))/1000000;
			resultString.setText(d1.format(totalResult)+"M"+"\u2126 "+qualityLabel+"%");
		}else{
			totalResult=Integer.parseInt(indexString)*Integer.parseInt(multiplierLabel);
			resultString.setText(totalResult+"\u2126 "+qualityLabel+"%");
		}
    }
	//create the image for the resistor's strips
	protected void updateLabel(JLabel picture, String name) {
        ImageIcon icon = createImageIcon("/data/" + name + ".png");
        picture.setIcon(icon);
        picture.setToolTipText("A drawing of a " + name.toLowerCase());
        if (icon != null) {
            picture.setText(null);
        } else {
            picture.setText("Image not found");
        }
    }
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Resistors.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

 }
class JBackgroundPanel extends JPanel {
  private BufferedImage img;
 
  public JBackgroundPanel() {
    // load the background image
    try {
      java.net.URL url = Resistors.class.getResource("/data/fondo.jpg");
      img = ImageIO.read(url);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // paint the background image and scale it to fill the entire space
    g.drawImage(img, 0, 0, 210, 238, this);
  }
}



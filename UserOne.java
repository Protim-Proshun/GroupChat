import javax.swing.*; // import package for JFrame
import java.awt.*; // import package for colors
import java.awt.event.*; // import ActionListener

import java.io.*; // import for BufferedWriter
import java.net.*; //import for Socket 


public class UserOne extends JFrame implements ActionListener, Runnable{
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JTextArea a1;
	
	BufferedWriter writer; //to send messages
	BufferedReader reader; //to read messages from other users
	
	
	UserOne(){
		
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(129, 216, 208));
		p1.setBounds(0, 0, 450, 70);
		add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2); //to convert i2 into ImageIcon so that it could be used inside JLabel
		JLabel l1 = new JLabel(i3);   // to show image on the frame
		l1.setBounds(5, 17, 30, 30); // to make own layout (x,y,element-length,element-width)
		//add(l1); // to add element on the frame
		p1.add(l1); //to add element on the panel
		
		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/group.jpg"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);
		
		JLabel l3 = new JLabel("Our Group");
		l3.setFont(new Font("TimesRoman", Font.BOLD, 20));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 15, 100, 20);
		p1.add(l3);
		
		JLabel l4 = new JLabel("Tom, Jerry, Sponge Bob, Mickey");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 35, 100, 15);
		p1.add(l4);
		
		a1 = new JTextArea();
		a1.setBounds(5, 75, 440, 475);
		a1.setBackground(new Color(224, 255, 255));
		a1.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		a1.setEditable(false);
		a1.setLineWrap(true);
		a1.setWrapStyleWord(true);
		add(a1);
		
		t1 = new JTextField();
		t1.setBounds(5, 555, 310, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		add(t1);
		
		b1 = new JButton("send");
		b1.setBounds(320, 555, 123, 40);
		b1.addActionListener(this);
		add(b1);
		
		//getContentPane().setBackground(Color.YELLOW); //to change the color of the frame
		setLayout(null); //not to use layout of swing
		setSize(450, 600);
		setLocation(150, 200);
		setUndecorated(true);
		setVisible(true);
		
		try {
			
			Socket socketClient = new Socket("localhost", 2006);
			writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())); //to send messages into the server
			reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));  //to get the messages from the server
			
		}catch(Exception e) {}
	}
	
	public void actionPerformed(ActionEvent ae) {   //when we implement interface then we have to override all the abstract methods of the interface
		String str = "<<Tom>>\n" + t1.getText();	//sending message after clicking send
		try {
			writer.write(str); //sending by buffer
			writer.write("\r\n");
			writer.flush();
		}catch(Exception e) {}
		t1.setText("");
	}
	
	public void run() {
		try {
			String msg = "";
			while((msg = reader.readLine()) != null) {
				a1.append(msg + "\n");
			}
		}catch(Exception e) {}
	}
	
	public static void main(String[] args) {
		UserOne one = new UserOne();
		Thread t1 = new Thread(one);
		t1.start();  //calls run() method internally
	}

}

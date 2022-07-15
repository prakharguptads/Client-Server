import javax.swing.*;
import java.awt.*;
import java.awt.ActiveEvent.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame implements Runnable{

	JPanel p1 , inputpanel , centerpanel , Buttonpanel;
	JLabel L1 , L2 ;
	JTextArea ta1 ;
	JButton b1 , b2;
	JTextField t1 ;
	
	Thread th1 ;
	String Name ;
	//ServerSocket ss ; // srf server ki request accept krega 
	Socket s ; //client k server ka object hoga
	BufferedReader br ;
	BufferedWriter bw; 
	
	
	public ChatClient()

	{
		setVisible(true);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p1 = new JPanel();
		inputpanel = new JPanel() ;
		centerpanel = new JPanel() ;
		Buttonpanel = new JPanel();
		L1 = new JLabel() ; 
		L2 = new JLabel();
		ta1 = new JTextArea(5,20);
		b1 = new JButton("Send");
		b2 = new JButton("Cancel");
		t1 = new JTextField(20);
		JScrollPane js = new JScrollPane(ta1);
		p1.setLayout(new BorderLayout());
		inputpanel.add(L1);
		inputpanel.add(L2);
		p1.add(inputpanel,"North");
		centerpanel.setLayout(new BorderLayout());
		centerpanel.add(js,"Center");
		centerpanel.add(t1,"South");
		p1.add(centerpanel,"Center");
		Buttonpanel.add(b1);
		Buttonpanel.add(b2);
		p1.add(Buttonpanel,"South");
		getContentPane().add(p1);
		p1.setBorder(BorderFactory.createTitledBorder("ChatClient"));
		ta1.setEditable(false);
		Name = JOptionPane.showInputDialog(null, "Enter Name"); // user se name poochega n name m store ho jawga 
		L2.setText(Name); // name se l1 lable m aa jaega
		L2.setFont(new Font("Arial",Font.BOLD,20));
		L2.setBackground(Color.red);
		b1.addActionListener(e->actionB1(e));
		b2.addActionListener(e->actionB2(e));
		
		try
		{
			//ss = new ServerSocket(8000);
			//s = ss.accept(); // obtain client socket
			s = new Socket("localhost",9999); // local host for client and n server on same machine // 8000 is for the gate no. or the port // address  
			
			br = new BufferedReader(
					new InputStreamReader(
							s.getInputStream()));//byte m data aaega kyoki input stream h , fr wo hme read krna h to buffered reader se char m convert kr lia 
			bw = new BufferedWriter(new OutputStreamWriter(
					s.getOutputStream()));
					bw.write("hello");
					bw.newLine();
					bw.flush();
					th1 = new Thread(this);
					th1.start();

		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
	}
	public void run()
	{
		for(;;)
		{
			try
			{
				ta1.append(br.readLine()+"\n");
			}
			catch (Exception ex)
			{
				// TODO: handle exception
			}
		}
	}
	public void actionB1(ActionEvent e) //send
	{
		try
		{
			String msg = Name +"says->"+t1.getText();
			bw.write(msg);
			bw.newLine();
			bw.flush();
			ta1.append(msg+"\n"); // hum bhi wo msg dekh ske isliye
			t1.setText("");
		}
		catch(Exception ex)
		{
			
		}
	}
	public void actionB2(ActionEvent e)
	{
		th1.stop();
		System.exit(0);
	}

	public static void main(String args[])
	{
		new ChatClient();
	}

}

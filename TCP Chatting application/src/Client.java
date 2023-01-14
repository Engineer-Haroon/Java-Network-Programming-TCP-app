package random;

import java.io.*;
import java.net.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.*;
public class Client  implements ActionListener{
    static Boolean typing1=true;
    static JFrame f=new JFrame();
    private static InetAddress host;
    private static int port = 3223;
    static String messag = "";
    static Socket link = null;
    static DataInputStream input;
    static DataOutputStream output;
    static JButton sender = new JButton("Send");
    static JTextField message = new JTextField("");
    static JPanel P = new JPanel();
    static JPanel a1;
    static JLabel l2;
    static Timer T;
    static Box vertical=Box.createVerticalBox();
   
    
 static int x=0;
    public Client() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        P.setBackground(new Color(7, 94,84));
        P.setLayout(null);
        P.setBounds(0, 0, 450, 70);
        f.add(P);
        //-------------------------
        JLabel l=new JLabel("Client");
        l.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        l.setForeground(Color.WHITE);
        l.setBounds(110,15, 100,18);
        P.add(l);
        
        //---------------------------------
        l2=new JLabel("Active");
        l2.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        l2.setForeground(Color.WHITE);
        l2.setBounds(110,35, 100,20);
        P.add(l2);
        //--------------------------------------
        T=new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!typing1){                                                            
                    l2.setText("Online");
                    }                
            }
        });       
        
        a1=new JPanel();
        a1.setBounds(5, 75, 440, 380);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(a1);
        //--------------------------------------
        
        message.setBounds(5, 455, 310, 40);
        message.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(message);
        
        //---------------------------------------------
        
        message.addKeyListener(new KeyAdapter() {             
            
            public void keyPressed(KeyEvent ke){                             
                 T.stop();
                 typing1=true;
                 x++;
                 if (x==1){
                     isWriting(x);
                 }
                 
            }     
            
            public void keyReleased(KeyEvent ke){
                
                typing1=false;                
                if(!T.isRunning()){
                    T.start();                             
                }               
            }           
        });
                                                       
        //-------------------------------------------------
        sender.setBounds(320, 455, 123, 40);
        sender.setBackground(new Color(7,94,84));
        sender.setForeground(Color.WHITE);
        sender.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        sender.addActionListener(this);
        f.add(sender);
        //------------------------------------------
        
        
        f.getContentPane().setBackground(Color.white);
        f.setLayout(null);
        f.setSize(450, 500);
        f.setLocation(100,50);
        f.setUndecorated(true);
        f.setVisible(true);
       //-----------------------------------------------                                                                                   
    }
        
           public void actionPerformed(ActionEvent evt)        
             {
              
              try{
                String msgout="";
                x=0;
                msgout=message.getText();
                JPanel p2=formatLabel(msgout);
                a1.setLayout(new BorderLayout());
                JPanel right=new JPanel(new BorderLayout());
                right.add(p2,BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);                                               
                output.writeUTF(msgout);                             
                message.setText("");                                   
            }
              catch(Exception e){              
           
              }
            }

          public static  JPanel formatLabel(String msgout) {
              
            JPanel p3=new JPanel();
            p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
            JLabel l1=new JLabel("<html><p style=\"width:100px\">"+msgout+"</p></html>");
            l1.setFont(new Font("Tahoma",Font.PLAIN,16));
            l1.setBackground(new Color(37,211,102));
            l1.setOpaque(true);
            l1.setBorder(new EmptyBorder(15,15,15,50));
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
            JLabel l2=new JLabel();
            l2.setText(sdf.format(cal.getTime()));
            p3.add(l1);
            p3.add(l2);
            return p3;
            }                                            
          
          public static void isWriting(int x){
        try {
                        
            output.writeUTF("type");
            
        } catch (IOException ex) {
           
        }
     }
    public static void main(String[] args) {                                                
        try {

            host = InetAddress.getLocalHost();
            System.out.println("\nConnection!\n");
            Client s = new Client();
        } catch (UnknownHostException e) {
            System.out.println("\nCan not connection!\n");
        }
        try{
            
            link=new Socket(host, port);
            input=new DataInputStream(link.getInputStream());
            output=new DataOutputStream(link.getOutputStream());
            String mes_age="";
            while(true){
                
                   a1.setLayout(new BorderLayout());
                mes_age=input.readUTF();                
                if (mes_age.equals("type")){ 
                        l2.setText("Typing...");
                        f.validate();
                }
                else{
                     l2.setText("Online");                    
                JPanel p2=formatLabel(mes_age);
                JPanel left=new JPanel(new BorderLayout());
                left.add(p2,BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);                         
                f.validate();             
        }
        }
        }
        catch(Exception e){
                System.out.println(e);
        }
    }



}
  
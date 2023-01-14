
package random;

/**
 *
 * @author Haroon
 */
import java.io.*;

import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.*;

public class Server implements ActionListener{    
    private static ServerSocket server;
    private static int port = 3223;
     static JFrame f=new JFrame();
     static   Socket link = null;    
    static DataInputStream input1;
   static DataOutputStream output1;
    static JButton sender1 = new JButton("Send");
    static JTextField message1 = new JTextField("");
    static JPanel P = new JPanel();
    static JScrollPane ss;
        static JPanel a1;
        static JLabel l2s;
        static Box vertical=Box.createVerticalBox();
       static boolean typing=true;
        static Timer T; 
        static int x=0;
    public Server(){
         P.setBackground(new Color(7, 94,84));
        P.setLayout(null);
        P.setBounds(0, 0, 450, 70);
        f.add(P);
        //-------------------------
        JLabel l=new JLabel("Server");
        l.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        l.setForeground(Color.WHITE);
        l.setBounds(110,15, 100,18);
        P.add(l);
        
        //---------------------------------
        l2s=new JLabel("Online");
        l2s.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        l2s.setForeground(Color.WHITE);
        l2s.setBounds(110,35, 100,20);
        P.add(l2s);
        //--------------------------------------
        Timer T=new Timer(1,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!typing){                                    
                    
                    l2s.setText("Online");                  
                }
            }
        });
        T.setInitialDelay(2000);   
     
       a1=new JPanel();                     
       a1.setBounds(5, 75, 400, 350);
       a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));                            
       f.add(a1);
        //--------------------------------------
        
        message1.setBounds(5, 455, 310, 40);
        message1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(message1);
        
        //---------------------------------------------
        message1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke){

            T.stop();
            typing=true;
           x++;
                 if (x==1){
                     isWriting(x);
                 }
            }
            public void keyReleased(KeyEvent ke){
             typing=false;
              if(!T.isRunning()){
                    
                T.start();

             }

            }
        });
        
        //-------------------------------------------------
        sender1.setBounds(320, 455, 123, 40);
        sender1.setBackground(new Color(7,94,84));
        sender1.setForeground(Color.WHITE);
        sender1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        sender1.addActionListener(this);
        f.add(sender1);
        //------------------------------------------
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(Color.white);
        f.setLayout(null);
        f.setSize(450, 500);
        f.setLocation(800,100);
        f.setUndecorated(true);
        f.setVisible(true);
       //-----------------------------------------------                                                                                   
       
    }

    
           public void actionPerformed(ActionEvent evt)        
             {
              
              try{
                String msgout="";
                
                msgout=message1.getText();
                x=0;
                JPanel p2=formatLabel(msgout);
                a1.setLayout(new BorderLayout());
                JPanel right=new JPanel(new BorderLayout());
                right.add(p2,BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);                                               
                output1.writeUTF(msgout);                
                message1.setText("");                     
                f.validate();
            }
              catch(Exception e){
                  
              }
            }
           
          public static  JPanel formatLabel(String msgout) {
            JPanel p3=new JPanel();
            p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
            JLabel l1=new JLabel("<html><p style=\"width:150px\">"+msgout+"</p></html>");
            l1.setFont(new Font("Tahoma",Font.PLAIN,16));
            l1.setBackground(new Color(37,211,102));
            l1.setOpaque(true);
            l1.setBorder(new EmptyBorder(15,15,15,50));
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
            JLabel l2=new JLabel();
            l2.setText(sdf.format(cal.getTime()));
            p3.add(l1);
            p3.add(l2);
            return p3;
            }                                            

              public static void isWriting(int x){
        try {
                       
            output1.writeUTF("type");
            
        } catch (IOException ex) {            
        }
     }
      
    public static void main(String[] args) {
        
        Server s=new Server();
        
        String message="";  
        
        try {
            System.out.println("Connectiong");
            server = new ServerSocket(port);
            
            while(true){
                
                link = server.accept();
                 input1 = new DataInputStream((link.getInputStream()));
                 output1 = new DataOutputStream(link.getOutputStream());
                 
                while(true){                                 
                    message=input1.readUTF();
                    if (message.equals("type")){
                       
                        l2s.setText("Typing...");
                        f.validate();
                        
                    }                                                                                      
                    else{
                        
                        l2s.setText("Online");                    
                        typing=true;
                        JPanel p2=formatLabel(message);
                        JPanel left=new JPanel(new BorderLayout());
                        left.add(p2,BorderLayout.LINE_START);
                        vertical.add(left);
                        f.validate();                                        
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Not connection..");
            System.exit(1);
        }
    }                                    
}
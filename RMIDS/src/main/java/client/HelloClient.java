package client;

import java.rmi.Naming;
import java.rmi.*;
import java.net.*;
import server.Hello;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Timer;

/**
 * Created by Allen Hu on 2016/11/22.
 */
public class HelloClient extends JFrame implements ActionListener{
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LabelPanel stime = null;
    private LabelPanel ctime = null;
    private JButton cfm, cancel;
    private JPanel ip, port, bts;
    private JLabel ipin, portin;
    private JTextField tf1, tf2;
    private Hello hlo;
    public HelloClient() {

        ip = new JPanel();
        port = new JPanel();
        bts = new JPanel();

        ipin = new JLabel("IP Address: ");
        ipin.setBounds(10, 80, 80, 25);
        portin = new JLabel("Port: ");
        portin.setBounds(10, 80, 80, 25);

        cfm = new JButton("Confirm");
        cancel = new JButton("Cancel");
        cfm.addActionListener(this);
        cancel.addActionListener(this);

        tf1 = new JTextField(20);
        tf2 = new JTextField(10);

        this.setLayout(new GridLayout(5, 1));

        ip.add(ipin);
        ip.add(tf1);
        port.add(portin);
        port.add(tf2);
        bts.add(cfm);
        bts.add(cancel);

        this.add(ip);
        this.add(port);
        this.add(bts);

        this.setTitle("Time Board");
        this.setSize(500, 200);              //窗体大小
        this.setLocationRelativeTo(null);    //在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //退出关闭JFrame
        this.setVisible(true);               //显示窗体
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Confirm") {
            connect();
        }
        else if (e.getActionCommand() == "Cancel") {
            System.exit(0);
        }
    }
    public void connect() {
        String ipaddr = tf1.getText();
        String port = tf2.getText();
        ipaddr = "114.212.85.80";
        port = "12306";
        String rmiaddr = "rmi://" + ipaddr + ":" + port + "/HQHello";
        try {
            hlo = (Hello) Naming.lookup(rmiaddr);
            ctime = new LabelPanel();
            getContentPane().add(new TimePanel("Local Time"));
            ctime.setBounds(10, 80, 80, 25);
            stime = new LabelPanel();
            stime.setBounds(10, 80, 80, 25);
            getContentPane().add(new TimePanel("Server Time"));
            Timer timer = new Timer();
            //timer.schedule(task, firstTime, period)
            timer.schedule(new ShowTime(hlo.getServertime()), new Date(), 1000);
            this.setVisible(true);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    class ShowTime extends TimerTask {
        String servertime;
        public ShowTime(String stm) {
            servertime = stm;
        }
        //刷新
        public void run() {
            ctime.label.setText(sdf.format(new Date()));
            try {
                stime.label.setText(hlo.getServertime());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
    class TimePanel extends JPanel {
        private String LocalorServer;
        public TimePanel(String lors) {
            LocalorServer = lors;
        }
        public void paint(Graphics g) {
            super.paint(g);
            g.drawString(String.valueOf(LocalorServer), 10, 10);
            g.drawString(sdf.format(new Date()), 90, 10);
        }
    }
    class LabelPanel extends JPanel {
        JLabel label = new JLabel("");
        JLabel note = new JLabel("Local Time");
        public LabelPanel() {
            add(note);
            add(label);
        }
    }
    public static void main(String args[]) {
//        try {
//            // Hello hlo = (Hello) Naming.lookup("rmi://114.212.85.80:12306/HQHello");
//            // Date dt = new Date();
//            //System.out.println("本机时间：" + dt.toString());
//            //System.out.println("服务器时间：" + hlo.getServertime());
//
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        HelloClient hc = new HelloClient();
    }
}

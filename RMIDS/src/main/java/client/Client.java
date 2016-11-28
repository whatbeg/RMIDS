package client;

import java.rmi.Naming;
import java.rmi.*;
import java.net.*;

import server.Clock;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Timer;

/**
 * Created by Allen Hu on 2016/11/22.
 */
public class Client extends JFrame implements ActionListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LabelPanel stime = null;
    private LabelPanel ctime = null;
    private JButton cfm, cancel;
    private JPanel ip, port, bts;
    private JLabel ipin, portin;
    private JTextField tf1, tf2;
    private Clock hlo;
    public Client() {                            // 基础UI

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
        if (e.getActionCommand().equals("Confirm")) {
            connect();
        }
        else if (e.getActionCommand().equals("Cancel")) {
            System.exit(0);
        }
    }
    public void connect() {
        String ipaddr = tf1.getText();
        String port = tf2.getText();
        if (ipaddr.equals(""))
            ipaddr = "114.212.85.80";
        if (port.equals(""))
            port = "1099";
        String rmiaddr = "rmi://" + ipaddr + ":" + port + "/clock";
        try {
            hlo = (Clock) Naming.lookup(rmiaddr);
            stime = new LabelPanel();
            stime.setBounds(10, 80, 80, 25);
            getContentPane().add(new TimePanel("1234"));
            Timer timer = new Timer();
            //timer.schedule(task, firstTime, period)
            timer.schedule(new ShowServerTime(), new Date(), 1000);
            this.setVisible(true);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    class ShowServerTime extends TimerTask {
        //刷新
        public void run() {
            repaint();
        }
    }
    class TimePanel extends JPanel {
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private String code;
        public TimePanel(String c) { code = c; }
        public void paint(Graphics g) {
            super.paint(g);
            g.drawString(String.valueOf("Server Time: "), 10, 10);
            try {
                long start = System.currentTimeMillis();
                long res = hlo.getServertime(code);
                long end = System.currentTimeMillis();    //cost 0.5
                System.out.println("Invoke Time: " + (end-start + 0.5));
                if (res == 0)
                    g.drawString("Request Denied!", 90, 10);
                else
                    g.drawString(sdf.format(res+(end-start+0.5)/2), 90, 10);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    class LabelPanel extends JPanel {
        JLabel label = new JLabel("");
        public LabelPanel() {
            add(label);
        }
    }
    public static void main(String args[]) {
        Client hc = new Client();
    }
}

package client;

import java.rmi.Naming;
import java.rmi.*;
import java.net.*;

import master.MasterCore;
import server.FileStore;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.io.*;

/**
 * Created by Allen Hu on 2016/11/22.
 */
public class Client extends JFrame implements ActionListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LabelPanel stime = null;
    private LabelPanel ctime = null;
    private JButton cfm, cancel, cget, cdown, cup;
    private JPanel ip, port, bts, other, fn;
    private JLabel ipin, portin, cip, visitno, filein;
    private JTextField tf1, tf2, tf3;
    private MasterCore masterCore;
    private FileStore hlo;
    public Client() {                            // 基础UI

        ip = new JPanel();
        port = new JPanel();
        fn = new JPanel();
        bts = new JPanel();
        other = new JPanel();

        ipin = new JLabel("IP Address: ");
        ipin.setBounds(10, 80, 80, 25);
        portin = new JLabel("Port: ");
        portin.setBounds(10, 80, 80, 25);
        filein = new JLabel("FileName: ");
        portin.setBounds(10, 80, 80, 25);

        cfm = new JButton("Login");
        cancel = new JButton("Cancel");
        cget = new JButton("Get FileList");
        cdown = new JButton("Download");
        cup = new JButton("Upload");
        cfm.addActionListener(this);
        cancel.addActionListener(this);
        cget.addActionListener(this);
        cup.addActionListener(this);
        cdown.addActionListener(this);

        tf1 = new JTextField(20);
        tf2 = new JTextField(10);
        tf3 = new JTextField(10);

        this.setLayout(new GridLayout(13, 1));

        ip.add(ipin);
        ip.add(tf1);
        port.add(portin);
        port.add(tf2);
        fn.add(filein);
        fn.add(tf3);
        bts.add(cfm);
        bts.add(cancel);
        bts.add(cget);
        bts.add(cup);
        bts.add(cdown);

        this.add(ip);
        this.add(port);
        this.add(fn);
        this.add(bts);
        this.add(other);

        this.setTitle("Time Board");
        this.setSize(700, 500);              //窗体大小
        this.setLocationRelativeTo(null);    //在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //退出关闭JFrame

        this.setVisible(true);               //显示窗体
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {  // 登录显示第几次访问
            connectMaster();
        }
        else if (e.getActionCommand().equals("Get FileList")) {
            GetFileList();
        }
        else if (e.getActionCommand().equals("Download")) {
            String fileName = tf3.getText();
            DOWNLOAD(fileName);
        }
        else if (e.getActionCommand().equals("Upload")) {
            String fileName = tf3.getText();
            UPLOAD(fileName);
        }
        else if (e.getActionCommand().equals("Cancel")) {
            System.exit(0);
        }
    }
    public void GetFileList() {
        try {
            ArrayList<String> fileList = masterCore.getFileList();
            System.out.println("Files in Server: ");
            JPanel panel = new JPanel();
            JLabel fl = new JLabel();
            String s = "\nFiles In Server:   \r\n";
            for (String file : fileList) {
                System.out.println(file);
                s += file + "   \r\n";
            }
            fl.setText(s);
            panel.add(fl);
            this.add(panel);
            this.setVisible(true);               //显示窗体
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void connectMaster() {
        String ipaddr = tf1.getText();
        String port = tf2.getText();
        if (ipaddr.equals(""))
            ipaddr = "114.212.85.80";
        if (port.equals(""))
            port = "1099";
        String rmiaddr = "rmi://" + ipaddr + ":" + port + "/master";
        try {
            masterCore = (MasterCore) Naming.lookup(rmiaddr);
            stime = new LabelPanel();
            stime.setBounds(10, 80, 80, 25);
            getContentPane().add(new TimePanel("1234"));
            Timer timer = new Timer();
            timer.schedule(new ShowServerTime(), new Date(), 1000);
            cip = new JLabel();
            visitno = new JLabel();
            cip.setText("客户端IP地址为: 114.212.80.21" + "\n");
            int logres = masterCore.login("1234");
            System.out.println("LOGRES: " + logres);
            if (logres == -1) {
                visitno.setText("登录失败!\n");
            }
            else if (logres == -2) {
                visitno.setText("用户名或者密码错误!");
            }
            else if (logres > 0) {
                visitno.setText("这是系统的第" + String.valueOf(logres) + "次访问！\n");
            }
            other.add(cip);
            other.add(visitno);
            this.setVisible(true);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public int connectServer() {
        String ipaddr = tf1.getText();
        String port = tf2.getText();
        if (ipaddr.equals(""))
            ipaddr = "114.212.85.80";
        if (port.equals(""))
            port = "1097";
        String rmiaddr = "rmi://" + ipaddr + ":" + port + "/clock";
        try {
            hlo = (FileStore) Naming.lookup(rmiaddr);
//            stime = new LabelPanel();
//            stime.setBounds(10, 80, 80, 25);
//            getContentPane().add(new TimePanel("1234"));
//            Timer timer = new Timer();
//            timer.schedule(new ShowServerTime(), new Date(), 1000);
//            this.setVisible(true);
            return 1;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
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
                long res = masterCore.getServerTime(code);
                long end = System.currentTimeMillis();    //cost 0.5
                // System.out.println("Invoke Time: " + (end-start + 0.5));
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
    public void UPLOAD(String fileName) {
        String gen = System.getProperty("user.dir");
        if (gen.contains("\\target\\classes"))
            gen = gen.replace("\\target\\classes", "");
        File outf = new File(gen + "\\src\\main\\java\\client\\" + fileName);
        int state = connectServer();
        if (state == 0) {
            System.out.println("连接服务器失败~");
            return;
        }
        else {
            System.out.println("连接服务器成功~");
        }
        try {
            File fdest = new File(gen + "\\src\\main\\java\\server\\" + fileName);
            if (!fdest.exists()) fdest.createNewFile();
            hlo.upload(outf, fdest);
            masterCore.chgFileList(fileName, 1);
            System.out.println("上传成功~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void DOWNLOAD(String fileName) {
        String gen = System.getProperty("user.dir");
        if (gen.contains("\\target\\classes"))
            gen = gen.replace("\\target\\classes", "");
        File downf = new File(gen + "\\src\\main\\java\\client\\" + fileName);
        int state = connectServer();
        if (state == 0) {
            System.out.println("连接服务器失败~");
            return;
        }
        else {
            System.out.println("连接服务器成功~");
        }
        try {
            File fdest = new File(gen + "\\src\\main\\java\\server\\" + fileName);
            if (!downf.exists()) downf.createNewFile();
            hlo.download(downf, fdest);
            masterCore.chgFileList(fileName, -1);
            System.out.println("下载成功~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        Client hc = new Client();
    }
}

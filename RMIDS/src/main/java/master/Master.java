package master;

import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.awt.event.*;
/**
 * Created by Allen Hu on 2016/12/20.
 */

public class Master extends JFrame implements ActionListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LabelPanel servtime = null;
    private JPanel pnl;
    private JButton cancel;
    private JLabel note;
    public Master(String text) {             //基础UI
        pnl = new JPanel();
        cancel = new JButton("Exit");
        cancel.addActionListener(this);
        note = new JLabel(text);
        this.setLayout(new GridLayout(2, 1));
        pnl.add(note);
        pnl.add(cancel);
        this.add(pnl);
        this.setTitle("Master Time Board");
        this.setSize(500, 200);              //窗体大小
        this.setLocationRelativeTo(null);    //在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //退出关闭JFrame
        this.setVisible(true);               //显示窗体
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }
    class ShowServerTime extends TimerTask {
        //刷新
        public void run() {
            repaint();
        }
    }
    class TimePanel extends JPanel {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawString(String.valueOf("Master Time:"), 10, 10);
            g.drawString(sdf.format(new Date()), 90, 10);
        }
    }
    class LabelPanel extends JPanel {
        JLabel label = new JLabel("");
        public LabelPanel() {
            add(label);
        }
    }
    public void display() {
        servtime = new LabelPanel();    //新建面板
        servtime.setBounds(10, 80, 80, 25);
        getContentPane().add(new TimePanel());
        Timer timer = new Timer();    //定时器
        //timer.schedule(task, firstTime, period)
        timer.schedule(new ShowServerTime(), new Date(), 1000); //一秒钟获取服务器时间一次
        this.setVisible(true);
    }
    public static void main(String args[]) {
        String ss = "";
        String ip, port;
        String []myArgs = {"", ""};
        if (args.length != 2) {
            System.out.println("Usage java master.Master <ipaddress> <port>");
            //System.exit(2);
            myArgs[0] = "114.212.85.80";
            myArgs[1] = "1099";
        }
        else {
            myArgs[0] = args[0];
            myArgs[1] = args[1];
        }
        ip = myArgs[0];
        port = myArgs[1];
        try {
            MasterCore mstc = new MasterImpl();
            LocateRegistry.createRegistry(Integer.parseInt(port));         //注册远程引用
            Naming.bind("rmi://114.212.85.80:1099/master", mstc);   //绑定命名
            ss = "Naming 绑定成功！";
        } catch (RemoteException e) {
            ss = "创建远程对象发生异常！";
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
            ss = "发生重复绑定对象异常！";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ss = "发生URL畸形异常！";
        }
        Master hs = new Master(ss);
        hs.display();
    }
}

package server;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Qiu Hu on 2016/11/22.
 */

public class ClockImpl extends UnicastRemoteObject implements Clock {
    private HashSet<String> Codes = new HashSet<String>();
    public ClockImpl() throws RemoteException {
        try {
            String gen = System.getProperty("user.dir");
            if (gen.contains("\\target\\classes"))
                gen = gen.replace("\\target\\classes", "");
            BufferedReader bf = new BufferedReader(new FileReader(gen + "\\src\\main\\java\\server\\code.txt"));
            String s;
            while ((s = bf.readLine()) != null) {
                Codes.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String helloWorld(String code) throws RemoteException {
        return "Hello World! RMI!";
    }
    public long getServertime(String code) throws RemoteException {
        if (Codes.contains(code))
            return System.currentTimeMillis();   //传回时间戳
        else
            return 0;      //无权限
    }
}

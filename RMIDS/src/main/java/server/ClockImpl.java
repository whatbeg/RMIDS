package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Qiu Hu on 2016/11/22.
 */

public class ClockImpl extends UnicastRemoteObject implements Clock {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ClockImpl() throws RemoteException {}
    public String helloWorld() throws RemoteException {
        return "Hello World! RMI!";
    }
    public String getServertime() throws RemoteException {
        Date dt = new Date();
        System.out.println(dt + "Invoked!");
        return sdf.format(dt);
    }
}

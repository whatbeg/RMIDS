package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Allen Hu on 2016/11/22.
 */

public class HelloImpl extends UnicastRemoteObject implements Hello {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public HelloImpl() throws RemoteException {}

    public String helloWorld() throws RemoteException {
        return "server.Hello World! RMI!";
    }
    public String SayHello(String name) throws RemoteException {
        return "server.Hello, " + name + "! Are you ok?";
    }
    public String getServertime() throws RemoteException {
        Date dt = new Date();
//        for (int i=0;i<10000;i++) {
//            for (int j=0;j<40000;j++) {
//                int t = i*j + 1;
//                t = t*t*t / t*12-12;
//            }
//        }
        System.out.println(new Date() + "Invoked!");
        return sdf.format(dt);
        //return "NONO";
    }
}

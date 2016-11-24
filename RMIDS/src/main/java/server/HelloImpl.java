package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by Allen Hu on 2016/11/22.
 */

public class HelloImpl extends UnicastRemoteObject implements Hello {
    public HelloImpl() throws RemoteException {}

    public String helloWorld() throws RemoteException {
        return "server.Hello World! RMI!";
    }
    public String SayHello(String name) throws RemoteException {
        return "server.Hello, " + name + "! Are you ok?";
    }
    public String getServertime() throws RemoteException {
        Date dt = new Date();
        System.out.println("Invoked");
        return dt.toString();
    }
}

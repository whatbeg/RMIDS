package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;

/**
 * Created by Allen Hu on 2016/11/22.
 */
public class HelloServer {
    public static void main(String args[]) {
        try {
            Hello hello = new HelloImpl();
            LocateRegistry.createRegistry(12306);
            Naming.bind("rmi://114.212.85.80:12306/HQHello", hello);
            System.out.println("Naming 绑定成功！");

        } catch (RemoteException e) {
            System.out.println("创建远程对象发生异常！");
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            System.out.println("发生重复绑定对象异常！");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("发生URL畸形异常！");
            e.printStackTrace();
        }
    }
}

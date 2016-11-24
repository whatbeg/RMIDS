package server; /**
 * Created by Allen Hu on 2016/11/22.
 */

import java.rmi.*;

public interface Hello extends Remote {

    public String SayHello(String name) throws RemoteException;
    public String helloWorld() throws RemoteException;
    public String getServertime() throws RemoteException;
}

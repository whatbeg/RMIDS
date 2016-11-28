package server;
/**
 * Created by Qiu Hu on 2016/11/22.
 */
import java.rmi.*;

public interface Clock extends Remote {
    public String helloWorld() throws RemoteException;
    public String getServertime() throws RemoteException;
}

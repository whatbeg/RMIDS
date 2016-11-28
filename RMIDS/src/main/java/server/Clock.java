package server;
/**
 * Created by Qiu Hu on 2016/11/22.
 */
import java.rmi.*;

public interface Clock extends Remote {
    public String helloWorld(String code) throws RemoteException;
    public long getServertime(String code) throws RemoteException;
}

package master;

/**
 * Created by Allen Hu on 2016/12/20.
 */
import java.rmi.*;
import java.util.ArrayList;

public interface MasterCore extends Remote {
    public long getServerTime(String code) throws RemoteException;
    public int login(String code) throws RemoteException;
    public ArrayList<String> getFileList() throws RemoteException;
    public String getUserIP() throws RemoteException;
    public void chgFileList(String fileName, int type) throws RemoteException;
}

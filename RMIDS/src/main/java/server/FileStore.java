package server;
/**
 * Created by Qiu Hu on 2016/11/22.
 */
import java.rmi.*;
import java.io.*;

public interface FileStore extends Remote {
    public void upload(File outf, File f) throws RemoteException, IOException;
    public void download(File downf, File f) throws RemoteException, IOException;
}

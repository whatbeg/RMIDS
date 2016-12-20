package server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Qiu Hu on 2016/11/22.
 */

public class FSImpl extends UnicastRemoteObject implements FileStore {
    private HashSet<String> Codes = new HashSet<String>();
    public FSImpl() throws RemoteException {
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
    public void upload(File outf, File f) throws RemoteException, IOException {
        BufferedReader bf = new BufferedReader(new FileReader(outf));
        BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));   // 覆盖
        String s;
        while((s = bf.readLine()) != null) {
            bw.write(s);
        }
        bw.flush();
        bw.close();
        bf.close();
    }
    public void download(File downf, File f) throws RemoteException, IOException{
        BufferedReader bf = new BufferedReader(new FileReader(f));
        BufferedWriter bw = new BufferedWriter(new FileWriter(downf, false));   // 覆盖
        String s;
        while ((s = bf.readLine()) != null) {
            bw.write(s);
        }
        bw.flush();
        bw.close();
        bf.close();
    }
}

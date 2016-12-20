package master;

import master.MasterCore;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Allen Hu on 2016/12/20.
 */
public class MasterImpl extends UnicastRemoteObject implements MasterCore {
    private HashSet<String> Codes = new HashSet<String>();
    public MasterImpl() throws RemoteException {
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
    public long getServerTime(String code) throws RemoteException {
        if (Codes.contains(code))
            return System.currentTimeMillis();   //传回时间戳
        else
            return 0;      //无权限
    }
    public int login(String code) throws RemoteException {
        if (Codes.contains(code)) {
            try {
                String gen = System.getProperty("user.dir");
                if (gen.contains("\\target\\classes"))
                    gen = gen.replace("\\target\\classes", "");
                File f = new File(gen + "\\src\\main\\java\\master\\visitnum.txt");
//                System.out.println(gen + "\\src\\main\\java\\master\\visitnum.txt");
                BufferedReader bf = new BufferedReader(new FileReader(f));
                String s;
                int vis_num;
                if ((s = bf.readLine()) != null) {
                    vis_num = Integer.valueOf(s);
                }
                else
                    vis_num = 0;
                vis_num++;
                bf.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(gen + "\\src\\main\\java\\master\\visitnum.txt"));
                System.out.print("visnum = " + vis_num);
                bw.write(String.valueOf(vis_num));
                bw.newLine();
                bw.flush();
                bw.close();
                return vis_num;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
            return -1;
        }
        else
            return -2;   //密码错误
    }
    public ArrayList<String> getFileList() throws RemoteException {
        ArrayList<String> fileList = new ArrayList<String>();
        try {
            String gen = System.getProperty("user.dir");
            if (gen.contains("\\target\\classes"))
                gen = gen.replace("\\target\\classes", "");
            BufferedReader bf = new BufferedReader(new FileReader(gen + "\\src\\main\\java\\master\\FileList.txt"));
            String s;
            while ((s = bf.readLine()) != null) {
                fileList.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }
    public String getUserIP() throws RemoteException {
        return "";
    }
    public void chgFileList(String fileName, int type) {
        ArrayList<String> fileList = new ArrayList<String>();
        String gen = System.getProperty("user.dir");
        try {
            if (gen.contains("\\target\\classes"))
                gen = gen.replace("\\target\\classes", "");
            BufferedReader bf = new BufferedReader(new FileReader(gen + "\\src\\main\\java\\master\\FileList.txt"));
            String s;
            while ((s = bf.readLine()) != null) {
                fileList.add(s);
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (type == -1) {
            return;
        }
        if (type == 1 && fileList.contains(fileName)) {
            System.err.println("已经存在同名文件~");
        }
        else if (type == 0 && !fileList.contains(fileName)) {
            System.err.println("您要删除的文件不存在~");
        }
        else if (type == 1){
            fileList.add(fileName);
        }
        else if (type == 0) {
            fileList.remove(fileName);
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(gen + "\\src\\main\\java\\master\\FileList.txt"));
            for (String fl : fileList) {
                bw.write(fl);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

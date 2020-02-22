package org.example.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenneth
 * @date 2020/2/21
 */
public class SystemInfo {
    private static String macAddressStr = null;
    private static String computerName = System.getenv().get("COMPUTERNAME");

    private static final String[] windowsCommand = {"ipconfig", "/all"};
    private static final String[] linuxCommand = {"/sbin/ifconfig", "-a"};
    private static final Pattern macPattern =
            Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);


    /**
     * Method for get System Name
     * 
     * @return Host name
     */
    public static String getSystemName() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost(); // Get LocalHost refrence
            String name = inetaddress.getHostName(); // Get Host Name
            return name; // return Host Name
        } catch (Exception E) {
            E.printStackTrace(); // print Exception StackTrace
            return null;
        }
    }

    /**
     * method to get Host IP
     * 
     * @return Host IP Address
     */
    public static String getIPAddress() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost(); // Get LocalHost refrence
            String ip = inetaddress.getHostAddress(); // Get Host IP Address
            return ip; // return IP Address
        } catch (Exception E) {
            E.printStackTrace(); // print Exception StackTrace
            return null;
        }

    }


    private final static List<String> getMacAddressList() throws IOException {
        final ArrayList<String> macAddressList = new ArrayList<String>();
        final String os = System.getProperty("os.name");
        final String command[];

        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else if (os.startsWith("Linux")) {
            command = linuxCommand;
        } else if (os.startsWith("Mac OS X")) {
            command = linuxCommand;
        } else {
            throw new IOException("Unknow operating system:" + os);
        }
        // 执行命令
        final Process process = Runtime.getRuntime().exec(command);

        BufferedReader bufReader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = bufReader.readLine()) != null;) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
                // macAddressList.add(matcher.group(1).replaceAll("[-:]",
                // ""));//去掉MAC中的“-”
            }
        }

        process.destroy();
        bufReader.close();
        return macAddressList;
    }


    /**
     * method to get Host Mac Address
     * 
     * @return Mac Address
     */
    public static String getMAC() {
        if (macAddressStr == null || macAddressStr.equals("")) {
            StringBuffer sb = new StringBuffer(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
            try {
                List<String> macList = getMacAddressList();
                for (Iterator<String> iter = macList.iterator(); iter.hasNext();) {
                    String amac = iter.next();
                    if (!amac.equals("0000000000E0")) {
                        sb.append(amac);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            macAddressStr = sb.toString();

        }
        return macAddressStr;
    }

}

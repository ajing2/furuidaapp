package com.furuida.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.trilead.ssh2.StreamGobbler;
import org.apache.log4j.Logger;


/**
 * Provides static methods for running SSH, scp as well as local commands.
 * @author 付正全
 */
public class ExecCommand {
    /**
     * 测试标志.
     */
    // String debugflg = PropertiesUtil.getProperty("debugflg");
    /**
     * 记录日志.
     */
    public static final Logger LOG = Logger.getLogger(ExecCommand.class);

    /**
     *      scpGet(java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     * @param host
     *            host。
     * @param username
     *            username
     * @param password
     *            password
     * @param remoteFile
     *            remoteFile
     * @param localDir
     *            localDir
     * @throws IOException
     *             IOException
     */
//    public void scpGet(String host, String username, String password, String remoteFile, String localDir)
//            throws IOException {
//        Connection conn = getOpenedConnection(host, username, password);
//        SCPClient client = new SCPClient(conn);
//        client.get(remoteFile, localDir);
//        conn.close();
//    }

    /**
     *      scpPut(java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)。
     * @param host
     *            host
     * @param username
     *            username
     * @param password
     *            password
     * @param localFile
     *            localFile
     * @param remoteDir
     *            remoteDir
     * @throws IOException
     *             IOException
     */
//    public void scpPut(String host, String username, String password, String localFile, String remoteDir)
//            throws IOException {
//        Connection conn = getOpenedConnection(host, username, password);
//        SCPClient client = new SCPClient(conn);
//        client.put(localFile, remoteDir);
//        conn.close();
//    }

    /**
     *      java.lang.String, java.lang.String)
     * @param host
     *            host
     * @param username
     *            username
     * @param password
     *            password
     * @param cmd
     *            cmd
     * @return List<String>
     * @throws IOException
     *             IOException
     */
//    public List<String> runSSH(String host, String username, String password, List<String> cmd) throws IOException {
//        // logger.debug("running SSH cmd [" + cmd + "]");
//        // int value = 0;
//        Connection conn = getOpenedConnection(host, username, password);
//        List<String> result = new ArrayList<String>();
//        if (null != cmd) {
//            for (String str : cmd) {
//                if (null != str && !"".equals(cmd)) {
//                    Session sess = conn.openSession();
//                    sess.execCommand(str);
//                    InputStream stdout = new StreamGobbler(sess.getStdout());
//                    @SuppressWarnings("resource")
//                    BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//
//                    while (true) {
//                        // attention: do not comment this block, or you will hit
//                        // NullPointerException
//                        // when you are trying to read exit status
//                        String line = br.readLine();
//                        if (line == null) {
//                            break;
//                        } else {
//                            // logger.info(line);
//                            // System.out.println(line);
//                            // resultbuf += line + "\n";
//                            result.add(line);
//                        }
//                    }
//                    /*
//                     * if (null != sess && null != sess.getExitStatus()) {
//                     * value = sess.getExitStatus().intValue();
//                     * } else {
//                     * value = -1;
//                     * }
//                     */
//                    sess.close();
//                }
//            }
//        }
//        conn.close();
//        if (result.isEmpty()) {
//            result = null;
//        }
//        return result;
//    }

    /**
     * 远程SSH（单命令发送）.
     * @param host
     *            服务端ip地址
     * @param username
     *            服务端username
     * @param password
     *            服务端password
     * @param cmd
     *            执行命令
     * @return List<String>
     * @throws IOException
     *             IOException
     */
//    public List<String> runSSHOneCmd(String host, String username, String password, String cmd) throws IOException {
//        LOG.debug("running SSH cmd [" + cmd + "]");
//        Connection conn = getOpenedConnection(host, username, password);
//        List<String> result = new ArrayList<String>();
//        if (null != cmd) {
//            Session sess = conn.openSession();
//            sess.execCommand(cmd);
//            InputStream stdout = new StreamGobbler(sess.getStdout());
//            @SuppressWarnings("resource")
//            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//            while (true) {
//                // attention: do not comment this block, or you will hit
//                // NullPointerException
//                // when you are trying to read exit status
//                String line = br.readLine();
//                if (line == null) {
//                    break;
//                } else {
//                    result.add(line);
//                }
//            }
//        }
//        conn.close();
//        return result;
//    }

    /**
     * return a opened Connection.
     * @param host
     *            host
     * @param username
     *            username
     * @param password
     *            password
     * @return Connection
     * @throws IOException
     *             IOException
     */
//    private Connection getOpenedConnection(String host, String username, String password) throws IOException {
//        Connection conn = new Connection(host);
//        conn.connect(); // make sure the connection is opened
//        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
//        if (!isAuthenticated) {
//            throw new IOException("Authentication failed.");
//        }
//        return conn;
//    }

    /**
     * @param cmd
     *            cmd
     * @return List<String>
     * @throws IOException
     *             IOException
     */
    public String runLocal(String cmd) throws IOException {
        // logger.debug("running local cmd [" + cmd + "]");
        // System.out.println("running local cmd [" + cmd + "]");
        // Runtime rt = Runtime.getRuntime();
        String[] cmds = new String[] {"/bin/sh", "-c", cmd }; // edit by lufeng
        // Process p = rt.exec(cmds);

        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();
        InputStream stdout = new StreamGobbler(p.getInputStream());
        @SuppressWarnings("resource")
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        String result = new String();
        String resultbuf = "";
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            } else {
                resultbuf += line + "\n";
            }
        }
        result = resultbuf;
        // result.add(Integer.toString(p.exitValue()));
        return result;
    }

    /**
     * @param cmd
     *            cmd
     * @return List<String>
     * @throws IOException
     *             IOException
     */
    public String runLocaln(String cmd) throws IOException {
        // logger.debug("running local cmd [" + cmd + "]");
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(cmd);
        InputStream stdout = new StreamGobbler(p.getInputStream());
        @SuppressWarnings("resource")
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        String result = new String();
        String resultbuf = "";
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            } else {
                resultbuf += line;
            }
        }
        result = resultbuf;
        return result;
    }

    /**
     * @param cmd
     *            cmd
     * @return List<String>
     * @throws IOException
     *             IOException
     */
    public List<String> runCmd(String cmd) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();
        if (p != null) {
            InputStream stdout = new StreamGobbler(p.getErrorStream());
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            List<String> result = new ArrayList<String>();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    result.add(line);
                }
            }
            return result;
        } else {
            System.out.println("no pid\r\n");
            return null;
        }

    }

    /**
     * @param cmd
     *            cmd
     * @return List<String>
     * @throws IOException
     *             IOException
     */
    public List<String> runLocallist(String cmd) throws IOException {
        BufferedReader bufferedReader = null;
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(cmd);
        if (p != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            List<String> result = new ArrayList<String>();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                } else {
                    result.add(line);
                }
            }
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new IOException("run command failed！" + e.toString(), e);
            }
            p.destroy();
            return result;
        } else {
            System.out.println("no pid\r\n");
            return null;
        }
    }
    /**
     * 复杂linux命令执行，需要在命令前加入“\bash\sh, -c”.
     * @see
     * @param opscmd
     *            cmd
     * @return List<String>
     * @throws IOException
     *             IOException
     */
    public String runLocal(String ... opscmd) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(opscmd);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = new String();
        String resultbuf = "";
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            } else {
                resultbuf += line + "\n";
            }
        }
        result = resultbuf;
        if (null != br) {
            br.close();
        }
        return result;
    }
}

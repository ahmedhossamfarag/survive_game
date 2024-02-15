package engine;

import views.scene.Window;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Network {

    //Listen Port
    public static final int port2 = 6789;

    //Send Port
    public static final int port = 1234;

    public static String host;

    public static boolean run;

    public static void send(Update u) {
        if (!Game.gameId) return;
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Socket client = new Socket(host, port2);
                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
                    out.writeUTF(u.toString());
                    client.shutdownInput();
                    client.close();
                    break;
                } catch (IOException e) {
                    if (i == 4 && Value.netEnabled.getValue()) {
                        JOptionPane.showMessageDialog(Main.window, "Connection Problem!!");
                    } else
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ignored) {
                        }
                }
            }
        });
        thread.start();
    }


    public static void createGame() {
        run = true;
        Window.introScene.gameIdScene.setSuccess(false);
        Thread thread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                socket.setSoTimeout(60000);
                Socket server = socket.accept();
                server.shutdownOutput();
                server.shutdownInput();
                host = server.getInetAddress().getHostName();
                server.close();
                Window.introScene.gameIdScene.setSuccess(true);
            } catch (SocketTimeoutException e) {
                JOptionPane.showMessageDialog(Main.window, "Time out!!");
                Window.introScene.set(Window.introScene.chooseScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
            run = false;
        });
        thread.start();
    }

    public static void enterGame(String host) throws IOException {
        Socket client = new Socket(host, port2);
        client.shutdownInput();
        client.shutdownOutput();
        Network.host = host;
        client.close();
    }


    public static void exit() {
        run = false;
    }


    public static void read() {
        run = true;
        Thread thread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                while (run) {
                    Socket server = socket.accept();
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    String s = in.readUTF();
                    try {
                        Update.execute(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    server.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}

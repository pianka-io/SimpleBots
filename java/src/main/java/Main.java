import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        String server = "ash.wserv.org";
        int port = 6112;
        String username = "username";
        String password = "password";
        String home = "dark";

        Socket socket = connect(server, port);
        PrintWriter writer = output(socket);
        BufferedReader reader = input(socket);

        login(writer, username, password, home);
        send(writer, "hello!");

        loop(socket, reader);

        disconnect(socket);
    }

    private static Socket connect(String server, Integer port) throws IOException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(server, port);

        socket.connect(address);

        return socket;
    }

    private static void disconnect(Socket socket) throws IOException {
        socket.close();
    }

    private static PrintWriter output(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    private static BufferedReader input(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private static void login(PrintWriter writer, String username, String password, String home) {
        writer.println("C1\r\n");
        writer.println("ACCT " + username + "\r\n");
        writer.println("PASS " + password + "\r\n");
        writer.println("HOME " + home + "\r\n");
        writer.println("LOGIN\r\n");
    }

    private static void send(PrintWriter writer, String data) {
        writer.println(data + "\r\n");
    }

    private static void loop(Socket socket, BufferedReader reader) throws IOException {
        while (!socket.isClosed() && socket.isConnected()) {
            String data = reader.readLine();
            if (data == null) continue;

            System.out.println(data);
        }
    }
}

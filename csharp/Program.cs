using System.Net.Sockets;
using System.Text;

class Program
{
    static void Main()
    {
        string server = "ash.wserv.org";
        int port = 6112;
        string username = "username";
        string password = "password";
        string home = "warnet";

        using (var socket = Connect(server, port))
        {
            Login(socket, username, password, home);
            Send(socket, "hello!");

            Loop(socket);

            Disconnect(socket);
        }
    }

    static TcpClient Connect(string server, int port)
    {
        var client = new TcpClient(server, port);
        return client;
    }

    static void Disconnect(TcpClient client)
    {
        client.Close();
    }

    static void Login(TcpClient client, string username, string password, string home)
    {
        Send(client, "C1");
        Send(client, "ACCT " + username);
        Send(client, "PASS " + password);
        Send(client, "HOME " + home);
        Send(client, "LOGIN");
    }

    static void Send(TcpClient client, string data)
    {
        var stream = client.GetStream();
        byte[] buffer = Encoding.UTF8.GetBytes(data + "\r\n");
        stream.Write(buffer, 0, buffer.Length);
    }

    static void Loop(TcpClient client)
    {
        var stream = client.GetStream();
        using (var reader = new StreamReader(stream, Encoding.UTF8))
        {
            while (true)
            {
                string line = reader.ReadLine()?.Trim();
                if (line == null) break; // Break if the server closes the connection
                Console.WriteLine(line);
            }
        }
    }
}

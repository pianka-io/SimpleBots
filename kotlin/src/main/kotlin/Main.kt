import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

fun main() {
    val server = "ash.wserv.org"
    val port = 6112
    val username = "username"
    val password = "password"
    val home = "dark"

    val socket = connect(server, port)
    val writer = output(socket)
    val reader = input(socket)

    login(writer, username, password, home)
    send(writer, "hello!")

    loop(socket, reader)

    disconnect(socket)
}

fun connect(server: String, port: Int): Socket {
    val socket = Socket()
    val address = InetSocketAddress(server, port)

    socket.connect(address)

    return socket
}

fun disconnect(socket: Socket) {
    socket.close()
}

fun output(socket: Socket): PrintWriter {
    return PrintWriter(socket.getOutputStream(), true)
}

fun input(socket: Socket): BufferedReader {
    return BufferedReader(InputStreamReader(socket.inputStream))
}

fun login(writer: PrintWriter, username: String, password: String, home: String) {
    writer.println("C1\r\n")
    writer.println("ACCT ${username}\r\n")
    writer.println("PASS ${password}\r\n")
    writer.println("HOME ${home}\r\n")
    writer.println("LOGIN\r\n")
}

fun send(writer: PrintWriter, data: String) {
    writer.println("${data}\r\n")
}

fun loop(socket: Socket, reader: BufferedReader) {
    while (!socket.isClosed && socket.isConnected) {
        val data = reader.readLine() ?: continue
        println(data)
    }
}

import socket as net


def main():
    server = "ash.wserv.org"
    port = 6112
    username = "username"
    password = "password"
    home = "dark"

    socket = connect(server, port)
    login(socket, username, password, home)
    send(socket, "hello!")

    loop(socket)

    disconnect(socket)


def connect(server, port):
    socket = net.socket(net.AF_INET, net.SOCK_STREAM)
    socket.connect((server, port))

    return socket


def disconnect(socket):
    socket.shutdown()
    socket.close()


def login(socket, username, password, home):
    send(socket, "C1")
    send(socket, "ACCT " + username)
    send(socket, "PASS " + password)
    send(socket, "HOME " + home)
    send(socket, "LOGIN")


def send(socket, data):
    socket.sendall((data + "\r\n").encode("utf-8"))


def loop(socket):
    file = socket.makefile()

    while True:
        line = file.readline().strip()
        print(line)


if __name__ == '__main__':
    main()

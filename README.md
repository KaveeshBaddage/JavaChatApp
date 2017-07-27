# JavaChatApp
The chat application is implemented by using Java web sockets. Here multiple clients can communicate with each other.A client can send message to another client or send messages to everyone.

To run this application first you have to compile MultiThreadChatClient.java and MultiThreadServer.java files.

Files can be compile using following commands.

    javac MultiThreadServer.java
    javac MultiThreadChatClient.java 

Now you can run several clients and the server.

First, run the server using following code.

    java MultiThreadServer

Next, you need to run following code several time according to a number of clients that needs.

    java MultiThreadChatClient

As an example, if you need to run five clients, you need to run above command five times in five terminals. Client ID is an integer which based on the running order of the file.(The first terminal which runs the above command is regarding to the first client and that client's ID is 1.)

You should type message according to the defined message format.If the message is not in correct format "Invalid Message" will be shown in terminal.

If you need to send a message to single recipient

    sendmsg <message> to <recipientID>

    ex - sendmsg HelloClint1 to 1

Here, If the specified recipient has not started yet, terminal will show "User offline" message.  

If you need to send a message to everyone

    sendtoall HelloEveryone

Client can ask from the server to do simple calculation and get that answer to him or send it to another specified client.

    calculate <operand1> <operator> <operand1> to <recipient's ID> 


Ex calculate 1 + 2 to 2

Here answer is calculated by server and send it to the client 2
If a client wants to get an answer for a calculation, recipient's ID should be his ID. 


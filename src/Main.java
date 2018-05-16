import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Main extends JFrame
{
    private ServerSocket server;
    private boolean run_server = true;
    private int red, green, blue;
    private String id = "Lumen_client";
    private JPanel panel;

    public Main()
    {
        super("Lumen");
        setSize(new Dimension(640, 480));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel();
        //panel.setBackground(Color.red);

        this.add(panel);
        try
        {
            server = new ServerSocket(4242);
            System.out.println("..::Init Lumen Server, running on port 4242::..");
            listen();
        } catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void listen()
    {
        Thread tListen = new Thread(() ->
        {
            try
            {
                System.out.println("Starting server...");
                while(run_server)
                {
                    Socket client = server.accept();
                    System.out.println("Received connection..");
                    if(client!=null)
                    {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        if(in!=null)
                        {
                            String line;
                            while ((line = in.readLine())!=null)
                            {
                                System.out.println("Request: " + line);
                                StringTokenizer tokenizer = new StringTokenizer(line, "\t");
                                String cmd = tokenizer.nextToken();
                                switch (cmd.toUpperCase())
                                {
                                    case "HELLO":
                                        sendMessage("HELLO\t" + id);
                                        break;
                                    case "SETR":
                                        if(tokenizer.hasMoreTokens())
                                        {
                                            try
                                            {
                                                red = Integer.parseInt(tokenizer.nextToken());
                                                panel.setBackground(new Color(red, green, blue));
                                                //scene.setFill(Color.color(red, green, blue));
                                                System.out.println("Updated red value.");
                                                sendMessage("ACK");
                                            }catch (NumberFormatException e)
                                            {
                                                System.err.println(e.getMessage() + "\nAre you fucking stupid?");
                                            }
                                        }else{
                                            System.err.println("Invalid token count!");
                                        }
                                        break;
                                    case "SETG":
                                        if(tokenizer.hasMoreTokens())
                                        {
                                            try
                                            {
                                                green = Integer.parseInt(tokenizer.nextToken());
                                                panel.setBackground(new Color(red, green, blue));
                                                //scene.setFill(Color.color(red, green, blue));
                                                System.out.println("Updated green value.");
                                                sendMessage("ACK");
                                            }catch (NumberFormatException e)
                                            {
                                                System.err.println(e.getMessage() + "\nAre you fucking stupid?");
                                            }
                                        }else{
                                            System.err.println("Invalid token count!");
                                        }
                                        break;
                                    case "SETB":
                                        if(tokenizer.hasMoreTokens())
                                        {
                                            try
                                            {
                                                blue = Integer.parseInt(tokenizer.nextToken());
                                                panel.setBackground(new Color(red, green, blue));
                                                //scene.setFill(Color.color(red, green, blue));
                                                System.out.println("Updated blue value.");
                                                sendMessage("ACK");
                                            }catch (NumberFormatException e)
                                            {
                                                System.err.println(e.getMessage() + "\nAre you fucking stupid?");
                                            }
                                        }else{
                                            System.err.println("Invalid token count!");
                                        }
                                        break;
                                    default:
                                        System.err.println("Unknown command '" + cmd + "'");
                                        break;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        tListen.start();
    }

    public boolean sendMessage(String message) throws IOException
    {
        //Log.d("sendMessage" ,">>>>" + InetAddress.getLocalHost().getHostAddress());
        /*Socket dest = new Socket(InetAddress.getByName("192.168.43.31"),4243);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(dest.getOutputStream()));
        if(out==null)
            return false;
        out.write(message);
        out.flush();
        out.close();*/
        return true;
    }

    /*public boolean sendMessage(String message, Socket dest) throws IOException
    {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(dest.getOutputStream()));
        if(out==null)
            return false;
        out.write(message);
        out.flush();
        out.close();
        return true;
    }*/

    public static void main(String[] args)
    {
        new Main().setVisible(true);
    }
}

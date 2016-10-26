package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

class SendMessage implements Runnable {

    BufferedReader readConsole;
    PrintWriter out;

    public SendMessage(BufferedReader readConsole, PrintWriter out) {
        this.readConsole = readConsole;
        this.out = out;
    }

    @Override
    public void run() {
        String readLine;
        try {
            while (true) {
                readLine = readConsole.readLine();
                out.println(readLine);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
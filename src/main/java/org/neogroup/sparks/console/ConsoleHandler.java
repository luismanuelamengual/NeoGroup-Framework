
package org.neogroup.sparks.console;

public abstract class ConsoleHandler implements Runnable {

    private final Console console;
    private String prompt;
    private boolean running;

    public ConsoleHandler(Console console) {
        this.console = console;
        this.prompt = "$";
        this.running = false;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Console getConsole() {
        return console;
    }

    public void close () {
        running = false;
        console.close();
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        running = true;
        while (running && !console.isClosed()) {
            console.write(prompt);
            console.write(" ");
            console.flush();
            String command = console.readLine();
            try { onCommandEntered (console, new Command(command)); } catch (Exception ex) {}
        }
        running = false;
    }

    protected abstract void onCommandEntered (Console console, Command command);
}

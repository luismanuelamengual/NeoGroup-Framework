
package org.neogroup.sparks.console;

import java.io.*;

public class Console {

    private boolean closed;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public Console (InputStream in, OutputStream out) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.writer = new PrintWriter(out);
        closed = false;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public Console close()  {
        if (!closed) {
            try { reader.close(); } catch (Exception ex) {}
            try { writer.close(); } catch (Exception ex) {}
            closed = true;
        }
        return this;
    }

    public boolean isClosed () {
        if (!closed && writer.checkError()) {
            close();
        }
        return closed;
    }

    public String readLine()  {
        String line = null;
        try { line = reader.readLine(); } catch (Exception ex) { close(); }
        return line;
    }

    public boolean ready() {
        boolean ready = false;
        try { ready = reader.ready(); } catch (IOException e) { close(); }
        return ready;
    }

    public int read() {
        int read = 0;
        try { read = reader.read(); } catch (IOException e) { close(); }
        return read;
    }

    public int read(char[] cbuf, int off, int len) {
        int read = 0;
        try { read = reader.read(cbuf, off, len); } catch (IOException e) { close(); }
        return read;
    }

    public Console flush() {
        writer.flush();
        return this;
    }

    public Console write(String buf) {
        writer.write(buf);
        return this;
    }

    public Console write(int c) {
        writer.write(c);
        return this;
    }

    public Console write(char[] cbuf, int off, int len) {
        writer.write(cbuf, off, len);
        return this;
    }

    public Console write(String s, int off, int len) {
        writer.write(s, off, len);
        return this;
    }

    public Console newLine() {
        writer.println();
        return this;
    }

    public void print(boolean b) {
        writer.print(b);
    }

    public void print(char c) {
        writer.print(c);
    }

    public void print(int i) {
        writer.print(i);
    }

    public void print(long l) {
        writer.print(l);
    }

    public void print(float f) {
        writer.print(f);
    }

    public void print(double d) {
        writer.print(d);
    }

    public void print(char[] s) {
        writer.print(s);
    }

    public void print(String s) {
        writer.print(s);
    }

    public void print(Object obj) {
        writer.print(obj);
    }

    public void println() {
        writer.println();
    }

    public void println(boolean x) {
        writer.println(x);
    }

    public void println(char x) {
        writer.println(x);
    }

    public void println(int x) {
        writer.println(x);
    }

    public void println(long x) {
        writer.println(x);
    }

    public void println(float x) {
        writer.println(x);
    }

    public void println(double x) {
        writer.println(x);
    }

    public void println(char[] x) {
        writer.println(x);
    }

    public void println(String x) {
        writer.println(x);
    }

    public void println(Object x) {
        writer.println(x);
    }
}

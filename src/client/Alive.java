package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Mensaje;

public class Alive extends Thread {

	private Socket socket;
	private ObjectOutputStream out;

	public void run() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			while (true) {
				out.writeObject(new Mensaje(Mensaje.ALIVE, null));
				sleep(5000); // timeout es 10 seg, esto manda una señal cada 5 seg.
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		catch (InterruptedException e2) {
			e2.printStackTrace();
			System.err.println("\n Thread Alive: sueño interrumpido.\n");
		}

		finally {
			System.out.println("\n Thread Alive acaba de morir.\n");
		}

	}
}

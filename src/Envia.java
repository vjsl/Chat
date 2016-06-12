import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Envia {
	private String IPDestino;
	private Socket socket;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public Envia(String ipDestino){
		this.IPDestino = ipDestino;
		try {
			this.socket = new Socket(IPDestino,3322);
			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);
			dataInputStream = new DataInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void controle(String mensagem){
		int value=0;
		try {
			dataOutputStream.writeUTF(mensagem);			
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public int enviar(String mensagem){
		int value=0;
		try {
			dataOutputStream.writeUTF(mensagem);
			String retorno = dataInputStream.readUTF();
			if(retorno.equals("ok")){
				value=1;
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
		return value;

	}
	public void enviaNome(String nome){
		try {
			dataOutputStream.writeUTF(nome);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setIP(String IP){

	}


}

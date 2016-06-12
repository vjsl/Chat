import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Manipulador {
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private File file;


	public Manipulador(String nome){
		try{
			file = new File(nome);
			file.createNewFile();
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(file,true);
			bufferedWriter = new BufferedWriter(fileWriter);
		}catch(FileNotFoundException ex){
			JOptionPane.showMessageDialog(null, "Já verifiquei e você não tem mensagens antigas com este usuário!");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public void grava(String mensagem) throws IOException{
		bufferedWriter.write(mensagem);
		bufferedWriter.flush();

	}
	public List le(){
		List<String> mensagens = new ArrayList<String>();
		try {
			String mensagem;
			do{
				mensagem = bufferedReader.readLine();
				if(mensagem!=null){
					mensagens.add(mensagem+"\r\n");
				}
			}while(mensagem!=null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mensagens;

	}
}

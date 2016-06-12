
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class PegaIPLocal {
	
	private InetAddress myself;
	private String ip;
	public PegaIPLocal(){
	
	}
	public String getIP(){
		try {
			  this.myself = InetAddress.getLocalHost();
			  this.ip = myself.getHostAddress();
			}
			  catch (UnknownHostException ex) {
			    JOptionPane.showMessageDialog(null,"Desculpe não consegui identificar seu endereço IP", "Erro!", JOptionPane.ERROR_MESSAGE);
			  }
		return this.ip; 
	}	

}

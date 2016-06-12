import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.io.DataOutputStream;

public class UI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private String userName;
	private int status;
	private PegaIPLocal ip;
	private JTextArea textArea;
	private Envia envia;
	private File file;
	private String nome;
	private JTextField textArea_1;
	private DataOutputStream dataOutputStream;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public UI() throws Exception {
		ip = new PegaIPLocal();
		recebe();
		setResizable(false);

		setIconImage(Toolkit.getDefaultToolkit().getImage(UI.class.getResource("/icons/Messenger.png")));
		setTitle("Projeto 02 - Chatting Messenger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(344, 11, 86, 20);
		contentPane.add(textField);
		textField.setText(ip.getIP());
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {				
					envia = new Envia(textField_1.getText());
				}
			}
		});
		textField_1.setBounds(344, 33, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 62, 420, 111);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);

		JLabel lblSeuIp = new JLabel("Seu IP:");
		lblSeuIp.setBounds(291, 14, 46, 14);
		contentPane.add(lblSeuIp);

		JLabel lblIpDoContato = new JLabel("IP do contato:");
		lblIpDoContato.setBounds(254, 36, 93, 14);
		contentPane.add(lblIpDoContato);

		JLabel lblInfraestruturaDeComunicao = new JLabel("Infraestrutura de Comunica\u00E7\u00E3o - Projeto 02");
		lblInfraestruturaDeComunicao.setBounds(10, 7, 254, 18);
		contentPane.add(lblInfraestruturaDeComunicao);

		JLabel lblSeuNome = new JLabel("Seu nome:");
		lblSeuNome.setBounds(10, 34, 63, 18);
		contentPane.add(lblSeuNome);

		textField_2 = new JTextField();
		textField_2.setBounds(74, 32, 93, 22);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblStatusDaltima = new JLabel("Status da \u00FAltima mensagem:");
		lblStatusDaltima.setBounds(10, 240, 175, 18);
		contentPane.add(lblStatusDaltima);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UI.class.getResource("/com/jtattoo/plaf/icons/medium/pearl_red_28x28.png")));
		label.setBounds(182, 240, 30, 18);
		contentPane.add(label);

		JButton btnEnviar = new JButton("Enviar!");
		btnEnviar.setEnabled(false);
		btnEnviar.setToolTipText("Por favor, configure um nome antes para tornar a experi\u00EAncia mais amig\u00E1vell");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				label.setIcon(new ImageIcon(UI.class.getResource("/com/jtattoo/plaf/icons/medium/pearl_red_28x28.png")));	
				String escreveu = textArea_1.getText();
				textArea.append("Você: "+escreveu+"\r\n");
				int retorno = envia.enviar(userName+": "+escreveu+"\r\n");
				if (retorno == 1){
					label.setIcon(new ImageIcon(UI.class.getResource("/com/jtattoo/plaf/icons/medium/pearl_green_28x28.png")));					
				}
				textArea_1.setText("");
			}
		});
		btnEnviar.setBounds(324, 184, 106, 32);
		contentPane.add(btnEnviar);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(status==0){
					textField_2.setEditable(false);
					userName = textField_2.getText();
					status=1;
					btnOk.setText("Editar");
					envia.enviaNome(userName);
					envia.controle("O IP: "+ ip.getIP() + " agora se chama: " + userName+"\r\n");
					btnEnviar.setEnabled(true);
				}else{
					status = 0;
					userName = "";
					textField_2.setEditable(true);
					btnOk.setText("OK");
				}
			}
		});
		btnOk.setBounds(171, 31, 65, 24);
		contentPane.add(btnOk);

		textArea_1 = new JTextField();
		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					label.setIcon(new ImageIcon(UI.class.getResource("/com/jtattoo/plaf/icons/medium/pearl_green_28x28.png")));	
					String escreveu = textArea_1.getText();
					textArea.append("Você: "+escreveu+"\r\n");
					int retorno = envia.enviar(userName+": "+escreveu+"\r\n");
					textArea_1.setText("");
					if (retorno == 1){
						label.setIcon(new ImageIcon(UI.class.getResource("/com/jtattoo/plaf/icons/medium/pearl_green_28x28.png")));
					}
				}
			}
		});
		textArea_1.setBounds(10, 185, 313, 32);
		contentPane.add(textArea_1);
		textArea_1.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 234, 420, 3);
		contentPane.add(separator);


	}
	public void recebe()throws Exception{
		new Thread(){
			public void run(){
				try {
					ServerSocket serverSocket = new ServerSocket(3322);
					FileOutputStream fileOutputStream;
					while(true){
						System.out.println("Aguardando conexões");
						Socket socket = serverSocket.accept();
						textArea.setText("Um usuário conectou: "+socket.getInetAddress().getHostAddress()+"\r\n");
						System.out.println("Conectado a: "+ socket.getInetAddress().getHostAddress());
						InputStream inputStream = socket.getInputStream();
						DataInputStream dataInputStream = new DataInputStream(inputStream);
						String texto;
						nome = dataInputStream.readUTF();
						Manipulador manipulador = new Manipulador(nome);
						List<String> mensagensAntigas = manipulador.le();
						if(!mensagensAntigas.isEmpty()){
							for(int i = 0; i < mensagensAntigas.size(); i++){
								textArea.append(mensagensAntigas.get(i));
							}
						}
						String dados = dataInputStream.readUTF();
						textArea.append(dados);
						dataOutputStream = new DataOutputStream(socket.getOutputStream());
						while(!(texto = dataInputStream.readUTF()).equals("close")){
							textArea.append(texto);
							System.out.println("recebido");
							manipulador.grava(texto);
							dataOutputStream.writeUTF("ok");

						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}

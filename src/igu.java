import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ManipuladorArquivos.LeitorArquivos;
import ManipuladorArquivos.LimpaArquivo;
import Modelo.Pergunta;

public class igu {

	private JFrame frame;
	private JPanel panel;

	private JTextField actionTextField;
	private JTextField contextTextField;
	private JTextField fallbackTextField;
	private JTextField fileInputTextField;
	private JTextField fileOutputTextField;

	private JLabel actionLabel;
	private JLabel contextLabel;
	private JLabel fallbackLabel;
	private JLabel fileInputLabel;
	private JLabel fileOutputLabel;
	private JButton processarButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					igu window = new igu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public igu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		actionTextField = new JTextField(20);
		contextTextField = new JTextField(20);
		fallbackTextField = new JTextField(20);
		fileInputTextField = new JTextField(20);
		fileOutputTextField = new JTextField(20);
		actionLabel = new JLabel("action");
		contextLabel = new JLabel("context");
		fallbackLabel = new JLabel("fallback");
		fileInputLabel = new JLabel("fileInput");
		fileOutputLabel = new JLabel("fileOutput");
		processarButton = new JButton("Processar");
		processarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String pathOutput = "/home/leonardo.machado/Documentos/Tarefas/IA/dialogos/Patricia/output/";
				String pathInput = "/home/leonardo.machado/Documentos/Tarefas/IA/dialogos/Patricia/input/";

					LimpaArquivo limpa = new LimpaArquivo();
					String action = actionTextField.getText();
					String context = contextTextField.getText();
					String fallback = fallbackTextField.getText();
					String fileInput = fileInputTextField.getText();
					String fileOutput = fileOutputTextField.getText();
				
					
					ArrayList<Pergunta> arquivoLimpo = null;
					try {
						arquivoLimpo = limpa.obtemPerguntas(pathInput + fileInput);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayList<Pergunta> setActionFallBackContext = limpa.setActionFallBackContext(arquivoLimpo, action, fallback,
							context);
					try {
						LeitorArquivos.gravaArquivo(pathOutput + fileOutput,context, setActionFallBackContext);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.print(setActionFallBackContext);
				
			}
		});
		
		panel.add(actionLabel);
		panel.add(actionTextField);
		panel.add(contextLabel);
		panel.add(contextTextField);
		panel.add(fallbackLabel);
		panel.add(contextTextField);
		panel.add(fallbackLabel);
		panel.add(fallbackTextField);
		panel.add(fileInputLabel);
		panel.add(fileInputTextField);
		panel.add(fileOutputLabel);
		panel.add(fileOutputTextField);
		panel.add(processarButton);
		frame.add(panel);
	}

}

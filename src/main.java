import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import ManipuladorArquivos.LeitorArquivos;
import ManipuladorArquivos.LimpaArquivo;
import Modelo.Pergunta;

public class main {
	private static final String pathOutput = "/home/leonardo.machado/Documentos/Tarefas/IA/dialogos/Patricia/output/";
	private static final String pathInput = "/home/leonardo.machado/Documentos/Tarefas/IA/dialogos/Patricia/input/";

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		LimpaArquivo limpa = new LimpaArquivo();
		String action = null;
		String fallback = null;
		String context = null;
		String fileInput = null;
		String fileOutput = null;
		System.out.println("Digite o nome do arquivo de entrada:");
		fileInput = in.nextLine();
		System.out.println("Digite o nome do arquivo de saida:");
		fileOutput = in.nextLine();
		System.out.println("Digite o nome action:");
		action = in.nextLine();
		System.out.println("Digite o nome fallback:");
		fallback = in.nextLine();
		System.out.println("Digite o nome context:");
		context = in.nextLine();
		

		ArrayList<Pergunta> arquivoLimpo = limpa.obtemPerguntas(pathInput + fileInput);
		ArrayList<Pergunta> setActionFallBackContext = limpa.setActionFallBackContext(arquivoLimpo, action, fallback,
				context);
		LeitorArquivos.gravaArquivo(pathOutput + fileOutput,context, setActionFallBackContext);
		System.out.print(setActionFallBackContext);

	}

}

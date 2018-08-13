package ManipuladorArquivos;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

import Modelo.Pergunta;

public class LeitorArquivos {
	private static final String TXT = ".txt";
	private static final String YML = ".yml";

	public static String leitor(String arquivo) throws IOException {
		Path path = Paths.get(arquivo+TXT);
		byte[] bytesArquivo = Files.readAllBytes(path);
		return new String(bytesArquivo);
	}

	public static void escritor(String arquivo, String dados) throws IOException {
		Path path = Paths.get(arquivo);
		Files.write(path, dados.getBytes());
	}

	public static void criaDiretorioSeNecessarioExiste(String arquivo) {
		File diretorio = new File(arquivo); // ajfilho é uma pasta!
		if (!diretorio.exists()) {
			diretorio.mkdirs(); // mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
		}
	}

	public static void gravaArquivo(String arquivo, String context, ArrayList<Pergunta> perguntas) throws IOException {
		String espaco = "\n";
		String dados = "context-private:" + espaco;
		dados += "- " + context + espaco;
		dados += "client:" + espaco;
		dados += "- 1" + espaco;
		dados += "conversations:" + espaco;
		for (Pergunta pergunta : perguntas) {
			dados += pergunta.getPergunta() + espaco;
			for (String resposta : pergunta.getRespostas()) {
				dados += resposta + espaco;
			}
		}
		escritor(arquivo+YML, dados);
	}


}

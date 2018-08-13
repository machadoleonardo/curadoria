package ManipuladorArquivos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import Modelo.Pergunta;

public class LimpaArquivo {
	String atendente = "Atendente:";
	String resposta = "Resposta:";

	public ArrayList<Pergunta> obtemPerguntas(String arquivo) throws IOException {

		String arquivoBruto = LeitorArquivos.leitor(arquivo);

		arquivoBruto = formataArquivoRegex(arquivoBruto);

		return montaPerguntas(arquivoBruto);
	}

	public ArrayList<Pergunta> setActionFallBackContext(ArrayList<Pergunta> perguntas, String action, String fallback,
			String context) {
		String fallBackFull = "{fallback=" + fallback + "}";
		String actionFull = "{action=" + action + "}";
		String contextFull = "{context=" + context + "}";
		String simbolHife = "  - ";
		String simbolAsterix = "  * ";
		for (Pergunta pergunta : perguntas) {
			pergunta.setPergunta(simbolHife + pergunta.getPergunta() + fallBackFull);
			pergunta.getRespostas().set(0, simbolHife + pergunta.getRespostas().get(0) + actionFull);
			int respostasSize = pergunta.getRespostas().size();
			if (respostasSize > 1) {
				for (int index = 1; index < pergunta.getRespostas().size(); index++) {
					String temp = simbolAsterix + pergunta.getRespostas().get(index);
					pergunta.getRespostas().set(index, temp);
				}
			}

		}
		perguntas.get(0).setPergunta(perguntas.get(0).getPergunta() + contextFull);
		return perguntas;
	}

	private ArrayList<Pergunta> montaPerguntas(String arquivoBruto) {
		ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
		ArrayList<String> arquivoPorLinhas = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(arquivoBruto, "\n");
		Pergunta pergunta = null;
		while (st.hasMoreTokens()) {
			String ler = st.nextToken();
			arquivoPorLinhas.add(ler);
		}
		for (int index = 0; index < arquivoPorLinhas.size(); index++) {
			String linha = arquivoPorLinhas.get(index);
			if (linha.contains(atendente)) {
				if (pergunta != null) {
					pergunta.setRespostas((ArrayList<String>) pergunta.getRespostas().stream().distinct()
							.collect(Collectors.toList()));
					if (!perguntas.contains(pergunta)) {
						perguntas.add(pergunta);
					}
				}
				pergunta = new Pergunta();
				if (!arquivoPorLinhas.get(index + 1).contains(resposta)) {
					pergunta.setPergunta(linha.substring(atendente.length(), linha.length()).trim() + " "
							+ arquivoPorLinhas.get(index + 1));
				} else {
					pergunta.setPergunta(linha.substring(atendente.length(), linha.length()).trim());
				}
				for (Pergunta peguntaArquivo : perguntas) {
					if (peguntaArquivo.getPergunta().equals(pergunta.getPergunta())) {
						pergunta = peguntaArquivo;
					}
				}

			} else if (linha.contains(resposta)) {
				pergunta.getRespostas().add(linha.substring(resposta.length(), linha.length()).trim());
			}

		}
		pergunta.setRespostas(
				(ArrayList<String>) pergunta.getRespostas().stream().distinct().collect(Collectors.toList()));
		if (!perguntas.contains(pergunta)) {
			perguntas.add(pergunta);
		}
		return perguntas;
	}

	private String formataArquivoRegex(String arquivoBruto) {
		arquivoBruto = removeTabulacaoResposta(arquivoBruto);
		arquivoBruto = removeLinhasEmBranco(arquivoBruto);
		arquivoBruto = corrigeNome(arquivoBruto);
		return ajustaRespostas(arquivoBruto);
	}

	private String corrigeNome(String arquivoBruto) {
		String pattern = "(\\%nome\\%)";
		return arquivoBruto.replaceAll(pattern, "\\[\\$person\\.name\\]");
	}

	private String ajustaRespostas(String arquivoBruto) {
		String pattern = "(Resposta \\d{1,4} . Ficha de Negócios Nº \\d{1,4}.*)(\\n)";
		return arquivoBruto.replaceAll(pattern, "Resposta:");
	}

	private String removeLinhasEmBranco(String arquivoBruto) {
		return arquivoBruto = arquivoBruto.replaceAll("(^$\\n)", "");
	}

	private String removeTabulacaoResposta(String arquivoBruto) {
		String pattern = "(    • Resposta|    •  Resposta)";
		return arquivoBruto.replaceAll(pattern, "Resposta");
	}

}

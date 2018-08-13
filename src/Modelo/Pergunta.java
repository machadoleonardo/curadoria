package Modelo;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pergunta {
	String pergunta;
	ArrayList<String> respostas;
	
	public Pergunta() {
		pergunta = "";
		respostas = new ArrayList<>();
	}
	
	

}

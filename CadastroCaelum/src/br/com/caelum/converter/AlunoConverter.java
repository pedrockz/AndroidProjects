package br.com.caelum.converter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.caelum.cadastro.modelo.Aluno;

public class AlunoConverter {
	
	public String toJSON(List<Aluno> alunos){
		//Alguns metodos lancarao Exceptions, portanto, o codigo devera estar dentro de um try catch
		try{
			JSONStringer jsonStringer = new JSONStringer();
	
			jsonStringer.object().key("list").array().
				object().key("aluno").array();
			
			//Para cada aluno eh preciso explicar para o JSONStringer como criar no JSON as informacoes do Aluno
			for(Aluno aluno : alunos){
				jsonStringer.object()
					.key("id").value(aluno.getId())
					.key("nome").value(aluno.getNome())
					.key("telefone").value(aluno.getTelefone())
					.key("endereco").value(aluno.getEndereco())
					.key("site").value(aluno.getSite())
					.key("nota").value(aluno.getNota())
				.endObject();
			}
	
			return jsonStringer.endArray()
					.endObject().endArray().endObject().toString();
		}catch (JSONException e) {
			throw new RuntimeException(e);
		}		
	}
	
}

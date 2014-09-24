package br.com.caelum.suporte;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {

	private final String url;
	
	public WebClient(String url){
		this.url = url;
	}
	
	//Implementacao do envio do json para o servidor
	public String post(String json){
		try{
			 /* Utilizacao da blblioteca da Apache que esta inclusa no Android para realizar uma 
			    requisicao para o servidor: */
				DefaultHttpClient httpClient = new DefaultHttpClient();

			 // Escolha da uri passada para o construtor:
				HttpPost post = new HttpPost(url);
				post.setEntity(new StringEntity(json));
			 /* Para que o servidor seja notificado que a informacao que esta sendo enviada se encontra no 
			    formato JSON, deve-se informar o Content Type no corpo da requisicao, avisando tambem que
				espera-se a resposta no mesmo formato: */
				post.setHeader("Accept", "aplication/json");
				post.setHeader("Content-type", "aplication/json");
			 /* Com o post corretamente configurado, utiliza-se a instancia de HttpClient para fazer o 
				POST para o servidor: */
				HttpResponse response = httpClient.execute(post);
				String jsonDeResposta = EntityUtils.toString(response.getEntity());
				
				return jsonDeResposta;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}


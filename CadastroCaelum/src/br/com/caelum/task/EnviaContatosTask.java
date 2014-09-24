package br.com.caelum.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.converter.AlunoConverter;
import br.com.caelum.suporte.WebClient;

public class EnviaContatosTask extends AsyncTask<Object, Object, String> {

	//Constante com o endereco do servidor para o qual sera enviado o JSON dos contatos
	private final Context context;
	private ProgressDialog progress;
	private final String endereco = "http://www.caelum.com.br/mobile";
	
	//Construtor - Sera preciso criar e manipular elementos na tela, portanto, sera preciso um Context
	public EnviaContatosTask(Context context){
		this.context = context;
	}
	
	//Cria um ProgressDialog que aparecera enquanto a requisicao estiver sendo feita para o servidor
	@Override
	protected void onPreExecute(){
		//Eh preciso guardar o progress, para que ele possa ser cancelado ao final da requisicao
		progress = ProgressDialog.show(context, "Aguarde...", "Envio de dados para a web", true, true);
	}
	
	@Override
	protected String doInBackground(Object... params){
		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> lista = dao.getLista();
		dao.close();
		
		String listaJSON = new AlunoConverter().toJSON(lista);
		
		String jsonDeResposta = new WebClient(endereco).post(listaJSON);
		
		return jsonDeResposta;
	}
	
	//Colocar o resultado na tela do Android
	@Override
	protected void onPostExecute(String result){
		progress.dismiss(); //cancelando o ProgressDialog criado
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}

}

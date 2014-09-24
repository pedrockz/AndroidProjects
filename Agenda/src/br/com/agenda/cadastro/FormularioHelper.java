package br.com.agenda.cadastro;

import br.com.escola.agenda.modelo.Contato;
import br.com.escola.cadastro.R;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FormularioHelper {

	//Atributo do tipo Contato que recebera os dados do formulario:
	private Contato contato; //No exemplo original, nao e' "Contato", e simm, "Aluno".
	
	private EditText nome;
	private EditText telefone;
	private EditText endereco;
	private EditText site;
	private RatingBar nota; //RatingBAR
	private ImageView foto;	 //ImageView
	
	//O construtor recebera uma activity para poder utilizar o metodo findViewById
	//O metodo findViewById buscara todas as Views do xml de layout formulario.xml
	public FormularioHelper(FormularioActivity activity) {
		this.contato = new Contato();
		//Para que possamos acessar as views de qualquer metodo da classe, a declaracao dela deve ser feita como um
		//atributo de classe e nao como variavel de metodo.
		this.nome = (EditText) activity.findViewById(R.id.nome);
		this.telefone = (EditText) activity.findViewById(R.id.telefone);
		this.endereco = (EditText) activity.findViewById(R.id.endereco);
		this.site = (EditText) activity.findViewById(R.id.site);
		this.nota = (RatingBar) activity.findViewById(R.id.nota);
		this.foto = (ImageView) activity.findViewById(R.id.foto);
	}
		//Metodo "pegaContatoDoFormulario()": Os dados do formulario serao extraidos da tela e colocados no atributo
		//"Contato" (usando seus setters). O metodo deve retornar a instancia de "Contato" com os dados do formulario.
		public Contato pegaContatoDoFormulario() {
			contato.setNome(nome.getText().toString());
			contato.setTelefone(telefone.getText().toString());
			contato.setEndereco(endereco.getText().toString());
			contato.setSite(site.getText().toString());
			contato.setNota(Double.valueOf(nota.getProgress()));
			
			return contato;
			
		}

}

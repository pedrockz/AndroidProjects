package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioHelper {

	private Aluno aluno;
	private EditText nome;
	private EditText telefone;
	private EditText site;
	private RatingBar nota;
	private EditText endereco;
	private ImageView foto;
	
	public FormularioHelper(FormularioActivity activity){
		this.aluno = new Aluno();
		this.nome = (EditText) activity.findViewById(R.id.nome);
		this.telefone = (EditText) activity.findViewById(R.id.telefone);
		this.site = (EditText) activity.findViewById(R.id.site);
		this.nota = (RatingBar) activity.findViewById(R.id.nota);
		this.endereco = (EditText) activity.findViewById(R.id.endereco);
		this.foto = (ImageView) activity.findViewById(R.id.foto);
	}
	
	public ImageView getFoto(){
		return foto;
	}
	
	//Coloca os dados do aluno no formulario para a edicao
	public void colocaAlunoNoFormulario(Aluno aluno){
		nome.setText(aluno.getNome());
		telefone.setText(aluno.getTelefone());
		site.setText(aluno.getSite());
		nota.setProgress(aluno.getNota().intValue());
		endereco.setText(aluno.getEndereco());
		
		this.aluno = aluno;
		if(aluno.getCaminhoFoto() != null) {
			this.carregaImagem(aluno.getCaminhoFoto());
		}
	}
	
	//Metodo que extrai os dados do formulario e coloca-os no objeto aluno.
	public Aluno pegaAlunoDoFormulario(){
		aluno.setNome(nome.getText().toString());
		aluno.setEndereco(endereco.getText().toString());
		aluno.setSite(site.getText().toString());
		aluno.setTelefone(telefone.getText().toString());
		aluno.setNota(Double.valueOf(nota.getProgress()));
		return aluno;
	}
	
	//E preciso caarregar a foto, papra isso usa-se a classe BitmapFactory, que vai msotrar a imagem e gerar seu thumbnail:
	public void carregaImagem(String localArquivoFoto){
		//obter a  imagem a partir da String:
		Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
		Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, 100, 100, true);
		//colocar a foto do aluno
		aluno.setCaminhoFoto(localArquivoFoto);
		foto.setImageBitmap(imagemFotoReduzida);
	}
	
}

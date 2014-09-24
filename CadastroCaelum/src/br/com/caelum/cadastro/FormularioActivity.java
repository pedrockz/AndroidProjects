package br.com.caelum.cadastro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends Activity {

	private FormularioHelper helper;
	private String localArquivoFoto; //local onde a foto sera gravada
	private static final int TIRA_FOTO = 123;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		/* iniciar o atributo helper com uma nova instancia de FormularioHelper, passando para ele o
		   contexto, pois queremos acessar o metodo findViewById de la */
		this.helper = new FormularioHelper(this);
		
		Intent intent = this.getIntent();
		final Aluno alunoParaSerAlterado = (Aluno)intent.getSerializableExtra("alunoselecionado");
		
        //Comportamento: Um clique no botao Salvar ira disparar um Toast e voltara para a lista de alunos
        Button botao = (Button) findViewById(R.id.botao); 
        
		// Verificar se foi enviado um aluno atraves da Intent utilizando a constante criada anteriormente
        if(alunoParaSerAlterado != null) { //Ja existe, portanto, sera uma alteracao...
        	botao.setText("Alterar");
            //Para o usuario editar os dados de um Aluno, e preciso mostrar seus valores atuais.
            // (Colocar cada um dos campos da classe Aluno em seu respectivo componente visual)
        	helper.colocaAlunoNoFormulario(alunoParaSerAlterado);
        }
             
        botao.setOnClickListener(new OnClickListener() {	
        	@Override
			public void onClick(View view) {
				Aluno aluno = helper.pegaAlunoDoFormulario();
				//Toast.makeText(FormularioActivity.this, "Aluno: " + aluno.getNome(), Toast.LENGTH_LONG).show();
				//Usa o DAO para salvar o aluno do banco de dados
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				
				/* if(aluno.getId() == null) { //aluno nao existente
					//dao.salvar(aluno); //nao existe esse metodo, nas resolucoes da apostila
					dao.insere(aluno);
				}else{
					dao.alterar(aluno);
				} */
				dao.insereOuAtualiza(aluno);
				dao.close();
				finish(); //voltar para a lista de alunos
			}
		});
        
        //CAMERA:
        ImageView foto = helper.getFoto();
        foto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
				File arquivo = new File(localArquivoFoto);
				Uri localFoto = Uri.fromFile(arquivo);
				//CHAMADA DA CAMERA DO ANDROID
				Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Intent da camera do android
				//Solicitar que uma copia da foto seja gravada no local desejado
				irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				//Converter a String com o local desejado para um objeto do tipo URI:
				startActivityForResult(irParaCamera, TIRA_FOTO); //metodo que colocara a intencao em pratica. Intent; codigo qualquer
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == TIRA_FOTO){
			if(resultCode == Activity.RESULT_OK){
				helper.carregaImagem(this.localArquivoFoto);
			}else{
				this.localArquivoFoto = null;
			}
		}
	}

}

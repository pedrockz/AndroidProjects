package br.com.agenda.cadastro;

//import android.support.v7.app.ActionBarActivity;
import br.com.escola.agenda.modelo.Contato;
import br.com.escola.cadastro.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

//public class FormularioActivity extends ActionBarActivity {
public class FormularioActivity extends Activity{

	private FormularioHelper helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		//Iniciando o atributo helper com uma nova instância de FormularioHelper, passando para ele o contexto, pois queremos
		//acessar o método findViewById de la.
		this.helper = new FormularioHelper(this);
		//Vinculando um listener ao clique do botão "Salvar":
		Button botao = (Button) findViewById(R.id.botao);
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Sera exibido um Toast com o nome do contato que foi preenchido no formulario
				Contato contato = helper.pegaContatoDoFormulario();
				Toast.makeText(FormularioActivity.this, "Contato: " + contato.getNome(), Toast.LENGTH_SHORT).show();
				//Apos exibir o toast voltaremos novamente para a lista de contatos
				//finish();
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

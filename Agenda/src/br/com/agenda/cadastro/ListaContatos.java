package br.com.agenda.cadastro;

//import android.support.v7.app.ActionBarActivity;
import br.com.escola.cadastro.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//public class ListaContatos extends ActionBarActivity {
public class ListaContatos extends Activity {

    private ListView listaContatos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_contatos);
        
        String[] contatos = {"Anderson", "Filipe", "Guilherme"};
        
        //Para podermos manipular o ListView que criamos no xml, vamos recuperar essa View:
        this.listaContatos = (ListView) findViewById(R.id.lista_contatos);
        //Criacao de views para cada nome do array de nomes. Utilizacao do Adapter:
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, contatos);
        //Vinculando o Adapter criado com a ListView:
        listaContatos.setAdapter(adapter);
        
        //Ao clicar em um contato, sera exibida a sua posicao:
        listaContatos.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				Toast.makeText(ListaContatos.this, 
						"Posição selecionada: " + posicao, Toast.LENGTH_LONG).show();				
			}
        });
        //Ao clicar em um contato (click longo), sera exibido o seu nome:
        listaContatos.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				//Recuperando o contato clicado e exibindo um Toast com o seu nome:
				String aluno = (String) adapter.getItemAtPosition(posicao);
				Toast.makeText(ListaContatos.this, "Clique longo: "
						+ aluno, Toast.LENGTH_LONG).show();
				return false;
			}
		});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	//Descobrir o id do menu clicado
    	switch(item.getItemId()){
    	case R.id.menu_novo:
    		Intent intent = new Intent(this, FormularioActivity.class);
    		startActivity(intent);
    		return false;
    		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    //Criando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//Uso do MenuInflater para inflar o xml do menu criado, para que o menu fique visível na tela
    	getMenuInflater().inflate(R.menu.menu_principal, menu);
    	return super.onCreateOptionsMenu(menu);
    }

}
    


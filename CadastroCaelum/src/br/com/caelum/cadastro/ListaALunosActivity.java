package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.caelum.cadastro.adapter.GaleriaAlunosAdapter;
import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.task.EnviaContatosTask;

public class ListaALunosActivity extends Activity {

    private ListView listaAlunos;
    private List<Aluno> alunos;
    private Aluno alunoSelecionado; //armazenara um aluno que sofrer um long click

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);   
        
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();
        
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        
        // E preciso regitrar a ListView como uma View que possui Menu de Contexto
        registerForContextMenu(listaAlunos);
        
        //criacao de views: sera necessario adaptar para tela cada String de nome
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,
        		android.R.layout.simple_list_item_1, alunos);
        
        //Vinculando o adapter com a ListView
        listaAlunos.setAdapter(adapter);
        
        //Capturar o clique em um item da lista
        listaAlunos.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int posicao,
					long id) {
				//Editar um aluno:
				Intent edicao = new Intent(ListaALunosActivity.this, FormularioActivity.class);
				Aluno alunoSelecionado = (Aluno)listaAlunos.getItemAtPosition(posicao);
						edicao.putExtra(Extras.ALUNO_SELECIONADO, alunoSelecionado);
						startActivity(edicao);
			}			
        });
        
        //Mostrar o nome do aluno quando houver um clique longo
        listaAlunos.setOnItemLongClickListener(
        		new OnItemLongClickListener(){
        			@Override
        			public boolean onItemLongClick(AdapterView<?> adapter,
        					View view, int posicao, long id) {
        				/* String aluno = (String) adapter.getItemAtPosition(posicao);
        				Toast.makeText(ListaALunosActivity.this, "Clique Longo: "
        						+ aluno, Toast.LENGTH_LONG).show(); */
        				alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao); //capturar o item clicado com long click
        				return false;
        			}
        		});
    }
	
	//Faz com que o menu fique visivel na tela - MenuInflater inflara o xml de menu criado
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
	}

	//Tratar o clique no item do menu - Lancar um Toast para indicar que realmente interceptamos a chamada
	//pra esse item do menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()) {
		case R.id.menu_novo:
			Intent intent = new Intent(this, FormularioActivity.class); //Criar uma intencao para abrir a classe "FormularioActivity"
			startActivity(intent); //Executar a intencao
			return false;
		case R.id.menu_enviar_alunos:
			/*AlunoDAO dao = new AlunoDAO(this);
			List<Aluno> alunos = dao.getLista();
			dao.close();
			String json = new AlunoConverter().toJSON(alunos);
			WebClient client = new WebClient("http://www.caelum.com.br/mobile");
			String resposta = client.post(json);
			Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();
			//return false; */
			new EnviaContatosTask(this).execute();
			return false;
		case R.id.menu_provas:
			Intent provas = new Intent(this, ProvasActivity.class);
			startActivity(provas);
			return false;
		case R.id.menu_mapa:
			Intent mapa = new Intent(this, MostraAlunosProximosActivity.class);
			startActivity(mapa);
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected void onResume(){
		super.onResume();
		this.carregaLista();
	}

    private void carregaLista(){
    	AlunoDAO dao = new AlunoDAO(this);
    	List<Aluno> alunos = dao.getLista();
    	dao.close();
    	//ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno> (this, android.R.layout.simple_list_item_1, alunos);
    	ListaAlunosAdapter adapter = new ListaAlunosAdapter(this,alunos);
    	this.listaAlunos.setAdapter(adapter);
    }
    
    //Menu de contexto quando houver um long click
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
    	super.onCreateContextMenu(menu, v, menuInfo);
    	
    	/* Evento "ligar" - o clique direcionara para uma nova activity. Deve-se vincular uma Intent ao MenuItem
    	   criado para a ligacao */
    	MenuItem ligar = menu.add("Ligar");
    	Intent intentLigar = new Intent(Intent.ACTION_CALL); //passar para a Intent a intencao de realizar uma ligacao
    	intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone())); //passar o telefone do aluno
    	ligar.setIntent(intentLigar); 
    	
    	/* Evento "enviar sms" - chamada da activity de sms, intent do tipo Intent.ACTION_VIEW  */
    	MenuItem sms = menu.add("Enviar SMS");
    	Intent intentSms = new Intent(Intent.ACTION_VIEW);
    	intentSms.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
    	intentSms.putExtra("sms_body", "Mensagem");
    	sms.setIntent(intentSms);
    	
    	/* Evento "achar no mapa" - chamada da intent de localizacao, tambem abre uma intent de ACTION_VIEW, 
    	   passando os dados da localizacao. Por enquanto sera usada Uri.parse
    	   ("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()) */
    	MenuItem acharNoMapa = menu.add("Achar no Mapa");
    	Intent intentMapa = new Intent(Intent.ACTION_VIEW);
    	intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()));
    	acharNoMapa.setIntent(intentMapa);
    	
    	/* Evento "Navegar no site" - Chama-se uma action vie passando como data a URI do site do aluno  */
    	MenuItem site = menu.add("Navegar no site");
    	Intent intentSite = new Intent(Intent.ACTION_VIEW);
    	String http = alunoSelecionado.getSite().contains("http://")?"":"http://";
    	intentSite.setData(Uri.parse(http+alunoSelecionado.getSite()));
    	site.setIntent(intentSite);
    	
    	/* Item Deletar fara a delecao do item selecionado. Pegar o retorno do metodo add, que foi invocado
    	   no menu e registrar um OnMenuItemClicListener ao novo MenuItem criado */
    	MenuItem deletar = menu.add("Deletar");
    	deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
    		@Override
    		public boolean onMenuItemClick(MenuItem item){
    			AlunoDAO dao = new AlunoDAO(ListaALunosActivity.this);
    			dao.deletar(alunoSelecionado);
    			dao.close();
    			carregaLista();
    			return false;
    		}
    	});
    	
    	/* Evento "Enviar Email -  */
    	MenuItem email = menu.add("Enviar E-mail");
    	Intent intentEmail = new Intent(Intent.ACTION_SEND);
    	intentEmail.setType("message/rtc822");
    	intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"pedro.nardo@fatec.sp.gov.br"});
    	intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Testando subject do email");
    	intentEmail.putExtra(Intent.EXTRA_TEXT, "Testando corpo do email");
    	email.setIntent(intentEmail);
    }
	
	
}

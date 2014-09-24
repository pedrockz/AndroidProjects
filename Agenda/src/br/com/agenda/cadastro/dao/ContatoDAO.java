package br.com.agenda.cadastro.dao;

import br.com.escola.agenda.modelo.Contato;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ContatoDAO extends SQLiteOpenHelper {

	//Variaveis que armazenarao as informacoes do banco:
	private static final int VERSAO = 1;
	private static final String DATABASE = "Agenda";
	private static final String TABELA = "Contatos";
	
	public ContatoDAO(Context context) {
		//parametros do construtor: context / nome do BD / CursorFactory / versao do BD
		super(context, DATABASE, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		String ddl = "CREATE TABLE " + TABELA + " (id INTERGER PRIMARY KEY," +
	    " nome TEXT UNIQUE NOT NULL, telefone TEXT, endereco TEXT, " + 
				"site TEXT, nota REAL, caminhoFoto TEXT);";
		database.execSQL(ddl);
	}
	
	//Quando precisar fazer alguma alteracao no banco, sera chamado o metodo "onUpgrade", apagando o banco
	//e criando-o novamente.
	@Override
	public void onUpgrade(SQLiteDatabase database, int versaoAntiga, int versaoNova) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		database.execSQL(sql);
		onCreate(database);
	}
	
	//Metodo que insere um contato no BD:
	public void insere(Contato contato) {
		//Armazenando as informacoes do contato num objeto auxiliar do tipo ContentValues.
		//Essa classe nos ajudara a explicar ao BD em quais colunas serao salvas cada uma das informacoes do aluno.
		ContentValues values = new ContentValues();
		
		values.put("nome", contato.getNome());
		values.put("telefone", contato.getTelefone());
		values.put("endereco", contato.getEndereco());
		values.put("site", contato.getSite());
		values.put("nota", contato.getNota());
		values.put("caminhoFoto", contato.getCaminhoFoto());
		
		getWritableDatabase().insert(TABELA, null, values);
	}
	

}

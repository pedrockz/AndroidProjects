package br.com.caelum.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.caelum.cadastro.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	//Constantes:
	private static final int VERSAO = 1;
	private static final String TABELA = "Alunos";
	private static final String DATABASE = "CadastroCaelum";
	
	//Construtor que pede apenas um Context
	public AlunoDAO(Context context){
		super(context, DATABASE, null, VERSAO); //Parametros: Contexto; nome do DB; CursorFactory; versao DB
	}
	
	//Criar o Banco de Dados:
	public void onCreate(SQLiteDatabase database) {
		//Criar a tabela no Banco de Dados:
		String ddl = "CREATE TABLE " + TABELA + " (id INTEGER PRIMARY KEY," + 
				" nome TEXT UNIQUE NOT NULL, telefone TEXT, endereco TEXT, " + 
				" site TEXT, nota REAL, caminhofoto TEXT);";
		database.execSQL(ddl);
	}
	
	//Atualizar o banco, a insercao de uma coluna nova, em uma tabela, por exemplo:
	public void onUpgrade(SQLiteDatabase database, int versaoAntiga, int versaoNova){
		String sql = "DROP TABLE IF EXISTIS " + TABELA;
		database.execSQL(sql);
		onCreate(database);
	}
	
	//Adicionar alunos ao Banco de Dados:
	public void insere(Aluno aluno){
		/* Informacoes do aluno serao armazenadas em um objeto auxiliar do tipo ContentValues.
		   Essa classe nos ajuda a explicar ao banco de dados, em quais colunas serao salvas cada
		   uma das informacoes do aluno */
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("caminhofoto", aluno.getCaminhoFoto());
		//passar as informacoes acima para serem inseridas no banco de dados usando o metodo insert da classe SQLiteDatabase
		getWritableDatabase().insert(TABELA, null, values);
	}
	
	//Buscar todos os alunos do banco de dados
	public List<Aluno> getLista() {
		//ArrayList para gravar os alunos retornados do banco de dados:
		List<Aluno> alunos = new ArrayList<Aluno>();
		//Obtendo acesso a leitura no banco de dados
		SQLiteDatabase db = getReadableDatabase();
		//Obtendo um cursor invocando o metodo rawQuery do objeto SQLiteDAtabase obtido da classe SQLiteDatabase:
		Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);
		//Colocando todos os dados das colunas  de certo registro/linha em uma nova instancia de aluno:
		while(c.moveToNext()){
			Aluno aluno = new Aluno();
			aluno.setId(c.getLong(c.getColumnIndex("id")));
			aluno.setNome(c.getString(c.getColumnIndex("nome")));
			aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
			aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
			aluno.setSite(c.getString(c.getColumnIndex("site")));
			aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
			aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhofoto")));
			alunos.add(aluno); //Esse aluno sera adicionado a lista de alunos.
		}
		c.close();
		return alunos; //Retorna a lista com todos os alunos do banco de dados
	}
	
	//Deletar um aluno
	public void deletar(Aluno aluno){
		//array de Strings com os argumentos a serem passados  para a query:
		String[] args = {aluno.getId().toString()};
		getWritableDatabase().delete(TABELA, "id=?", args);
	}
	
	//Alterar os campos de um aluno atraves do seu id. 
	//Utilizacao da classe ContentValues para inserir novos valores de cada um dos campos.
	public void alterar(Aluno aluno){
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("caminhofoto", aluno.getCaminhoFoto());
		
		String [] args = { aluno.getId().toString() };
		//getWritableDatabase(): Obtem um Database do SQLiteOpenHeader no qual e possivel chamar o metodo
		//update e passar um array de String contentdo os argumentos do filtro dos registros que devem
		//ser alterados.
		getWritableDatabase().update(TABELA, values, "id=?", args);
	}
	
	/* Ao inves de realizar a verificacao se deve inserir ou alterar um aluno no formulario, 
	   esse verificacao pode ser feita dentro do DAO, no seguinte metodo: */
	public void insereOuAtualiza(Aluno aluno){
		if(aluno.getId() == null)
			this.insere(aluno);
		else
			this.alterar(aluno);
	}
	
	/* Metodo responsavel por devolver se o telefone que enviou o SMS se encontra ou nao
	   no cadastro: */
	public boolean isAluno(String telefone){
		String[] parametros = {telefone};
		Cursor rawQuery = getReadableDatabase()
				.rawQuery("SELECT telefone FROM " + TABELA
						+ " WHERE telefone = ?", parametros);
		int total = rawQuery.getCount();
		rawQuery.close();
		return total > 0;
	}
	
}

package dataTier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import common.FriendStatus;
import common.UserMetaData;

public final class DataAccess {

	private static DataAccess dataAccessInstance;
	private static Connection conn;
	private static Statement stat;
	
	/* Constructor */
	private DataAccess(){
		try{
		Class.forName("org.h2.Driver");
		//Para testeo se hara una base de datos en memoria.
		//TODO cambiar a servidor embebido para produccion.
		conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
		stat = conn.createStatement();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static DataAccess getInstance(){
		if(dataAccessInstance==null)
			dataAccessInstance = new DataAccess();
		return dataAccessInstance;
	}
	
	//----------
	// Metodos
	//----------
	/*
	 * Obtiene todos los usuarios
	 * ***TESTEADA***
	 */
	public List<UserMetaData> getAllUsers() {
		List<UserMetaData> listaUsuarios = null;
		try {
			ResultSet rs =  stat.executeQuery("SELECT * FROM USUARIOS");
			listaUsuarios = new ArrayList<UserMetaData>();
			while(rs.next()) {
				UserMetaData data = new UserMetaData();
				data.setUser(rs.getString("User"));
				data.setPassword(rs.getString("Password"));
				data.setApyn(rs.getString("Apyn"));
				data.setMail(rs.getString("Mail"));
				data.setFechaAlta(rs.getDate("FechaAlta"));
				data.setFechaNacimiento(rs.getDate("FechaNacimiento"));
				if(rs.getBoolean("Conectado")==true)
					data.setConectado(1);
				else
					data.setConectado(0);
				listaUsuarios.add(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaUsuarios;
	}

	/*
	 * Inserta usuarios a la base de datos
	 * ***TESTEADA***
	 */
	public void insertUser(UserMetaData user) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO USUARIOS(User,Password,Mail,Apyn,FechaNacimiento,FechaAlta,Conectado) VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, user.getUser());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getMail());
			ps.setString(4, user.getApyn());
			ps.setDate(5, new Date(user.getFechaNacimiento().getTime()));
			ps.setDate(6, new Date(user.getFechaAlta().getTime()));
			ps.setInt(7, user.getConectado());
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void modifyUser(UserMetaData user) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE USUARIOS SET Password=?, Mail=?, Apyn=?, FechaNacimiento=?, Conectado=? WHERE User=?");
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getMail());
			ps.setString(3, user.getApyn());
			ps.setDate(4, new Date(user.getFechaNacimiento().getTime()));
			ps.setInt(5, user.getConectado());
			ps.setString(6, user.getUser());
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * Borra usuarios
	 */
	public void deleteUser(UserMetaData user) {
		try{
			stat.execute("DELETE FROM USUARIOS WHERE User="+user.getUser());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void blanquearClave(String user, String password) {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE USUARIOS SET Password = ? WHERE User = ?");
			ps.setString(1, password);
			ps.setString(2, user);
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void penalizar(String user, String motivo, long fechaFin) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO LogPenalizacion (User,Descripcion,FechaFin) VALUES(?,?,?)");
			ps.setString(1, user);
			ps.setString(2, motivo);
			ps.setDate(3, new Date(fechaFin));
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public void despenalizar(String user) {
		Calendar c = Calendar.getInstance();
		c.setTime(new java.util.Date());
		c.add(Calendar.DATE, -1);
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE LogPenalizacion SET FechaFin = ? WHERE User = ?");
			ps.setDate(1, new Date(c.getTime().getTime()));
			ps.setString(2, user);
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public UserMetaData getUserByUsername(String user) {
		UserMetaData data = null;
		try {
			ResultSet rs = stat.executeQuery("SELECT * FROM USUARIOS WHERE User='" + user + "'");
			if (rs.next()) {
				data = new UserMetaData();
				data.setUser(rs.getString("User"));
				data.setPassword(rs.getString("Password"));
				data.setApyn(rs.getString("Apyn"));
				data.setMail(rs.getString("Mail"));
				data.setFechaAlta(rs.getDate("FechaAlta"));
				data.setFechaNacimiento(rs.getDate("FechaNacimiento"));
				if (rs.getBoolean("Conectado") == true)
					data.setConectado(1);
				else
					data.setConectado(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	/*
	 * ***TESTEADA***
	 */
	public List<String> getUsers(String textoBusqueda) {
		List<String> listaUsuarios = null;
		try {
			ResultSet rs = stat.executeQuery("SELECT User FROM USUARIOS WHERE UPPER(User) like " 
					+ " '%"+textoBusqueda.toUpperCase() 
					+"%' or UPPER(Apyn) like '%"+ textoBusqueda.toUpperCase()
					+"%' or UPPER(Mail) like '%"+ textoBusqueda.toUpperCase() +"%' AND Conectado=1");
			listaUsuarios = new ArrayList<String>();
			while(rs.next())
				listaUsuarios.add(rs.getString("User"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaUsuarios;
	}

	/*
	 * ***TESTEADA***
	 */
	public ArrayList<FriendStatus> getFriends(UserMetaData user) {
		try {
			ArrayList<FriendStatus> friendList = new ArrayList<FriendStatus>();
			ResultSet rSet;

			String statement = "SELECT U1.User,Conectado FROM USUARIOS U1 INNER JOIN (SELECT User1 as User FROM AMIGOS WHERE User2='" +user.getUser()+"'"
					+ "UNION SELECT User2 as User FROM AMIGOS WHERE User1='" +user.getUser() +"') AS U2 ON U1.User= U2.User ";
			rSet = stat.executeQuery(statement);

			while (rSet.next())
				friendList.add(new FriendStatus(rSet.getString("User"), rSet.getInt("Conectado")));

			return friendList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * ***TESTEADA***
	 */
	public List<String> getLoginHistory(String user) {
		List<String> listaLogin = null;
		try {
			//TODO reveer query
			//stat.executeQuery("SELECT * FROM LOGLOGIN WHERE User ='" + user + "' AND DATEADD(day,15,FECHAHORAINICIO)");
			ResultSet rSet = stat.executeQuery("SELECT * FROM LOGLOGIN WHERE User ='" + user + "'");
			listaLogin = new ArrayList<String>();
			while(rSet.next()) {
				listaLogin.add(rSet.getString("FechaHoraInicio"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaLogin;
	}

	public boolean checkUser(UserMetaData user){
		try{
			String statement = "SELECT 1 FROM USUARIOS WHERE User='"+ user.getUser()
					+"' and Password='"+ user.getPassword() +"'";
			return stat.executeQuery(statement).first();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public BanInfo checkBan(String user){
		//Devuelve cantidad de dias restantes y descripcion.
		try{
			 String statement = "SELECT p.Descripcion, DateDiff('day',CURRENT_DATE(),p.fechafin) as Restantes from LOGPENALIZACION p INNER JOIN USUARIOS u on u.User = p.user WHERE p.User='"+ user +"' order by ID desc";
			 ResultSet rs = stat.executeQuery(statement);
			 if(rs.first())
				 return new BanInfo(rs.getInt("Restantes"),rs.getString("Descripcion"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public void insertAmigos(String usuario1, String usuario2) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Amigos VALUES (?,?)");
			ps.setString(1, usuario1);
			ps.setString(2, usuario2);
			ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addUserToPuntaje(String user)
	{
		try{
			String statement = "INSERT INTO Puntaje VALUES('"+user+"',0,0,0);";
			stat.executeQuery(statement);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addVictory(String user)
	{
		try{
			String statement = "UPDATE Puntaje SET GANADOS = (SELECT GANADOS+1 FROM PUNTAJE WHERE USER ='"+user+"') WHERE USER ='"+user+"'";
			stat.executeQuery(statement);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addDraw(String user)
	{
		try{
			String statement = "UPDATE Puntaje SET EMPATADOS =(SELECT EMPATADOS+1 FROM PUNTAJE WHERE USER='"+user+"') WHERE USER ='"+user+"'";
			stat.executeQuery(statement);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addDefeat(String user)
	{
		try{
			String statement = "UPDATE Puntaje SET PERDIDOS = (SELECT PERDIDOS+1 FROM PUNTAJE WHERE USER='"+user+"') WHERE USER = '"+user+"'";
			stat.executeQuery(statement);
			}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public ResultSet getPuntajes()
	{
		try{
			String statement ="SELECT * FROM PUNTAJE";
			return stat.executeQuery(statement);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getPuntajeByUser(String user)
	{
		try{
			String statement = "SELECT * FROM PUNTAJE WHERE USER ='"+user+"'";
			return stat.executeQuery(statement);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
		
}

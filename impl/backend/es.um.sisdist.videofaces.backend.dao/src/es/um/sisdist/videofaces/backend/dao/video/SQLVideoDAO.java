package es.um.sisdist.videofaces.backend.dao.video;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import es.um.sisdist.videofaces.backend.dao.models.User;
import es.um.sisdist.videofaces.backend.dao.models.Video;
import es.um.sisdist.videofaces.backend.dao.models.Video.PROCESS_STATUS;

public class SQLVideoDAO implements IVideoDAO {
	Connection conn;
	
	public SQLVideoDAO()
	{
        try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			// Si el nombre del host se pasa por environment, se usa aquí.
			// Si no, se usa localhost. Esto permite configurarlo de forma
			// sencilla para cuando se ejecute en el contenedor, y a la vez
			// se pueden hacer pruebas locales
			Optional<String> sqlServerName = 
					Optional.ofNullable(System.getenv("SQL_SERVER"));
			conn = DriverManager.getConnection("jdbc:mysql://" +
					sqlServerName.orElse("localhost") +
					"/videofaces?user=root&password=root");
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public Optional<Video> saveVideo(String userid, String date, String filename, PROCESS_STATUS process_status) {
		// Get the max ID

		String queryID = "SELECT max(CAST(id AS UNSIGNED)) FROM videos";
		PreparedStatement preparedStmtID;
		try {
			preparedStmtID = conn.prepareStatement(queryID);
			ResultSet rs = preparedStmtID.executeQuery();
			rs.next();
			String id;
			// Si no hay ninguna id asignamos al primer video la id 0, a partir de ahi cada usuario tendra la id del anterior más 1
			
			if(rs.getString(1) == null)
				id = "0";
			else
				id = String.valueOf(Long.valueOf(rs.getString(1)) + 1);
			
			System.out.println("-->" + id);

			// Query de inserción mysql
			String query = " insert into videos (id, userid, date, filename, process_status, videodata)"
					+ " values (?, ?, ?, ?, ?, ?)";

			// Preparamos los datos que vamos a insertar en la query
			PreparedStatement preparedStmt;
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, userid);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, filename);
			int videoStatus;
			if(process_status == PROCESS_STATUS.PROCESSING)
				videoStatus = 0;
			else
				videoStatus = 1;
			preparedStmt.setInt(5, videoStatus);
			
			File file = new File(filename);
			FileInputStream inputStream = new FileInputStream(file);
			preparedStmt.setBlob(6, inputStream);
			preparedStmt.execute();
			
			return Optional.of(new Video(id, userid, process_status, date, filename));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}
	@Override
	public Optional<Video> getVideoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getStreamForVideo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PROCESS_STATUS getVideoStatus(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}

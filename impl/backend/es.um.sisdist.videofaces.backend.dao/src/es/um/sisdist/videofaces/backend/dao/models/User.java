/**
 *
 */
package es.um.sisdist.videofaces.backend.dao.models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID; 
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
public class User
{
	static MessageDigest md;
	static {
		try
		{
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private static String bytesToHex(byte[] bytes) 
    {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
    
	public static String md5pass(String clearpass)
	{
		try
		{
			return bytesToHex(md.digest(clearpass.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e)
		{
			return clearpass;
		}
	}
	public static String generateToken(String id, String username) {
		
		UUID uuid=UUID.randomUUID();   
		String token = uuid.toString(); 
		return token;
	}
	
	public static String generateAuthtoken(String url, String token, String date) {
			String cadenaAuth = url+date+token;
	    	String authHash = User.md5pass(cadenaAuth);
		    return authHash;
		    
		}
	private String id;
	private String email;
	private String password_hash;
	private String name;
	
	private String TOKEN;
	
	private int visits;
	
	public User(String id, String email, String password_hash, String name, String token, int visits)
	{
		this.id = id;
		this.email = email;
		this.password_hash = password_hash;
		this.name = name;
		TOKEN = token;
		this.visits = visits;
	}
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the password_hash
	 */
	public String getPassword_hash()
	{
		return password_hash;
	}

	/**
	 * @param password_hash the password_hash to set
	 */
	public void setPassword_hash(String password_hash)
	{
		this.password_hash = password_hash;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the TOKEN
	 */
	public String getTOKEN()
	{
		return TOKEN;
	}

	/**
	 * @param tOKEN the tOKEN to set
	 */
	public void setTOKEN(String TOKEN)
	{
		this.TOKEN = TOKEN;
	}

	/**
	 * @return the visits
	 */
	public int getVisits()
	{
		return visits;
	}

	/**
	 * @param visits the visits to set
	 */
	public void setVisits(int visits)
	{
		this.visits = visits;
	}
	

	public User()
	{
	}
}
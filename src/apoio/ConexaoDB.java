package apoio;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author rafael
 */
public class ConexaoDB 
{
    private static ConexaoDB instancia = null;
    private Connection conexao = null;

    public ConexaoDB() throws Exception
    {
        try 
        {
            Properties prop = new Properties();
            
            prop.load(new FileInputStream("src/properties/global.properties"));
            String dbdriver = prop.getProperty("db.driver");
            String dburl = prop.getProperty("db.url");
            String dbuser = prop.getProperty("db.user");
            String dbsenha = prop.getProperty("db.senha");

            Class.forName(dbdriver);

            if ( dbuser.length() != 0 )
            {
                conexao = DriverManager.getConnection(dburl, dbuser, dbsenha);
            }
            
            else
            {
                conexao = DriverManager.getConnection(dburl);
            }

        } 
        
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    
     public static ConexaoDB getInstance() throws Exception
     {
        if (instancia == null) 
        {
            instancia = new ConexaoDB();
        }
        
        return instancia;
    }

    public Connection getConnection() 
    {
        if (conexao == null) 
        {
            throw new RuntimeException("conexao==null");
        }
        
        return conexao;
    }

    public void shutDown() 
    {
        try 
        {
            conexao.close();
            instancia = null;
            conexao = null;
        } 
        
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}

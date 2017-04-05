package UtilsGeneral;

import entities.hibernate.SessionConnection;
import exceptions.ConectarException;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 *
 * @author Daniel
 */
public class SessionConectar {
    
     SessionConnection sc;
     Session session;
     SessionFactoryImplementor sessionFactoryImplementation;
     Connection conexion;
     ConnectionProvider connectionProvider;
    
    public SessionConectar(){        
        sc=new SessionConnection();       
        session= sc.useSession();
        sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory(); 
        connectionProvider = sessionFactoryImplementation.getConnectionProvider();
    }
    
    public Connection conectar() throws ConectarException{        
        try {            
            conexion=connectionProvider.getConnection();
        } catch (SQLException ex) {
           throw new ConectarException(ex.getMessage());
        }
        return conexion;
    }
    
    public void cerrarSession() throws SQLException{
        session.close();      
    }
}

package entities.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionConnection {
    
    private static final SessionConnection instance=new SessionConnection();
    
        SessionFactory sf;
        Session session;
        
        
    private SessionConnection(){
        sf= NewHibernateUtil.getSessionFactory();
        session = sf.openSession();       
    }
    
    public static SessionConnection getConnection(){
        return instance;      
    }
    
    public Session useSession(){
        if(!session.isOpen()){
            session=sf.openSession();             
        }           
            return session;               
    }
}

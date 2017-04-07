package entities.hibernate;

import org.hibernate.Session;
import org.hibernate.StatelessSession;

public class SessionConnection {
    
        Session session;        
        
    public SessionConnection(){        
        session = NewHibernateUtil.getSessionFactory().openSession();        
    }
       
    public Session useSession(){               
        return session;               
    }
    
    public StatelessSession useStatelessSession(){
        return NewHibernateUtil.getSessionFactory().openStatelessSession();
    }
    
    public void closeSession(){
        session.close();
    }
}

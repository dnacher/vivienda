package entities.hibernate;

import org.hibernate.Session;

public class SessionConnection {
    
        Session session;        
        
    public SessionConnection(){        
        session = NewHibernateUtil.getSessionFactory().openSession();        
    }
       
    public Session useSession(){               
        return session;               
    }
    
    public void closeSession(){
        session.close();
    }
}

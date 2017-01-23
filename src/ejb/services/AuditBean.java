/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Audit;
import exceptions.ServiceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class AuditBean implements AuditLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public AuditBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Audit audit) throws ServiceException {
        correcto=false;
        try{            
            session.save(audit);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean modificar(Audit audit) throws ServiceException {
        try{            
            session.update(audit);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }
    
}

package impl;

import dao.MessagesDAO;
import data.Messages;
import org.hibernate.HibernateException;
import utils.Constants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class MessagesDAOImpl implements MessagesDAO {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDB");

    @Override
    public Integer addChatHistoryEntity(String message, String sender, String thread) {
        Integer entityID = null;
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            session.getTransaction().begin();
            Messages entity = new Messages();
            entity.setMessage(message);
            entity.setSender(sender);
            entity.setThreadNumber(thread);
            session.persist(entity);
            entityID = entity.getId();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entityID;
    }

    @Override
    public void listChatHistory() {
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            session.getTransaction().begin();
            List entities = session.createQuery("FROM " + Constants.TABLE_NAME).getResultList();
            for (Iterator iterator = entities.iterator(); iterator.hasNext();){
                Messages messagesEntity = (Messages)iterator.next();
                System.out.print("Id: " + messagesEntity.getId());
                System.out.print(" Message: " + messagesEntity.getMessage());
                System.out.print("  Sender: " + messagesEntity.getSender());
                System.out.println("  Thread: " + messagesEntity.getThreadNumber());
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateChatHistoryEntityById(Integer id, String newSender) {
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            session.getTransaction().begin();
            Messages entity = session.find(Messages.class, id);
            entity.setSender(newSender);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteChatHistoryEntityById(Integer entityId) {
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            session.getTransaction().begin();
            Messages entity = session.find(Messages.class, entityId);
            session.remove(entity);
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}

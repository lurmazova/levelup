package database;

import data.Messages;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import utils.Constants;

import java.util.Iterator;
import java.util.List;

public class MessagesManager {
    private static SessionFactory factory;

    public static void main(String[] args) {

        MessagesManager manager = new MessagesManager();

        Integer ID1 = manager.addChatHistoryEntity("message1", "sender1", "thread1");
        Integer ID2 = manager.addChatHistoryEntity("message2", "sender2", "thread2");
        Integer ID3 = manager.addChatHistoryEntity("message3", "sender3", "thread3");

        manager.listChatHistory();

        manager.updateChatHistoryEntityById(ID3, "sender33");

        manager.deleteChatHistoryEntityById(ID2);

        manager.listChatHistory();
    }

    public Integer addChatHistoryEntity(String message, String sender, String thread) {
        createFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer entityID = null;

        try {
            tx = session.beginTransaction();
            Messages entity = new Messages();
            entity.setMessage(message);
            entity.setSender(sender);
            entity.setThreadNumber(thread);
            entityID = (Integer) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entityID;
    }

    public void listChatHistory( ) {
        createFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List entities = session.createQuery("FROM " + Constants.TABLE_NAME).list();
            for (Iterator iterator = entities.iterator(); iterator.hasNext();){
                Messages messagesEntity = (Messages) iterator.next();
                System.out.print("Message: " + messagesEntity.getMessage());
                System.out.print("  Sender: " + messagesEntity.getSender());
                System.out.println("  Thread: " + messagesEntity.getThreadNumber());
            }
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateChatHistoryEntityById(Integer entityId, String sender) {
        createFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Messages entity = (Messages)session.get(Messages.class, entityId);
            entity.setSender(sender);
            session.update(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteChatHistoryEntityById(Integer entityId) {
        createFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Messages entity = (Messages)session.get(Messages.class, entityId);
            session.delete(entity);
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    private void createFactory() {
        try {
            factory = new Configuration().
                    configure().
                    addAnnotatedClass(Messages.class).
                    buildSessionFactory();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

    }
}

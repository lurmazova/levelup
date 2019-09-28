package database;

import dao.MessagesDAO;
import impl.MessagesDAOImpl;

public class MessageManager {

    public static void main(String[] args) {
        MessagesDAO messagesDAO = new MessagesDAOImpl();
        messagesDAO.addChatHistoryEntity("a", "a", "a");
        messagesDAO.listChatHistory();
    }
}

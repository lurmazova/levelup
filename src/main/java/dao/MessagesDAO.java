package dao;

public interface MessagesDAO {
    Integer addChatHistoryEntity(String message, String sender, String thread);
    void listChatHistory();
    void updateChatHistoryEntityById(Integer id, String newSender);
    void deleteChatHistoryEntityById(Integer entityId);
}

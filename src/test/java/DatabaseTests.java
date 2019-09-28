import database.MessageManager;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;

public class DatabaseTests {
    private MessageManager messageManager;
    private ArrayList<Integer> entitiesList;

    @BeforeMethod
    public void setUp() {
        messageManager = new MessageManager();
        entitiesList = new ArrayList<>();
    }

    @Test
    public void checkAddChatHistoryEntityOneLetter()  {
        Integer curId = messageManager.addChatHistoryEntity("a", "a", "a");
        Assert.assertNotNull(curId, "New entity is not added");
        entitiesList.add(curId);
    }

    @Test
    public void checkAddChatHistoryEntityNumbers()  {
        Integer curId = messageManager.addChatHistoryEntity("1", "2", "3");
        Assert.assertNotNull(curId, "New entity is not added");
        entitiesList.add(curId);
    }

    @Test
    public void checkGetMessagesList() {
        Assert.assertNotNull(messageManager.listChatHistory(), "Chat history is empty");
    }

    @AfterSuite
    public void tearDown() {
        for(Integer currentID : entitiesList) {
            messageManager.deleteChatHistoryEntityById(currentID);
        }
    }
}

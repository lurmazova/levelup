import dao.MessagesDAO;
import data.Messages;
import impl.MessagesDAOImpl;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MessagesDAOTests {
    private ArrayList<Integer> entitiesList;

    @Mock
    MessagesDAO dao;

    @InjectMocks
    MessagesDAOImpl messagesService;

    @Spy
    List<Messages> messages = new ArrayList<>();

    @Captor
    ArgumentCaptor<Messages> captor;

    @BeforeMethod
    public void setUp() {
        dao = new MessagesDAOImpl();
        entitiesList = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
        List<Messages> messages = getMessagesList();
    }

    @Test
    public void checkAddChatHistoryEntityOneLetter()  {
        Integer curId = dao.addChatHistoryEntity("a", "a", "a");
        Assert.assertNotNull(curId, "New entity is not added");
        entitiesList.add(curId);
    }

    @Test
    public void checkAddChatHistoryEntityNumbers()  {
        Integer curId = dao.addChatHistoryEntity("1", "2", "3");
        Assert.assertNotNull(curId, "New entity is not added");
        entitiesList.add(curId);
    }

    //test for void method
    @Test
    public void checkUpdateChatHistoryEntityById() {
        doNothing().when(dao).updateChatHistoryEntityById(42, "sender42");
        messagesService.updateChatHistoryEntityById(42, "sender1111");
        verify(dao, times(1)).updateChatHistoryEntityById(captor.capture().getId(), "sender1111");
        Assert.assertEquals(captor.getValue().getSender(), "sender1111");
    }

    //stub the exception of deletion of entity
    @Test(expectedExceptions = RuntimeException.class)
    public void checkDeleteEntityById() {
        doThrow(RuntimeException.class).when(dao).deleteChatHistoryEntityById(messages.get(2).getId());
        messagesService.deleteChatHistoryEntityById(any(Messages.class).getId());
    }

    @AfterSuite
    public void tearDown() {
        for(Integer currentID : entitiesList) {
            dao.deleteChatHistoryEntityById(currentID);
        }
    }

    public List<Messages> getMessagesList() {
        Messages m1 = new Messages();
        m1.setId(100);
        m1.setMessage("Hi, I am your aunt");
        m1.setThreadNumber("11111");

        Messages m2 = new Messages();
        m1.setId(101);
        m1.setMessage("Hi, I am your uncle");
        m1.setThreadNumber("22222");

        Messages m3 = new Messages();
        m1.setId(103);
        m1.setMessage("Deletion of that causes RuntimeException");
        m1.setThreadNumber("33333");

        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        return messages;
    }

}

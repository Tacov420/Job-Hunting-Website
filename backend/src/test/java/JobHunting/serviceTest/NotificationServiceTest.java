package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    public Notification setNotification(int notificationId, int userId, String content, boolean isRead) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setNotificationId(notificationId);
        notification.setUserId(userId);
        notification.setRead(isRead);
        return notification;
    }

    @Test
    public void testGetNotificationList() {
        Notification notification1 = setNotification(0, 0, "HiHi", true);
        Notification notification2 = setNotification(1, 0, "HiHi2", false);
        Notification notification3 = setNotification(2, 0, "HiHi3", false);
        List<Notification> notificationList = Arrays.asList(
            notification1, notification2, notification3
        );
        when(notificationRepository.findByUserId(0)).thenReturn(notificationList);
        Object result = notificationService.getNotificationList(0);
        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("0", Arrays.asList("HiHi", true));
        expected.put("1", Arrays.asList("HiHi2", false));
        expected.put("2", Arrays.asList("HiHi3", false));
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testReadAllNotifications() {
        Notification notification2 = setNotification(1, 0, "HiHi2", false);
        Notification notification3 = setNotification(2, 0, "HiHi3", false);
        List<Notification> notificationList = Arrays.asList(
            notification2, notification3
        );
        when(notificationRepository.findByUserIdAndIsRead(0, false)).thenReturn(notificationList);
        String result = notificationService.readAllNotifications(0);
        assertEquals("All notifications were read", result);
    }

    @Test
    public void testDeleteNotification() {
        String result = notificationService.deleteNotification(0);
        assertEquals("Delete notification successfully", result);
    }
}

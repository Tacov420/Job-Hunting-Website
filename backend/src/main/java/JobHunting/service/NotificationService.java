package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;

import JobHunting.model.*;
import JobHunting.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Optional;
// import java.io.IOException;
// import java.security.GeneralSecurityException;
// import java.security.Security;
// import javax.annotation.PostConstruct;
// import org.springframework.beans.factory.annotation.Value;
// import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class NotificationService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // @Value("${vapid.public.key}")
    // private String publicKey;
    // @Value("${vapid.private.key}")
    // private String privateKey;

    private PushService pushService;

    // @PostConstruct
    // public void init() throws GeneralSecurityException, IOException {
    // Security.addProvider(new BouncyCastleProvider());
    // pushService = new PushService(publicKey, privateKey);
    // }

    private String createInterviewReminderMessageJson(Progress progress) {
        return "{\"title\": \"Interview Reminder\", \"body\": \"You have an interview with " + progress.getCompanyName()
                + " in 3 days.\"}";
    }

    private String createJobNotificationMessage(Job job) {
        return "{\"title\": \"New Job Posting\", \"body\": \"" + job.getJobTitle() + " position available at "
                + job.getCompany() + "\"}";
    }

    public void sendNotification(Subscription subscription, String messageJson) {
        try {
            nl.martijndwars.webpush.Notification notification = new nl.martijndwars.webpush.Notification(subscription,
                    messageJson);
            pushService.send(notification);
        } catch (Exception e) {
            // Log the exception for better error tracking
            e.printStackTrace();
        }
    }

    public boolean deleteNotification(String userName, String notificationIdAsString) {
        ObjectId notificationId = new ObjectId(notificationIdAsString);
        Optional<JobHunting.model.Notification> optionalNotification = notificationRepository.findById(notificationId);

        if (optionalNotification.isPresent() && optionalNotification.get().getUserName().equals(userName)) {
            notificationRepository.delete(optionalNotification.get());
            return true;
        }
        return false;
    }

    public List<JobHunting.model.Notification> getAllNotification(String userName) {
        return notificationRepository.findByUserName(userName);
    }

    public List<JobHunting.model.Notification> getNotificationsForUser(String userName) {
        return notificationRepository.findByUserName(userName);
    }

    public void sendNotification(JobHunting.model.Subscription customSubscription, String messageJson) {
        try {
            // Convert the custom subscription to the library's subscription type
            Subscription librarySubscription = new Subscription(
                    customSubscription.getEndpoint(),
                    new Subscription.Keys(customSubscription.getPublicKey(), customSubscription.getAuth()));

            // Create and send the notification
            Notification notification = new Notification(librarySubscription, messageJson);
            pushService.send(notification);
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this error
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void checkAndNotifyNewJobs() {
        List<Job> newJobs = jobRepository.findByNotificationSentFalse();
        List<JobHunting.model.Subscription> allSubscriptions = subscriptionRepository.findAll();
        for (Job job : newJobs) {
            String message = createJobNotificationMessage(job);
            for (JobHunting.model.Subscription customSubscription : allSubscriptions) {
                sendNotification(customSubscription, message);
            }
            job.setNotificationSent(true);
            jobRepository.save(job);
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void sendInterviewReminders() {
        Date now = new Date();
        Date thresholdDate = addDaysToDate(now, 3); // Add 3 days to the current date
        List<Progress> upcomingInterviews = progressRepository.findProgressWithInterviewsInNextDays(now, thresholdDate);
        upcomingInterviews.forEach(progress -> {
            String userName = progress.getUserName();
            Optional<JobHunting.model.Subscription> optionalCustomSubscription = subscriptionRepository
                    .findByUserName(userName);

            if (optionalCustomSubscription.isPresent()) {
                JobHunting.model.Subscription customSubscription = optionalCustomSubscription.get();
                String message = createInterviewReminderMessageJson(progress);
                sendNotification(customSubscription, message);
            }
        });
    }

    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

}

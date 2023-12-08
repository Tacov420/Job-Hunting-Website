package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;

import JobHunting.model.*;
import JobHunting.repository.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Calendar;
import java.util.Optional;

import org.bson.types.ObjectId;
//import org.springframework.scheduling.annotation.Scheduled;

@Service
public class NotificationService {

    private JobRepository jobRepository;
    private ProgressRepository progressRepository;
    private NotificationRepository notificationRepository;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public NotificationService(
            JobRepository jobRepository,
            ProgressRepository progressRepository,
            NotificationRepository notificationRepository,
            SubscriptionRepository subscriptionRepository) {
        this.jobRepository = jobRepository;
        this.progressRepository = progressRepository;
        this.notificationRepository = notificationRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    // Notification message

    private String createInterviewReminderMessageJson(Progress progress) {
        return "You have an interview with " + progress.getCompanyName() + " in 3 days.";
    }

    private String createJobNotificationMessage(Job job) {
        return job.getJobTitle() + " position available at " + job.getCompany();
    }

    public void subscribeToJob(String userName, String company) {
        List<Job> companyJobs = jobRepository.findByCompanyAndNotificationSentFalse(company, false);

        if (companyJobs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No available jobs for the company or all jobs have been notified.");
        }

        Subscription subscription = subscriptionRepository.findByUserName(userName)
                .orElseGet(() -> new Subscription(userName, new HashSet<>()));

        if (subscription.getSubscribedCompanies().add(company)) {
            subscriptionRepository.save(subscription);
            throw new ResponseStatusException(HttpStatus.OK, "Subscribed to " + company);
        }
    }

    public void unsubscribeFromJob(String userName, String company) {
        // Fetch the user's current subscription
        Optional<Subscription> optionalSubscription = subscriptionRepository.findByUserName(userName);
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();

            // Check if the company is in the list, if so, remove it
            subscription.getCompany().remove(company);
            subscriptionRepository.save(subscription);
        }
    }

    public void sendNotification(String userName, String message) {
        // Create a new Notification object
        Notification notification = new Notification();
        notification.setUserName(userName);
        notification.setMessage(message);
        notification.setDate(new Date());
        notification.setSent(false);
        notification.setRead(false);

        // Set the notification type based on the message content
        if (message.contains("interview")) {
            notification.setNotificationType("Interview");
        } else {
            notification.setNotificationType("Job");
        }

        // Save the notification into the database
        notificationRepository.save(notification);
    }

    public boolean deleteNotification(String userName, String notificationIdAsString) {
        ObjectId notificationId = new ObjectId(notificationIdAsString);
        Optional<Notification> optionalNotification = notificationRepository
                .findById(notificationId);

        if (optionalNotification.isPresent() && optionalNotification.get().getUserName().equals(userName)) {
            notificationRepository.delete(optionalNotification.get());
            return true;
        }
        return false;
    }

    public List<Notification> getAllNotificationsForUser(String userName) {
        return notificationRepository.findByUserName(userName);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getAllUnsentNotifications() {
        return notificationRepository.findBySentFalse();
    }

    public void notifySubscribedJobs(String userName) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findByUserName(userName);
        if (!optionalSubscription.isPresent()) {
            throw new CustomNotFoundException("No subscription found for the user.");

        }

        Subscription subscription = optionalSubscription.get();
        for (String company : subscription.getSubscribedCompanies()) {
            List<Job> jobs = jobRepository.findByCompanyAndNotificationSentFalse(company, false);

            if (jobs.isEmpty()) {

                continue;
            }

            for (Job job : jobs) {
                sendNotification(userName, createJobNotificationMessage(job));
                job.setNotificationSent(true);
                jobRepository.save(job);
            }
        }
        if (subscription.getSubscribedCompanies().isEmpty()) {
            throw new CustomNotFoundException("No companies subscribed for the user.");
        }

    }

    public boolean markAsRead(String notificationIdAsString) {
        ObjectId notificationId = new ObjectId(notificationIdAsString);
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);

        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setRead(true);
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }

    // run once a day
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void checkAndNotifyNewJobs() {
        // Fetch all new jobs that haven't sent a notification yet
        List<Job> newJobs = jobRepository.findByNotificationSentFalse();
        for (Job job : newJobs) {
            String message = createJobNotificationMessage(job);
            // Find all subscriptions for the company
            List<Subscription> subscriptions = subscriptionRepository
                    .findByCompanyContains(job.getCompany());
            for (Subscription subscription : subscriptions) {
                // This will call the above method to send and save notifications
                sendNotification(subscription.getUserName(), message);
            }
            // Mark the job as notified
            job.setNotificationSent(true);
            jobRepository.save(job);
        }
    }

    // run once a day and checks for any interviews scheduled within the next three
    // days
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void sendInterviewReminders() {
        Date threeDaysFromNow = addDaysToDate(new Date(), 3);
        List<Progress> upcomingInterviews = progressRepository.findProgressWithInterviewsInNextDays(new Date(),
                threeDaysFromNow);
        for (Progress progress : upcomingInterviews) {
            String message = createInterviewReminderMessageJson(progress);

            sendNotification(progress.getUserName(), message);

        }
    }

    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    private class CustomNotFoundException extends RuntimeException {
        public CustomNotFoundException(String message) {
            super(message);
        }
    }
}

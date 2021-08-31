package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.library.model.Message;
import ru.geekbrains.library.model.NewsMessage;
import ru.geekbrains.library.model.Newsletter;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.repositories.NewsletterRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MailService {
    private final MailSender mailSender;
    private final NewsletterRepository newsletterRepository;

    @Async("threadPoolTaskExecutor")
    public void sendMessage(User user, String subject, String message) {
        final SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("WA.test.conf.smtp@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject(subject);
        mail.setText(message);
        mailSender.send(mail);
    }

    public void newsletterSub(User user) {
        String subject = "Новости eLibrary";
        Newsletter newsletter = new Newsletter();
        newsletter.setUser(user);
        String text = String.format("Уважаемый, %s!%n. Вы подписались на новостную рассылку интернет-магазина eLibrary!"
                , user.getUsername()
        );
        sendMessage(user, subject, text);
        newsletterRepository.save(newsletter);
    }

    public void newsletterUnsub(User user) {
        String subject = "Новости eLibrary";
        Newsletter newsletter = new Newsletter();
        newsletter.setUser(user);
        String text = String.format("Уважаемый, %s!%n. Вы отписались от новостной рассылки интернет-магазина eLibrary!"
                , user.getUsername()
        );
        sendMessage(user, subject, text);
        user.setNewsletter(null);
    }

    public void greatLetter(User user) {
        String subject = "Приветственное письмо";
        String text = String.format("Уважаемый, %s!%n. Рады приветствовать вас в магазине eLibrary!"
                , user.getUsername()
        );
        sendMessage(user, subject, text);
    }

    public void broadCastMessage(List<User> users, String subject, String message) {
        for (User user : users) {
            sendMessage(user, subject, message);
        }
    }

    public void sendMessageFromFeedbackForm(List<User> managersList, Message message) {
        String subject = "Сообщение от пользователя";
        String text = message.toString();
        broadCastMessage(managersList, subject, text);
    }

    @Bean("threadPoolTaskExecutor")
    private TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(500);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    public void sendNewsMessage(NewsMessage message) {
        List<User> getters = newsletterRepository.findAll()
                .stream()
                .map(Newsletter::getUser)
                .collect(Collectors.toList());
        broadCastMessage(getters,message.getSubject(),message.getText());
    }
}

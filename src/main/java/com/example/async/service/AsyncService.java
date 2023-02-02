package com.example.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    //외부 클래스의 정의된 빈 객체의 @Async 호출
    public void asyncCall_1()  {
        System.out.println("[asyncCall_1] :: " + Thread.currentThread().getName());
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    public String asyncCallWithFuture() throws ExecutionException, InterruptedException {
        System.out.println("[asyncCallWithFuture] :: " + Thread.currentThread().getName());
        Future<String> resultFuture = emailService.sendMailWithFuture();
        while (true) {
            if (resultFuture.isDone()) {
                return resultFuture.get();
            }
        }
    }

    //인스턴스를 생성한후 호출 비동기 처리 안됨
    public void asyncCall_2() {
        System.out.println("[asyncCall_2] :: " + Thread.currentThread().getName());
        EmailService emailService = new EmailService();
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    //내부 메서드를 @Async 로 정의 후 홏풀
    public void asyncCall_3() {
        System.out.println("[asyncCall_3] :: " + Thread.currentThread().getName());
        sendMail();
    }

    @Async
    public void sendMail() {
        System.out.println("[asyncCall_3] :: " + Thread.currentThread().getName());
    }
}

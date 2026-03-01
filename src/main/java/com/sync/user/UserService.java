package com.sync.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class UserService {
    public String getById(Long id)  {
        try {
            Thread.sleep(1000);
            log.info("GetId: {} - Thread: {}", id, Thread.currentThread().getName());
            return "USUARIO BUSCADO : " + id.toString() + ";";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async(AsyncConfig.VIRTUAL_TASK_EXECUTOR)
    public CompletableFuture<String> getByIdVirtualTaks(Long id) {
        return CompletableFuture.completedFuture(getById(id));
    }

    @Async(AsyncConfig.PLATFORM_TASK_EXECUTOR)
    public CompletableFuture<String> getByIdPlatformTaks(Long id) {
        return CompletableFuture.completedFuture(getById(id));
    }

    public String addNumber(String name){
        try {
            // Simula trabalho demorado (1 segundo)
            Thread.sleep(1000);
            log.info("GetName: {} - Thread: {}", name, Thread.currentThread().getName());
            // Adiciona um número aleatório entre 100 e 999
            int numero = ThreadLocalRandom.current().nextInt(100, 1000);
            return name + " → " + numero;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return name + " → ERRO";
        }
    }
}

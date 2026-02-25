package com.sync.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.LongStream;

@RestController("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("ping")
    public String ping(){
        return "OK!";
    }

    @GetMapping("no-async/{num}")
    public ResponseEntity<List<String>> getById(@PathVariable Long num) throws InterruptedException {
        List<String> users = new ArrayList<>();

        for(long i = 0; i < num; i++){
            users.add(userService.getById(i));
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("async-v/{num}")
    public ResponseEntity<List<String>> getByIdWithAsyncV(@PathVariable Long num) throws InterruptedException {
        // Criamos uma lista para guardar as "promessas" (os tickets da pizza)
//        List<CompletableFuture<String>> futures = new ArrayList<>();
//
//        for (long i = 0; i < num; i++) {
//            // Aqui nós APENAS DISPARAMOS a tarefa.
//            // O método getByIdAsync retorna IMEDIATAMENTE um CompletableFuture.
//            // Ele não espera o Thread.sleep(1000) do Service terminar.
//            CompletableFuture<String> ticket = userService.getByIdAsync(i);
//
//            futures.add(ticket);
//        }

//        List<String> results = new ArrayList<>();
//
//        for (CompletableFuture<String> ticket : futures) {
//            // O .join() é o momento da verdade.
//            // Se a tarefa i terminou, ele pega o resultado na hora.
//            // Se a tarefa i ainda está dormindo (sleep), o loop PARA aqui e espera.
//            String resultado = ticket.join();
//
//            results.add(resultado);
//        }

        List<CompletableFuture<String>> futures = LongStream.range(0, num)
                .mapToObj(userService::getByIdVirtualTaks)
                .toList();

        List<String> results = futures.stream()
                .map(CompletableFuture::join) // O "await" de cada um
                .toList();

        return ResponseEntity.ok(results);
    }


    @GetMapping("async-p/{num}")
    public ResponseEntity<List<String>> getByIdWithAsyncP(@PathVariable Long num) throws InterruptedException {
        // Criamos uma lista para guardar as "promessas" (os tickets da pizza)
//        List<CompletableFuture<String>> futures = new ArrayList<>();
//
//        for (long i = 0; i < num; i++) {
//            // Aqui nós APENAS DISPARAMOS a tarefa.
//            // O método getByIdAsync retorna IMEDIATAMENTE um CompletableFuture.
//            // Ele não espera o Thread.sleep(1000) do Service terminar.
//            CompletableFuture<String> ticket = userService.getByIdAsync(i);
//
//            futures.add(ticket);
//        }

//        List<String> results = new ArrayList<>();
//
//        for (CompletableFuture<String> ticket : futures) {
//            // O .join() é o momento da verdade.
//            // Se a tarefa i terminou, ele pega o resultado na hora.
//            // Se a tarefa i ainda está dormindo (sleep), o loop PARA aqui e espera.
//            String resultado = ticket.join();
//
//            results.add(resultado);
//        }

        List<CompletableFuture<String>> futures = LongStream.range(0, num)
                .mapToObj(userService::getByIdPlatformTaks)
                .toList();

        List<String> results = futures.stream()
                .map(CompletableFuture::join) // O "await" de cada um
                .toList();

        return ResponseEntity.ok(results);
    }
}

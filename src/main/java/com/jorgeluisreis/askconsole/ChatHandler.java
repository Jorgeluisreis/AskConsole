package com.jorgeluisreis.askconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.fusesource.jansi.AnsiConsole;
import org.json.JSONException;

import com.jorgeluisreis.askconsole.client.ApiKeyManager;
import com.jorgeluisreis.askconsole.service.ConsoleService;
import com.jorgeluisreis.askconsole.util.TextFormatter;
import net.jorgedev.ConsoleClear;

import static org.fusesource.jansi.Ansi.ansi;

public class ChatHandler {

    private final ConsoleService consoleService;
    private final BufferedReader reader;

    public ChatHandler(ConsoleService consoleService) {
        this.consoleService = consoleService;
        this.reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
    }

    public void startChat(String hash) throws IOException {
        ApiKeyManager apiKeyManager = new ApiKeyManager();

        MenuManager menuManager = new MenuManager(consoleService, apiKeyManager);
        AnsiConsole.systemInstall();
        ConsoleClear.run();
        System.out.println("               _     _____                      _      ");
        System.out.println("     /\\       | |   / ____|                    | |     ");
        System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
        System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
        System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
        System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
        System.out.println("                                                        ");
        System.out.println("                                                        ");
        System.out.println("Bem-vindo ao AskConsole!");

        try {
            String conversationHistory = consoleService.getConversationHistory(hash);
            if (conversationHistory != null && !conversationHistory.isEmpty()) {
                System.out.println(conversationHistory);
            } else {
                System.out.println(
                        ansi().fgYellow().a("Nenhuma conversa anterior encontrada. Iniciando um novo chat...").reset());
            }
        } catch (JSONException e) {
            System.out.println(ansi().fgRed().a("Erro ao carregar o histórico da conversa: " + e.getMessage()).reset());
        }
        while (true) {
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print(ansi().fgGreen().a(formattedDate + " - Eu: ").reset());
            String input = reader.readLine().trim();

            if (input.equalsIgnoreCase("/sair")) {
                System.out.println(ansi().fgYellow().a("Saindo do chat...").reset());
                ConsoleClear.run();
                menuManager.displayMenu();
                return;
            }

            if (input.isEmpty()) {
                System.out.println(ansi().fgRed().a("Entrada inválida! Por favor, digite algo.").reset());
                continue;
            }

            if (!input.equalsIgnoreCase("/sair")) {
                String response = consoleService.chat(input);

                if (response.startsWith("{")) {
                    continue;
                } else {
                    String formattedResponse = TextFormatter.formatText(response);
                    String formattedDateIA = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss"));
                    System.out.println(ansi().fgCyan().a("\n" + formattedDateIA + " - IA: ").reset()
                            + formattedResponse);
                }
            }
        }
    }
}
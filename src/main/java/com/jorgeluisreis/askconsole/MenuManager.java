package com.jorgeluisreis.askconsole;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;
import org.json.JSONException;

import com.jorgeluisreis.askconsole.client.ApiKeyManager;
import com.jorgeluisreis.askconsole.client.GeminiApiClient;
import com.jorgeluisreis.askconsole.service.ConsoleService;
import com.jorgeluisreis.askconsole.util.ConversationManager;
import net.jorgedev.ConsoleClear;

import static org.fusesource.jansi.Ansi.ansi;

public class MenuManager {

    private ConsoleService consoleService;
    private final ApiKeyManager apiKeyManager;
    private final ConversationManager conversationManager = new ConversationManager();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public MenuManager(ConsoleService consoleService, ApiKeyManager apiKeyManager) {
        this.consoleService = consoleService;
        this.apiKeyManager = apiKeyManager;
    }

    public void displayMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);

        AnsiConsole.systemInstall();

        try {
            while (true) {
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
                System.out.println(ansi().fgBrightGreen().a("=== Menu Principal ===").reset());

                if (apiKeyManager.isApiKeyPresent()) {
                    System.out.println("1. Iniciar Chat");
                    System.out.println("2. Continuar Conversa");
                } else {
                    System.out.println("0. Importar Chave API");
                }
                System.out.println("3. Sobre o Projeto");
                System.out.println("4. Sair");
                System.out.print(ansi().fgYellow().a("Escolha uma opção: ").reset());

                String choice = scanner.next().trim();

                if (isValidInput(choice)) {
                    switch (choice) {
                        case "0":
                            importApiKey();
                            break;
                        case "1":
                            if (apiKeyManager.isApiKeyPresent()) {
                                startNewChat();
                            } else {
                                System.out
                                        .println(ansi().fgBrightRed().a("A chave API não está presente.").reset());
                            }
                            break;
                        case "2":
                            if (apiKeyManager.isApiKeyPresent()) {
                                continueChat();
                            } else {
                                System.out
                                        .println(ansi().fgBrightRed().a("A chave API não está presente.").reset());
                            }
                            break;
                        case "3":
                            showAbout();
                            break;
                        case "4":
                            System.out.println(ansi().fgBrightRed().a("Saindo do programa...").reset());
                            return;
                    }
                } else {
                    System.out.println(ansi().fgBrightRed().a("Opção inválida! Tente novamente.").reset());
                }
            }
        } finally {
            scanner.close();
            AnsiConsole.systemUninstall();
        }
    }

    private boolean isValidInput(String input) {
        try {
            int choice = Integer.parseInt(input);
            return choice >= 0 && choice <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void startNewChat() {
        try {

            String hash = conversationManager.generateHash(LocalDateTime.now().toString());

            GeminiApiClient geminiApiClient = new GeminiApiClient(apiKeyManager.getApiKey());
            consoleService = new ConsoleService(geminiApiClient, conversationManager, hash);

            ChatHandler chatHandler = new ChatHandler(consoleService);
            chatHandler.startChat(hash);

        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println(ansi().fgBrightRed().a("Erro ao iniciar o chat: " + e.getMessage()).reset());
        }
    }

    private void continueChat() throws IOException {
        List<String> conversations = conversationManager.listConversations();
        if (conversations.isEmpty()) {
            System.out.println(ansi().fgBrightRed().a("Nenhuma conversa encontrada para continuar.").reset());
            System.out.println("Pressione qualquer tecla para continuar...");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayMenu();
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleClear.run();
            System.out.println("               _     _____                      _      ");
            System.out.println("     /\\       | |   / ____|                    | |     ");
            System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
            System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
            System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
            System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
            System.out.println("                                                        ");
            System.out.println("                                                        ");
            System.out.println("=== Conversas Salvas ===");
            for (int i = 0; i < conversations.size(); i++) {
                System.out.println((i + 1) + ". " + conversations.get(i).replace(".json", ""));
            }
            System.out.print("Escolha uma opção: ");
            String choice;
            while (true) {
                if (scanner.hasNextLine()) {
                    choice = scanner.nextLine().trim();

                    if (choice.matches("\\d+")) {
                        int option = Integer.parseInt(choice);

                        if (option >= 1 && option <= conversations.size()) {

                            break;

                        } else {

                            System.out.println(ansi().fgBrightRed()
                                    .a("Escolha inválida. Por favor, escolha um número entre 1 e "
                                            + conversations.size())
                                    .reset());
                            System.out.println("Pressione qualquer tecla para continuar...");
                            scanner.nextLine();
                            continueChat();

                            return;

                        }
                    } else {

                        System.out.println(
                                ansi().fgBrightRed().a("Erro: entrada inválida. Por favor, digite um número.").reset());
                        System.out.println("Pressione qualquer tecla para continuar...");
                        scanner.nextLine();
                        continueChat();
                        return;

                    }
                } else {

                    System.out.println(
                            ansi().fgBrightRed().a("Erro: entrada inválida. Por favor, digite um número.").reset());
                    System.out.println("Pressione qualquer tecla para continuar...");
                    scanner.nextLine();
                    continueChat();
                    return;

                }

            }

            try {
                int index = Integer.parseInt(choice) - 1;
                if (index < 0) {
                    System.out.println(ansi().fgBrightRed()
                            .a("Erro: entrada inválida. Por favor, digite um número positivo.").reset());
                } else if (index >= conversations.size()) {
                    System.out.println(ansi().fgBrightRed()
                            .a("Escolha inválida. Por favor, escolha um número entre 1 e " + conversations.size())
                            .reset());
                } else {
                    String hash = conversations.get(index).replace(".json", "");
                    System.out.println(ansi().fgBrightGreen().a("Hash da Conversa: " + ansi().fgDefault().a(hash)));
                    String conversationHistory = consoleService.getConversationHistory(hash);
                    System.out.println(conversationHistory);

                    ChatHandler chatHandler = new ChatHandler(consoleService);
                    chatHandler.startChat(hash);
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        ansi().fgBrightRed().a("Erro: entrada inválida. Por favor, digite um número.").reset());
            }
        } catch (NumberFormatException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAbout() throws IOException {
        ConsoleClear.run();
        System.out.println("               _     _____                      _      ");
        System.out.println("     /\\       | |   / ____|                    | |     ");
        System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
        System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
        System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
        System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
        System.out.println("                                                        ");
        System.out.println("                                                        ");
        System.out.println(ansi().fgBrightCyan().a("=== Sobre o Projeto AskConsole ===").reset());
        System.out.println("AskConsole é uma aplicação CLI que permite interações ");
        System.out.println("com uma Inteligência Artificial para responder perguntas e realizar outras operações.");
        System.out.println("Utilizando o Gemini, este projeto demonstra a integração de forma extensiva ");
        System.out.println("em uma aplicação Java, oferecendo suporte a conversas e funcionalidades.");
        System.out.println();
        System.out.println(ansi().fgBrightCyan().a("========= Informações ============").reset());
        System.out.println("Desenvolvido por: Jorgeluisreis");
        System.out.println("Versão: 1.0.1");
        System.out.println("Build: 14/09/2024");
        System.out.println(ansi().fgBrightCyan().a("==================================").reset());
        System.out.println();
        System.out.println(ansi().fgYellow().a("Pressione Enter para voltar ao menu principal...").reset());

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayMenu();
    }

    private void importApiKey() {
        apiKeyManager.importApiKey();
        ConsoleClear.run();
        System.out.println("               _     _____                      _      ");
        System.out.println("     /\\       | |   / ____|                    | |     ");
        System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
        System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
        System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
        System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
        System.out.println("                                                        ");
        System.out.println("                                                        ");
        if (apiKeyManager.isApiKeyPresent()) {
            System.out.println(ansi().fgBrightGreen().a("Chave API importada com sucesso!").reset());
            System.out.println(ansi().fgBrightGreen().a("Você pode agora iniciar o chat.").reset());
        } else {
            System.out.println(ansi().fgBrightRed().a("Falha ao importar a chave API ou a chave é inválida.").reset());
        }

        System.out.println("Pressione Enter para continuar...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
package com.example.recibo;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu do Gerador de Recibos ===");
            System.out.println("1. Criar novo recibo");
            System.out.println("2. Listar todos os recibos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    criarNovoRecibo(scanner, dbManager);
                    break;
                case 2:
                    listarRecibos(dbManager);
                    break;
                case 3:
                    System.out.println("Encerrando o programa...");
                    dbManager.fecharConexao();
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarNovoRecibo(Scanner scanner, DatabaseManager dbManager) {
        System.out.println("\n=== Criar Novo Recibo ===");
        
        System.out.print("Nome do Pagador: ");
        String nomePagador = scanner.nextLine();
        
        System.out.print("CPF do Pagador: ");
        String cpfPagador = scanner.nextLine();
        
        System.out.print("Nome do Recebedor: ");
        String nomeRecebedor = scanner.nextLine();
        
        System.out.print("CPF do Recebedor: ");
        String cpfRecebedor = scanner.nextLine();
        
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        ReciboGenerator recibo = new ReciboGenerator(
            nomePagador, cpfPagador, nomeRecebedor, cpfRecebedor, valor, descricao
        );

        dbManager.salvarRecibo(recibo);
        System.out.println("\nRecibo criado com sucesso!");
        System.out.println(recibo.gerarRecibo());
    }

    private static void listarRecibos(DatabaseManager dbManager) {
        System.out.println("\n=== Lista de Recibos ===");
        List<ReciboGenerator> recibos = dbManager.listarRecibos();
        
        if (recibos.isEmpty()) {
            System.out.println("Nenhum recibo cadastrado.");
            return;
        }

        for (ReciboGenerator recibo : recibos) {
            System.out.println(recibo.gerarRecibo());
            System.out.println("------------------------");
        }
    }
} 
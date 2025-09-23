package com.example;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;
import model.Produto;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/dbproduto";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // ajuste conforme seu MySQL

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 0 -> salvarProduto(scanner);
                case 1 -> buscarTodosProdutos();
                case 2 -> buscarProdutoPorId(scanner);
                case 3 -> atualizarProduto(scanner);
                case 4 -> excluirProduto(scanner);
                case 5 -> {
                    System.out.println("Saindo do programa...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }

    private static void exibirMenu() {
        System.out.println("\n### Menu de Operações ###");
        System.out.println("0. Salvar novo produto");
        System.out.println("1. Buscar todos produtos");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Atualizar produto");
        System.out.println("4. Excluir produto");
        System.out.println("5. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void salvarProduto(Scanner scanner) {
        System.out.println("\n### Criar Novo Produto ###");

        System.out.print("Nome do produto: ");
        String nomeProduto = scanner.nextLine();

        System.out.print("Marca do produto: ");
        String marcaProduto = scanner.nextLine();

        System.out.print("Valor do produto: ");
        BigDecimal valorProduto = new BigDecimal(scanner.nextLine());

        Produto produto = new Produto();
        produto.setNomeProduto(nomeProduto);
        produto.setMarcaProduto(marcaProduto);
        produto.setValorProduto(valorProduto);

        String sql = "INSERT INTO produto (nome, marca, valor) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getMarcaProduto());
            stmt.setBigDecimal(3, produto.getValorProduto());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Produto inserido. Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
        }
    }

    private static void buscarTodosProdutos() {
        System.out.println("\n### Buscar Todos ###");

        String sql = "SELECT * FROM produto";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String marca = rs.getString("marca");
                BigDecimal valor = rs.getBigDecimal("valor");

                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Marca: " + marca);
                System.out.println("Valor: R$ " + valor);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    private static void buscarProdutoPorId(Scanner scanner) {
        System.out.println("\n### Buscar Produto por ID ###");
        System.out.print("Digite o ID: ");
        long id = Long.parseLong(scanner.nextLine());

        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getLong("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Marca: " + rs.getString("marca"));
                System.out.println("Valor: R$ " + rs.getBigDecimal("valor"));
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
    }

    private static void atualizarProduto(Scanner scanner) {
        System.out.println("\n### Atualizar Produto ###");

        System.out.print("Digite o ID do produto: ");
        long id = Long.parseLong(scanner.nextLine());

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Nova marca: ");
        String marca = scanner.nextLine();

        System.out.print("Novo valor: ");
        BigDecimal valor = new BigDecimal(scanner.nextLine());

        String sql = "UPDATE produto SET nome = ?, marca = ?, valor = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, marca);
            stmt.setBigDecimal(3, valor);
            stmt.setLong(4, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private static void excluirProduto(Scanner scanner) {
        System.out.println("\n### Excluir Produto ###");
        System.out.print("Informe o ID do produto: ");
        long idDelete = Long.parseLong(scanner.nextLine());

        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, idDelete);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto excluído com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
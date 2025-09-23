package com.example;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; // Importação correta para Statement SQL
import java.util.Scanner;
import model.Produto;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = Integer.parseInt(scanner.nextLine());
        
            switch (opcao) {
                case 0 -> salvarProduto();
                case 1 -> buscarTodosProdutos();
                case 2 -> buscarProdutoPorId();
                case 3 -> atualizarProduto();
                case 4 -> excluirProduto();
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

    private static void salvarProduto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n### Criar Novo Produto ###");

        System.out.println("Nome do produto: ");
        String nomeProduto = scanner.nextLine();

        System.out.println("Marca do produto: ");
        String marcaProduto = scanner.nextLine();

        System.out.println("Valor do produto: ");
        BigDecimal valorProduto = new BigDecimal(scanner.nextLine());

        //objeto produto
        Produto produto = new Produto();
        produto.setNomeProduto(nomeProduto);
        produto.setMarcaProduto(marcaProduto);
        produto.setValorProduto(valorProduto);
    
        // conexão com banco
    
        var url = "jdbc:mysql://localhost:3306/dbproduto"; //tem q ver isso dae

        try (var connection = DriverManager.getConnection(url, "root", "")) {
            System.out.println("banco conectado");

            // inserindo produto na colonua produto
            //usar preparedStatement?
            String sql = "INSERT INTO produto (nome, marca, valor) VALUES ('" +
                produto.getNomeProduto() + "', '" + produto.getMarcaProduto() + "', " + produto.getValorProduto() + ")";
        
            try(Statement stmt = connection.createStatement()) {
                int linhasAfetadas = stmt.executeUpdate(sql);
                System.out.println("Produto inserido. Linhas afetadas: " + linhasAfetadas);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados ou executar a query: " + e.getMessage());
            e.printStackTrace();
        }
        
    }  

    private static void buscarTodosProdutos() {
        System.out.println("\n### Buscar Todos ###");
        
        var url = "jdbc:mysql://localhost:3306/dbproduto"; //vee

        try (var connection = DriverManager.getConnection(url, "root", "")) {
            String sql = "SELECT * FROM produto";

            try(Statement stmt = connection.createStatement()) {
                var rs = stmt.executeQuery(sql);

                while(rs.next()){
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
            }
        } catch (SQLException e) { // "e" só pra armazenar o erro
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static void buscarProdutoPorId() {
        System.out.println("\n### Buscar Produto por ID ###");
        
        //var url = ""
    }

    private static void atualizarProduto() {
        System.out.println("\n### Atualizar Produto ###");
        // TODO- Implementar atualizar
    }

    private static void excluirProduto() {
        System.out.println("\n### Excluir Produto ###");
        // TODO- Implementar excluir
    }
    

}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int porta = 1234;

        try {
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nova conexão recebida: " + clientSocket);

                Thread thread = new Thread(() -> {
                    try {
                        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                        String cpf = input.readLine();
                        System.out.println("CPF recebido: " + cpf);

                        boolean cpfValido = validarCPF(cpf);

                        if (cpfValido) {
                            output.println("Este CPF é válido");
                        } else {
                            output.println("Este CPF é inválido");
                        }

                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D+", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }

        if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }

        if (Character.getNumericValue(cpf.charAt(10)) != segundoDigito) {
            return false;
        }

        return true;
    }

}

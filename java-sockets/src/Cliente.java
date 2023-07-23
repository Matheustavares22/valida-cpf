import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String enderecoServidor = "localhost";
        int porta = 1234;

        try {
            Socket socket = new Socket(enderecoServidor, porta);
            System.out.println("Conectado ao servidor: " + enderecoServidor + ":" + porta);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Digite um CPF para verificação: ");
            String cpf = input.readLine();
            output.println(cpf);

            String resposta = serverInput.readLine();
            System.out.println("Resposta do servidor: " + resposta);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

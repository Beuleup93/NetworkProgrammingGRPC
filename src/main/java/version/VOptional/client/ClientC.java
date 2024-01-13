package version.VOptional.client;

import com.proto.authentification.ASCheckerGrpc;
import com.proto.authentification.Paire;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ClientC {
    public static void main(String[] args) {
        /* Création du channel gRPC qui specifie l'adresse et le port du serveur de reco*/
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 28414)
                .usePlaintext()
                .build();

        /* Creation stub pour invocation synchrone blocante sur le service distant */
        ASCheckerGrpc.ASCheckerBlockingStub client = ASCheckerGrpc.newBlockingStub(channel);

        /* Invocation du service */
        System.out.println("Test Client C");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("Login: ");
                String login = sc.next();
                System.out.println("Password: ");
                String pwd = sc.next();

                Paire p = Paire.newBuilder()
                        .setLogin(login)
                        .setPwd(pwd)
                        .build();

                System.out.println("check validité : " + p);
                System.out.println("Réponse : "+client.ckeck(p).getResponse());
                System.out.println("Check le login et password et FIN pour quitter");
                String nom = sc.next();
                if (nom.equals("FIN")){
                    break;
                }
            }
        }
    }
}

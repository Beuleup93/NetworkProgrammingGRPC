package version.VOptional.client;

import com.proto.authentification.ASManagerGrpc;
import com.proto.authentification.Paire;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ClientM {
    public static void main(String[] args) {
        /* Création du channel gRPC qui specifie l'adresse et le port du serveur de reco*/
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 28415)
                .usePlaintext()
                .build();

        /* Creation stub pour invocation synchrone blocante sur le service distant */
        ASManagerGrpc.ASManagerBlockingStub clientStub = ASManagerGrpc.newBlockingStub(channel);

        /* Invocation du service */
        System.out.println("Test Client C");
        Paire paire = null;
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String choix;
                String login;
                String passwd;

                System.out.println("+---------------------------------+");
                System.out.println("| ADD - creer une paire             |");
                System.out.println("| CHK - tester une paire            |");
                System.out.println("| MOD - mettre à jour une paire     |");
                System.out.println("| DEL - supprimer une paire         |");
                System.out.println("| FIN - arreter                     |");
                System.out.println("+---------------------------------+");

                choix = sc.next();
                sc.nextLine(); // saute le retour à la ligne

                switch (choix) {
                    case "FIN":
                        sc.close();
                        System.exit(0);
                    case "ADD":
                        System.out.println("Tapez le login");
                        login = sc.next();
                        sc.nextLine(); // saute le retour à la ligne
                        System.out.println("Tapez le mot de passe");
                        passwd = sc.next();
                        sc.nextLine();

                        paire = Paire.newBuilder()
                                .setLogin(login)
                                .setPwd(passwd)
                                .build();

                        System.out.println("Réponse du serveur : "+clientStub.add(paire).getResponse());
                        break;
                    case "CHK":
                        System.out.println("Tapez le login");
                        login = sc.next();
                        sc.nextLine();
                        System.out.println("Tapez le mot de passe");
                        passwd = sc.next();
                        paire = Paire.newBuilder()
                                .setLogin(login)
                                .setPwd(passwd)
                                .build();
                        System.out.println("Réponse du serveur : "+clientStub.chk(paire).getResponse());
                        break;
                    case "MOD":
                        System.out.println("Tapez le login");
                        login = sc.next();
                        sc.nextLine(); // saute le retour à la ligne
                        System.out.println("Tapez le mot de passe");
                        passwd = sc.next();
                        System.out.println("YOOOO");
                        paire = Paire.newBuilder()
                                .setLogin(login)
                                .setPwd(passwd)
                                .build();
                        System.out.println("Réponse du serveur : "+clientStub.update(paire).getResponse());
                        break;
                    case "DEL":
                        System.out.println("Tapez le login");
                        login = sc.next();
                        sc.nextLine(); // saute le retour à la ligne
                        System.out.println("Tapez le mot de passe");
                        passwd = sc.next();
                        paire = Paire.newBuilder()
                                .setLogin(login)
                                .setPwd(passwd)
                                .build();
                        System.out.println("Réponse du serveur : "+clientStub.delete(paire).getResponse());
                        break;
                }
            }
        }
    }
}

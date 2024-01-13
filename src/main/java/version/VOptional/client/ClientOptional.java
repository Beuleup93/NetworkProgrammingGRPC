package version.VOptional.client;

import com.proto.authentification.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ClientOptional {
    public static void main(String[] args) {
        /* Création du channel gRPC qui specifie l'adresse et le port du serveur de reco*/
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 3244)
                .usePlaintext()
                .build();

        /* Creation stub pour invocation synchrone blocante sur le service distant */
        LoggingServiceGrpc.LoggingServiceBlockingStub client = LoggingServiceGrpc.newBlockingStub(channel);
        System.out.println("Client pour la version optionnel: Affichage des logs avec filtres");

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String choix;
                String login;
                String passwd;
                String respOrType;
                ServerResponse serverResponse = ServerResponse.BAD;
                TypeRequete typeRequete = TypeRequete.ADD;
                Paire paire = null;
                GetLogRequest logRequest = null;

                System.out.println("+----------------MENU-----------------+");
                System.out.println("| PAIRE - Filtrer avec une paire |");
                System.out.println("| TYPE - Filtrer selon le type d'opération (ADD,DEL,MOD) |");
                System.out.println("| RESPONSE - Filtrer selon le type de réponse (GOOD,DONE,ERROR,BAD) |");
                System.out.println("| ALL - Tous les logs |");
                System.out.println("| FIN - arreter                     |");
                System.out.println("+----------------****-----------------+");

                choix = sc.next();
                sc.nextLine(); // saute le retour à la ligne

                switch (choix) {
                    case "FIN":
                        sc.close();
                        System.exit(0);

                    case "ALL":
                        logRequest = GetLogRequest.newBuilder()
                                .buildPartial();
                        client.get(logRequest).forEachRemaining(response->{
                            System.out.println("---------------------------------------------------------------");
                            System.out.println(response);
                        });
                        break;

                    case "PAIRE":
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

                        logRequest = GetLogRequest.newBuilder()
                                .setPaire(paire)
                                .buildPartial();
                        client.get(logRequest).forEachRemaining(response->{
                            System.out.println("--------------------------------------------------------------");
                            System.out.println(response);
                        });
                        break;

                    case "TYPE":
                        System.out.println(" Saisir le type d'opération à filtrer (par défaut ADD) ==> ADD,DEL ou MOD");
                        respOrType = sc.next();
                        switch(respOrType){
                            case "ADD":
                                typeRequete = TypeRequete.ADD;
                                break;

                            case "DEL":
                                typeRequete = TypeRequete.DEL;
                                break;

                            case "MOD":
                                typeRequete = TypeRequete.MOD;
                                break;
                            default:
                                System.out.println(" Oups!! Erreur de Saisie, Nous vous avons choisi l'option par défaut ==> ADD");
                                break;
                        }

                        logRequest = GetLogRequest.newBuilder()
                                .setType(typeRequete)
                                .buildPartial();

                        client.get(logRequest).forEachRemaining(response->{
                            System.out.println("--------------------------------------------------------------");
                            System.out.println(response);
                        });
                        break;

                    case "RESPONSE":
                        System.out.println("Saisir le type de réponse à filtrer (par défaut GOOD) ==> GOOD,DONE,ERROR OU BAD ");
                        respOrType = sc.next();

                        switch(respOrType){
                            case "DONE":
                                serverResponse = ServerResponse.DONE;
                                break;
                            case "ERROR":
                                serverResponse = ServerResponse.ERROR;
                                break;
                            case "BAD":
                                serverResponse = ServerResponse.BAD;
                                break;
                            case "GOOD":
                                serverResponse = ServerResponse.GOOD;
                                break;
                            default:
                                System.out.println(" Oups!! Erreur de Saisie, Nous vous avons choisi l'option par défaut ==> BAD.");
                                break;
                        }

                        logRequest = GetLogRequest.newBuilder()
                                .setResponse(serverResponse)
                                .buildPartial();

                        client.get(logRequest).forEachRemaining(response->{
                            System.out.println("--------------------------------------------------------------");
                            System.out.println(response);
                        });
                        break;

                    default:
                        System.out.println(" Oups!! Otions non disponible sur le menu. Fermeture du programme");
                        sc.close();
                        System.exit(0);
                        break;
                }
            }
        }
    }
}

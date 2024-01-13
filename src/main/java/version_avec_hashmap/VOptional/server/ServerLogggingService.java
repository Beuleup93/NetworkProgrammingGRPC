package version_avec_hashmap.VOptional.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import version_avec_hashmap.VOptional.LogingServiceGrpcImpl;

public class ServerLogggingService {
    public static void main(String[] args) {
        try {
            System.err.println("Serveur L RUNNING");
            LogingServiceGrpcImpl servant =
                    new LogingServiceGrpcImpl();
            // Initialisation du serveur sur le port 28414
            Server server = ServerBuilder
                    .forPort(3244)
                    .addService(servant) // les services que le serveur offre
                    .build()
                    .start();

            // Interception Ctrl C et arrêt processus
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (server != null) {
                    System.out.println("Demande d'arret du serveur");
                    server.shutdown();
                    System.out.println("Serveur arreter");
                }
            }));
            // Boucle infinie
            server.awaitTermination();
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}

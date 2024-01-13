package version_avec_hashmap.v1.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerAS {
    public static void main(String[] args) {
        try {
            System.err.println("Serveur AS RUNNING");
            ASCheckerGrpcImpl servant =
                    new ASCheckerGrpcImpl();
            // Initialisation du serveur sur le port 28414
            Server server = ServerBuilder
                    .forPort(28414)
                    .addService(servant)
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

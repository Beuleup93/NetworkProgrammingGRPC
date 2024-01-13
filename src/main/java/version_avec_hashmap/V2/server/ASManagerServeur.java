package version_avec_hashmap.V2.server;

import version_avec_hashmap.v1.server.ASCheckerGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ASManagerServeur {
    public static void main(String[] args) {
        try {
            System.err.println("Serveur AS RUNNING");

            Server server1 = ServerBuilder
                    .forPort(28414)
                    .addService(new ASCheckerGrpcImpl())
                    .build()
                    .start();

            Server server2 = ServerBuilder
                    .forPort(28415)
                    .addService(new ASManagerGrpcImpl())
                    .build()
                    .start();

            // Interception Ctrl C et arrêt processus
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (server1 != null) {
                    server1.shutdown();
                    System.out.println("server 1 arreter");
                }
                if (server2 != null) {
                    server2.shutdown();
                    System.out.println("Server 2 arreter");
                }
            }));
            // Boucle infinie
            server1.awaitTermination();
            server2.awaitTermination();

        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}

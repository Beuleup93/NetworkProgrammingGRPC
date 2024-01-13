package version.V2.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import version.metier.ListAuthH2;
import version.v1.server.ASCheckerGrpcH2Impl;

public class ASManagerServeurH2 {

    public static void main(String[] args) {

        try {
            System.err.println("Serveur AS RUNNING");

            Server server1 = ServerBuilder
                    .forPort(28414)
                    .addService(new ASCheckerGrpcH2Impl(new ListAuthH2("authentif")))
                    .build()
                    .start();

            Server server2 = ServerBuilder
                    .forPort(28415)
                    .addService(new ASManagerGrpcH2Impl(new ListAuthH2("authentif")))
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

package version.V3.server;

import com.proto.authentification.ASCheckerGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import version.metier.ListAuthH2;
import version.v1.server.ASCheckerGrpcH2Impl;

public class ASManagerServeurH2 {

    public static void main(String[] args) {

        try {
            System.err.println("Serveur AS RUNNING");
            // le servant manager
            ASManagerGrpcH2Impl servantManager = new ASManagerGrpcH2Impl(new ListAuthH2("authentif"));
            // le serveur manager
            // Note : le servant est aussi un intercepteur de requête gRPC
            Server serverManager = ServerBuilder.forPort(28415)
                    .addService(ServerInterceptors.intercept(servantManager, servantManager))
                    .build().start();

            ASCheckerGrpcH2Impl servantChecker = new ASCheckerGrpcH2Impl(new ListAuthH2("authentif"));
            Server serverChecker = ServerBuilder.forPort(28414)
                    .addService(ServerInterceptors.intercept(servantChecker, servantChecker))
                    .build().start();

            // Interception de l'arrêt du programme par Ctrl-C
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (serverManager != null) {
                    serverManager.shutdown();
                }

                if (serverChecker != null) {
                    serverChecker.shutdown();
                }

            }));
            // attente de la fin du programme (boucle infinie ici)
            serverManager.awaitTermination();
            serverChecker.awaitTermination();

        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}

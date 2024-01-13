package version_avec_hashmap.VOptional.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import version_avec_hashmap.metier.ListeAuth;
import version_avec_hashmap.v1.server.ASCheckerGrpcImpl;

public class ASManagerServeur {

    public static void main(String[] args) {

        try {
            System.err.println("Serveur AS RUNNING");
            // le servant manager
            ASManagerGrpcImpl servantManager = new ASManagerGrpcImpl(new ListeAuth());
            // le serveur manager
            // Note : le servant est aussi un intercepteur de requête gRPC
            Server serverManager = ServerBuilder.forPort(28415)
                    .addService(ServerInterceptors.intercept(servantManager, servantManager))
                    .build().start();

            ASCheckerGrpcImpl servantChecker = new ASCheckerGrpcImpl();
            Server serverCheker = ServerBuilder.forPort(28414)
                    .addService(ServerInterceptors.intercept(servantChecker, servantChecker))
                    .build().start();
            // Interception de l'arrêt du programme par Ctrl-C
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (serverManager != null) {
                    serverManager.shutdown();
                }
                if (serverCheker != null) {
                    serverCheker.shutdown();
                }
            }));
            // attente de la fin du programme (boucle infinie ici)
            serverManager.awaitTermination();
            serverCheker.awaitTermination();

        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}

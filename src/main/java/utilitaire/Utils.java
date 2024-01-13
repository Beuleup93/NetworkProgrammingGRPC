package utilitaire;

import com.proto.authentification.LogRequest;
import com.proto.authentification.LoggingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Utils {
    public static void logToServerL(LogRequest logRequest){
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 3244)
                .usePlaintext()
                .build();
        /* Creation stub pour invocation synchrone blocante sur le serveur L */
        LoggingServiceGrpc.LoggingServiceBlockingStub client = LoggingServiceGrpc.newBlockingStub(channel);
        //journalisation

        client.log(logRequest);
        System.out.println("Operation journalisée");
        channel.shutdown();
    }
}

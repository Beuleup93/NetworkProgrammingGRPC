package version.V3.client;

import com.proto.authentification.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ClientTmpAS {
    public static void main(String[] args) {
        /* Création du channel gRPC qui specifie l'adresse et le port du serveur de reco*/
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 3244)
                .usePlaintext()
                .build();

        /* Creation stub pour invocation synchrone blocante sur le service distant */
        LoggingServiceGrpc.LoggingServiceBlockingStub client = LoggingServiceGrpc.newBlockingStub(channel);

        System.out.println("Client Temporaire");
        Scanner sc = new Scanner(System.in);
        System.out.println("Login: ");
        String login = sc.next();
        System.out.println("Password: ");
        String pwd = sc.next();

        Paire p = Paire.newBuilder()
                .setLogin(login)
                .setPwd(pwd)
                .build();

        LogRequest logRequest = LogRequest.newBuilder()
                .setType(TypeRequete.ADD)
                .setPaire(p)
                .setResponse(ServerResponse.GOOD)
                .setDate("12-01-2023")
                .setPort("3244")
                .setIp("127.0.0.1")
                .build();
        System.out.println("Journalisation : "+client.log(logRequest));
        }
}


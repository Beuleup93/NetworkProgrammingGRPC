package version_avec_hashmap.V3.server;

import version_avec_hashmap.metier.ListeAuth;
import com.proto.authentification.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import utilitaire.Utils;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

public class ASManagerGrpcImpl extends ASManagerGrpc.ASManagerImplBase implements ServerInterceptor {

    // Logger permettant d'afficher des messages dans la console
    private static final Logger logger = Logger.getLogger(ASManagerGrpcImpl.class.getName());

    private String host; // contiendra l'adresse IP du client
    private  int port; // contiendra le port du client
    private LogRequest logRequest;
    private final ListeAuth listeAuth;

    public ASManagerGrpcImpl() {
        this.listeAuth = new ListeAuth();
        this.logRequest = null;
    }
    @Override
    public void add(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.creer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.ADD)
                    .setPaire(request)
                    .setResponse(ServerResponse.DONE)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.ADD)
                    .setPaire(request)
                    .setResponse(ServerResponse.ERROR)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.supprimer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.DEL)
                    .setPaire(request)
                    .setResponse(ServerResponse.DONE)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.DEL)
                    .setPaire(request)
                    .setResponse(ServerResponse.ERROR)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void update(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.mettreAJour(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.MOD)
                    .setPaire(request)
                    .setResponse(ServerResponse.DONE)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.MOD)
                    .setPaire(request)
                    .setResponse(ServerResponse.ERROR)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void chk(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.tester(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.GOOD).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.CHK)
                    .setPaire(request)
                    .setResponse(ServerResponse.GOOD)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
            // journalisation
            this.logRequest = LogRequest.newBuilder()
                    .setType(TypeRequete.CHK)
                    .setPaire(request)
                    .setResponse(ServerResponse.BAD)
                    .setDate(new Date().toString())
                    .setPort(this.port+"")
                    .setIp(this.host)
                    .build();
            Utils.logToServerL(this.logRequest);
        }
        responseObserver.onCompleted();
    }

    // methode utilitaire pour journaliser sur le serveur L depuis le serveur AS

    /** Méthode permettant de récupérer l'adresse IP et le port du client
     * @param inetSocketString : adresse IP et port du client sous la forme /IP:port
     */
    protected void getRemoteAddr(String inetSocketString) {
        // The substring is simply host:port, even if host is IPv6 as it fails to use []. Can't use
        // standard parsing because the string isn't following any standard.
        this.host = inetSocketString.substring(0, inetSocketString.lastIndexOf(':'));
        this.port = Integer.parseInt(inetSocketString.substring(inetSocketString.lastIndexOf(':') + 1));
        logger.info("Host: "+this.host+", Port: "+this.port);
    }


    /**
     * Méthode permettant de récupérer l'adresse IP et le port du client lors d'un appel gRPC
     * @param call l'appel gRPC
     * @param headers les headers de la requête
     * @param next le prochain appel gRPC (c'est cela qui appellera la bonne méthode rcp)
     * @return un listener d'appel RPC
     * @param <ReqT> paramètre générique requête
     * @param <RespT> paramètre générique réponse
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        // on récupère les attributs de la requête
        Attributes attributes = call.getAttributes();

        // on récupère l'attribut contenant l'adresse IP et le port du client
        logger.info("Identité du client : " + attributes.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR));

        // on appelle la méthode qui va extraire l'IP et le port du client
        this.getRemoteAddr(Objects.requireNonNull(attributes.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR)).toString());

        // Puis on appelle la méthode qui va appeler la bonne méthode gRPC
        return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
        }, headers);
    }
}

package version_avec_hashmap.V2.server;

import version_avec_hashmap.metier.ListeAuth;
import com.proto.authentification.ASManagerGrpc;
import com.proto.authentification.GResponse;
import com.proto.authentification.Paire;
import com.proto.authentification.ServerResponse;
import io.grpc.stub.StreamObserver;

public class ASManagerGrpcImpl extends ASManagerGrpc.ASManagerImplBase {
    private final ListeAuth listeAuth;

    public ASManagerGrpcImpl() {
        this.listeAuth = new ListeAuth();
    }
    @Override
    public void add(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.creer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.supprimer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void update(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.mettreAJour(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void chk(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.tester(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.GOOD).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }
}

package version.V2.server;

import com.proto.authentification.ASManagerGrpc;
import com.proto.authentification.GResponse;
import com.proto.authentification.Paire;
import com.proto.authentification.ServerResponse;
import io.grpc.stub.StreamObserver;
import version.metier.ListAuthH2;

public class ASManagerGrpcH2Impl extends ASManagerGrpc.ASManagerImplBase {
    private final ListAuthH2 listAuthH2;

    public ASManagerGrpcH2Impl(ListAuthH2 listAuthH2) {

        this.listAuthH2 = listAuthH2;
    }
    @Override
    public void add(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listAuthH2.creer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listAuthH2.supprimer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void update(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listAuthH2.mettreAJour(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void chk(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listAuthH2.tester(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.GOOD).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }
}

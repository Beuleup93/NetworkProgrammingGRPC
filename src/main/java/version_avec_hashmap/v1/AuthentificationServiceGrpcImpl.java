package version_avec_hashmap.v1;/*
package version.v1;

import com.proto.annuaire.AUDResponse;
import com.proto.annuaire.TypeCR;
import com.proto.authentification.*;
import io.grpc.stub.StreamObserver;
import version.metier.ListeAuth;

public class AuthentificationServiceGrpcImpl extends AuthentificationServiceGrpc.AuthentificationServiceImplBase {
    private final ListeAuth listeAuth;

    public AuthentificationServiceGrpcImpl() {
        // Instancie notre BD
        this.listeAuth = new ListeAuth();
    }

    @Override
    public void addRessource(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.creer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void delRessource(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.supprimer(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void modifyRessource(Paire request, StreamObserver<GResponse> responseObserver) {
        if (listeAuth.mettreAJour(request.getLogin(), request.getPwd())) {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.DONE).build());
        } else {
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getRessource(Paire request, StreamObserver<GResponse> responseObserver) {
        if (request.getLogin() != null && request.getPwd() != null){
            if (listeAuth.tester(request.getLogin(), request.getPwd())) {
                responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.GOOD).build());
            } else {
                responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.BAD).build());
            }
            responseObserver.onCompleted();
        }else{
            responseObserver.onNext(GResponse.newBuilder().setResponse(ServerResponse.ERROR).build());
        }
    }
}
*/

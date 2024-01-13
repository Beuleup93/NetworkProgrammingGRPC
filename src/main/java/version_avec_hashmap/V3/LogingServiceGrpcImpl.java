package version_avec_hashmap.V3;

import version_avec_hashmap.metier.ListeAuth;
import com.google.protobuf.Empty;
import com.proto.authentification.LogRequest;
import com.proto.authentification.LoggingServiceGrpc;
import io.grpc.stub.StreamObserver;

public class LogingServiceGrpcImpl extends LoggingServiceGrpc.LoggingServiceImplBase {
    private final ListeAuth listeAuth;

    public LogingServiceGrpcImpl(ListeAuth listeAuth) {
        this.listeAuth = listeAuth;
    }

    @Override
    public void log(LogRequest request, StreamObserver<Empty> responseObserver) {
        // Traitement à faire
        System.out.println(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}

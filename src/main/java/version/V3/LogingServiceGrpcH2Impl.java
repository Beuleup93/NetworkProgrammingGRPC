package version.V3;

import com.google.protobuf.Empty;
import com.proto.authentification.GetLogRequest;
import com.proto.authentification.LogRequest;
import com.proto.authentification.LoggingServiceGrpc;
import io.grpc.stub.StreamObserver;
import version.metier.ListAuthH2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogingServiceGrpcH2Impl extends LoggingServiceGrpc.LoggingServiceImplBase {
    private final ListAuthH2 listAuthH2;

    public LogingServiceGrpcH2Impl(ListAuthH2 listAuthH2) {
        this.listAuthH2 = listAuthH2;
    }

    @Override
    public void log(LogRequest request, StreamObserver<Empty> responseObserver) {
        // Traitement à faire
        System.out.println(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}

package version_avec_hashmap.VOptional;

import com.google.protobuf.Empty;
import com.proto.authentification.GetLogRequest;
import com.proto.authentification.LogRequest;
import com.proto.authentification.LoggingServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogingServiceGrpcImpl extends LoggingServiceGrpc.LoggingServiceImplBase {
    private List<LogRequest> logs ;

    public LogingServiceGrpcImpl() {
        logs = new ArrayList<LogRequest>();
    }

    @Override
    public void log(LogRequest request, StreamObserver<Empty> responseObserver) {
        // Traitement à faire
        //System.out.println(request);
        logs.add(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetLogRequest request, StreamObserver<LogRequest> responseObserver) {
        List<LogRequest> logFilters = new ArrayList<>();
        if(request.hasPaire()){
            logFilters = logs.stream()
                    .filter(log -> log.getPaire().getPwd().equals(request.getPaire().getPwd())
                            && log.getPaire().getLogin().equals(request.getPaire().getLogin())
                    )
                    .collect(Collectors.toList());
            System.out.println(logs);
        } else if(request.hasResponse()){
            logFilters = logs.stream()
                    .filter(log -> log.getResponse().equals(request.getResponse())
                    )
                    .collect(Collectors.toList());
        }else if(request.hasType()){
            logFilters = logs.stream()
                    .filter(log -> log.getType().equals(request.getType())
                    )
                    .collect(Collectors.toList());
        }else{
            logFilters = logs.stream().toList();
        }

        for (LogRequest log : logFilters){
            responseObserver.onNext(log);
        }
        responseObserver.onCompleted();
    }
}

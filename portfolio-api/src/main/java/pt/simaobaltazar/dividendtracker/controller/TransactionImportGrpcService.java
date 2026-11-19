package pt.simaobaltazar.dividendtracker.controller;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import pt.simaobaltazar.dividendtracker.dto.TransactionInput;
import pt.simaobaltazar.dividendtracker.grpc.ImportSummary;
import pt.simaobaltazar.dividendtracker.grpc.TransactionImportServiceGrpc;
import pt.simaobaltazar.dividendtracker.grpc.TransactionRecord;
import pt.simaobaltazar.dividendtracker.model.TransactionType;
import pt.simaobaltazar.dividendtracker.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class TransactionImportGrpcService extends TransactionImportServiceGrpc.TransactionImportServiceImplBase {

    private final TransactionService transactionService;

    public TransactionImportGrpcService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public StreamObserver<TransactionRecord> importTransactions(StreamObserver<ImportSummary> responseObserver) {
        return new StreamObserver<>() {

            private int imported = 0;
            private final List<String> errors = new ArrayList<>();

            @Override
            public void onNext(TransactionRecord record) {
                try {
                    TransactionInput input = new TransactionInput(
                            TransactionType.valueOf(record.getType()),
                            new BigDecimal(record.getQuantity()),
                            new BigDecimal(record.getPrice()),
                            LocalDate.parse(record.getDate()),
                            record.getAssetTicker());
                    transactionService.add(input);
                    imported++;
                } catch (RuntimeException e) {
                    errors.add(record.getAssetTicker() + " " + record.getDate() + ": " + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                ImportSummary summary = ImportSummary.newBuilder()
                        .setImported(imported)
                        .setRejected(errors.size())
                        .addAllErrors(errors)
                        .build();
                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }
}
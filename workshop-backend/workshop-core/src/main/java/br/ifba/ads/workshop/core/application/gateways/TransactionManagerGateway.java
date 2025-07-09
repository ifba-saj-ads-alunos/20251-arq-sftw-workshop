package br.ifba.ads.workshop.core.application.gateways;

import java.util.function.Supplier;

public interface TransactionManagerGateway {
    <T> T doInTransaction(Supplier<T> action);
    void doInTransaction(Runnable action);
}

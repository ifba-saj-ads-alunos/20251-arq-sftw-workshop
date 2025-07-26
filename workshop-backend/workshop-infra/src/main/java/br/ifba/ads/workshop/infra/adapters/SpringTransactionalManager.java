    package br.ifba.ads.workshop.infra.adapters;

    import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
    import org.springframework.stereotype.Component;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.function.Supplier;

    @Component
    public class SpringTransactionalManager implements TransactionManagerGateway {
        @Override
        @Transactional
        public <T> T doInTransaction(Supplier<T> action) {
            return action.get();
        }

        @Override
        @Transactional
        public void doInTransaction(Runnable action) {
            action.run();
        }
    }

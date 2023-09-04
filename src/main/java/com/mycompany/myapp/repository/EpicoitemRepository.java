package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Epicoitem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Epicoitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpicoitemRepository extends ReactiveCrudRepository<Epicoitem, Long>, EpicoitemRepositoryInternal {
    @Override
    <S extends Epicoitem> Mono<S> save(S entity);

    @Override
    Flux<Epicoitem> findAll();

    @Override
    Mono<Epicoitem> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EpicoitemRepositoryInternal {
    <S extends Epicoitem> Mono<S> save(S entity);

    Flux<Epicoitem> findAllBy(Pageable pageable);

    Flux<Epicoitem> findAll();

    Mono<Epicoitem> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Epicoitem> findAllBy(Pageable pageable, Criteria criteria);
}

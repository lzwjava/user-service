package com.demo.lab.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public abstract class Repository<T extends Entity> {

    protected final AtomicInteger idGenerator;
    protected List<T> entities;

    public Repository() {
        entities = new ArrayList<>();
        idGenerator = new AtomicInteger(1);
    }

    public boolean saveEntity(T entity) {
        entity.setId(idGenerator.getAndIncrement());
        return entities.add(entity);
    }

    public T getEntity(Predicate<? super T> filter) {
        Objects.requireNonNull(filter);
        final Iterator<T> each = entities.iterator();
        while (each.hasNext()) {
            T next = each.next();
            if (filter.test(next)) {
                return next;
            }
        }
        return null;
    }

    public T getEntityById(int id) {
        return getEntity(entity -> entity.getId() == id);
    }


    public boolean deleteEntity(int id) {
        return entities.removeIf((role -> role.getId() == id));
    }

}

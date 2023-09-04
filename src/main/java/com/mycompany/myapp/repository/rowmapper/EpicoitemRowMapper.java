package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Epicoitem;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Epicoitem}, with proper type conversions.
 */
@Service
public class EpicoitemRowMapper implements BiFunction<Row, String, Epicoitem> {

    private final ColumnConverter converter;

    public EpicoitemRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Epicoitem} stored in the database.
     */
    @Override
    public Epicoitem apply(Row row, String prefix) {
        Epicoitem entity = new Epicoitem();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCategory(converter.fromRow(row, prefix + "_category", String.class));
        entity.setCostPrice(converter.fromRow(row, prefix + "_cost_price", Float.class));
        entity.setUnitPrice(converter.fromRow(row, prefix + "_unit_price", Float.class));
        entity.setPicFilename(converter.fromRow(row, prefix + "_pic_filename", String.class));
        return entity;
    }
}

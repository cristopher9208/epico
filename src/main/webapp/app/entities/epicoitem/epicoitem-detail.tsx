import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './epicoitem.reducer';

export const EpicoitemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const epicoitemEntity = useAppSelector(state => state.epicoitem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="epicoitemDetailsHeading">
          <Translate contentKey="proyectoepicoApp.epicoitem.detail.title">Epicoitem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{epicoitemEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="proyectoepicoApp.epicoitem.name">Nombre</Translate>
            </span>
          </dt>
          <dd>{epicoitemEntity.name}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="proyectoepicoApp.epicoitem.category">Categoria</Translate>
            </span>
          </dt>
          <dd>{epicoitemEntity.category}</dd>
          <dt>
            <span id="costPrice">
              <Translate contentKey="proyectoepicoApp.epicoitem.costPrice">Costo publico</Translate>
            </span>
          </dt>
          <dd>{epicoitemEntity.costPrice}</dd>
          <dt>
            <span id="unitPrice">
              <Translate contentKey="proyectoepicoApp.epicoitem.unitPrice">Precio unitario</Translate>
            </span>
          </dt>
          <dd>{epicoitemEntity.unitPrice}</dd>
          <dt>
            <span id="picFilename">
              <Translate contentKey="proyectoepicoApp.epicoitem.picFilename">Nombre imagen</Translate>
            </span>
          </dt>
          <dd>{epicoitemEntity.picFilename}</dd>
        </dl>
        <Button tag={Link} to="/epicoitem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/epicoitem/${epicoitemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EpicoitemDetail;

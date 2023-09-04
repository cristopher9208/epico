import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEpicoitem } from 'app/shared/model/epicoitem.model';
import { getEntity, updateEntity, createEntity, reset } from './epicoitem.reducer';

export const EpicoitemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const epicoitemEntity = useAppSelector(state => state.epicoitem.entity);
  const loading = useAppSelector(state => state.epicoitem.loading);
  const updating = useAppSelector(state => state.epicoitem.updating);
  const updateSuccess = useAppSelector(state => state.epicoitem.updateSuccess);

  const handleClose = () => {
    navigate('/epicoitem');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...epicoitemEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...epicoitemEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoepicoApp.epicoitem.home.createOrEditLabel" data-cy="EpicoitemCreateUpdateHeading">
            <Translate contentKey="proyectoepicoApp.epicoitem.home.createOrEditLabel">Create or edit a Epicoitem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="epicoitem-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoepicoApp.epicoitem.name')}
                id="epicoitem-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoepicoApp.epicoitem.category')}
                id="epicoitem-category"
                name="category"
                data-cy="category"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoepicoApp.epicoitem.costPrice')}
                id="epicoitem-costPrice"
                name="costPrice"
                data-cy="costPrice"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoepicoApp.epicoitem.unitPrice')}
                id="epicoitem-unitPrice"
                name="unitPrice"
                data-cy="unitPrice"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoepicoApp.epicoitem.picFilename')}
                id="epicoitem-picFilename"
                name="picFilename"
                data-cy="picFilename"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/epicoitem" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EpicoitemUpdate;

import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './rooms.reducer';
import { IRooms } from 'app/shared/model/rooms.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRoomsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RoomsUpdate = (props: IRoomsUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { roomsEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/rooms');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createDate = convertDateTimeToServer(values.createDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);
    values.deleteDate = convertDateTimeToServer(values.deleteDate);

    if (errors.length === 0) {
      const entity = {
        ...roomsEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jbookingApp.rooms.home.createOrEditLabel">
            <Translate contentKey="jbookingApp.rooms.home.createOrEditLabel">Create or edit a Rooms</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : roomsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="rooms-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="rooms-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="roomNameLabel" for="rooms-roomName">
                  <Translate contentKey="jbookingApp.rooms.roomName">Room Name</Translate>
                </Label>
                <AvField
                  id="rooms-roomName"
                  type="text"
                  name="roomName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="capacityLabel" for="rooms-capacity">
                  <Translate contentKey="jbookingApp.rooms.capacity">Capacity</Translate>
                </Label>
                <AvField
                  id="rooms-capacity"
                  type="string"
                  className="form-control"
                  name="capacity"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="hasConditionerLabel">
                  <AvInput id="rooms-hasConditioner" type="checkbox" className="form-check-input" name="hasConditioner" />
                  <Translate contentKey="jbookingApp.rooms.hasConditioner">Has Conditioner</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="hasVideoconferenceLabel">
                  <AvInput id="rooms-hasVideoconference" type="checkbox" className="form-check-input" name="hasVideoconference" />
                  <Translate contentKey="jbookingApp.rooms.hasVideoconference">Has Videoconference</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createDateLabel" for="rooms-createDate">
                  <Translate contentKey="jbookingApp.rooms.createDate">Create Date</Translate>
                </Label>
                <AvInput
                  id="rooms-createDate"
                  type="datetime-local"
                  className="form-control"
                  name="createDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.roomsEntity.createDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateDateLabel" for="rooms-updateDate">
                  <Translate contentKey="jbookingApp.rooms.updateDate">Update Date</Translate>
                </Label>
                <AvInput
                  id="rooms-updateDate"
                  type="datetime-local"
                  className="form-control"
                  name="updateDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.roomsEntity.updateDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deleteDateLabel" for="rooms-deleteDate">
                  <Translate contentKey="jbookingApp.rooms.deleteDate">Delete Date</Translate>
                </Label>
                <AvInput
                  id="rooms-deleteDate"
                  type="datetime-local"
                  className="form-control"
                  name="deleteDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.roomsEntity.deleteDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/rooms" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  roomsEntity: storeState.rooms.entity,
  loading: storeState.rooms.loading,
  updating: storeState.rooms.updating,
  updateSuccess: storeState.rooms.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RoomsUpdate);

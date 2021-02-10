import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRooms } from 'app/shared/model/rooms.model';
import { getEntities as getRooms } from 'app/entities/rooms/rooms.reducer';
import { getEntity, updateEntity, createEntity, reset } from './subscribings.reducer';
import { ISubscribings } from 'app/shared/model/subscribings.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubscribingsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubscribingsUpdate = (props: ISubscribingsUpdateProps) => {
  const [roomIdId, setRoomIdId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { subscribingsEntity, rooms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/subscribings');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRooms();
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
        ...subscribingsEntity,
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
          <h2 id="jbookingApp.subscribings.home.createOrEditLabel">
            <Translate contentKey="jbookingApp.subscribings.home.createOrEditLabel">Create or edit a Subscribings</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : subscribingsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="subscribings-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="subscribings-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="roomIdLabel" for="subscribings-roomId">
                  <Translate contentKey="jbookingApp.subscribings.roomId">Room Id</Translate>
                </Label>
                <AvField
                  id="subscribings-roomId"
                  type="string"
                  className="form-control"
                  name="roomId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="loginLabel" for="subscribings-login">
                  <Translate contentKey="jbookingApp.subscribings.login">Login</Translate>
                </Label>
                <AvField
                  id="subscribings-login"
                  type="text"
                  name="login"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createDateLabel" for="subscribings-createDate">
                  <Translate contentKey="jbookingApp.subscribings.createDate">Create Date</Translate>
                </Label>
                <AvInput
                  id="subscribings-createDate"
                  type="datetime-local"
                  className="form-control"
                  name="createDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.subscribingsEntity.createDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateDateLabel" for="subscribings-updateDate">
                  <Translate contentKey="jbookingApp.subscribings.updateDate">Update Date</Translate>
                </Label>
                <AvInput
                  id="subscribings-updateDate"
                  type="datetime-local"
                  className="form-control"
                  name="updateDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.subscribingsEntity.updateDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deleteDateLabel" for="subscribings-deleteDate">
                  <Translate contentKey="jbookingApp.subscribings.deleteDate">Delete Date</Translate>
                </Label>
                <AvInput
                  id="subscribings-deleteDate"
                  type="datetime-local"
                  className="form-control"
                  name="deleteDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.subscribingsEntity.deleteDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="subscribings-roomId">
                  <Translate contentKey="jbookingApp.subscribings.roomId">Room Id</Translate>
                </Label>
                <AvInput id="subscribings-roomId" type="select" className="form-control" name="roomId.id">
                  <option value="" key="0" />
                  {rooms
                    ? rooms.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/subscribings" replace color="info">
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
  rooms: storeState.rooms.entities,
  subscribingsEntity: storeState.subscribings.entity,
  loading: storeState.subscribings.loading,
  updating: storeState.subscribings.updating,
  updateSuccess: storeState.subscribings.updateSuccess,
});

const mapDispatchToProps = {
  getRooms,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubscribingsUpdate);

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
import { getEntity, updateEntity, createEntity, reset } from './bookings.reducer';
import { IBookings } from 'app/shared/model/bookings.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBookingsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingsUpdate = (props: IBookingsUpdateProps) => {
  const [roomIdId, setRoomIdId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bookingsEntity, rooms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/bookings');
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
    values.beginDate = convertDateTimeToServer(values.beginDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.createDate = convertDateTimeToServer(values.createDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);
    values.deleteDate = convertDateTimeToServer(values.deleteDate);

    if (errors.length === 0) {
      const entity = {
        ...bookingsEntity,
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
          <h2 id="jbookingApp.bookings.home.createOrEditLabel">
            <Translate contentKey="jbookingApp.bookings.home.createOrEditLabel">Create or edit a Bookings</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bookingsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="bookings-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="bookings-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="roomIdLabel" for="bookings-roomId">
                  <Translate contentKey="jbookingApp.bookings.roomId">Room Id</Translate>
                </Label>
                <AvField
                  id="bookings-roomId"
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
                <Label id="loginLabel" for="bookings-login">
                  <Translate contentKey="jbookingApp.bookings.login">Login</Translate>
                </Label>
                <AvField
                  id="bookings-login"
                  type="text"
                  name="login"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="beginDateLabel" for="bookings-beginDate">
                  <Translate contentKey="jbookingApp.bookings.beginDate">Begin Date</Translate>
                </Label>
                <AvInput
                  id="bookings-beginDate"
                  type="datetime-local"
                  className="form-control"
                  name="beginDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingsEntity.beginDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="bookings-endDate">
                  <Translate contentKey="jbookingApp.bookings.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="bookings-endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingsEntity.endDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createDateLabel" for="bookings-createDate">
                  <Translate contentKey="jbookingApp.bookings.createDate">Create Date</Translate>
                </Label>
                <AvInput
                  id="bookings-createDate"
                  type="datetime-local"
                  className="form-control"
                  name="createDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingsEntity.createDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateDateLabel" for="bookings-updateDate">
                  <Translate contentKey="jbookingApp.bookings.updateDate">Update Date</Translate>
                </Label>
                <AvInput
                  id="bookings-updateDate"
                  type="datetime-local"
                  className="form-control"
                  name="updateDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingsEntity.updateDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deleteDateLabel" for="bookings-deleteDate">
                  <Translate contentKey="jbookingApp.bookings.deleteDate">Delete Date</Translate>
                </Label>
                <AvInput
                  id="bookings-deleteDate"
                  type="datetime-local"
                  className="form-control"
                  name="deleteDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingsEntity.deleteDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="bookings-roomId">
                  <Translate contentKey="jbookingApp.bookings.roomId">Room Id</Translate>
                </Label>
                <AvInput id="bookings-roomId" type="select" className="form-control" name="roomId.id">
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
              <Button tag={Link} id="cancel-save" to="/bookings" replace color="info">
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
  bookingsEntity: storeState.bookings.entity,
  loading: storeState.bookings.loading,
  updating: storeState.bookings.updating,
  updateSuccess: storeState.bookings.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(BookingsUpdate);

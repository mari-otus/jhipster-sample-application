import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rooms.reducer';
import { IRooms } from 'app/shared/model/rooms.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRoomsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RoomsDetail = (props: IRoomsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { roomsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jbookingApp.rooms.detail.title">Rooms</Translate> [<b>{roomsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="roomName">
              <Translate contentKey="jbookingApp.rooms.roomName">Room Name</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.roomName}</dd>
          <dt>
            <span id="capacity">
              <Translate contentKey="jbookingApp.rooms.capacity">Capacity</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.capacity}</dd>
          <dt>
            <span id="hasConditioner">
              <Translate contentKey="jbookingApp.rooms.hasConditioner">Has Conditioner</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.hasConditioner ? 'true' : 'false'}</dd>
          <dt>
            <span id="hasVideoconference">
              <Translate contentKey="jbookingApp.rooms.hasVideoconference">Has Videoconference</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.hasVideoconference ? 'true' : 'false'}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="jbookingApp.rooms.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.createDate ? <TextFormat value={roomsEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="jbookingApp.rooms.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.updateDate ? <TextFormat value={roomsEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deleteDate">
              <Translate contentKey="jbookingApp.rooms.deleteDate">Delete Date</Translate>
            </span>
          </dt>
          <dd>{roomsEntity.deleteDate ? <TextFormat value={roomsEntity.deleteDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/rooms" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rooms/${roomsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ rooms }: IRootState) => ({
  roomsEntity: rooms.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RoomsDetail);

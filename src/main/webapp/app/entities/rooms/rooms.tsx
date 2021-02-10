import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './rooms.reducer';
import { IRooms } from 'app/shared/model/rooms.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRoomsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Rooms = (props: IRoomsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { roomsList, match, loading } = props;
  return (
    <div>
      <h2 id="rooms-heading">
        <Translate contentKey="jbookingApp.rooms.home.title">Rooms</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jbookingApp.rooms.home.createLabel">Create new Rooms</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {roomsList && roomsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.roomName">Room Name</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.capacity">Capacity</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.hasConditioner">Has Conditioner</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.hasVideoconference">Has Videoconference</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.createDate">Create Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.updateDate">Update Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.rooms.deleteDate">Delete Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roomsList.map((rooms, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${rooms.id}`} color="link" size="sm">
                      {rooms.id}
                    </Button>
                  </td>
                  <td>{rooms.roomName}</td>
                  <td>{rooms.capacity}</td>
                  <td>{rooms.hasConditioner ? 'true' : 'false'}</td>
                  <td>{rooms.hasVideoconference ? 'true' : 'false'}</td>
                  <td>{rooms.createDate ? <TextFormat type="date" value={rooms.createDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{rooms.updateDate ? <TextFormat type="date" value={rooms.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{rooms.deleteDate ? <TextFormat type="date" value={rooms.deleteDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${rooms.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rooms.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rooms.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jbookingApp.rooms.home.notFound">No Rooms found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ rooms }: IRootState) => ({
  roomsList: rooms.entities,
  loading: rooms.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Rooms);

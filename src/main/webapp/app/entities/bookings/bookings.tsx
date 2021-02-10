import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './bookings.reducer';
import { IBookings } from 'app/shared/model/bookings.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Bookings = (props: IBookingsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { bookingsList, match, loading } = props;
  return (
    <div>
      <h2 id="bookings-heading">
        <Translate contentKey="jbookingApp.bookings.home.title">Bookings</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jbookingApp.bookings.home.createLabel">Create new Bookings</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {bookingsList && bookingsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.roomId">Room Id</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.login">Login</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.beginDate">Begin Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.createDate">Create Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.updateDate">Update Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.deleteDate">Delete Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.bookings.roomId">Room Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bookingsList.map((bookings, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bookings.id}`} color="link" size="sm">
                      {bookings.id}
                    </Button>
                  </td>
                  <td>{bookings.roomId}</td>
                  <td>{bookings.login}</td>
                  <td>{bookings.beginDate ? <TextFormat type="date" value={bookings.beginDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bookings.endDate ? <TextFormat type="date" value={bookings.endDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bookings.createDate ? <TextFormat type="date" value={bookings.createDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bookings.updateDate ? <TextFormat type="date" value={bookings.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bookings.deleteDate ? <TextFormat type="date" value={bookings.deleteDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bookings.roomId ? <Link to={`rooms/${bookings.roomId.id}`}>{bookings.roomId.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bookings.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bookings.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bookings.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="jbookingApp.bookings.home.notFound">No Bookings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bookings }: IRootState) => ({
  bookingsList: bookings.entities,
  loading: bookings.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Bookings);

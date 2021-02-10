import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './subscribings.reducer';
import { ISubscribings } from 'app/shared/model/subscribings.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubscribingsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Subscribings = (props: ISubscribingsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { subscribingsList, match, loading } = props;
  return (
    <div>
      <h2 id="subscribings-heading">
        <Translate contentKey="jbookingApp.subscribings.home.title">Subscribings</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jbookingApp.subscribings.home.createLabel">Create new Subscribings</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {subscribingsList && subscribingsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.subscribings.roomId">Room Id</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.subscribings.login">Login</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.subscribings.createDate">Create Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.subscribings.updateDate">Update Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.subscribings.deleteDate">Delete Date</Translate>
                </th>
                <th>
                  <Translate contentKey="jbookingApp.subscribings.roomId">Room Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subscribingsList.map((subscribings, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${subscribings.id}`} color="link" size="sm">
                      {subscribings.id}
                    </Button>
                  </td>
                  <td>{subscribings.roomId}</td>
                  <td>{subscribings.login}</td>
                  <td>
                    {subscribings.createDate ? <TextFormat type="date" value={subscribings.createDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {subscribings.updateDate ? <TextFormat type="date" value={subscribings.updateDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {subscribings.deleteDate ? <TextFormat type="date" value={subscribings.deleteDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{subscribings.roomId ? <Link to={`rooms/${subscribings.roomId.id}`}>{subscribings.roomId.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${subscribings.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${subscribings.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${subscribings.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="jbookingApp.subscribings.home.notFound">No Subscribings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ subscribings }: IRootState) => ({
  subscribingsList: subscribings.entities,
  loading: subscribings.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Subscribings);

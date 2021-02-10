import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bookings.reducer';
import { IBookings } from 'app/shared/model/bookings.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingsDetail = (props: IBookingsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bookingsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jbookingApp.bookings.detail.title">Bookings</Translate> [<b>{bookingsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="roomId">
              <Translate contentKey="jbookingApp.bookings.roomId">Room Id</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.roomId}</dd>
          <dt>
            <span id="login">
              <Translate contentKey="jbookingApp.bookings.login">Login</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.login}</dd>
          <dt>
            <span id="beginDate">
              <Translate contentKey="jbookingApp.bookings.beginDate">Begin Date</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.beginDate ? <TextFormat value={bookingsEntity.beginDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="jbookingApp.bookings.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.endDate ? <TextFormat value={bookingsEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="jbookingApp.bookings.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.createDate ? <TextFormat value={bookingsEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="jbookingApp.bookings.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.updateDate ? <TextFormat value={bookingsEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deleteDate">
              <Translate contentKey="jbookingApp.bookings.deleteDate">Delete Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.deleteDate ? <TextFormat value={bookingsEntity.deleteDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="jbookingApp.bookings.roomId">Room Id</Translate>
          </dt>
          <dd>{bookingsEntity.roomId ? bookingsEntity.roomId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bookings" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bookings/${bookingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bookings }: IRootState) => ({
  bookingsEntity: bookings.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingsDetail);

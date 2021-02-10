import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './subscribings.reducer';
import { ISubscribings } from 'app/shared/model/subscribings.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubscribingsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubscribingsDetail = (props: ISubscribingsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { subscribingsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jbookingApp.subscribings.detail.title">Subscribings</Translate> [<b>{subscribingsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="roomId">
              <Translate contentKey="jbookingApp.subscribings.roomId">Room Id</Translate>
            </span>
          </dt>
          <dd>{subscribingsEntity.roomId}</dd>
          <dt>
            <span id="login">
              <Translate contentKey="jbookingApp.subscribings.login">Login</Translate>
            </span>
          </dt>
          <dd>{subscribingsEntity.login}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="jbookingApp.subscribings.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {subscribingsEntity.createDate ? (
              <TextFormat value={subscribingsEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="jbookingApp.subscribings.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {subscribingsEntity.updateDate ? (
              <TextFormat value={subscribingsEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deleteDate">
              <Translate contentKey="jbookingApp.subscribings.deleteDate">Delete Date</Translate>
            </span>
          </dt>
          <dd>
            {subscribingsEntity.deleteDate ? (
              <TextFormat value={subscribingsEntity.deleteDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jbookingApp.subscribings.roomId">Room Id</Translate>
          </dt>
          <dd>{subscribingsEntity.roomId ? subscribingsEntity.roomId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subscribings" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subscribings/${subscribingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ subscribings }: IRootState) => ({
  subscribingsEntity: subscribings.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubscribingsDetail);

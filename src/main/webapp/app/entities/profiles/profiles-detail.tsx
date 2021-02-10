import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './profiles.reducer';
import { IProfiles } from 'app/shared/model/profiles.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfilesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfilesDetail = (props: IProfilesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { profilesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jbookingApp.profiles.detail.title">Profiles</Translate> [<b>{profilesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">
              <Translate contentKey="jbookingApp.profiles.login">Login</Translate>
            </span>
          </dt>
          <dd>{profilesEntity.login}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jbookingApp.profiles.email">Email</Translate>
            </span>
          </dt>
          <dd>{profilesEntity.email}</dd>
          <dt>
            <span id="mobilePhone">
              <Translate contentKey="jbookingApp.profiles.mobilePhone">Mobile Phone</Translate>
            </span>
          </dt>
          <dd>{profilesEntity.mobilePhone}</dd>
          <dt>
            <span id="isEmailNotify">
              <Translate contentKey="jbookingApp.profiles.isEmailNotify">Is Email Notify</Translate>
            </span>
          </dt>
          <dd>{profilesEntity.isEmailNotify ? 'true' : 'false'}</dd>
          <dt>
            <span id="isPhoneNotify">
              <Translate contentKey="jbookingApp.profiles.isPhoneNotify">Is Phone Notify</Translate>
            </span>
          </dt>
          <dd>{profilesEntity.isPhoneNotify ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/profiles" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profiles/${profilesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ profiles }: IRootState) => ({
  profilesEntity: profiles.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfilesDetail);

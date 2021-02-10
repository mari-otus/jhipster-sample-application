import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './profiles.reducer';
import { IProfiles } from 'app/shared/model/profiles.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProfilesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfilesUpdate = (props: IProfilesUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { profilesEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/profiles');
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
    if (errors.length === 0) {
      const entity = {
        ...profilesEntity,
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
          <h2 id="jbookingApp.profiles.home.createOrEditLabel">
            <Translate contentKey="jbookingApp.profiles.home.createOrEditLabel">Create or edit a Profiles</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : profilesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="profiles-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="profiles-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="profiles-login">
                  <Translate contentKey="jbookingApp.profiles.login">Login</Translate>
                </Label>
                <AvField
                  id="profiles-login"
                  type="text"
                  name="login"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="profiles-email">
                  <Translate contentKey="jbookingApp.profiles.email">Email</Translate>
                </Label>
                <AvField id="profiles-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="mobilePhoneLabel" for="profiles-mobilePhone">
                  <Translate contentKey="jbookingApp.profiles.mobilePhone">Mobile Phone</Translate>
                </Label>
                <AvField id="profiles-mobilePhone" type="text" name="mobilePhone" />
              </AvGroup>
              <AvGroup check>
                <Label id="isEmailNotifyLabel">
                  <AvInput id="profiles-isEmailNotify" type="checkbox" className="form-check-input" name="isEmailNotify" />
                  <Translate contentKey="jbookingApp.profiles.isEmailNotify">Is Email Notify</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="isPhoneNotifyLabel">
                  <AvInput id="profiles-isPhoneNotify" type="checkbox" className="form-check-input" name="isPhoneNotify" />
                  <Translate contentKey="jbookingApp.profiles.isPhoneNotify">Is Phone Notify</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/profiles" replace color="info">
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
  profilesEntity: storeState.profiles.entity,
  loading: storeState.profiles.loading,
  updating: storeState.profiles.updating,
  updateSuccess: storeState.profiles.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfilesUpdate);

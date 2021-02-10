import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Profiles from './profiles';
import ProfilesDetail from './profiles-detail';
import ProfilesUpdate from './profiles-update';
import ProfilesDeleteDialog from './profiles-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProfilesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProfilesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProfilesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Profiles} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProfilesDeleteDialog} />
  </>
);

export default Routes;

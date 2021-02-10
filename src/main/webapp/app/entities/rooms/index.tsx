import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Rooms from './rooms';
import RoomsDetail from './rooms-detail';
import RoomsUpdate from './rooms-update';
import RoomsDeleteDialog from './rooms-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoomsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoomsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoomsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Rooms} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoomsDeleteDialog} />
  </>
);

export default Routes;

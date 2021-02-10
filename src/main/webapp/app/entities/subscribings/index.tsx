import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Subscribings from './subscribings';
import SubscribingsDetail from './subscribings-detail';
import SubscribingsUpdate from './subscribings-update';
import SubscribingsDeleteDialog from './subscribings-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubscribingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubscribingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubscribingsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Subscribings} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SubscribingsDeleteDialog} />
  </>
);

export default Routes;

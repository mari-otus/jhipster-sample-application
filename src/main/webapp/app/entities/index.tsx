import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Rooms from './rooms';
import Bookings from './bookings';
import Profiles from './profiles';
import Subscribings from './subscribings';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}rooms`} component={Rooms} />
      <ErrorBoundaryRoute path={`${match.url}bookings`} component={Bookings} />
      <ErrorBoundaryRoute path={`${match.url}profiles`} component={Profiles} />
      <ErrorBoundaryRoute path={`${match.url}subscribings`} component={Subscribings} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

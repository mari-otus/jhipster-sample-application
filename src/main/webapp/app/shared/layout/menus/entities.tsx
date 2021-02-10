import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/rooms">
      <Translate contentKey="global.menu.entities.rooms" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bookings">
      <Translate contentKey="global.menu.entities.bookings" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/profiles">
      <Translate contentKey="global.menu.entities.profiles" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/subscribings">
      <Translate contentKey="global.menu.entities.subscribings" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

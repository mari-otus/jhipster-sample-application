import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRooms, defaultValue } from 'app/shared/model/rooms.model';

export const ACTION_TYPES = {
  FETCH_ROOMS_LIST: 'rooms/FETCH_ROOMS_LIST',
  FETCH_ROOMS: 'rooms/FETCH_ROOMS',
  CREATE_ROOMS: 'rooms/CREATE_ROOMS',
  UPDATE_ROOMS: 'rooms/UPDATE_ROOMS',
  DELETE_ROOMS: 'rooms/DELETE_ROOMS',
  RESET: 'rooms/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRooms>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type RoomsState = Readonly<typeof initialState>;

// Reducer

export default (state: RoomsState = initialState, action): RoomsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ROOMS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ROOMS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ROOMS):
    case REQUEST(ACTION_TYPES.UPDATE_ROOMS):
    case REQUEST(ACTION_TYPES.DELETE_ROOMS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ROOMS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ROOMS):
    case FAILURE(ACTION_TYPES.CREATE_ROOMS):
    case FAILURE(ACTION_TYPES.UPDATE_ROOMS):
    case FAILURE(ACTION_TYPES.DELETE_ROOMS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ROOMS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ROOMS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ROOMS):
    case SUCCESS(ACTION_TYPES.UPDATE_ROOMS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ROOMS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/rooms';

// Actions

export const getEntities: ICrudGetAllAction<IRooms> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ROOMS_LIST,
  payload: axios.get<IRooms>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IRooms> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ROOMS,
    payload: axios.get<IRooms>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRooms> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ROOMS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRooms> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ROOMS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRooms> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ROOMS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

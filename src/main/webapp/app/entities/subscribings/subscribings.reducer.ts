import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubscribings, defaultValue } from 'app/shared/model/subscribings.model';

export const ACTION_TYPES = {
  FETCH_SUBSCRIBINGS_LIST: 'subscribings/FETCH_SUBSCRIBINGS_LIST',
  FETCH_SUBSCRIBINGS: 'subscribings/FETCH_SUBSCRIBINGS',
  CREATE_SUBSCRIBINGS: 'subscribings/CREATE_SUBSCRIBINGS',
  UPDATE_SUBSCRIBINGS: 'subscribings/UPDATE_SUBSCRIBINGS',
  DELETE_SUBSCRIBINGS: 'subscribings/DELETE_SUBSCRIBINGS',
  RESET: 'subscribings/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubscribings>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SubscribingsState = Readonly<typeof initialState>;

// Reducer

export default (state: SubscribingsState = initialState, action): SubscribingsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBSCRIBINGS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBSCRIBINGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBSCRIBINGS):
    case REQUEST(ACTION_TYPES.UPDATE_SUBSCRIBINGS):
    case REQUEST(ACTION_TYPES.DELETE_SUBSCRIBINGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBSCRIBINGS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBSCRIBINGS):
    case FAILURE(ACTION_TYPES.CREATE_SUBSCRIBINGS):
    case FAILURE(ACTION_TYPES.UPDATE_SUBSCRIBINGS):
    case FAILURE(ACTION_TYPES.DELETE_SUBSCRIBINGS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBSCRIBINGS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBSCRIBINGS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBSCRIBINGS):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBSCRIBINGS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBSCRIBINGS):
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

const apiUrl = 'api/subscribings';

// Actions

export const getEntities: ICrudGetAllAction<ISubscribings> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBSCRIBINGS_LIST,
  payload: axios.get<ISubscribings>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISubscribings> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBSCRIBINGS,
    payload: axios.get<ISubscribings>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISubscribings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBSCRIBINGS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubscribings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBSCRIBINGS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubscribings> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBSCRIBINGS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

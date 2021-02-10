import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProfiles, defaultValue } from 'app/shared/model/profiles.model';

export const ACTION_TYPES = {
  FETCH_PROFILES_LIST: 'profiles/FETCH_PROFILES_LIST',
  FETCH_PROFILES: 'profiles/FETCH_PROFILES',
  CREATE_PROFILES: 'profiles/CREATE_PROFILES',
  UPDATE_PROFILES: 'profiles/UPDATE_PROFILES',
  DELETE_PROFILES: 'profiles/DELETE_PROFILES',
  RESET: 'profiles/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProfiles>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProfilesState = Readonly<typeof initialState>;

// Reducer

export default (state: ProfilesState = initialState, action): ProfilesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROFILES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROFILES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROFILES):
    case REQUEST(ACTION_TYPES.UPDATE_PROFILES):
    case REQUEST(ACTION_TYPES.DELETE_PROFILES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROFILES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROFILES):
    case FAILURE(ACTION_TYPES.CREATE_PROFILES):
    case FAILURE(ACTION_TYPES.UPDATE_PROFILES):
    case FAILURE(ACTION_TYPES.DELETE_PROFILES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROFILES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROFILES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROFILES):
    case SUCCESS(ACTION_TYPES.UPDATE_PROFILES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROFILES):
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

const apiUrl = 'api/profiles';

// Actions

export const getEntities: ICrudGetAllAction<IProfiles> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROFILES_LIST,
  payload: axios.get<IProfiles>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProfiles> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROFILES,
    payload: axios.get<IProfiles>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProfiles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROFILES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProfiles> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROFILES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProfiles> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROFILES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

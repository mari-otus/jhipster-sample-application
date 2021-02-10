import { Moment } from 'moment';
import { IRooms } from 'app/shared/model/rooms.model';

export interface ISubscribings {
  id?: number;
  roomId?: number;
  login?: string;
  createDate?: string;
  updateDate?: string;
  deleteDate?: string;
  roomId?: IRooms;
}

export const defaultValue: Readonly<ISubscribings> = {};

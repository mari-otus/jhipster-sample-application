import { Moment } from 'moment';
import { IRooms } from 'app/shared/model/rooms.model';

export interface IBookings {
  id?: number;
  roomId?: number;
  login?: string;
  beginDate?: string;
  endDate?: string;
  createDate?: string;
  updateDate?: string;
  deleteDate?: string;
  roomId?: IRooms;
}

export const defaultValue: Readonly<IBookings> = {};

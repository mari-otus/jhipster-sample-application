import { Moment } from 'moment';

export interface IRooms {
  id?: number;
  roomName?: string;
  capacity?: number;
  hasConditioner?: boolean;
  hasVideoconference?: boolean;
  createDate?: string;
  updateDate?: string;
  deleteDate?: string;
}

export const defaultValue: Readonly<IRooms> = {
  hasConditioner: false,
  hasVideoconference: false,
};

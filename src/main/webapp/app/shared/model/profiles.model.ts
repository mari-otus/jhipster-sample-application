export interface IProfiles {
  id?: number;
  login?: string;
  email?: string;
  mobilePhone?: string;
  isEmailNotify?: boolean;
  isPhoneNotify?: boolean;
}

export const defaultValue: Readonly<IProfiles> = {
  isEmailNotify: false,
  isPhoneNotify: false,
};

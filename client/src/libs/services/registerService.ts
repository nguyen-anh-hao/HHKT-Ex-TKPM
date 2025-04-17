import { RegisterRequest } from '@/interfaces/RegisterResponse';
import { RegisterResponse } from '@/interfaces/RegisterResponse';
import {
  getRegistrations,
  getRegistrationById,
  postRegistration,
  patchRegistration
} from '@/libs/api/registerApi';
import { cleanData } from '@/libs/utils/cleanData';

export const fetchRegistrations = async (): Promise<RegisterResponse[]> => {
  try {
    return await getRegistrations();
  } catch (error) {
    throw error;
  }
};

export const fetchRegistrationById = async (id: number): Promise<RegisterResponse> => {
  try {
    return await getRegistrationById(id);
  } catch (error) {
    throw error;
  }
};

export const createRegistration = async (value: RegisterRequest) => {
  const requestData = cleanData(value) as unknown as RegisterRequest;
  try {
    const data = await postRegistration(requestData);
    return data;
  } catch (error) {
    throw error;
  }
};

export const updateRegistration = async (
  id: number,
  value: Partial<RegisterRequest>
) => {
  const requestData = cleanData(value);
  try {
    const data = await patchRegistration(id, requestData);
    return data;
  } catch (error) {
    throw error;
  }
};

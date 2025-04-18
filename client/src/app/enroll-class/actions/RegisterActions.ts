import { message } from 'antd';
import { RegisterResponse } from '../../../interfaces/RegisterResponse';

// Function to add a new register
export const addRegister = (registers: RegisterResponse[], newRegister: RegisterResponse): RegisterResponse[] => {

  if (registers.some(register => register.classId === newRegister.classId && register.studentId === newRegister.studentId)) {
    message.error('Đăng ký lớp học này đã tồn tại!');
    return registers;
  }


  return [...registers, newRegister];
};


export const updateRegister = (registers: RegisterResponse[], updatedRegister: RegisterResponse): RegisterResponse[] => {
  return registers.map(register =>
    register.id === updatedRegister.id ? { ...register, ...updatedRegister } : register
  );
};




export interface RegisterResponse {
  id: number;
  studentId: string;
  studentName: string;
  classId: string;
  courseName: string;
  status: 'REGISTERED' | 'CANCELLED' | 'COMPLETED';
  grade: number | null;
  createdAt: string;
  updatedAt: string;
}

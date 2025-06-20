export interface CreateSemesterRequest {
  semester: number;
  startDate: string;
  endDate: string;
  academicYear: string;
  lastCancelDate: string;
  createdBy?: string;
}

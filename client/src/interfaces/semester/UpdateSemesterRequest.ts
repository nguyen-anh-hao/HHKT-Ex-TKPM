export interface UpdateSemesterRequest {
  id: number;
  semester?: number;
  startDate?: string;
  endDate?: string;
  academicYear?: string;
  lastCancelDate?: string;
  updatedBy?: string;
}

import { z } from 'zod';
import { Student } from '@/interfaces/student/Student';

/**
 * Creates a validation schema for student creation
 * @param existingStudents - Array of existing students to check for duplicates
 * @returns Zod schema for student validation
 */
export const createStudentSchema = (existingStudents: Student[]) => {
  return z.object({
    // Basic information
    studentId: z.string()
      .nonempty('Student ID is required')
      .refine(
        (id) => !existingStudents.some(student => student.studentId === id),
        { message: 'Student ID already exists' }
      ),
      
    fullName: z.string()
      .nonempty('Full name is required'),
      
    // Contact information
    email: z.string()
      .email('Invalid email format')
      .refine(
        (email) => !existingStudents.some(student => student.email === email),
        { message: 'Email already exists' }
      ),
      
    phone: z.string()
      .nonempty('Phone number is required')
      .refine(
        (phone) => !existingStudents.some(student => student.phone === phone),
        { message: 'Phone number already exists' }
      ),
      
    phoneCountry: z.string()
      .nonempty('Country code is required'),
      
    // Optional validation for other fields could be added here
  });
};

/**
 * Creates a validation schema for student updates
 * @param currentStudentId - The ID of the student being updated
 * @param existingStudents - Array of existing students to check for duplicates
 * @returns Zod schema for student update validation
 */
export const updateStudentSchema = (currentStudentId: string, existingStudents: Student[]) => {
  return createStudentSchema(existingStudents)
    .extend({
      studentId: z.string().optional(),
      email: z.string()
        .email('Invalid email format')
        .refine(
          (email) => !existingStudents.some(student => 
            student.email === email && student.studentId !== currentStudentId
          ),
          { message: 'Email already exists' }
        ),
      phone: z.string()
        .refine(
          (phone) => !existingStudents.some(student => 
            student.phone === phone && student.studentId !== currentStudentId
          ),
          { message: 'Phone number already exists' }
        ),
    });
};
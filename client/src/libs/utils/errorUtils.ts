import { AxiosError } from 'axios';

/**
 * Enhanced error handling for API calls
 * @param error - The error caught in the catch block
 * @param context - Optional context information about where the error occurred
 */
export const handleApiError = (error: unknown, context?: string): never => {
  // Convert to a known error type
  let enhancedError: Error;
  
  if (error instanceof AxiosError) {
    // Extract response data if available
    const responseData = error.response?.data;
    const status = error.response?.status;
    const message = responseData?.message || error.message;
    
    enhancedError = new Error(
      `API Error${context ? ` in ${context}` : ''}: ${message} [Status: ${status || 'unknown'}]`
    );
    
    // Preserve the original error's properties
    enhancedError.cause = error;
    (enhancedError as any).response = error.response;
    (enhancedError as any).status = status;
  } else if (error instanceof Error) {
    enhancedError = error;
    if (context) {
      enhancedError.message = `${context}: ${enhancedError.message}`;
    }
  } else {
    // Handle non-Error objects
    enhancedError = new Error(`Unknown error${context ? ` in ${context}` : ''}: ${String(error)}`);
  }
  
  // Log the error for debugging
  console.error(enhancedError);
  
  // Rethrow the enhanced error
  throw enhancedError;
};

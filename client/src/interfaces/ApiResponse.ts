interface ValidationError {
    objectName: string;
    field: string;
    rejectedValue: any;
    codes: string[];
    arguments: any[];
    defaultMessage: string;
    bindingFailure: boolean;
    code: string;
}

export interface ApiErrorResponse {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    trace?: string;
    path?: string;
    errors?: ValidationError[];
}

interface PaginationInfo {
    page: number;
    limit: number;
    totalPages: number;
    totalItems: number;
}

export interface ApiSuccessResponse<T> {
    message: string;
    status: number;       
    data: T | null;
    paginationInfo?: PaginationInfo | null;
}

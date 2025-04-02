export interface Identity {
    documentType: string;
    documentNumber: string;
    expiredDate: string;
    hasChip: boolean;
    issuedBy: string;
    issuedCountry: string;
    issuedDate: string;
    note: string | null;
}
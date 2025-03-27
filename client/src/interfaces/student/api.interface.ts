export interface StudentGetResponse {
    studentId: string;
    fullName: string;
    dob: string;
    gender: string;
    faculty: string;
    intake: string;
    program: string;
    email: string;
    phone: string;
    studentStatus: string;
    nationality: string;
    addresses: Address[];
    documents: Indentity[];
    createdAt: string;
    updatedAt: string;
    createdBy: string;
    updatedBy: string;
}

export interface StudentPostRequest {
    mssv: string;
    hoTen: string;
    ngaySinh: number[];
    gioiTinh: string;
    khoaId: number;
    khoaHoc: string;
    chuongTrinhId: number;
    email: string;
    soDienThoai: string;
    tinhTrangId: number;
    quocTich: string;
    diaChis: Address[];
    giayTos: Indentity[];
}

export interface Address {
    addressType: string;
    houseNumberStreetName: string;
    wardCommune: string;
    district: string;
    cityProvince: string;
    country: string;
}

export interface Indentity {
    documentType: string;
    documentNumber: string;
    expiredDate: string;
    hasChip: boolean;
    issuedBy: string;
    issuedCountry: string;
    issuedDate:  string;
    note: string | null;
}
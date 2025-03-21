export interface StudentGetResponse {
    mssv: string;
    hoTen: string;
    ngaySinh: number[];
    gioiTinh: string;
    khoa: string;
    khoaHoc: string;
    chuongTrinh: string;
    email: string;
    soDienThoai: string;
    tinhTrang: string;
    quocTich: string;
    diaChis: Address[];
    giayTos: Indentity[];
    createdAt: number[];
    updatedAt: number[];
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
    loaiDiaChi: string;
    soNhaTenDuong: string;
    phuongXa: string;
    quanHuyen: string;
    tinhThanhPho: string;
    quocGia: string;
}

export interface Indentity {
    loaiGiayTo: string;
    soGiayTo: string;
    ngayCap: number[];
    ngayHetHan: number[];
    noiCap: string;
    quocGiaCap: string;
    ghiChu: string;
    coGanChip: boolean;
}
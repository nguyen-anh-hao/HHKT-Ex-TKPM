#include <iostream>
#include <vector>
#include <string>
#include <regex>

using namespace std;

// Cấu trúc sinh viên
struct SinhVien {
    string mssv;
    string hoTen;
    string ngaySinh;
    string gioiTinh;
    string khoa;
    string khoaHoc;
    string chuongTrinh;
    string diaChi;
    string email;
    string soDienThoai;
    string tinhTrang;

    SinhVien(string m, string h, string ns, string gt, string k, string kh, string ct, string dc, string e, string sdt, string tt)
        : mssv(m), hoTen(h), ngaySinh(ns), gioiTinh(gt), khoa(k), khoaHoc(kh), chuongTrinh(ct), diaChi(dc), email(e), soDienThoai(sdt), tinhTrang(tt) {}
};

// Danh sách các khoa
vector<string> danhSachKhoa = {"Khoa Luat", "Khoa Tieng Anh Thuong Mai", "Khoa Tieng Nhat", "Khoa Tieng Phap"};
// Danh sách các tình trạng sinh viên
vector<string> danhSachTinhTrang = {"Dang hoc", "Da tot nghiep", "Da thoi hoc", "Tam dung hoc"};

// Kiểm tra email hợp lệ
bool kiemTraEmail(const string& email) {
    regex pattern(R"((\w+)(\.\w+)*@(\w+)(\.\w+)+)");
    return regex_match(email, pattern);
}

// Kiểm tra số điện thoại hợp lệ
bool kiemTraSoDienThoai(const string& soDienThoai) {
    regex pattern(R"(\d{10})");
    return regex_match(soDienThoai, pattern);
}

// Kiểm tra khoa hợp lệ
bool kiemTraKhoa(const string& khoa) {
    for (const string& k : danhSachKhoa) {
        if (khoa == k) {
            return true;
        }
    }
    return false;
}

// Kiểm tra tình trạng hợp lệ
bool kiemTraTinhTrang(const string& tinhTrang) {
    for (const string& tt : danhSachTinhTrang) {
        if (tinhTrang == tt) {
            return true;
        }
    }
    return false;
}

// Hàm thêm sinh viên
void themSinhVien(vector<SinhVien>& sinhVienList) {
    string mssv, hoTen, ngaySinh, gioiTinh, khoa, khoaHoc, chuongTrinh, diaChi, email, soDienThoai, tinhTrang;
    
    cout << "Nhap Ma so sinh vien: ";
    cin >> mssv;
    cin.ignore(); // Để bỏ qua dấu enter dư thừa sau khi nhập mssv
    cout << "Nhap Ho ten: ";
    getline(cin, hoTen);
    cout << "Nhap Ngay sinh: ";
    getline(cin, ngaySinh);
    cout << "Nhap Gioi tinh: ";
    getline(cin, gioiTinh);
    cout << "Nhap Khoa: ";
    getline(cin, khoa);
    if (!kiemTraKhoa(khoa)) {
        cout << "Khoa khong hop le!" << endl;
        return;
    }
    cout << "Nhap Khoa hoc: ";
    getline(cin, khoaHoc);
    cout << "Nhap Chuong trinh: ";
    getline(cin, chuongTrinh);
    cout << "Nhap Dia chi: ";
    getline(cin, diaChi);
    cout << "Nhap Email: ";
    getline(cin, email);
    if (!kiemTraEmail(email)) {
        cout << "Email khong hop le!" << endl;
        return;
    }
    cout << "Nhap So dien thoai: ";
    getline(cin, soDienThoai);
    if (!kiemTraSoDienThoai(soDienThoai)) {
        cout << "So dien thoai khong hop le!" << endl;
        return;
    }
    cout << "Nhap Tinh trang: ";
    getline(cin, tinhTrang);
    if (!kiemTraTinhTrang(tinhTrang)) {
        cout << "Tinh trang khong hop le!" << endl;
        return;
    }

    sinhVienList.push_back(SinhVien(mssv, hoTen, ngaySinh, gioiTinh, khoa, khoaHoc, chuongTrinh, diaChi, email, soDienThoai, tinhTrang));
    cout << "Sinh vien da duoc them thanh cong!" << endl;
}

// Hàm xóa sinh viên
void xoaSinhVien(vector<SinhVien>& sinhVienList) {
    string mssv;
    cout << "Nhap MSSV sinh vien can xoa: ";
    cin >> mssv;

    bool found = false;
    for (auto it = sinhVienList.begin(); it != sinhVienList.end(); ++it) {
        if (it->mssv == mssv) {
            sinhVienList.erase(it);
            found = true;
            cout << "Sinh vien da duoc xoa!" << endl;
            break;
        }
    }

    if (!found) {
        cout << "Sinh vien khong tim thay!" << endl;
    }
}

// Hàm cập nhật thông tin sinh viên
void capNhatSinhVien(vector<SinhVien>& sinhVienList) {
    string mssv;
    cout << "Nhap MSSV sinh vien can cap nhat: ";
    cin >> mssv;

    bool found = false;
    for (auto& sv : sinhVienList) {
        if (sv.mssv == mssv) {
            found = true;
            cout << "Cap nhat thong tin sinh vien: " << endl;
            cout << "Nhap Ho ten moi: ";
            cin.ignore(); // Để bỏ qua dấu enter dư thừa
            getline(cin, sv.hoTen);
            cout << "Nhap Ngay sinh moi: ";
            getline(cin, sv.ngaySinh);
            cout << "Nhap Gioi tinh moi: ";
            getline(cin, sv.gioiTinh);
            cout << "Nhap Khoa moi: ";
            getline(cin, sv.khoa);
            cout << "Nhap Khoa hoc moi: ";
            getline(cin, sv.khoaHoc);
            cout << "Nhap Chuong trinh moi: ";
            getline(cin, sv.chuongTrinh);
            cout << "Nhap Dia chi moi: ";
            getline(cin, sv.diaChi);
            cout << "Nhap Email moi: ";
            getline(cin, sv.email);
            cout << "Nhap So dien thoai moi: ";
            getline(cin, sv.soDienThoai);
            cout << "Nhap Tinh trang moi: ";
            getline(cin, sv.tinhTrang);
            cout << "Thong tin sinh vien da duoc cap nhat!" << endl;
            break;
        }
    }

    if (!found) {
        cout << "Sinh vien khong tim thay!" << endl;
    }
}

// Hàm tìm kiếm sinh viên
void timKiemSinhVien(const vector<SinhVien>& sinhVienList) {
    string keyword;
    cout << "Nhap MSSV hoac Ho ten sinh vien can tim: ";
    cin.ignore();
    getline(cin, keyword);

    bool found = false;
    for (const auto& sv : sinhVienList) {
        if (sv.mssv == keyword || sv.hoTen == keyword) {
            found = true;
            cout << "Thong tin sinh vien: " << endl;
            cout << "MSSV: " << sv.mssv << endl;
            cout << "Ho ten: " << sv.hoTen << endl;
            cout << "Ngay sinh: " << sv.ngaySinh << endl;
            cout << "Gioi tinh: " << sv.gioiTinh << endl;
            cout << "Khoa: " << sv.khoa << endl;
            cout << "Khoa hoc: " << sv.khoaHoc << endl;
            cout << "Chuong trinh: " << sv.chuongTrinh << endl;
            cout << "Dia chi: " << sv.diaChi << endl;
            cout << "Email: " << sv.email << endl;
            cout << "So dien thoai: " << sv.soDienThoai << endl;
            cout << "Tinh trang: " << sv.tinhTrang << endl;
            break;
        }
    }

    if (!found) {
        cout << "Sinh vien khong tim thay!" << endl;
    }
}

int main() {
    vector<SinhVien> sinhVienList;
    int choice;

    while (true) {
        cout << "\n---- CHUONG TRINH QUAN LY SINH VIEN ----" << endl;
        cout << "1. Them sinh vien" << endl;
        cout << "2. Xoa sinh vien" << endl;
        cout << "3. Cap nhat thong tin sinh vien" << endl;
        cout << "4. Tim kiem sinh vien" << endl;
        cout << "5. Thoat" << endl;
        cout << "Nhap lua chon: ";
        cin >> choice;

        switch (choice) {
            case 1:
                themSinhVien(sinhVienList);
                break;
            case 2:
                xoaSinhVien(sinhVienList);
                break;
            case 3:
                capNhatSinhVien(sinhVienList);
                break;
            case 4:
                timKiemSinhVien(sinhVienList);
                break;
            case 5:
                cout << "Thoat chuong trinh." << endl;
                return 0;
            default:
                cout << "Lua chon khong hop le!" << endl;
        }
    }

    return 0;
}

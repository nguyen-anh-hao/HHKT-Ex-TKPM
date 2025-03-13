# Quản lý sinh viên

## Cấu trúc source code
```bash
/HHKT-Ex-TKPM
├── .gitignore # Danh sách file bị bỏ qua khi push Git
├── QLSV       # Chương trình sau khi biên dịch
├── QLSV.cpp   # Source Code
```

## Hướng dẫn cài đặt & chạy chương trình

Do project được viết đơn giản bằng ngôn ngữ C++ nên có nhiều cách để cài đặt (VSCode, Visual Studio, CodeBlock,...). Dưới đây, nhóm hướng dẫn cách cài đặt đơn giản bằng trình biên dịch g++ (GCC).

- **Windows**: Cài đặt MinGW.
- **Linux / macOS**: Cài đặt GCC (nếu chưa có)
  ```bash
  sudo apt update && sudo apt install g++
  ```

# Biên dịch

Chạy lệnh sau trong terminal/cmd.

```bash
g++ QLSV.cpp -o QLSV     # Linux/macOS
g++ QLSV.cpp -o QLSV.exe # Windows
```

# Chạy chương trình

```
./QLSV   # Linux/macOS
QLSV.exe # Windows
```

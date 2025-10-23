# QuanLyMoHinh (Gundam Inventory)

Dự án nhỏ bằng Java để quản lý mô hình Gundam theo phong cách hướng đối tượng, sử dụng pattern Decorator để mở rộng tính năng mô hình và Singleton để quản lý tồn kho.

## Mục tiêu

Ứng dụng cho phép tạo, nhập, bán, sửa, xóa và liệt kê các mô hình Gundam trong kho. Thiết kế tập trung vào tính mở rộng: có thể thêm các decorator (LED, vũ khí, sơn, bản giới hạn...) mà không thay đổi lớp cơ sở.

## Tính năng

- Tạo mô hình cơ bản (`BasicGundam`) với tên và giá gốc.
- Mở rộng mô hình bằng các `Decorator`: `LedDecorator`, `WeaponDecorator` (ví dụ: thêm LED, thêm vũ khí).
- Quản lý tồn kho qua `GundamInventory` (Singleton): nhập hàng, bán hàng, xem tồn kho.
- Menu tương tác trong `App.java` để thêm/xóa/sửa/bán/hiển thị sản phẩm.
- Mã mẫu mở rộng và tài liệu (trong `mermay.txt`) gồm `PaintingDecorator`, `LimitedEdition`, và `GundamKey` để gợi ý cách mở rộng.

## Cấu trúc project (các file chính)

- `src/` - mã nguồn Java:
	- `App.java` - chương trình chính, có menu tương tác.
	- `Gundam.java` - abstract base class.
	- `BasicGundam.java` - mô tả mô hình cơ bản.
	- `GundamDecorator.java` - base decorator.
	- `LedDecorator.java`, `WeaponDecorator.java` - ví dụ decorator.
	- `GundamInventory.java` - singleton quản lý kho.
	- `mermay.txt` - hướng dẫn chi tiết và nhiều ví dụ mã mở rộng.
	- `text.txt` - sơ đồ Mermaid của cấu trúc lớp.

## Cách chạy (Windows PowerShell)

1. Mở PowerShell ở thư mục gốc `QuanLyMoHinh`.
2. Biên dịch tất cả các file .java:

```powershell
if (-Not (Test-Path out)) { New-Item -ItemType Directory -Path out }
$files = Get-ChildItem -Path src -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
```

3. Chạy chương trình chính (menu):

```powershell
java -cp out App
```

Ghi chú: PowerShell cũ có thể gặp encoding khi in dấu tiếng Việt; dự án đã dùng chuỗi ASCII-only cho các thông báo (ví dụ: "Da nhap", "Ton kho") để tránh lỗi hiển thị.

## Hướng mở rộng

- Thêm decorator mới (ví dụ: `PaintingDecorator`) bằng cách mở rộng `GundamDecorator` và override `getDescription()`/`getPrice()`.
- Nếu muốn phân biệt mỗi mô hình theo ID/serial, thêm trường ID vào `Gundam` và điều chỉnh `equals`/`hashCode` hoặc dùng `GundamKey`.
- Có thể thêm UI console nâng cao hoặc lưu/truy xuất kho vào file/DB.

## Liên hệ

Repo gốc: https://github.com/KhanhDanh-design/java_project.git

---
Phiên bản: cơ bản. Nếu bạn muốn mình tự động push các thay đổi lên GitHub hoặc tạo các file bổ sung (unit tests, Dockerfile, v.v.), nói mình biết.


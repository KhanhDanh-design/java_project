# QuanLyMoHinh (Gundam Inventory)

Dự án nhỏ bằng Java để quản lý mô hình Gundam theo phong cách hướng đối tượng, sử dụng pattern Decorator để mở rộng tính năng mô hình và Singleton để quản lý tồn kho.

## Mục tiêu

Ứng dụng cho phép tạo, nhập, bán, sửa, xóa và liệt kê các mô hình Gundam trong kho. Thiết kế tập trung vào tính mở rộng: có thể thêm các decorator (LED, vũ khí, sơn, bản giới hạn...) mà không thay đổi lớp cơ sở.

## Tính năng (đã triển khai)

- Tạo mô hình cơ bản (`BasicGundam`) với tên và giá gốc.
- Mở rộng mô hình bằng các `Decorator`: `LedDecorator`, `WeaponDecorator` (ví dụ: thêm LED, thêm vũ khí). Các decorator có thể xâu chuỗi (một mô hình có thể có LED và vũ khí cùng lúc).
- Quản lý tồn kho qua `GundamInventory` (Singleton):
	- `importGundam(g, qty)` — nhập hàng (tăng số lượng).
	- `sellGundam(g, qty)` — bán hàng (giảm số lượng nếu đủ tồn).
	- `setQuantity(g, qty)` — đặt trực tiếp số lượng (0 để xóa mục).
	- `removeGundam(g)` — xóa hoàn toàn một mục khỏi kho.
- Menu tương tác trong `App.java` (console):
	- Các hành động: `add`, `remove`, `edit`, `sell`, `show`, `list`, `exit`.
	- Khi thực hiện `remove` / `edit` / `sell`, chương trình sẽ hiển thị danh sách sản phẩm (có chỉ số) để người dùng chọn bằng số, tránh phải gõ chính xác mô tả.
	- Lệnh `list` cho phép lọc theo khoảng giá (min/max) và khoảng số lượng (min/max) trước khi hiển thị.
	- Đã thêm kiểm tra nhập liệu: khi nhập số lượng hoặc giá, chương trình chỉ chấp nhận số (yêu cầu nhập lại nếu nhập chữ).
- `mermay.txt` và `text.txt` chứa tài liệu bổ sung: hướng dẫn mở rộng, ví dụ `PaintingDecorator`, `LimitedEdition`, `GundamKey` và sơ đồ Mermaid.

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
javac *.java
```

3. Chạy chương trình chính (menu):

```powershell
java App
```

Ghi chú: PowerShell cũ có thể gặp encoding khi in dấu tiếng Việt; dự án đã dùng chuỗi ASCII-only cho các thông báo (ví dụ: "Da nhap", "Ton kho") để tránh lỗi hiển thị.

## Hướng mở rộng

- Thêm decorator mới (ví dụ: `PaintingDecorator`) bằng cách mở rộng `GundamDecorator` và override `getDescription()`/`getPrice()`.
- Nếu muốn phân biệt mỗi mô hình theo ID/serial, thêm trường ID vào `Gundam` và điều chỉnh `equals`/`hashCode` hoặc dùng `GundamKey`.
- Có thể thêm UI console nâng cao hoặc lưu/truy xuất kho vào file/DB.

## Liên hệ

Repo gốc: https://github.com/KhanhDanh-design/java_project.git




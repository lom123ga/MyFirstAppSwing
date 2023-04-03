# MyFirstAppSwing

## 22/03/2023 Complete model 

### Lý thuyết mới duy nhất được sủ dựng trong này chính là về digital signature 

1. Pubkey hash :

Làm thế nào để tạo một pubkey hash ? 

Chỉ cần lấy publickey và cho nó qua hàm SHA-256 và hàm RIPEMD160 : 

[Public key] -> SHA-256 -> RIPEMD160 -> Hash160 (key 20 bytes)

điều này làm ngắn đi public key và cũng cho thể coi nó là một dạng thô của address

2. Address (P2PKH) : 

1 địa chỉ cơ bản bao gồm : 
 - prefix : để chỉ loại khóa cần tạo , ở đây là [00]
 - hash public key : hash160
 - checksum : để bắt lỗi 

 Và cuối cùng tất cả được chuyển thành base58 để thân thiện hơn với người dùng 
 (base58 là hệ mã mà đã bỏ đi những ký tự giống nhau trong bản chữ la tính và chữ số như số 0 và chữ o ... )

 [00][pubkeyhash160][checksum] -> base58 -> address

 3. Checksum 

 Checksum được tao ra từ data của pubkeyhash160, từ dữ liệu đó thông qua 2 lần SHA-256 và lấy 4 bytes đầu ta được checksum

 4. Về phần View dùng 2 thư viện quan trọng là  Miglayout và TimingFramework 

 > Miglayout : 

- Nó là một thư viện layout để tự động xếp chồng và sắp xếp giao diện người dùng

- MigLayout có thể được sử dụng để tạo bố cục trên cả máy tính để bàn và thiết bị di động.

- Nó cho phép bạn định vị, căn chỉnh và kích thước các thành phần một cách dễ dàng.

- MigLayout sử dụng một cú pháp đơn giản để định vị và căn chỉnh các thành phần, cho phép bạn tạo các bố cục phức tạp mà không cần phải viết mã phức tạp.

- Nó hỗ trợ các tính năng như tự động sắp xếp lại, hỗ trợ kích thước tự động và quản lý thứ tự hiển thị. 

VD 

``` java 
    // Tạo layout sử dụng MigLayout
      setLayout(new MigLayout());
      
      // Thêm nút vào layout
      JButton button = new JButton("Click me!");
      add(button, "cell 0 0, grow");
      
      // Thêm trường văn bản vào layout
      JTextField textField = new JTextField();
      add(textField, "cell 0 1, grow");
```

đây là môt ví dụ về sử dụng miglayout trong đó cell và grow là các từ khóa để chỉ định vị trí thành phần các cách xử lý khi kích thước cửa sổ thay đổi

> TimingFramework : 

Thư viện này cung cấp cách để tạo ra các hoạt động đồng bộ và không đông bộ về mặt thời gian

Các class chính bao gồm  :

- TimingSource: Lớp chịu trách nhiệm đo lường thời gian và cập nhật các hoạt động được kích hoạt trong thư viện.

- Animator: Lớp cung cấp một cách dễ dàng để tạo ra các hoạt động đồng bộ với thời gian cho các thành phần đồ họa trên giao diện người dùng.

- SwingTimer: Lớp cung cấp một cách dễ dàng để tạo ra các hoạt động không đồng bộ với thời gian cho các thành phần đồ họa trên giao diện người dùng.
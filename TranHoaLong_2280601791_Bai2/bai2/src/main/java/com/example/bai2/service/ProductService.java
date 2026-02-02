package com.example.bai2.service;
import com.example.bai2.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final List<Product> listProduct = new ArrayList<>();

    // Lấy tất cả sản phẩm
    public List<Product> getAll() {
        return listProduct;
    }

    // Lấy sản phẩm theo id
    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm sản phẩm mới
    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);

        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }

    // Cập nhật sản phẩm
    public void update(Product editProduct) {
        Product found = get(editProduct.getId());

        if (found != null) {
            found.setName(editProduct.getName());
            found.setPrice(editProduct.getPrice());

            if (editProduct.getImage() != null) {
                found.setImage(editProduct.getImage());
            }
        }
    }

    // Upload & cập nhật hình ảnh
    public void updateImage(Product product, MultipartFile imageProduct) {

        if (imageProduct == null || imageProduct.isEmpty()) {
            return;
        }

        String contentType = imageProduct.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        try {
            // ĐÚNG thư mục bạn tạo
            Path dirImages = Paths.get(
                    "src/main/resources/uploads/images"
            );

            if (!Files.exists(dirImages)) {
                Files.createDirectories(dirImages);
            }

            String newFileName =
                    UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();

            Path pathFileUpload = dirImages.resolve(newFileName);

            Files.copy(
                    imageProduct.getInputStream(),
                    pathFileUpload,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Chỉ lưu tên file
            product.setImage(newFileName);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload hình ảnh", e);
        }
    }

    public void delete(int id) {
        Product found = get(id);

        if (found != null) {

            // Xóa file ảnh nếu có
            if (found.getImage() != null) {
                try {
                    Path imagePath = Paths.get(
                            "src/main/resources/uploads/images",
                            found.getImage()
                    );
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            listProduct.remove(found);
        }
    }
}
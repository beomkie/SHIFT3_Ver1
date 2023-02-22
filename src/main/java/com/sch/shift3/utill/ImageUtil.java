package com.sch.shift3.utill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class ImageUtil {
    static String uploadPath = "src/main/resources/static/ImageFile/";

    public enum Uses {
        SHOP_IMAGE(800),
        SHOP_THUMBNAIL(320),
        ;

        private final int width;


        Uses(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }
    }

    // 이미지 크기 줄이기
    public static byte[] resizeImageFile(MultipartFile files, Uses imageUses){
        try {
            // 이미지 읽어 오기
            if (files.isEmpty())
                throw new NoSuchFieldException("파일이 없습니다.");

            if (Objects.requireNonNull(files.getContentType()).equals("image/gif"))
                return files.getBytes();

            BufferedImage inputImage = ImageIO.read(files.getInputStream());
            // 이미지 세로 가로 측정
            int originWidth = inputImage.getWidth();
            int originHeight = inputImage.getHeight();
            BufferedImage newImage = null;

            if (originWidth <= imageUses.getWidth()) {
                // 이미지 크기가 원하는 크기보다 작을 경우
                return files.getBytes();
            } else {
                // 기존 이미지 비율을 유지하여 세로 길이 설정
                int newHeight = (int) Math.round((originHeight * imageUses.getWidth()) / (double) originWidth);

                //gif 파일일 경우
                // 이미지 품질 설정
                // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
                // Image.SCALE_FAST : 이미지 부드러움보다 속도 우선
                // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
                // Image.SCALE_SMOOTH : 속도보다 이미지 부드러움을 우선
                // Image.SCALE_AREA_AVERAGING : 평균 알고리즘 사용
                Image resizeImage = inputImage.getScaledInstance(imageUses.getWidth(), newHeight, Image.SCALE_SMOOTH);
                newImage = new BufferedImage(imageUses.getWidth(), newHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics graphics = newImage.getGraphics();
                graphics.drawImage(resizeImage, 0, 0, null);
                graphics.dispose();
            }

            // newImage to byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String formatName = "";
            if (files.getOriginalFilename() == null) {
                formatName = "jpg";
            } else {
                formatName = files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".") + 1);
            }

            ImageIO.write(newImage, formatName, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            throw new RuntimeException("이미지를 업로드에 실패하였습니다.");
        }
    }

    public static String upload(MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            // get 확장자 with dot
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = uuid + extension;
            Path imgPath = Paths.get(uploadPath + "/" + fileName);
            File dest = new File(imgPath.toUri());
            file.transferTo(dest);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseEntity<Resource> display(String filename) {
        Resource resource = new FileSystemResource(uploadPath  + filename);
        if(!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(uploadPath + filename);
            header.add("Content-type", Files.probeContentType(filePath));
        }catch(IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

}

